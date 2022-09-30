package com.ordana.immersive_weathering.events;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.blocks.Weatherable;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.blocks.soil.MulchBlock;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;


public class ModEvents {

//TODO: missing events:wet sponge

    @FunctionalInterface
    public interface InteractionEvent {
        InteractionResult run(Item i, ItemStack stack,
                              BlockPos pos,
                              BlockState state,
                              Player player, Level level,
                              InteractionHand hand,
                              BlockHitResult hit);
    }

    private static final List<InteractionEvent> EVENTS = new ArrayList<>();

    static {
        EVENTS.add(ModEvents::slimePistons);
        EVENTS.add(ModEvents::pickaxeCracking);
        EVENTS.add(ModEvents::brickRepair);
        EVENTS.add(ModEvents::burnMoss);
        EVENTS.add(ModEvents::shearShearing);
        EVENTS.add(ModEvents::spawnAsh);
        EVENTS.add(ModEvents::grassFlinting);
        EVENTS.add(ModEvents::axeStripping);
        EVENTS.add(ModEvents::barkRepairing);
        EVENTS.add(ModEvents::rustScraping);
        EVENTS.add(ModEvents::rustSponging);
    }


    public static InteractionResult onBlockCLicked(ItemStack stack, Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) return InteractionResult.PASS;
        Item i = stack.getItem();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        for (var event : EVENTS) {
            var result = event.run(i, stack, pos, state, player, level, hand, hitResult);
            if (result != InteractionResult.PASS) return result;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult rustSponging(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item == Items.WET_SPONGE && CommonConfigs.SPONGE_RUSTING.get()) {
            if (state.getBlock() instanceof Rustable rustable) {
                var rusted = rustable.getPrevious(state);

                if (rusted.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer serverPlayer) {
                        ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.SPONGE.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);

                        level.setBlockAndUpdate(pos, rusted.get());
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult rustScraping(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (CommonConfigs.AXE_SCRAPING.get() && state.getBlock() instanceof Rustable rustable) {
            Rustable.RustLevel rustLevel = rustable.getAge();

            if (item instanceof AxeItem) {
                if (!rustLevel.canScrape()) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.playSound(player, pos, SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        player.awardStat(Stats.ITEM_USED.get(item));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                    return InteractionResult.SUCCESS;
                }

                if (rustLevel != Rustable.RustLevel.UNAFFECTED) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);

                    if (player instanceof ServerPlayer serverPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        //todo
                        //if (IntegrationHandler.quark) s = QuarkPlugin.fixVerticalSlab(s, state);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        level.setBlockAndUpdate(pos, rustable.getPrevious(state).get());
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            } else if (item == ModItems.STEEL_WOOL.get()) {
                if (rustLevel != Rustable.RustLevel.UNAFFECTED) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        level.setBlockAndUpdate(pos, Rustable.getUnaffectedRustState(state));
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult burnMoss(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                              Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof FlintAndSteelItem && CommonConfigs.MOSS_BURNING.get()) {
            BlockState s = Mossable.getUnaffectedMossBlock(state);
            if (s != state) {
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.FLAME, UniformInt.of(3, 5));
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);

                if (player instanceof ServerPlayer) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                    s = Weatherable.setStable(s);
                    if (IntegrationHandler.quark) s = QuarkPlugin.fixVerticalSlab(s, state);
                    player.awardStat(Stats.ITEM_USED.get(item));
                    level.setBlockAndUpdate(pos, s);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult grassFlinting(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                   Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item == Items.FLINT && CommonConfigs.GRASS_FLINTING.get()) {
            if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.MYCELIUM) || state.is(Blocks.PODZOL) || state.is(ModBlocks.HUMUS.get()) || state.is(ModBlocks.FLUVISOL.get())) {
                level.playSound(player, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.SUCCESS;
            }
            if (state.is(Blocks.DIRT)) {
                level.playSound(player, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.setBlockAndUpdate(pos, Blocks.COARSE_DIRT.defaultBlockState());
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult spawnAsh(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                              Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof ShovelItem && CommonConfigs.ASH_ITEM_SPAWN.get()) {
            if (state.getBlock() instanceof CampfireBlock && state.getValue(BlockStateProperties.LIT)) {
                if (         !player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                    Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModBlocks.SOOT.get()));
                }
                //no need to cancel
            }
            return InteractionResult.PASS;
        }
        if (item == Items.APPLE) {
            if (state.getBlock() instanceof MulchBlock) {
                level.playSound(player, pos, ModSoundEvents.YUMMY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                //no need to cancel
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult slimePistons(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item == Items.SLIME_BALL && CommonConfigs.PISTON_SLIMING.get()) {
            if (state.is(Blocks.PISTON) && !state.getValue(PistonBaseBlock.EXTENDED)) {
                level.playSound(player, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.setBlockAndUpdate(pos, Blocks.STICKY_PISTON.withPropertiesOf(state));
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        if (item == Items.SHEARS && CommonConfigs.PISTON_SLIMING.get()) {
            if (state.is(Blocks.STICKY_PISTON) && !state.getValue(PistonBaseBlock.EXTENDED)) {
                level.playSound(player, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
                stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                level.setBlockAndUpdate(pos, Blocks.PISTON.withPropertiesOf(state));

                if (         !player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                    Block.popResourceFromFace(level, pos, hitResult.getDirection(), Items.SLIME_BALL.getDefaultInstance());
                }
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }


    //maybe put this into crackable blocks. has the advantage here that it can work on modded blocks we dont own
    private static InteractionResult pickaxeCracking(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                     Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof PickaxeItem && CommonConfigs.PICKAXE_CRACKING.get()) {
            if (!player.isSecondaryUseActive() && CommonConfigs.PICKAXE_CRACKING_SHIFT.get())
                return InteractionResult.PASS;

            BlockState newBlock = Crackable.getCrackedBlock(state);
            if (newBlock != state) {
                if (IntegrationHandler.quark) newBlock = QuarkPlugin.fixVerticalSlab(newBlock, state);
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                    if (state.getBlock() instanceof Crackable crackable) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), crackable.getRepairItem(state).getDefaultInstance());
                    }
                }
                level.playSound(player, pos, newBlock.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));

                if (player instanceof ServerPlayer) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));

                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    //well this could very well be in each crackable classes...
    private static InteractionResult brickRepair(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
            player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getBlock() instanceof Crackable crackable && crackable.getRepairItem(state) == item) {
            //Fix cracked stuff
            BlockState fixedBlock = crackable.getPreviousCracked(state).orElse(null);
            if (fixedBlock != null) {

                //fixing stuff prevents them from weathering
                fixedBlock = Weatherable.setStable(fixedBlock);
                if (IntegrationHandler.quark) fixedBlock = QuarkPlugin.fixVerticalSlab(fixedBlock, state);

                SoundEvent placeSound = fixedBlock.getSoundType().getPlaceSound();
                level.playSound(player, pos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);


                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, fixedBlock);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult barkRepairing(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
            player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        Pair<Item, Block> fixedLog = WeatheringHelper.getBarkForStrippedLog(state).orElse(null);
        if (fixedLog != null && stack.getItem() == fixedLog.getFirst()) {
            BlockState fixedState = fixedLog.getSecond().withPropertiesOf(state);

            level.playSound(player, pos, fixedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

            if (player != null) {
                if (!player.isCreative()) stack.shrink(1);
            }

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                player.awardStat(Stats.ITEM_USED.get(item));
            }

            level.setBlockAndUpdate(pos, fixedState);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult shearShearing(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                   Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        if (item instanceof ShearsItem) {
            //azalea shearing
            BlockState newState = null;
            if (CommonConfigs.AZALEA_SHEARING.get()) {
                newState = WeatheringHelper.getAzaleaSheared(state).orElse(null);
                if (newState != null) {
                    if (level.isClientSide) {
                        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.AZALEA_FLOWER.get(), UniformInt.of(4, 6));
                    } else {
                        if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                            Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                        }
                    }
                }
            }
            //moss shearing
            if (newState == null && CommonConfigs.MOSS_SHEARING.get()) {
                newState = Mossable.getUnaffectedMossBlock(state);
                if (newState != state) {
                    if (IntegrationHandler.quark) newState = QuarkPlugin.fixVerticalSlab(newState, state);
                    if (!level.isClientSide) {
                        if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                            Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.MOSS_CLUMP.get()));
                        }
                    } else {
                        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.MOSS.get(), UniformInt.of(3, 5));
                    }
                } else newState = null;
            }
            //common logic
            if (newState != null) {
                level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlockAndUpdate(pos, newState);

                stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(item));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult axeStripping(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof AxeItem && CommonConfigs.AXE_STRIPPING.get()) {
            var bark = WeatheringHelper.getBarkToStrip(state);
            if (bark != null) {

                Block.popResourceFromFace(level, pos, hitResult.getDirection(), bark.getDefaultInstance());
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

                var barkParticle = WeatheringHelper.getBarkParticle(state).orElse(new BlockParticleOption(ParticleTypes.BLOCK, state));
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, barkParticle, UniformInt.of(3, 5));

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
            }
        }
        return InteractionResult.PASS;
    }

}