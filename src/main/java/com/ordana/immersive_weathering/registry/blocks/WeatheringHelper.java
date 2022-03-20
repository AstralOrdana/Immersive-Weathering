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


    //TODO: maybe finish this
    private static final Supplier<Map<RegistryKey<Biome>, DataPool<Block>>> BIOME_FLOWERS = Suppliers.memoize(() -> ImmutableMap.<RegistryKey<Biome>, DataPool<Block>>builder()
            .put(BiomeKeys.PLAINS, DataPool.<Block>builder()
                    .add(Blocks.GRASS, 300)
                    .add(Blocks.DANDELION, 50)
                    .add(ModBlocks.WEEDS, 50)
                    .add(Blocks.AZURE_BLUET, 50)
                    .add(Blocks.PUMPKIN, 1)
                    .build())
            .put(BiomeKeys.SWAMP, DataPool.<Block>builder()
                    .add(Blocks.GRASS, 100)
                    .add(Blocks.BLUE_ORCHID, 50)
                    .build())
            .build());

    private static final Supplier<Map<Block, DataPool<Block>>> NETHER_VEGETATION = Suppliers.memoize(() -> ImmutableMap.<Block, DataPool<Block>>builder()
            .put(Blocks.CRIMSON_NYLIUM, DataPool.<Block>builder()
                    .add(Blocks.CRIMSON_ROOTS, 20)
                    .add(Blocks.CRIMSON_FUNGUS, 5)
                    .build())
            .put(Blocks.WARPED_NYLIUM, DataPool.<Block>builder()
                    .add(Blocks.WARPED_ROOTS, 20)
                    .add(Blocks.WARPED_FUNGUS, 5)
                    .add(Blocks.NETHER_SPROUTS, 10)
                    .build())
            .build());

    public record CoralFamily(Block coral, Block fan, Block wallFan) {
    }

    private static final Supplier<ImmutableMap<Block, CoralFamily>> CORALS = Suppliers.memoize(() -> ImmutableMap.<Block, CoralFamily>builder()
            .put(Blocks.BRAIN_CORAL_BLOCK, new CoralFamily(Blocks.BRAIN_CORAL, Blocks.BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN))
            .put(Blocks.BUBBLE_CORAL_BLOCK, new CoralFamily(Blocks.BUBBLE_CORAL, Blocks.BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN))
            .put(Blocks.FIRE_CORAL_BLOCK, new CoralFamily(Blocks.FIRE_CORAL, Blocks.FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN))
            .put(Blocks.TUBE_CORAL_BLOCK, new CoralFamily(Blocks.TUBE_CORAL, Blocks.TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN))
            .put(Blocks.HORN_CORAL_BLOCK, new CoralFamily(Blocks.HORN_CORAL, Blocks.HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN))
            .build());

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

    public static final DataPool<Direction> ROOT_DIRECTIONS =
            DataPool.<Direction>builder()
                    .add(Direction.NORTH, 5)
                    .add(Direction.SOUTH, 5)
                    .add(Direction.WEST, 5)
                    .add(Direction.EAST, 5)
                    .add(Direction.UP, 1)
                    .add(Direction.DOWN, 20)
                    .build();

    public static Optional<CoralFamily> getCoralGrowth(BlockState baseBlock) {
        return Optional.ofNullable(CORALS.get().get(baseBlock.getBlock()));
    }

    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    public static Optional<Block> getGrassGrowthForBiome(RegistryKey<Biome> biome, Random random) {
        var list = BIOME_FLOWERS.get().get(biome);
        if (list != null) return list.getDataOrEmpty(random);
        return Optional.empty();
    }

    public static Optional<Block> getNyliumGrowth(BlockState state, Random random) {
        var list = NETHER_VEGETATION.get().get(state.getBlock());
        if (list != null) return list.getDataOrEmpty(random);
        return Optional.empty();
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

}