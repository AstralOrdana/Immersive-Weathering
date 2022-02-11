package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Random;

public class UncrackedBlock extends CrackableBlock {
    public UncrackedBlock(CrackLevel crackLevel, Settings settings) {
        super(crackLevel, settings);
    }

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        CLEANED_BLOCKS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CLEANED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);

        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.FIRE::equals)) {
                float f = 0.5F;
                if (random.nextFloat() < 0.5F) {
                    CLEANED_BLOCKS.forEach((solid, cracked) -> {
                        if (targetBlock.isOf(solid)) {
                            world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                        }
                    });
                }
            }
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ImmersiveWeathering.CRACKABLE::contains)
                    .toList().size() >= 20) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.CRACKED::contains)
                        .toList().size() <= 8) {
                    float f = 0.0000625F;
                    if (random.nextFloat() < 0.0000625F) {
                        CLEANED_BLOCKS.forEach((solid, cracked) -> {
                            if (targetBlock.isOf(solid)) {
                                world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    if (world.getBlockState(pos.offset(direction)).isIn(ImmersiveWeathering.CRACKED)) {
                        float g = 0.02F;
                        if (random.nextFloat() < 0.02F) {
                            CLEANED_BLOCKS.forEach((solid, cracked) -> {
                                if (targetBlock.isOf(solid)) {
                                    world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}

