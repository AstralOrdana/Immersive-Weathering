package com.ordana.immersive_weathering.common.blocks.frostable;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class FrostyGlassBlock extends AbstractGlassBlock implements Frosty {

    public FrostyGlassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NATURAL);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(NATURAL)) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                world.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
            }
        }
    }
}
