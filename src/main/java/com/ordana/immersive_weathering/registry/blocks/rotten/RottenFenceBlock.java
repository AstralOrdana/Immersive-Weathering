package com.ordana.immersive_weathering.registry.blocks.rotten;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class RottenFenceBlock extends FenceBlock {

    public RottenFenceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (random.nextInt(25) == 1) {
            BlockPos blockpos = pos.down();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(level, blockpos, Direction.UP)) {
                double d0 = (double) pos.getX() + random.nextDouble();
                double d1 = (double) pos.getY() - 0.05D;
                double d2 = (double) pos.getZ() + random.nextDouble();
                level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
