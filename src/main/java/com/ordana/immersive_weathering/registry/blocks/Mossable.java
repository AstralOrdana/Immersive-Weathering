package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Degradable;

import java.util.Optional;
import java.util.function.Supplier;

public interface Mossable extends Degradable<Mossable.MossLevel> {
    public static final Supplier<BiMap<Block, Block>> MOSS_LEVEL_INCREASES = Suppliers.memoize(() -> ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)
            ImmutableBiMap.builder()
                    .put(Blocks.STONE, ModBlocks.MOSSY_STONE))
            .put(Blocks.STONE_STAIRS, ModBlocks.MOSSY_STONE_STAIRS))
            .put(Blocks.STONE_SLAB, ModBlocks.MOSSY_STONE_SLAB))
            .put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE))
            .put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS))
            .put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB))
            .put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL))
            .put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS))
            .put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS))
            .put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB))
            .put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL))
            .put(Blocks.BRICKS, ModBlocks.MOSSY_BRICKS))
            .put(Blocks.BRICK_STAIRS, ModBlocks.MOSSY_BRICK_STAIRS))
            .put(Blocks.BRICK_SLAB, ModBlocks.MOSSY_BRICK_SLAB))
            .put(Blocks.BRICK_WALL, ModBlocks.MOSSY_BRICK_WALL))
            .build());

    public static final Supplier<BiMap<Block, Block>> MOSS_LEVEL_DECREASES = Suppliers.memoize(() -> MOSS_LEVEL_INCREASES.get().inverse());

    public static Optional<Block> getDecreasedMossBlock(Block block) {
        return Optional.ofNullable((Block)MOSS_LEVEL_DECREASES.get().get(block));
    }

    public static Block getUnaffectedMossBlock(Block block) {
        Block block2 = block;
        Block block3 = (Block)MOSS_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = (Block)MOSS_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    public static Optional<BlockState> getDecreasedMossState(BlockState state) {
        return Mossable.getDecreasedMossBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    public static Optional<Block> getIncreasedMossBlock(Block block) {
        return Optional.ofNullable((Block)MOSS_LEVEL_INCREASES.get().get(block));
    }

    public static BlockState getUnaffectedMossState(BlockState state) {
        return Mossable.getUnaffectedMossBlock(state.getBlock()).getStateWithProperties(state);
    }

    @Override
    default public Optional<BlockState> getDegradationResult(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    default public float getDegradationChanceMultiplier() {
        return 1.0f;
    }

    public static enum MossLevel {
        UNAFFECTED,
        MOSSY;

    }
}
