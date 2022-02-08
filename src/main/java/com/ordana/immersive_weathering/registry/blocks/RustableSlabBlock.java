package com.ordana.immersive_weathering.registry.blocks;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RustableSlabBlock extends SlabBlock implements Rustable {
    private final Rustable.RustLevel rustLevel;

    public RustableSlabBlock(Rustable.RustLevel rustLevel, AbstractBlock.Settings settings) {
        super(settings);
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
