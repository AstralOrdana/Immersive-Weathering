package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class CleanSlabBlock extends MossableSlabBlock{
    public CleanSlabBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
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
        }
    }
}
