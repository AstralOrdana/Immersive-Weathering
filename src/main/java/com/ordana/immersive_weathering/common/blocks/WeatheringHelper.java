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


    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getAzaleaSheared(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LeafPilesRegistry.LEAF_PILES.get().get(state.getBlock()));
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
            if (count >= maximumSize) return false;
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

    public static final SimpleWeightedRandomList<Direction> CORAL_DIRECTIONS =
            SimpleWeightedRandomList.<Direction>builder()
                    .add(Direction.NORTH, 1)
                    .add(Direction.SOUTH, 1)
                    .add(Direction.WEST, 1)
                    .add(Direction.EAST, 1)
                    .add(Direction.UP, 5)
                    .build();


    public static boolean isIciclePos(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(12) == 0;
    }

    public static void tryPlacingIcicle(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW && WeatheringHelper.isIciclePos(pos)) {
            BlockPos p = pos.below(state.is(BlockTags.SNOW) ? 2 : 1);
            BlockState placement = ModBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, Direction.DOWN);
            if (level.getBlockState(p).isAir() && placement.canSurvive(level, p)) {
                if (Direction.Plane.HORIZONTAL.stream().anyMatch(d -> {
                    BlockPos rel = p.relative(d);
                    return level.canSeeSky(rel) && level.getBlockState(rel).isAir();
                })) {
                    level.setBlockAndUpdate(p, placement);
                }
            }
        }
    }

    public static void onLightningHit(BlockPos centerPos, Level level, int rec) {
        BlockState vitrified = ModBlocks.VITRIFIED_SAND.get().defaultBlockState();
        level.setBlockAndUpdate(centerPos, vitrified);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || level.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.below();
            if (level.getBlockState(downPos).is(BlockTags.SAND)) {
                onLightningHit(downPos, level, rec);
            }
        }
        for (BlockPos target : BlockPos.withinManhattan(centerPos, 1, 0, 1)) {
            if (level.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (level.getBlockState(target).is(BlockTags.SAND)) {
                    onLightningHit(target, level, rec);
                }
            }
        }
    }
}
