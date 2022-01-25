package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class UncrackedBlock extends CrackableBlock {
    public UncrackedBlock(CrackLevel crackLevel, Settings settings) {
        super(crackLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
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
                    float f = 0.05688889F;
                    if (random.nextFloat() < 0.05688889F) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
    }
}

