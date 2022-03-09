package com.ordana.immersive_weathering.registry.blocks.crackable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Random;
import java.util.function.Supplier;

public class CrackableSlabBlock extends CrackedSlabBlock {

    public CrackableSlabBlock(CrackLevel crackLevel, Supplier<Item> brickItem, BlockBehaviour.Properties settings) {
        super(crackLevel, brickItem, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WEATHERABLE, false));
    }

    @Override
    public boolean isWeathering(BlockState state) {
        return state.getValue(WEATHERABLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(WEATHERABLE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.onNeighborChange(state, level, pos, neighbor);
        if (level instanceof ServerLevel serverLevel) {
            boolean weathering = this.getCrackSpreader().canEventuallyWeather(state, pos, serverLevel);
            if (state.getValue(WEATHERABLE) != weathering) {
                //update weathering state
                serverLevel.setBlockAndUpdate(pos, state.setValue(WEATHERABLE, weathering));
            }
        }
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        BlockState state = super.getStateForPlacement(placeContext);
        if (state != null) {
            boolean weathering = this.shouldWeather(state, placeContext.getClickedPos(), placeContext.getLevel());
            state.setValue(WEATHERABLE, weathering);
        }
        return state;
    }

    //-----weathereable-end---


    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            var opt = this.getNextCracked(state);
            BlockState newState = opt.orElse(state.setValue(WEATHERABLE,false));
            serverLevel.setBlockAndUpdate(pos, newState);
        }
    }

}
