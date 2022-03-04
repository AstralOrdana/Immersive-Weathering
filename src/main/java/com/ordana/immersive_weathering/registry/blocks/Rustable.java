package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface Rustable
        extends ChangeOverTimeBlock<Rustable.RustLevel> {
    Supplier<BiMap<Block, Block>> RUST_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(ModBlocks.CUT_IRON.get(), ModBlocks.EXPOSED_CUT_IRON.get())
            .put(ModBlocks.EXPOSED_CUT_IRON.get(), ModBlocks.WEATHERED_CUT_IRON.get())
            .put(ModBlocks.WEATHERED_CUT_IRON.get(), ModBlocks.RUSTED_CUT_IRON.get())
            .put(ModBlocks.CUT_IRON_SLAB.get(), ModBlocks.EXPOSED_CUT_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_SLAB.get(), ModBlocks.WEATHERED_CUT_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_SLAB.get(), ModBlocks.RUSTED_CUT_IRON_SLAB.get())
            .put(ModBlocks.CUT_IRON_STAIRS.get(), ModBlocks.EXPOSED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_STAIRS.get(), ModBlocks.WEATHERED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_STAIRS.get(), ModBlocks.RUSTED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.PLATE_IRON.get(), ModBlocks.EXPOSED_PLATE_IRON.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON.get(), ModBlocks.WEATHERED_PLATE_IRON.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON.get(), ModBlocks.RUSTED_PLATE_IRON.get())
            .put(ModBlocks.PLATE_IRON_SLAB.get(), ModBlocks.EXPOSED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_SLAB.get(), ModBlocks.WEATHERED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_SLAB.get(), ModBlocks.RUSTED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.PLATE_IRON_STAIRS.get(), ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get(), ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get(), ModBlocks.RUSTED_PLATE_IRON_STAIRS.get())
            .put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR.get())
            .put(ModBlocks.EXPOSED_IRON_DOOR.get(), ModBlocks.WEATHERED_IRON_DOOR.get())
            .put(ModBlocks.WEATHERED_IRON_DOOR.get(), ModBlocks.RUSTED_IRON_DOOR.get())
            .put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR.get())
            .put(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), ModBlocks.WEATHERED_IRON_TRAPDOOR.get())
            .put(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), ModBlocks.RUSTED_IRON_TRAPDOOR.get())
            .put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS.get())
            .put(ModBlocks.EXPOSED_IRON_BARS.get(), ModBlocks.WEATHERED_IRON_BARS.get())
            .put(ModBlocks.WEATHERED_IRON_BARS.get(), ModBlocks.RUSTED_IRON_BARS.get())
            .build());

    Supplier<BiMap<Block, Block>> RUST_LEVEL_DECREASES = Suppliers.memoize(() -> RUST_LEVEL_INCREASES.get().inverse());

    static Optional<Block> getDecreasedRustBlock(Block block) {
        return Optional.ofNullable(RUST_LEVEL_DECREASES.get().get(block));
    }

    static Block getUnaffectedRustBlock(Block block) {
        Block block2 = block;
        Block block3 = RUST_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = RUST_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    static Optional<BlockState> getDecreasedRustState(BlockState state) {
        return Rustable.getDecreasedRustBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getIncreasedRustBlock(Block block) {
        return Optional.ofNullable(RUST_LEVEL_INCREASES.get().get(block));
    }

    static BlockState getUnaffectedRustState(BlockState state) {
        return Rustable.getUnaffectedRustBlock(state.getBlock()).withPropertiesOf(state);
    }

    @Override
    default public Optional<BlockState> getNext(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default public float getChanceModifier() {
        if (this.getAge() == Rustable.RustLevel.UNAFFECTED) {
            return 0.75f;
        }
        return 1.0f;
    }

    enum RustLevel {
        UNAFFECTED,
        EXPOSED,
        WEATHERED,
        RUSTED;

    }
}
