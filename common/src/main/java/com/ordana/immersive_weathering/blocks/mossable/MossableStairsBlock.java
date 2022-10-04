package com.ordana.immersive_weathering.blocks.mossable;

import net.minecraft.util.RandomSource;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class MossableStairsBlock extends MossyStairsBlock {

    public MossableStairsBlock(MossLevel mossLevel, Supplier<Block> baseBlockState, Properties settings) {
        super(mossLevel,baseBlockState, settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WEATHERABLE, WeatheringState.FALSE));
    }

    @Override
    public boolean isWeathering(BlockState state) {
        return state.hasProperty(WEATHERABLE) && state.getValue(WEATHERABLE).isWeathering();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(WEATHERABLE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        this.updateWeatheredStateOnNeighborChanged(state, level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        BlockState state = super.getStateForPlacement(placeContext);
        return getWeatheredStateForPlacement(state, placeContext.getClickedPos(), placeContext.getLevel());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random){
        this.tryWeather(state, serverLevel, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.updateNeighborsAt(pos, this);
    }

}
