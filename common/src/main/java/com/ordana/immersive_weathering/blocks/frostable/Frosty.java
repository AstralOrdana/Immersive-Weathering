package com.ordana.immersive_weathering.blocks.frostable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Optional;
import java.util.function.Supplier;

public interface Frosty {

    Supplier<BiMap<Block, Block>> UNFROSTY_TO_FROSTY = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()

                .put(Blocks.GLASS, ModBlocks.FROSTY_GLASS.get())
                .put(Blocks.FERN, ModBlocks.FROSTY_FERN.get())
                .put(Blocks.GRASS, ModBlocks.FROSTY_GRASS.get())
                .put(Blocks.GLASS_PANE, ModBlocks.FROSTY_GLASS_PANE.get());

        //CommonPlatform.addExtraCrackedBlocks(builder);

        return builder.build();
    });

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> FROSTY_TO_UNFROSTY = Suppliers.memoize(() -> UNFROSTY_TO_FROSTY.get().inverse());


    default Optional<BlockState> getFrosty(BlockState state) {
        return getFrosty(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default Optional<BlockState> getUnfrosty(BlockState state) {
        return getUnfrosty(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getUnfrosty(Block block) {
        return Optional.ofNullable(FROSTY_TO_UNFROSTY.get().get(block));
    }

    static Optional<Block> getFrosty(Block block) {
        return Optional.ofNullable(UNFROSTY_TO_FROSTY.get().get(block));
    }

    BooleanProperty NATURAL = BooleanProperty.create("natural");

    default void tryUnFrost(BlockState state, ServerLevel world, BlockPos pos) {
        if (state.getValue(NATURAL)) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                world.setBlockAndUpdate(pos, getUnfrosty(state).get());
            }
        }
    }

}
