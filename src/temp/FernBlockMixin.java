package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TallGrassBlock.class)
public class FernBlockMixin extends Block {

    public FernBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        var targetPos = pos.above();
        if (world.getBlockState(pos).is(Blocks.GRASS)) {
            if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                if (BlockPos.withinManhattanStream(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.TALL_GRASS::equals)
                        .toList().size() <= 2) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockAndUpdate(pos, Blocks.TALL_GRASS.defaultBlockState());
                        world.setBlockAndUpdate(targetPos, Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
        if (world.getBlockState(pos).is(Blocks.FERN)) {
            if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                if (BlockPos.withinManhattanStream(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.LARGE_FERN::equals)
                        .toList().size() <= 2) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockAndUpdate(pos, Blocks.LARGE_FERN.defaultBlockState());
                        world.setBlockAndUpdate(targetPos, Blocks.LARGE_FERN.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
    }
}
