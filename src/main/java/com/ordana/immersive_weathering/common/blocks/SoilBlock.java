package com.ordana.immersive_weathering.common.blocks;


import com.ordana.immersive_weathering.data.BlockGrowthConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Random;

public class SoilBlock extends SnowyDirtBlock implements BonemealableBlock {

    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");

    private final BlockGrowthConfiguration growth;

    public SoilBlock(Properties settings, BlockGrowthConfiguration growth) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FERTILE, true).setValue(SNOWY, false));
        this.growth = growth;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(FERTILE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(FERTILE)) {
            var targetPos = pos.above();
            if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir() && growth != null) {
                if (!world.isAreaLoaded(pos, 4)) return;
               // if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                //        growth::contains, 8)) {
                //    world.setBlock(targetPos, growth.sample(random).getFirst(), 2);
               // }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter getter, BlockPos pos, BlockState state, boolean b) {
        return getter.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, state.setValue(FERTILE,true));
    }
}
