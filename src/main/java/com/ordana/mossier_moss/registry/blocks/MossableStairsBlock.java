package com.ordana.mossier_moss.registry.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MossableStairsBlock extends StairsBlock implements Mossable {
    private final Mossable.MossLevel mossLevel;

    public MossableStairsBlock(Mossable.MossLevel mossLevel, BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
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
