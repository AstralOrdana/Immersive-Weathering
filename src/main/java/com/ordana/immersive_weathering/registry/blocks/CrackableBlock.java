package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class CrackableBlock extends Block implements Crackable{
    private final CrackLevel crackLevel;

    public CrackableBlock(CrackLevel crackLevel, Settings settings) {
        super(settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).isPresent();
    }

    @Override
    public CrackLevel getDegradationLevel() {
        return this.crackLevel;
    }
}
