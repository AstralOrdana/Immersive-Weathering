package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrampleableDirtBlock extends Block {
    public TrampleableDirtBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.getBlockState(pos).isOf(Blocks.DIRT)) {
            if (world.random.nextFloat() < 0.001f) {
                world.setBlockState(pos, Blocks.COARSE_DIRT.getDefaultState());
            }
        }
        if (world.getBlockState(pos).isOf(Blocks.COARSE_DIRT)) {
            if (world.random.nextFloat() < 0.001f) {
                world.setBlockState(pos, Blocks.DIRT_PATH.getDefaultState());
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
