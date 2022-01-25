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

public class UnmossyUncrackedBlock extends MossableBlock {

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    public UnmossyUncrackedBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){

        CLEANED_BLOCKS.put(Blocks.CRACKED_STONE_BRICKS, Blocks.STONE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
        CLEANED_BLOCKS.put(Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.CRACKED_DEEPSLATE_TILES, Blocks.DEEPSLATE_TILES);
        CLEANED_BLOCKS.put(ModBlocks.CRACKED_BRICKS, Blocks.BRICKS);

        for (Direction direction : Direction.values()) {

            BlockPos targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);

            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ImmersiveWeathering.MOSS_SOURCE::contains)
                    .toList().size() >= 1) {
                float f = 0.5f;
                if (random.nextFloat() > 0.5f) {
                    this.tryDegrade(state, world, pos, random);
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
                        CLEANED_BLOCKS.forEach((cracked, solid) -> {
                            if (targetBlock.isOf(solid)) {
                                world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    if (world.getBlockState(pos.offset(direction)).isIn(ImmersiveWeathering.CRACKED)) {
                        float g = 0.2F;
                        if (random.nextFloat() < 0.2F) {
                            CLEANED_BLOCKS.forEach((cracked, solid) -> {
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

