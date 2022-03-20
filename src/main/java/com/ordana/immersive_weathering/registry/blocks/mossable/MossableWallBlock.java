package com.ordana.immersive_weathering.registry.blocks.mossable;

import java.util.Optional;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MossableWallBlock extends MossyWallBlock {

    public MossableWallBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
        this.setDefaultState(this.getDefaultState().with(WEATHERABLE, false).with(STABLE, false).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView getter, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state.with(WEATHERABLE, true).with(STABLE, true), getter, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView getter, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state.with(WEATHERABLE, true).with(STABLE, true), getter, pos, context);
    }

    //-----weathereable-start---

    @Override
    public boolean isWeatherable(BlockState state) {
        return state.contains(WEATHERABLE) && state.get(WEATHERABLE) && state.contains(STABLE) &&!state.get(STABLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder);
        stateBuilder.add(WEATHERABLE);
        stateBuilder.add(STABLE);
    }

    @Override
    public void neighborUpdate(BlockState state, World level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.neighborUpdate(state, level, pos, block, neighbor, true);
        if (level instanceof ServerWorld serverLevel) {
            boolean weathering = this.shouldWeather(state, pos, level);
            if (state.get(WEATHERABLE) != weathering) {
                //update weathering state
                serverLevel.setBlockState(pos, state.with(WEATHERABLE, weathering));
            }
        }
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext placeContext) {
        BlockState state = super.getPlacementState(placeContext);
        if (state != null) {
            boolean weathering = this.shouldWeather(state, placeContext.getBlockPos(), placeContext.getWorld());
            state.with(WEATHERABLE, weathering);
        }
        return state;
    }

    //-----weathereable-end---


    @Override
    public void randomTick(BlockState state, ServerWorld serverLevel, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            var opt = this.getNextMossy(state);
            BlockState newState = opt.orElse(state.with(WEATHERABLE,false));
            serverLevel.setBlockState(pos, newState);
        }
    }
}
