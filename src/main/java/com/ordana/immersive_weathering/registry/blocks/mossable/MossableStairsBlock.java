package com.ordana.immersive_weathering.registry.blocks.mossable;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MossableStairsBlock extends MossyStairsBlock {

    public MossableStairsBlock(MossLevel mossLevel, BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(mossLevel, baseBlockState, settings);
        this.setDefaultState(this.getDefaultState().with(WEATHERABLE, WeatheringState.FALSE));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeatherable(state);
    }

    //-----weathereable-start---

    @Override
    public boolean isWeatherable(BlockState state) {
        return state.contains(WEATHERABLE) && state.get(WEATHERABLE).isWeatherable();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder);
        stateBuilder.add(WEATHERABLE);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.neighborUpdate(state, world, pos, block, neighbor,true);
        this.updateWeatheredStateOnNeighborChanged(state, world, pos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext placeContext) {
        BlockState state = super.getPlacementState(placeContext);
        return getWeatheredStateForPlacement(state, placeContext.getBlockPos(), placeContext.getWorld());
    }

    //-----weathereable-end---


    @Override
    public void randomTick(BlockState state, ServerWorld serverWorld, BlockPos pos, Random random) {
        this.tryWeather(state, serverWorld, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.updateNeighbors(pos, this);
    }
}