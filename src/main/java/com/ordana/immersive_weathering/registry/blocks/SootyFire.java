package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class SootyFire extends FireBlock {
    public SootyFire(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int smokeHeight = 6;

        BlockPos sootPos = pos;
        for (int i = 0; i < smokeHeight; i++) {
            sootPos = sootPos.up();
            BlockState above = world.getBlockState(sootPos.up());
            if (Block.isFaceFullSquare(above.getCollisionShape(world, sootPos.up()), Direction.DOWN)) {
                if (world.getBlockState(sootPos).isAir()) {
                    world.setBlockState(sootPos, ModBlocks.SOOT.getDefaultState().with(Properties.UP, true), Block.NOTIFY_LISTENERS);
                }
                smokeHeight = i+1;
            }
        }
    }
}
