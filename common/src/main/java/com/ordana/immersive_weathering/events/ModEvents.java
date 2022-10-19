package com.ordana.immersive_weathering.events;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.blocks.Weatherable;
import com.ordana.immersive_weathering.blocks.charred.CharredBlock;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.blocks.soil.MulchBlock;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import com.ordana.immersive_weathering.reg.*;
import net.mehvahdjukaar.moonlight.api.events.IFireConsumeBlockEvent;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;


public class ModEvents {

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
                var rusted = rustable.getNext(state);

                if (rusted.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (CommonConfigs.SPONGE_RUST_DRYING.get()) {
                            ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.SPONGE.getDefaultInstance());
                            player.setItemInHand(hand, itemStack2);
                        }
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
        if (CommonConfigs.AXE_SCRAPING.get()) {

            if (item instanceof AxeItem && state.getBlock() instanceof Rustable rustable && !state.is(ModTags.WAXED_BLOCKS)) {
                Rustable.RustLevel rustLevel = rustable.getAge();
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
                if (state.getBlock() instanceof Rustable rustable && !state.is(ModTags.WAXED_BLOCKS)) {
                    Rustable.RustLevel rustLevel = rustable.getAge();
                    if (rustLevel != Rustable.RustLevel.UNAFFECTED) {
                        level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                            player.awardStat(Stats.ITEM_USED.get(item));
                            level.setBlockAndUpdate(pos, rustable.getPrevious(state).get());
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }

                if (state.is(ModTags.WAXED_BLOCKS)) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.WAX_OFF, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        level.setBlockAndUpdate(pos, WeatheringHelper.getUnwaxedBlock(state).orElse(null));
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
                if (state.is(ModTags.COPPER)) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        WeatheringCopper.getPrevious(state).ifPresent(o -> level.setBlockAndUpdate(pos, o));
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
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
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                    Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModBlocks.ASH_LAYER_BLOCK.get()));
                }
                //no need to cancel
            }
            return InteractionResult.PASS;
        }
        if (item == Items.APPLE) {
            if (state.getBlock() instanceof MulchBlock) {
                level.playSound(player, pos, ModSoundEvents.YUMMY.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.PASS;
        }
        if (item == Items.GOLDEN_APPLE) {
            if (state.getBlock() instanceof MulchBlock) {
                level.playSound(player, pos, ModSoundEvents.YUMMY.get(), SoundSource.BLOCKS, 1.0f, 2.0f);
            }
            return InteractionResult.PASS;
        }
        if (item == Items.ENCHANTED_GOLDEN_APPLE) {
            if (state.getBlock() instanceof MulchBlock) {
                level.playSound(player, pos, ModSoundEvents.YUMMY.get(), SoundSource.BLOCKS, 2.0f, 0.3f);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult slimePistons(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item == Items.SLIME_BALL && CommonConfigs.PISTON_SLIMING.get() && state.is(Blocks.PISTON) && !state.getValue(PistonBaseBlock.EXTENDED)) {
            if (level.isClientSide) {
                level.playSound(player, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
            } else {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.setBlockAndUpdate(pos, Blocks.STICKY_PISTON.withPropertiesOf(state));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.PASS;
        }

        if (item == Items.SHEARS && CommonConfigs.PISTON_SLIMING.get() && state.is(Blocks.STICKY_PISTON) && !state.getValue(PistonBaseBlock.EXTENDED)) {
            if (level.isClientSide) {
                level.playSound(player, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
            } else {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                level.setBlockAndUpdate(pos, Blocks.PISTON.withPropertiesOf(state));
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                    Block.popResourceFromFace(level, pos, hitResult.getDirection(), Items.SLIME_BALL.getDefaultInstance());
                }
            }
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
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
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
                    if (level.isClientSide) {
                        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.MOSS.get(), UniformInt.of(3, 5));
                    } else {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                            Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.MOSS_CLUMP.get()));
                        }
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


    private static InteractionResult burnMoss(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                              Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        if (item instanceof FlintAndSteelItem && CommonConfigs.MOSS_BURNING.get()) {
            BlockState newBlock = Mossable.getUnaffectedMossBlock(state);
            if (newBlock != state) {
                newBlock = Weatherable.setStable(newBlock);
                if (level.isClientSide) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.FLAME, UniformInt.of(3, 5));
                    level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                }
            }
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
                if (level.isClientSide) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));
                    level.playSound(player, pos, newBlock.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    if (state.getBlock() instanceof Crackable crackable) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        level.setBlockAndUpdate(pos, newBlock);
                        if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                            Block.popResourceFromFace(level, pos, hitResult.getDirection(), crackable.getRepairItem(state).getDefaultInstance());
                        }
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    //well this could very well be in each crackable classes...
    private static InteractionResult brickRepair(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
            player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getBlock() instanceof Crackable crackable && crackable.getRepairItem(state) == item && state.is(ModTags.CRACKED)) {
            BlockState newBlock = Crackable.getUncrackedCrackBlock(state);
            if (newBlock != null) {
                newBlock = Weatherable.setStable(newBlock);
                if (IntegrationHandler.quark) newBlock = QuarkPlugin.fixVerticalSlab(newBlock, state);
                if (level.isClientSide) {
                    level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
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
                if (level.isClientSide) {
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    var barkParticle = WeatheringHelper.getBarkParticle(state);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, barkParticle, UniformInt.of(3, 5));
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), bark.getDefaultInstance());
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }


    private static InteractionResult barkRepairing(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
            player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ModTags.BARK)) {
            Pair<Item, Block> fixedLog = WeatheringHelper.getBarkForStrippedLog(state).orElse(null);

            if (fixedLog != null && stack.getItem() == fixedLog.getFirst()) {
                BlockState newBlock = fixedLog.getSecond().withPropertiesOf(state);
                if (IntegrationHandler.quark) newBlock = QuarkPlugin.fixVerticalSlab(newBlock, state);
                if (level.isClientSide) {
                    level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    @EventCalled
    public static boolean onFireConsume(IFireConsumeBlockEvent event) {
        var level = event.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            var pos = event.getPos();
            var state = event.getState();
            double charChance = CommonConfigs.FIRE_CHARS_WOOD_CHANCE.get();
            double ashChance = CommonConfigs.ASH_SPAWNS_CHANCE.get();
            if (charChance != 0 || ashChance != 0) {
                BlockState newState = null;
                BlockState charred = charChance != 0 ? WeatheringHelper.getCharredState(state) : null;
                if (charred != null) {
                    if (serverLevel.random.nextFloat() < charChance) {
                        newState = charred.setValue(CharredBlock.SMOLDERING, serverLevel.random.nextBoolean());
                    }
                }
                if (charred == null) {
                    if (serverLevel.random.nextFloat() < ashChance) {
                        //TODO: set random layer height and do similar to supp??
                        newState = ModBlocks.ASH_LAYER_BLOCK.get().defaultBlockState();
                    }
                }
                if (newState != null) {
                    serverLevel.sendParticles(ModParticles.SOOT.get(),
                            pos.getX() + 0.5D,
                            pos.getY() + 0.5D,
                            pos.getZ() + 0.5D,
                            10,
                            0.5D, 0.5D, 0.5D,
                            0.0D);

                    return serverLevel.setBlock(pos, newState, 3);
                }
            }
        }
        return false;
    }

}