package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Degradable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public interface Crackable extends Degradable<Crackable.CrackLevel>, WeatherableBlock {

    Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()

            .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS)
            .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS)
            .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
            .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS)
            .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES)

            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB)
            .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB)
            .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB)
            .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB)
            .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB)

            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS)
            .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS)
            .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS)
            .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS)
            .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS)

            .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL)
            .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL)
            .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL)
            .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL)
            .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL)
            .build());

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> CRACK_LEVEL_DECREASES = Suppliers.memoize(() -> Objects.requireNonNull(CRACK_LEVEL_INCREASES.get()).inverse());

    static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_INCREASES.get().get(block));
    }

    @Override
    default Optional<BlockState> getDegradationResult(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    default float getChance() {
        return 1.0f;
    }

    enum CrackLevel {
        UNCRACKED,
        CRACKED;
    }
}