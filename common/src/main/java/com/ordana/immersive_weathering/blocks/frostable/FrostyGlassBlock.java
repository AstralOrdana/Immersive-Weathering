package com.ordana.immersive_weathering.blocks.frostable;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Random;

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
        tryUnFrost(state, world, pos);
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState neighborState, Direction direction) {
        if (neighborState.is(Blocks.GLASS)) return true;
        return super.skipRendering(blockState, neighborState, direction);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (neighborState.is(this) || neighborState.is(Blocks.GLASS)) return true;
        return false;
    }

}
