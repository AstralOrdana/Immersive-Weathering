package com.ordana.immersive_weathering.integration;

import com.google.common.collect.ImmutableBiMap;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;

import java.util.function.Supplier;

public class QuarkPlugin {

    @ExpectPlatform
    public static void addVerticalSlabPair(ImmutableBiMap.Builder<Block, Block> builder,
                                           Supplier<Block> altered) {
    }

    @ExpectPlatform
    public static void addAllVerticalSlabs(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    @ExpectPlatform
    public static BlockState fixVerticalSlab(BlockState fixedBlock, BlockState original) {
        throw new AssertionError();
    }

    @Contract
    @ExpectPlatform
    public static boolean isVerticalSlabsOn() {
        throw new AssertionError();
    }
}
