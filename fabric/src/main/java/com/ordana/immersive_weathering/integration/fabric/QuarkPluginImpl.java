package com.ordana.immersive_weathering.integration.fabric;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class QuarkPluginImpl {
    public static void onFirstClientTick() {
    }

    public static void addVerticalSlabPair(ImmutableBiMap.Builder<Block, Block> builder, Supplier<Block> altered) {
    }

    public static void addAllVerticalSlabs(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    public static BlockState fixVerticalSlab(BlockState fixedBlock, BlockState original) {
        return fixedBlock;
    }

}
