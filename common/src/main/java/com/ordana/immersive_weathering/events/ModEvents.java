package com.ordana.immersive_weathering.events;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.blocks.charred.CharredBlock;
import com.ordana.immersive_weathering.blocks.cracked.Crackable;
import com.ordana.immersive_weathering.blocks.mossy.Mossable;
import com.ordana.immersive_weathering.blocks.rusty.Rustable;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.blocks.snowy.Snowy;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.*;
import com.ordana.immersive_weathering.util.Weatherable;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.mehvahdjukaar.moonlight.api.client.util.ParticleUtil;
import net.mehvahdjukaar.moonlight.api.events.IFireConsumeBlockEvent;
import net.mehvahdjukaar.moonlight.api.events.ILightningStruckBlockEvent;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        EVENTS.add(ModEvents::pickaxeCracking);
        EVENTS.add(ModEvents::brickRepair);
        EVENTS.add(ModEvents::burnMoss);
        EVENTS.add(ModEvents::shearShearing);
        EVENTS.add(ModEvents::axeStripping);
        EVENTS.add(ModEvents::barkRepairing);
        EVENTS.add(ModEvents::rustScraping);
        EVENTS.add(ModEvents::rustSponging);
        EVENTS.add(ModEvents::blockSanding);
        EVENTS.add(ModEvents::blockSnowing);
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
                    ParticleUtil.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SMOKE, UniformInt.of(3, 5), -0.05f, 0.05f, false);
                    if (player instanceof ServerPlayer serverPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        player.awardStat(Stats.ITEM_USED.get(item));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger( serverPlayer, pos, stack);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }

                if (rustLevel != Rustable.RustLevel.UNAFFECTED) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);

                    if (player instanceof ServerPlayer serverPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
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
                        if (player instanceof ServerPlayer serverPlayer) {
                            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                            player.awardStat(Stats.ITEM_USED.get(item));
                            level.setBlockAndUpdate(pos, rustable.getPrevious(state).get());
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }

                if (state.is(ModTags.WAXED_BLOCKS)) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.WAX_OFF, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer serverPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        level.setBlockAndUpdate(pos, Objects.requireNonNull(ModWaxables.getUnWaxed(state).orElse(null)));
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
                if (state.is(ModTags.COPPER)) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer serverPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        WeatheringCopper.getPrevious(state).ifPresent(o -> level.setBlockAndUpdate(pos, o));
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
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
                        ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.GRAVITY_AZALEA_FLOWER.get(), UniformInt.of(4, 6));
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

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger( serverPlayer, pos, stack);
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
                    ParticleUtil.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.FLAME, UniformInt.of(3, 5), -0.05f, 0.05f, false);
                    level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
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
                            if (CommonConfigs.CRACKING_DROPS_BRICK.get()) Block.popResourceFromFace(level, pos, hitResult.getDirection(), crackable.getRepairItem(state).getDefaultInstance());
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
        if (state.getBlock() instanceof Crackable crackable && state.is(ModTags.CRACKED) && (crackable.getRepairItem(state) == item || item.getDefaultInstance().is(ModItems.MORTAR.get()))) {
            BlockState newBlock = Crackable.getUncrackedCrackBlock(state);
            if (newBlock != null) {
                newBlock = Weatherable.setStable(newBlock);
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
            Item bark = WeatheringHelper.getBarkToStrip(state);
            if (bark != null) {
                if (level.isClientSide) {
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    var barkParticle = new BlockParticleOption(ParticleTypes.BLOCK, state);
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
            Pair<Item, Block> woodFromLog = WeatheringHelper.getWoodFromLog(state).orElse(null);

            if (fixedLog != null && stack.getItem() == fixedLog.getFirst()) {
                BlockState newBlock = fixedLog.getSecond().withPropertiesOf(state);
                if (level.isClientSide) {
                    level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            else if (woodFromLog != null && stack.getItem() == woodFromLog.getFirst()) {
                if (hitResult.getDirection().getAxis() == state.getValue(BlockStateProperties.AXIS)) {
                    BlockState newBlock = woodFromLog.getSecond().withPropertiesOf(state);
                    if (level.isClientSide) {
                        level.playSound(player, pos, newBlock.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    } else {
                        if (!player.getAbilities().instabuild) stack.shrink(1);
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        level.setBlockAndUpdate(pos, newBlock);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);

                }
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult blockSanding(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
        player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        var sandy = Sandy.getSandy(state);
        if (stack.is(ModBlocks.SAND_LAYER_BLOCK.get().asItem()) && (sandy.isPresent() ||
            (state.getBlock() instanceof Sandy && state.getValue(ModBlockProperties.SANDINESS) == 0))) {
            level.playSound(player, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer serverPlayer) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                if (sandy.isPresent()) {
                    level.setBlockAndUpdate(pos, sandy.get());
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SANDINESS, 1));
                }
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult blockSnowing(Item item, ItemStack stack, BlockPos pos, BlockState state, Player
        player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        var snowy = Snowy.getSnowy(state);
        if (stack.is(Items.SNOWBALL) && snowy.isPresent()) {
            level.playSound(player, pos, SoundEvents.SNOW_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SNOW_BLOCK.defaultBlockState()), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                if (!player.getAbilities().instabuild) stack.shrink(1);
                level.setBlockAndUpdate(pos, snowy.get());
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @EventCalled
    public static void onLightningHit(ILightningStruckBlockEvent event) {
        BlockPos blockPos = event.getPos();
        LevelAccessor level = event.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        BlockGrowthHandler.tickBlock(TickSource.LIGHTNING, blockState, (ServerLevel) level, blockPos);
    }

    @EventCalled
    public static void onFireConsume(IFireConsumeBlockEvent event) {
        var level = event.getLevel();
        if (level instanceof ServerLevel serverLevel) {

            var state = event.getState();
            double charChance = CommonConfigs.FIRE_CHARS_WOOD_CHANCE.get();
            BlockState charred = WeatheringHelper.getCharredState(state);
            if (charChance == 0 || charred == null) return;

            if (serverLevel.random.nextFloat() < charChance) {
                event.setFinalState(charred.setValue(CharredBlock.SMOLDERING, serverLevel.random.nextBoolean()));
            }

        }
    }

}