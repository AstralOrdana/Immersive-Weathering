package com.ordana.immersive_weathering.common.blocks.mossable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class MossableWallBlock extends MossyWallBlock {

    public MossableWallBlock(MossLevel mossLevel, BlockBehaviour.Properties settings) {
        super(mossLevel, settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WEATHERABLE, WeatheringState.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return super.getShape(state.setValue(WEATHERABLE, WeatheringState.FALSE), getter, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return super.getCollisionShape(state.setValue(WEATHERABLE, WeatheringState.FALSE), getter, pos, context);
    }

    @Override
    public boolean isWeathering(BlockState state) {
        return state.getValue(WEATHERABLE).isWeathering();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(WEATHERABLE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.onNeighborChange(state, level, pos, neighbor);
        this.updateWeatheredStateOnNeighborChanged(state, level, pos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        BlockState state = super.getStateForPlacement(placeContext);
        return getWeatheredStateForPlacement(state, placeContext.getClickedPos(), placeContext.getLevel());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        this.tryWeather(state, serverLevel, pos, random);

    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        level.updateNeighborsAt(pos, this);
    }
}
