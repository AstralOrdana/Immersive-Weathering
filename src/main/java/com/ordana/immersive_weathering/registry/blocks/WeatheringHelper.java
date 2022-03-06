package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class WeatheringHelper {

    public record CoralFamily(Block coral, Block fan, Block wallFan) {
    }

    ;

    private static final Supplier<Map<Block, CoralFamily>> CORALS = Suppliers.memoize(() -> ImmutableMap.<Block, CoralFamily>builder()
            .put(Blocks.BRAIN_CORAL_BLOCK, new CoralFamily(Blocks.BRAIN_CORAL, Blocks.BRAIN_CORAL_FAN, Blocks.BRAIN_CORAL_WALL_FAN))
            .put(Blocks.BUBBLE_CORAL_BLOCK, new CoralFamily(Blocks.BUBBLE_CORAL, Blocks.BUBBLE_CORAL_FAN, Blocks.BUBBLE_CORAL_WALL_FAN))
            .put(Blocks.FIRE_CORAL_BLOCK, new CoralFamily(Blocks.FIRE_CORAL, Blocks.FIRE_CORAL_FAN, Blocks.FIRE_CORAL_WALL_FAN))
            .put(Blocks.TUBE_CORAL_BLOCK, new CoralFamily(Blocks.TUBE_CORAL, Blocks.TUBE_CORAL_FAN, Blocks.TUBE_CORAL_WALL_FAN))
            .put(Blocks.HORN_CORAL_BLOCK, new CoralFamily(Blocks.HORN_CORAL, Blocks.HORN_CORAL_FAN, Blocks.HORN_CORAL_WALL_FAN))
            .build());

    public static final Supplier<Map<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES)
            .put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), ModBlocks.AZALEA_LEAF_PILE.get())
            .build());

    public static final Supplier<Map<Block, Block>> LEAF_PILES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE.get())
            .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE.get())
            .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE.get())
            .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE.get())
            .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE.get())
            .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE.get())
            .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE.get())
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get())
            .build());


    public static Optional<CoralFamily> getCoralGrowth(BlockState baseBlock) {
        return Optional.ofNullable(CORALS.get().get(baseBlock.getBlock()));
    }

    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    /**
     * optimized version of BlockPos.withinManhattanStream / BlockPos.expandOutwards that tries to limit world.getBlockState calls
     *
     * @param blockPredicate type of target block
     * @param maximumSize    maximum amount of blocks that we want around this
     * @return true if blocks around that match the given predicate exceed(inclusive) the maximum size given
     */
    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, Level level,
                                                Predicate<BlockState> blockPredicate, int maximumSize) {
        int count = 0;
        for (BlockPos pos : BlockPos.withinManhattan(centerPos, radiusX, radiusY, radiusZ)) {
            if (blockPredicate.test(level.getBlockState(pos))) count += 1;
            if (count >= maximumSize) return true;
        }
        return false;
    }

    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius,Level level,
                                                Predicate<BlockState> blockPredicate, int maximumSize) {
        return hasEnoughBlocksAround(centerPos, radius,radius,radius,level, blockPredicate, maximumSize);
    }

}
