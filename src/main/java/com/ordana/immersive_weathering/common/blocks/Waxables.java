package com.ordana.immersive_weathering.common.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public final class Waxables {

    private static final Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(ModBlocks.EXPOSED_PLATE_IRON.get(), ModBlocks.WAXED_EXPOSED_PLATE_IRON.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON.get(), ModBlocks.WAXED_WEATHERED_PLATE_IRON.get())
            .put(ModBlocks.RUSTED_PLATE_IRON.get(), ModBlocks.WAXED_RUSTED_PLATE_IRON.get())

            .put(ModBlocks.PLATE_IRON_STAIRS.get(), ModBlocks.WAXED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get(), ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get(), ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.RUSTED_PLATE_IRON_STAIRS.get(), ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS.get())

            .put(ModBlocks.PLATE_IRON_SLAB.get(), ModBlocks.WAXED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_SLAB.get(), ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_SLAB.get(), ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.RUSTED_PLATE_IRON_SLAB.get(), ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB.get())

            .put(ModBlocks.CUT_IRON.get(), ModBlocks.WAXED_CUT_IRON.get())
            .put(ModBlocks.EXPOSED_CUT_IRON.get(), ModBlocks.WAXED_EXPOSED_CUT_IRON.get())
            .put(ModBlocks.WEATHERED_CUT_IRON.get(), ModBlocks.WAXED_WEATHERED_CUT_IRON.get())
            .put(ModBlocks.RUSTED_CUT_IRON.get(), ModBlocks.WAXED_RUSTED_CUT_IRON.get())

            .put(ModBlocks.CUT_IRON_STAIRS.get(), ModBlocks.WAXED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_STAIRS.get(), ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_STAIRS.get(), ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.RUSTED_CUT_IRON_STAIRS.get(), ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS.get())

            .put(ModBlocks.CUT_IRON_SLAB.get(), ModBlocks.WAXED_CUT_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_SLAB.get(), ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_SLAB.get(), ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB.get())
            .put(ModBlocks.RUSTED_CUT_IRON_SLAB.get(), ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB.get())

            .put(Blocks.IRON_DOOR, ModBlocks.WAXED_IRON_DOOR.get())
            .put(ModBlocks.EXPOSED_IRON_DOOR.get(), ModBlocks.WAXED_EXPOSED_IRON_DOOR.get())
            .put(ModBlocks.WEATHERED_IRON_DOOR.get(), ModBlocks.WAXED_WEATHERED_IRON_DOOR.get())
            .put(ModBlocks.RUSTED_IRON_DOOR.get(), ModBlocks.WAXED_RUSTED_IRON_DOOR.get())

            .put(Blocks.IRON_TRAPDOOR, ModBlocks.WAXED_IRON_TRAPDOOR.get())
            .put(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get())
            .put(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get())
            .put(ModBlocks.RUSTED_IRON_TRAPDOOR.get(), ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get())

            .put(Blocks.IRON_BARS, ModBlocks.WAXED_IRON_BARS.get())
            .put(ModBlocks.EXPOSED_IRON_BARS.get(), ModBlocks.WAXED_EXPOSED_IRON_BARS.get())
            .put(ModBlocks.WEATHERED_IRON_BARS.get(), ModBlocks.WAXED_WEATHERED_IRON_BARS.get())
            .put(ModBlocks.RUSTED_IRON_BARS.get(), ModBlocks.WAXED_RUSTED_IRON_BARS.get())
            .build());

    public static Optional<BlockState> getWaxedState(BlockState state) {
        return Optional.ofNullable(WAXABLES.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getUnWaxedState(BlockState state) {
        return Optional.ofNullable(WAXABLES.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

}
