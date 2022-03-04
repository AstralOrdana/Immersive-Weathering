package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface Crackable extends ChangeOverTimeBlock<Crackable.CrackLevel> {
    public static final Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)
            ImmutableBiMap.builder()
            .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS))
            .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS))
            .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS))
            .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS))
            .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS))
            .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES))

            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB))
            .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB))
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB))
            .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB))
            .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB))
            .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB))

            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS))
            .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS))
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS))
            .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS))
            .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS))
            .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS))

            .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL))
            .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL))
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL))
            .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL))
            .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL))
            .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL))
            .build());

    public static final Supplier<BiMap<Block, Block>> CRACK_LEVEL_DECREASES = Suppliers.memoize(() -> CRACK_LEVEL_INCREASES.get().inverse());

    public static Optional<Block> getDecreasedCrackBlock(Block block) {
        return Optional.ofNullable((Block)CRACK_LEVEL_DECREASES.get().get(block));
    }

    public static Block getUncrackedCrackBlock(Block block) {
        Block block2 = block;
        Block block3 = (Block)CRACK_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = (Block)CRACK_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    public static Optional<BlockState> getDecreasedCrackState(BlockState state) {
        return Crackable.getDecreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable((Block)CRACK_LEVEL_INCREASES.get().get(block));
    }

    public static BlockState getUncrackedCrackState(BlockState state) {
        return Crackable.getUncrackedCrackBlock(state.getBlock()).withPropertiesOf(state);
    }

    @Override
    default public Optional<BlockState> getNext(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default public float getChanceModifier() {
        return 1.0f;
    }

    public static enum CrackLevel {
        UNCRACKED,
        CRACKED;
    }
}
