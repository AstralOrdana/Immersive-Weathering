package com.ordana.immersive_weathering.registry.blocks.mossable;

import java.util.Optional;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MossableBlock extends MossyBlock{

    public MossableBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WEATHERABLE, false));
    }

    //-----weathereable-start---

    @Override
    public boolean isWeathering(BlockState state) {
        return state.get(WEATHERABLE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder);
        stateBuilder.add(WEATHERABLE);
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
            Optional<BlockState> opt = Optional.empty();
            if(this.getMossSpreader().getWanderWeatheringState(true, pos, serverLevel)) {
                opt = this.getNextMossy(state);
            }
            BlockState newState = opt.orElse(state.with(WEATHERABLE, false));
            serverLevel.setBlockState(pos, newState);
        }
    }

}
