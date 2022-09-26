package com.ordana.immersive_weathering.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.swing.text.html.BlockView;

public class AshBlock extends FallingBlock {
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public AshBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState pState, BlockView pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext c) {
            var e = c.getEntity();
            if (e instanceof LivingEntity) {
                return BOTTOM_SHAPE;
            }
        }
        return this.getShape(pState, pLevel, pPos, pContext);
    }

    public int getDustColor(BlockState state, BlockView world, BlockPos pos) {
        return -1842206;
    }
}
