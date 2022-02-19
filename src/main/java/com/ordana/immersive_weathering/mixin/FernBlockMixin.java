package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FernBlock.class)
public class FernBlockMixin extends Block {

    public FernBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var targetPos = pos.up();
        if (world.getBlockState(pos).isOf(Blocks.GRASS)) {
            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                if (BlockPos.streamOutwards(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.TALL_GRASS::equals)
                        .toList().size() <= 2) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState());
                        world.setBlockState(targetPos, Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
        if (world.getBlockState(pos).isOf(Blocks.FERN)) {
            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                if (BlockPos.streamOutwards(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.LARGE_FERN::equals)
                        .toList().size() <= 2) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockState(pos, Blocks.LARGE_FERN.getDefaultState());
                        world.setBlockState(targetPos, Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
    }
}
