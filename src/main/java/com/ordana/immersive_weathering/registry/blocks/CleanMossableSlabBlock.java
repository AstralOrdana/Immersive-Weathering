package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class CleanMossableSlabBlock extends MossableSlabBlock{
    public CleanMossableSlabBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getFluid() == Fluids.LAVA || neighborState.getFluidState().getFluid() == Fluids.FLOWING_LAVA) {
                return;
            }
        }
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if ((world.getBlockState(pos.offset(direction)).isIn(ImmersiveWeathering.MOSS_SOURCE) || (neighborState.contains(Properties.WATERLOGGED)) && neighborState.get(Properties.WATERLOGGED)) || state.get(WATERLOGGED)) {
                float f = 0.5f;
                if (random.nextFloat() > 0.5f) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
            if (BlockPos.streamOutwards(pos, 1, 1, 1)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.contains(Properties.WATERLOGGED) && e.get(Properties.WATERLOGGED)) || e.isIn(ImmersiveWeathering.MOSS_SOURCE))) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.MOSSY::contains)
                        .toList().size() <= 20) {
                    float f = 0.4f;
                    if (random.nextFloat() > 0.4f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.contains(Properties.WATERLOGGED) && e.get(Properties.WATERLOGGED)) || e.isIn(ImmersiveWeathering.MOSS_SOURCE))) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.MOSSY::contains)
                        .toList().size() <= 15) {
                    float f = 0.3f;
                    if (random.nextFloat() > 0.3f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.streamOutwards(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.contains(Properties.WATERLOGGED) && e.get(Properties.WATERLOGGED)) || e.isIn(ImmersiveWeathering.MOSS_SOURCE))) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.MOSSY::contains)
                        .toList().size() <= 8) {
                    float f = 0.2f;
                    if (random.nextFloat() > 0.2f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.streamOutwards(pos, 4, 4, 4)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.contains(Properties.WATERLOGGED) && e.get(Properties.WATERLOGGED)) || e.isIn(ImmersiveWeathering.MOSS_SOURCE))) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.MOSSY::contains)
                        .toList().size() <= 6) {
                    float f = 0.1f;
                    if (random.nextFloat() > 0.1f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.streamOutwards(pos, 5, 5, 5)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.contains(Properties.WATERLOGGED) && e.get(Properties.WATERLOGGED)) || e.isIn(ImmersiveWeathering.MOSS_SOURCE))) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.MOSSY::contains)
                        .toList().size() <= 3) {
                    float f = 0.09f;
                    if (random.nextFloat() > 0.09f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
    }
}
