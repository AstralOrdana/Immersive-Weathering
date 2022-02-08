package com.ordana.immersive_weathering.registry.blocks;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RustableStairsBlock
        extends StairsBlock
        implements Rustable {
    private final Rustable.RustLevel rustLevel;

    public RustableStairsBlock(Rustable.RustLevel rustLevel, BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(baseBlockState, settings);
        this.rustLevel = rustLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).isPresent();
    }

    @Override
    public Rustable.RustLevel getDegradationLevel() {
        return this.rustLevel;
    }
}
