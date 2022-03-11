package com.ordana.immersive_weathering.registry.blocks.crackable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrackableStairsBlock extends CrackedStairsBlock {

    public CrackableStairsBlock(CrackLevel crackLevel, Supplier<Block> baseBlockState, AbstractBlock.Settings settings) {
        super(crackLevel, baseBlockState, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WEATHERABLE, false));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeathering(state);
    }

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
        super.neighborUpdate(state, level, pos, block, neighbor,true);
        if (level instanceof ServerWorld serverLevel) {
            boolean weathering = this.shouldWeather(state, pos, serverLevel);
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
            var opt = this.getNextCracked(state);
            BlockState newState = opt.orElse(state.with(WEATHERABLE,false));
            serverLevel.setBlockState(pos, newState);
        }
    }
}
