package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.data.BlockGrowthManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Random;

public class ModGrassBlock extends GrassBlock implements BonemealableBlock {
    public static final BooleanProperty FERTILE = SoilBlock.FERTILE;

    public ModGrassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(FERTILE) && super.isRandomlyTicking(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(FERTILE)) {
            BlockGrowthManager.execute(state, level, pos);
        }
    }


    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        state = state.setValue(FERTILE, true);
        level.setBlock(pos, state, 2);
        super.performBonemeal(level,random,pos,state);
    }
}
