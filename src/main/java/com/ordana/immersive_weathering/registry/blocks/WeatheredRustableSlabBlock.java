package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class WeatheredRustableSlabBlock extends ExposedRustableSlabBlock {
    public WeatheredRustableSlabBlock(RustLevel rustLevel, Settings settings) {
        super(rustLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getFluid() == Fluids.WATER || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || state.get(WATERLOGGED)) {
                this.tickDegradation(state, world, pos, random);
            }
            else if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                float f = 0.07f;
                if (random.nextFloat() < 0.05688889f) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
        }
    }
}
