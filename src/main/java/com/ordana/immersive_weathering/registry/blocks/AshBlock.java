package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class AshBlock extends FallingBlock {
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public AshBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockView pLevel, BlockPos pPos, ShapeContext pContext) {
        if (pContext instanceof EntityShapeContext c) {
            var e = c.getEntity();
            if (e instanceof LivingEntity) {
                return BOTTOM_SHAPE;
            }
        }
        return this.getOutlineShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return -1842206;
    }
}
