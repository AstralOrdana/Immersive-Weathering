package com.ordana.immersive_weathering.common.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WeatheringHelper {


    //TODO: maybe finish this
    private static final Supplier<Map<ResourceKey<Biome>, SimpleWeightedRandomList<Block>>> BIOME_FLOWERS = Suppliers.memoize(() -> ImmutableMap.<ResourceKey<Biome>, SimpleWeightedRandomList<Block>>builder()
            .put(Biomes.PLAINS, SimpleWeightedRandomList.<Block>builder()
                    .add(Blocks.GRASS, 300)
                    .add(Blocks.DANDELION, 50)
                    .add(ModBlocks.WEEDS.get(), 50)
                    .add(Blocks.AZURE_BLUET, 50)
                    .add(Blocks.PUMPKIN, 1)
                    .build())
            .put(Biomes.SWAMP, SimpleWeightedRandomList.<Block>builder()
                    .add(Blocks.GRASS, 100)
                    .add(Blocks.BLUE_ORCHID, 50)
                    .build())
            .build());

    private static final Supplier<Map<Block, SimpleWeightedRandomList<Block>>> NETHER_VEGETATION = Suppliers.memoize(() -> ImmutableMap.<Block, SimpleWeightedRandomList<Block>>builder()
            .put(Blocks.CRIMSON_NYLIUM, SimpleWeightedRandomList.<Block>builder()
                    .add(Blocks.CRIMSON_ROOTS, 20)
                    .add(Blocks.CRIMSON_FUNGUS, 15)
                    .build())
            .put(Blocks.WARPED_NYLIUM, SimpleWeightedRandomList.<Block>builder()
                    .add(Blocks.WARPED_ROOTS, 20)
                    .add(Blocks.WARPED_FUNGUS, 15)
                    .add(Blocks.NETHER_SPROUTS, 10)
                    .build())
            .build());

    public record CoralFamily(Block coral, Block fan, Block wallFan) {
    }

    private static final Supplier<Map<Block, CoralFamily>> CORALS = Suppliers.memoize(() -> ImmutableMap.<Block, CoralFamily>builder()
            .put(Blocks.BRAIN_CORAL_BLOCK, new CoralFamily(Blocks.BRAIN_CORAL, Blocks.BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN))
            .put(Blocks.BUBBLE_CORAL_BLOCK, new CoralFamily(Blocks.BUBBLE_CORAL, Blocks.BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN))
            .put(Blocks.FIRE_CORAL_BLOCK, new CoralFamily(Blocks.FIRE_CORAL, Blocks.FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN))
            .put(Blocks.TUBE_CORAL_BLOCK, new CoralFamily(Blocks.TUBE_CORAL, Blocks.TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN))
            .put(Blocks.HORN_CORAL_BLOCK, new CoralFamily(Blocks.HORN_CORAL, Blocks.HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN))
            .build());

    public static final Supplier<BiMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA)
                .put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES)
                .put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), ModBlocks.AZALEA_LEAF_PILE.get());
        if (ModList.get().isLoaded("quark")) {
            Block a = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_hedge"));
            Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_hedge"));
            if (a != null && b != null) {
                builder.put(a, b);
            }
            Block c = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_leaf_carpet"));
            Block d = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_leaf_carpet"));
            if (c != null && d != null) {
                builder.put(c, d);
            }
        }
        return builder.build();
    });

    public static final Supplier<Map<Block, Block>> LEAF_PILES = Suppliers.memoize(() ->
            ImmutableMap.<Block, Block>builder()
                    .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE.get())
                    .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE.get())
                    .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE.get())
                    .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE.get())
                    .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE.get())
                    .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE.get())
                    .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE.get())
                    .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get())

                    .build()
    );

    public static final Supplier<Map<Block, Pair<Item, Block>>> STRIPPED_TO_BARK = Suppliers.memoize(() -> ImmutableMap.<Block, Pair<Item, Block>>builder()
            .put(Blocks.STRIPPED_OAK_LOG, Pair.of(ModItems.OAK_BARK.get(), Blocks.OAK_LOG))
            .put(Blocks.STRIPPED_OAK_WOOD, Pair.of(ModItems.OAK_BARK.get(), Blocks.OAK_WOOD))
            .put(Blocks.STRIPPED_BIRCH_LOG, Pair.of(ModItems.BIRCH_BARK.get(), Blocks.BIRCH_LOG))
            .put(Blocks.STRIPPED_BIRCH_WOOD, Pair.of(ModItems.BIRCH_BARK.get(), Blocks.BIRCH_WOOD))
            .put(Blocks.STRIPPED_SPRUCE_LOG, Pair.of(ModItems.SPRUCE_BARK.get(), Blocks.SPRUCE_LOG))
            .put(Blocks.STRIPPED_SPRUCE_WOOD, Pair.of(ModItems.SPRUCE_BARK.get(), Blocks.SPRUCE_WOOD))
            .put(Blocks.STRIPPED_JUNGLE_LOG, Pair.of(ModItems.JUNGLE_BARK.get(), Blocks.JUNGLE_LOG))
            .put(Blocks.STRIPPED_JUNGLE_WOOD, Pair.of(ModItems.JUNGLE_BARK.get(), Blocks.JUNGLE_WOOD))
            .put(Blocks.STRIPPED_DARK_OAK_LOG, Pair.of(ModItems.DARK_OAK_BARK.get(), Blocks.DARK_OAK_LOG))
            .put(Blocks.STRIPPED_DARK_OAK_WOOD, Pair.of(ModItems.DARK_OAK_BARK.get(), Blocks.DARK_OAK_WALL_SIGN))
            .put(Blocks.STRIPPED_ACACIA_LOG, Pair.of(ModItems.ACACIA_BARK.get(), Blocks.ACACIA_LOG))
            .put(Blocks.STRIPPED_ACACIA_WOOD, Pair.of(ModItems.ACACIA_BARK.get(), Blocks.ACACIA_WOOD))
            .put(Blocks.STRIPPED_CRIMSON_STEM, Pair.of(ModItems.CRIMSON_SCALES.get(), Blocks.CRIMSON_STEM))
            .put(Blocks.STRIPPED_CRIMSON_HYPHAE, Pair.of(ModItems.CRIMSON_SCALES.get(), Blocks.CRIMSON_HYPHAE))
            .put(Blocks.STRIPPED_WARPED_STEM, Pair.of(ModItems.WARPED_SCALES.get(), Blocks.WARPED_STEM))
            .put(Blocks.STRIPPED_WARPED_HYPHAE, Pair.of(ModItems.WARPED_SCALES.get(), Blocks.WARPED_HYPHAE))
            .build());


    public static Optional<CoralFamily> getCoralGrowth(BlockState baseBlock) {
        return Optional.ofNullable(CORALS.get().get(baseBlock.getBlock()));
    }

    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getAzaleaSheared(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    public static Optional<Block> getGrassGrowthForBiome(ResourceKey<Biome> biome, Random random) {
        var list = BIOME_FLOWERS.get().get(biome);
        if (list != null) return list.getRandomValue(random);
        return Optional.empty();
    }

    public static Optional<Block> getNyliumGrowth(BlockState state, Random random) {
        var list = NETHER_VEGETATION.get().get(state.getBlock());
        if (list != null) return list.getRandomValue(random);
        return Optional.empty();
    }

    public static Optional<Pair<Item, Block>> getBarkForStrippedLog(BlockState log) {
        return Optional.ofNullable(STRIPPED_TO_BARK.get().get(log.getBlock()));
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

    //modified version of hasEnoughBlocksAround for magma block
    public static boolean canMagmaSpread(BlockPos centerPos, int radius, Level level, int maximumSize) {
        int count = 0;
        boolean hasLava = false;
        //shuffling. provides way better result that iterating through it conventionally
        var list = grabBlocksAroundRandomly(centerPos, radius, radius, radius);
        for (BlockPos pos : list) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.MAGMA_BLOCK)) count += 1;
            else {
                var fluid = state.getFluidState();
                if (!hasLava && fluid.is(FluidTags.LAVA)) hasLava = true;
                else if (fluid.is(FluidTags.WATER)) return false;
            }
            if (count >= 12) return false;
        }
        return hasLava;
    }

    public static boolean canRootsSpread(BlockPos centerPos, int height, int width, Level world, int maximumSize) {
        int count = 0;
        boolean hasRoots = false;
        var list = grabBlocksAroundRandomly(centerPos.above(height / 2), width, height, width);
        for (BlockPos pos : list) {
            BlockState state = world.getBlockState(pos);
            if (state.is(Blocks.ROOTED_DIRT)) count += 1;
            else {
                if (!hasRoots && isLog(state)) hasRoots = true;
            }
            if (count >= maximumSize) return false;
        }
        return hasRoots;
    }

    public static boolean isLog(BlockState neighbor) {
        return neighbor.is(BlockTags.LOGS) && (!neighbor.hasProperty(RotatedPillarBlock.AXIS) ||
                neighbor.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y) &&
                !neighbor.getBlock().getRegistryName().getPath().contains("stripped");
    }

    public static final SimpleWeightedRandomList<Direction> ROOT_DIRECTIONS =
            SimpleWeightedRandomList.<Direction>builder()
                    .add(Direction.NORTH, 5)
                    .add(Direction.SOUTH, 5)
                    .add(Direction.WEST, 5)
                    .add(Direction.EAST, 5)
                    .add(Direction.UP, 1)
                    .add(Direction.DOWN, 20)
                    .build();

}
