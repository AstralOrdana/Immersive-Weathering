package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class SootyCampfire extends CampfireBlock {

    public SootyCampfire(boolean emitsParticles, int fireDamage, Settings settings) {
        super(emitsParticles, fireDamage, settings);
    }

    private boolean doesBlockCauseSignalFire(BlockState state) {
        return state.isOf(ModBlocks.WOODCHIPS_BLOCK);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

        public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

            int smokeHeight = 8;

            if (state.get(SIGNAL_FIRE)) {
                smokeHeight = 23;
            }

            if (state.get(LIT)) {
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
                BlockPos sootBlock = pos.up(random.nextInt(smokeHeight) + 1);
                int rand = random.nextInt(4);
                Direction sootDir = Direction.fromHorizontal(rand);
                BlockPos testPos = sootBlock.offset(sootDir);
                BlockState testBlock = world.getBlockState(testPos);

                if (Block.isFaceFullSquare(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                    BlockState currentState = world.getBlockState(sootBlock);
                    if (currentState.isOf(ModBlocks.SOOT)) {
                        world.setBlockState(sootBlock, currentState.with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                    }
                    else if (currentState.isAir()) {
                        world.setBlockState(sootBlock, ModBlocks.SOOT.getDefaultState().with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }