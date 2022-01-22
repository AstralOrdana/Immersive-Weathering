package com.ordana.mossier_moss.registry.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class CleanStairsBlock extends MossableStairsBlock {
    public CleanStairsBlock(MossLevel mossLevel, BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(mossLevel, baseBlockState, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            if (world.getBlockState(pos.offset(direction)).isOf(Blocks.MOSS_BLOCK) || world.getBlockState(pos.offset(direction)).isOf(Blocks.WATER)  || world.getBlockState(pos.offset(direction)).isOf(Blocks.VINE) || world.getBlockState(pos.offset(direction)).isOf(Blocks.MOSS_CARPET)) {
                float f = 0.1f;
                if (random.nextFloat() > 0.1f) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
        }
    }
}
