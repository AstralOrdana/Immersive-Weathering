package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WeatheringHelper {



    public static final Supplier<ImmutableMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.AZALEA, Blocks.FLOWERING_AZALEA)
            .put(Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES)
            .put(ModBlocks.AZALEA_LEAF_PILE, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());

    public static final Supplier<Map<Block, Block>> LEAF_PILES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE)
            .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE)
            .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE)
            .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE)
            .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE)
            .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE)
            .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());

    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    /**
     * Grabs block positions around center pos. Order of these is random and depends on current blockpos
     *
     * @param centerPos center pos
     */
    public static List<BlockPos> grabBlocksAroundRandomly(BlockPos centerPos, int radiusX, int radiusY, int radiusZ) {
        var list = BlockPos.streamOutwards(centerPos, radiusX, radiusY, radiusZ)
                .map(BlockPos::new)
                .collect(Collectors.toList());
        //shuffling. provides way better result that iterating through it conventionally
        Collections.shuffle(list, new Random(MathHelper.hashCode(centerPos)));
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
    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, World world, Predicate<BlockState> blockPredicate, int requiredAmount) {

        var lis = grabBlocksAroundRandomly(centerPos, radiusX, radiusY, radiusZ);

        int count = 0;
        for (BlockPos pos : lis) {
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }

        return false;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius, World world,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {
        return hasEnoughBlocksAround(centerPos, radius, radius, radius, world, blockPredicate, requiredAmount);
    }

    //same as before but just checks blocks facing this one (6 in total)
    public static boolean hasEnoughBlocksFacingMe(BlockPos centerPos, World world,
                                                  Predicate<BlockState> blockPredicate, int requiredAmount) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
        Collections.shuffle(list, new Random(MathHelper.hashCode(centerPos)));
        for (Direction dir : list) {
            BlockPos pos = centerPos.offset(dir);
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }
        return false;
    }

    //modified version of hasEnoughBlocksAround for magma block
    public static boolean canMagmaSpread(BlockPos centerPos, int radius, World world, int maximumSize) {
        int count = 0;
        boolean hasLava = false;
        //shuffling. provides way better result that iterating through it conventionally
        var list = grabBlocksAroundRandomly(centerPos, radius, radius, radius);
        for (BlockPos pos : list) {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.MAGMA_BLOCK)) count += 1;
            else {
                var fluid = state.getFluidState();
                if (!hasLava && fluid.isIn(FluidTags.LAVA)) hasLava = true;
                else if (fluid.isIn(FluidTags.WATER)) return false;
            }
            if (count >= maximumSize) return false;
        }
        return hasLava;
    }

    public static boolean isLog(BlockState neighbor) {
        return neighbor.isIn(BlockTags.LOGS) && (!neighbor.contains(PillarBlock.AXIS) ||
                neighbor.get(PillarBlock.AXIS) == Direction.Axis.Y) &&
                !neighbor.getBlock().getName().getString().contains("stripped");
    }

    public static boolean canRootsSpread(BlockPos centerPos, int height, int width, World world, int maximumSize) {
        int count = 0;
        boolean hasRoots = false;
        var list = grabBlocksAroundRandomly(centerPos.up(height/2), width, height, width);
        for (BlockPos pos : list) {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.ROOTED_DIRT)) count += 1;
            else {
                if (!hasRoots && isLog(state)) hasRoots = true;
            }
            if (count >= maximumSize) return false;
        }
        return hasRoots;
    }

    public static boolean isIciclePos(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        return posRandom.nextInt(12) == 0;
    }

    public static void tryPlacingIcicle(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW && WeatheringHelper.isIciclePos(pos)) {
            BlockPos p = pos.down(state.isIn(BlockTags.SNOW) ? 2 : 1);
            BlockState placement = ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);
            if (world.getBlockState(p).isAir() && placement.canPlaceAt(world, p)) {
                if (Direction.Type.HORIZONTAL.stream().anyMatch(d -> {
                    BlockPos rel = p.offset(d);
                    return world.isSkyVisible(rel) && world.getBlockState(rel).isAir();
                })) {
                    world.setBlockState(p, placement, 3);
                }
            }
        }
    }

    public static void onLightningHit(BlockPos centerPos, World world, int rec) {
        BlockState vitrified = ModBlocks.VITRIFIED_SAND.getDefaultState();
        world.setBlockState(centerPos, vitrified, 3);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || world.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.down();
            if (world.getBlockState(downPos).isIn(BlockTags.SAND)) {
                world.setBlockState(centerPos.up(), ModBlocks.FULGURITE.getDefaultState().with(FulguriteBlock.FACING, Direction.UP), 3);
                onLightningHit(downPos, world, rec);
            }
        }
        for (BlockPos target : BlockPos.iterateOutwards(centerPos, 1, 0, 1)) {
            if (world.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (world.getBlockState(target).isIn(BlockTags.SAND)) {
                    onLightningHit(target, world, rec);
                }
            }
        }
    }
}