package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class ExposedRustableStairsBlock extends CleanRustableStairsBlock {
    public ExposedRustableStairsBlock(RustLevel rustLevel, BlockState baseBlockState, Settings settings) {
        super(rustLevel, baseBlockState, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (world.hasRain(pos.up()) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER || state.get(WATERLOGGED)) {
                this.tickDegradation(state, world, pos, random);
            }
            if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                float f = 0.06f;
                if (random.nextFloat() > 0.06f) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
            if (world.hasRain(pos.offset(direction)) && world.getBlockState(pos.up()).isIn(ImmersiveWeathering.WEATHERED_IRON)) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.WEATHERED_IRON::contains)
                        .toList().size() <= 9) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
    }
}

