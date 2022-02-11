package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class SootyCampfire extends CampfireBlock {

    public SootyCampfire(boolean emitsParticles, int fireDamage, Settings settings) {
        super(emitsParticles, fireDamage, settings);
    }

    public void randomTick(BlockState state, World world, BlockPos pos, Random random) {

        int smokeHeight = 8;

        if (state.get(SIGNAL_FIRE)) {
            smokeHeight = 16;
        }

        if (state.get(LIT)) {
            BlockPos sootBlock = pos.up(random.nextInt(smokeHeight) + 1);
            int rand = random.nextInt(4);
            Direction sootDir = Direction.fromHorizontal(rand);
            BlockPos testPos = sootBlock.offset(sootDir);
            BlockState testBlock = world.getBlockState(testPos);
            if (Block.isFaceFullSquare(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                BlockState currentState = world.getBlockState(sootBlock);
                if (currentState.isOf(ModBlocks.SOOT)) {
                    world.setBlockState(sootBlock, currentState.with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                } else {
                    world.setBlockState(sootBlock, ModBlocks.SOOT.getDefaultState().with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                }
            }
        }
    }
}