package com.ordana.immersive_weathering.registry.blocks.rustable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;

import java.util.Optional;
import java.util.function.Supplier;

public interface Rustable
extends Degradable<Rustable.RustLevel> {
    public static final Supplier<BiMap<Block, Block>> RUST_LEVEL_INCREASES = Suppliers.memoize(() -> ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)((ImmutableBiMap.Builder) ((ImmutableBiMap.Builder)
                    ImmutableBiMap.builder()
            .put(ModBlocks.CUT_IRON, ModBlocks.EXPOSED_CUT_IRON))
            .put(ModBlocks.EXPOSED_CUT_IRON, ModBlocks.WEATHERED_CUT_IRON))
            .put(ModBlocks.WEATHERED_CUT_IRON, ModBlocks.RUSTED_CUT_IRON))
            .put(ModBlocks.CUT_IRON_SLAB, ModBlocks.EXPOSED_CUT_IRON_SLAB))
            .put(ModBlocks.EXPOSED_CUT_IRON_SLAB, ModBlocks.WEATHERED_CUT_IRON_SLAB))
            .put(ModBlocks.WEATHERED_CUT_IRON_SLAB, ModBlocks.RUSTED_CUT_IRON_SLAB))
            .put(ModBlocks.CUT_IRON_STAIRS, ModBlocks.EXPOSED_CUT_IRON_STAIRS))
            .put(ModBlocks.EXPOSED_CUT_IRON_STAIRS, ModBlocks.WEATHERED_CUT_IRON_STAIRS))
            .put(ModBlocks.WEATHERED_CUT_IRON_STAIRS, ModBlocks.RUSTED_CUT_IRON_STAIRS))
            .put(ModBlocks.PLATE_IRON, ModBlocks.EXPOSED_PLATE_IRON))
            .put(ModBlocks.EXPOSED_PLATE_IRON, ModBlocks.WEATHERED_PLATE_IRON))
            .put(ModBlocks.WEATHERED_PLATE_IRON, ModBlocks.RUSTED_PLATE_IRON))
            .put(ModBlocks.PLATE_IRON_SLAB, ModBlocks.EXPOSED_PLATE_IRON_SLAB))
            .put(ModBlocks.EXPOSED_PLATE_IRON_SLAB, ModBlocks.WEATHERED_PLATE_IRON_SLAB))
            .put(ModBlocks.WEATHERED_PLATE_IRON_SLAB, ModBlocks.RUSTED_PLATE_IRON_SLAB))
            .put(ModBlocks.PLATE_IRON_STAIRS, ModBlocks.EXPOSED_PLATE_IRON_STAIRS))
            .put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS, ModBlocks.WEATHERED_PLATE_IRON_STAIRS))
            .put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS, ModBlocks.RUSTED_PLATE_IRON_STAIRS))
            .put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR))
            .put(ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR))
            .put(ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.RUSTED_IRON_DOOR))
            .put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR))
            .put(ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR))
            .put(ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.RUSTED_IRON_TRAPDOOR))
            .put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS))
            .put(ModBlocks.EXPOSED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS))
            .put(ModBlocks.WEATHERED_IRON_BARS, ModBlocks.RUSTED_IRON_BARS))
                    .build());

    public static final Supplier<BiMap<Block, Block>> RUST_LEVEL_DECREASES = Suppliers.memoize(() -> RUST_LEVEL_INCREASES.get().inverse());

    public static Optional<Block> getDecreasedRustBlock(Block block) {
        return Optional.ofNullable((Block)RUST_LEVEL_DECREASES.get().get(block));
    }

    public static Block getUnaffectedRustBlock(Block block) {
        Block block2 = block;
        Block block3 = (Block)RUST_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = (Block)RUST_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    public static Optional<BlockState> getDecreasedRustState(BlockState state) {
        return Rustable.getDecreasedRustBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    public static Optional<Block> getIncreasedRustBlock(Block block) {
        return Optional.ofNullable((Block)RUST_LEVEL_INCREASES.get().get(block));
    }

    public static BlockState getUnaffectedRustState(BlockState state) {
        return Rustable.getUnaffectedRustBlock(state.getBlock()).getStateWithProperties(state);
    }

    @Override
    default public Optional<BlockState> getDegradationResult(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    default public float getDegradationChanceMultiplier() {
        if (this.getDegradationLevel() == Rustable.RustLevel.UNAFFECTED) {
            return 0.75f;
        }
        return 1.0f;
    }

    public static enum RustLevel {
        UNAFFECTED,
        EXPOSED,
        WEATHERED,
        RUSTED;

    }
}
