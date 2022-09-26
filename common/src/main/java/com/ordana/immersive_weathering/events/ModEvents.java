package com.ordana.immersive_weathering.events;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.Weatherable;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.LevelRenderer;
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
import java.util.HashMap;
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
    private static final HashMap<Block, Block> RUSTED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> STRIPPED_BLOCKS = new HashMap<>();

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

        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON.get(), ModBlocks.EXPOSED_CUT_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON.get(), ModBlocks.WEATHERED_CUT_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON.get(), ModBlocks.RUSTED_CUT_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_STAIRS.get(), ModBlocks.EXPOSED_CUT_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_STAIRS.get(), ModBlocks.WEATHERED_CUT_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_STAIRS.get(), ModBlocks.RUSTED_CUT_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_SLAB.get(), ModBlocks.EXPOSED_CUT_IRON_SLAB.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_SLAB.get(), ModBlocks.WEATHERED_CUT_IRON_SLAB.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_SLAB.get(), ModBlocks.RUSTED_CUT_IRON_SLAB.get());
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON.get(), ModBlocks.EXPOSED_PLATE_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON.get(), ModBlocks.WEATHERED_PLATE_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON.get(), ModBlocks.RUSTED_PLATE_IRON.get());
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_STAIRS.get(), ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get(), ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get(), ModBlocks.RUSTED_PLATE_IRON_STAIRS.get());
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_SLAB.get(), ModBlocks.EXPOSED_PLATE_IRON_SLAB.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_SLAB.get(), ModBlocks.WEATHERED_PLATE_IRON_SLAB.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_SLAB.get(), ModBlocks.RUSTED_PLATE_IRON_SLAB.get());
        RUSTED_BLOCKS.put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_DOOR.get(), ModBlocks.WEATHERED_IRON_DOOR.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_DOOR.get(), ModBlocks.RUSTED_IRON_DOOR.get());
        RUSTED_BLOCKS.put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), ModBlocks.WEATHERED_IRON_TRAPDOOR.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), ModBlocks.RUSTED_IRON_TRAPDOOR.get());
        RUSTED_BLOCKS.put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS.get());
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_BARS.get(), ModBlocks.WEATHERED_IRON_BARS.get());
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_BARS.get(), ModBlocks.RUSTED_IRON_BARS.get());

        STRIPPED_BLOCKS.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        STRIPPED_BLOCKS.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        STRIPPED_BLOCKS.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        STRIPPED_BLOCKS.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        STRIPPED_BLOCKS.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        STRIPPED_BLOCKS.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        STRIPPED_BLOCKS.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        STRIPPED_BLOCKS.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        STRIPPED_BLOCKS.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
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
            if (state.is(ModTags.RUSTABLE)) {
                level.playSound(player, pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.SPONGE.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);

                    level.setBlockAndUpdate(pos, RUSTED_BLOCKS.get(state.getBlock()).withPropertiesOf(state));
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult rustScraping(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (CommonConfigs.AXE_SCRAPING.get() && state.getBlock() instanceof Rustable rustable) {
            Rustable.RustLevel rustLevel = rustable.getAge();

            if(item instanceof AxeItem) {
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

                if (state.is(ModTags.EXPOSED_IRON)) {
                    ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);

                    if (player instanceof ServerPlayer) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                        //todo
                        //if (IntegrationHandler.quark) s = QuarkPlugin.fixVerticalSlab(s, state);
                        player.awardStat(Stats.ITEM_USED.get(item));
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (state.is(rusty)) {
                                level.setBlockAndUpdate(pos, clean.withPropertiesOf(state));
                            }
                        });
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
            else if (item == ModItems.STEEL_WOOL.get()) {
                if (rustLevel != Rustable.RustLevel.RUSTED) {
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
                Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModBlocks.SOOT.get()));
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

                Block.popResourceFromFace(level, pos, hitResult.getDirection(), Items.SLIME_BALL.getDefaultInstance());
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
                if (!player.getAbilities().instabuild) {
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
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                    }
                }
            }
            //moss shearing
            if (newState == null && CommonConfigs.MOSS_SHEARING.get()) {
                newState = Mossable.getUnaffectedMossBlock(state);
                if (newState != state) {
                    if (IntegrationHandler.quark) newState = QuarkPlugin.fixVerticalSlab(newState, state);
                    if (!level.isClientSide) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModItems.MOSS_CLUMP.get()));
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

                var barkParticle = ModParticles.OAK_BARK.get();
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, barkParticle, UniformInt.of(3, 5));

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }

                STRIPPED_BLOCKS.forEach((raw, stripped) -> {
                    if (state.is(raw)) {
                        level.setBlockAndUpdate(pos, stripped.withPropertiesOf(state));
                    }
                });

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

}