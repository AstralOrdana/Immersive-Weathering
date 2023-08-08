package com.ordana.immersive_weathering.blocks.crackable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Supplier;

public class CrackableBlock extends CrackedBlock {

    public CrackableBlock(CrackLevel crackLevel, Supplier<Item> brickItem, Properties settings) {
        super(crackLevel, brickItem, settings);
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
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        this.tryWeather(state, serverLevel, pos, random);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.updateNeighborsAt(pos, this);
        //level.blockEvent(pos,this,6,1);
    }

    /*
    //TODO: debug

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int i, int p_60494_) {
        if(i==6){
            if(level.isClientSide){
                ModParticles.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.FLAME, UniformInt.of(3,4));

            }
            return true;
        }return false;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource p_49891_) {
        super.animateTick(state, level, pos, p_49891_);
        if(state.getValue(WEATHERABLE).isWeathering())
        ModParticles.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.HAPPY_VILLAGER, UniformInt.of(5,7));
    }*/
}
