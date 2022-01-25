package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MossableSlabBlock extends SlabBlock implements Mossable {
    private final Mossable.MossLevel mossLevel;

    public MossableSlabBlock(Mossable.MossLevel mossLevel, Settings settings) {
        super(settings);
        this.mossLevel = mossLevel;
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).isPresent();
    }

    @Override
    public Mossable.MossLevel getDegradationLevel() {
        return this.mossLevel;
    }
}
