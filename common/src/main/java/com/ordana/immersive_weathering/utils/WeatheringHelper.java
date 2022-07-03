package com.ordana.immersive_weathering.utils;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.charred.CharredBlock;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.mixins.accessors.BiomeAccessor;
import com.ordana.immersive_weathering.platform.CommonPlatform;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WeatheringHelper {

    public static final Supplier<BiMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA)
                .put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES)
                .put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), ModBlocks.AZALEA_LEAF_PILE.get());

        CommonPlatform.addExtraFloweryBlocks(builder);
        return builder.build();
    });


    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getAzaleaSheared(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Pair<Item, Block>> getBarkForStrippedLog(BlockState log) {
        /*
        var pair = Optional.ofNullable(LeafPilesRegistry.STRIPPED_TO_BARK.get().get(log.getBlock()));

        if (pair.isPresent()) {
            String s = ServerConfigs.GENERIC_BARK.get();
            if (!s.isEmpty()) {
                ResourceLocation res = new ResourceLocation(s);
                if (ForgeRegistries.ITEMS.containsKey(res)) {
                    return Optional.of(Pair.of(ForgeRegistries.ITEMS.getValue(res), pair.get().getSecond()));
                }
            }
        }
        return pair;
        */
        //TODO: re add
        return Optional.empty();
    }

    /**
     * Grabs block positions around center pos. Order of these is random and depends on current blockpos
     *
     * @param centerPos center pos
     */
    public static List<BlockPos> grabBlocksAroundRandomly(BlockPos centerPos, int radiusX, int radiusY, int radiusZ) {
        var list = BlockPos.withinManhattanStream(centerPos, radiusX, radiusY, radiusZ)
                .map(BlockPos::new)
                .collect(Collectors.toList());
        //shuffling. provides way better result that iterating through it conventionally
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
        return list;
    }

    /**
     * optimized version of BlockPos.withinManhattanStream / BlockPos.expandOutwards that tries to limit world.getBlockState calls
     * Remember to call  "if (!level.isAreaLoaded(pos, radius)) return" before calling this
     *
     * @param blockPredicate type of target block
     * @param requiredAmount maximum amount of blocks that we want around this
     * @return true if blocks around that match the given predicate exceed(inclusive) the maximum size given
     */
    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, Level level,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {

        var lis = grabBlocksAroundRandomly(centerPos, radiusX, radiusY, radiusZ);

        int count = 0;
        for (BlockPos pos : lis) {
            if (blockPredicate.test(level.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }

        return false;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius, Level level,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {
        return hasEnoughBlocksAround(centerPos, radius, radius, radius, level, blockPredicate, requiredAmount);
    }

    //same as before but just checks blocks facing this one (6 in total)
    public static boolean hasEnoughBlocksFacingMe(BlockPos centerPos, Level level,
                                                  Predicate<BlockState> blockPredicate, int requiredAmount) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
        for (Direction dir : list) {
            BlockPos pos = centerPos.relative(dir);
            if (blockPredicate.test(level.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }
        return false;
    }

    public static boolean isLog(BlockState state) {
        return state.is(BlockTags.LOGS) && (!state.hasProperty(RotatedPillarBlock.AXIS) ||
                state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y) &&
                !CommonUtils.getID(state.getBlock()).getPath().contains("stripped");
    }

    public static boolean isIciclePos(BlockPos pos) {
        int rarity = CommonConfigs.ICICLE_RARITY.get();
        if (rarity == 1001) return false;
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(rarity) == 0;
    }

    //spawn sooth?
    public static void onFireExpired(ServerLevel serverLevel, BlockPos pos, BlockState state) {
    }

    //called after a block has been devoured by fire. All given blocks are thus flammable
    public static void onFireBurnBlock(Level level, BlockPos pos, BlockState state) {
        if(level instanceof ServerLevel serverLevel) tryCharBlock(serverLevel, pos, state);
    }

    public static boolean tryCharBlock(ServerLevel world, BlockPos pos, BlockState state) {
        double charChance = CommonConfigs.FIRE_CHARS_WOOD_CHANCE.get();
        double ashChance = CommonConfigs.ASH_SPAWNS_CHANCE.get();
        if (charChance != 0 || ashChance != 0) {
            BlockState newState = null;
            BlockState charred = charChance != 0 ? getCharredState(state) : null;
            if (charred != null) {
                if (world.random.nextFloat() < charChance) {
                    newState = charred.setValue(CharredBlock.SMOLDERING, world.random.nextBoolean());
                }
            }
            if (charred == null) {
                if (world.random.nextFloat() < ashChance) {
                    //TODO: set random layer height and do similar to supp??
                    newState = ModBlocks.ASH_LAYER_BLOCK.get().defaultBlockState();
                }
            }
            if (newState != null) {
                world.sendParticles(ModParticles.SOOT.get(),
                        pos.getX() + 0.5D,
                        pos.getY() + 0.5D,
                        pos.getZ() + 0.5D,
                        10,
                        0.5D, 0.5D, 0.5D,
                        0.0D);

                return world.setBlock(pos, newState, 3);
            }
        }
        return false;
    }

    @Nullable
    public static BlockState getCharredState(BlockState state) {
        Block charred = null;
        if (state.is(BlockTags.WOODEN_FENCES)) {
            charred = ModBlocks.CHARRED_FENCE.get();
        } else if (state.is(BlockTags.FENCE_GATES)) {
            charred = ModBlocks.CHARRED_FENCE_GATE.get();
        } else if (state.is(BlockTags.WOODEN_SLABS)) {
            charred = ModBlocks.CHARRED_SLAB.get();
        } else if (state.is(BlockTags.WOODEN_STAIRS)) {
            charred = ModBlocks.CHARRED_STAIRS.get();
        } else if (state.is(BlockTags.PLANKS)) {
            charred = ModBlocks.CHARRED_PLANKS.get();
        } else if (state.is(BlockTags.LOGS_THAT_BURN)) {
            charred = ModBlocks.CHARRED_LOG.get();
        }
        if (charred == null) return null;
        return charred.withPropertiesOf(state);
    }




    //(for fire I think)
    public boolean ashStuff(BlockState state, Level level, BlockPos pos) {
        BlockState downState = level.getBlockState(pos.below());
        if (level.random.nextFloat() > 0.2f) {
            //level.setBlock(pos, ModBlocks.ASH_LAYER_BLOCK.withPropertiesOf(state), 3);
        } else if (downState.is(Blocks.GRASS_BLOCK)) {
            return level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
        }
        return false;
    }


    //TODO: add serene seasons compat
    public static float getTemp(Level level, BlockPos pos) {
        Biome biome = level.getBiome(pos).value();
        return ((BiomeAccessor) (Object) biome).invokeGetTemperature(pos);
    }

    public static boolean isPosWet(Level level, Holder<Biome> biome, BlockPos pos) {
        return biome.is(ModTags.WET);
    }

    public static boolean isPosHot(Level level, Holder<Biome> biome, BlockPos pos) {
        return biome.is(ModTags.HOT);
    }

    public static boolean shouldGetWet(ServerLevel world, BlockPos pos) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);

            if (neighborState.getFluidState().is(FluidTags.WATER)) {
                isTouchingWater = true;
                break;
            }

            if (world.isRainingAt(pos.relative(direction))) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || world.dimensionType().ultraWarm()) {
                temperature++;
            } else if (isPosWet(world, biome, pos)) {
                temperature--;
            } else if (isPosHot(world, biome, pos)) {
                temperature++;
            }
        }
        return temperature < 0 || isTouchingWater;
    }

    public static void applyFreezing(Entity entity, int freezing) {
        applyFreezing(entity, freezing, false);
    }

    public static void applyFreezing(Entity entity, int freezing, boolean inWater) {
       if(entity instanceof Player){
           int a = 1; //TODO: this isnt working
       }
        if (freezing != 0 && entity.canFreeze() && (entity instanceof LivingEntity le) &&
                !(EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, le) > 0) &&
                !entity.getType().is(ModTags.LIGHT_FREEZE_IMMUNE)) {
            if (inWater) {
                if (le.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)) return;
            } else {
                if (le.hasEffect(MobEffects.CONDUIT_POWER)) return;
            }
            entity.setTicksFrozen(freezing);
        }
    }

    public static void growHangingRoots(ServerLevel world, Random random, BlockPos pos) {
        Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos targetPos = pos.relative(dir);
        BlockState targetState = world.getBlockState(targetPos);
        if (targetState.isAir()) return;
        BlockState newState = dir == Direction.DOWN ? Blocks.HANGING_ROOTS.defaultBlockState() :
                ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
        if (targetState.is(Blocks.WATER)) {
            newState = newState.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        world.setBlockAndUpdate(targetPos, newState);
    }



}
