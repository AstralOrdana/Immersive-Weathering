package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BranchesBlock extends LeavesBlock implements Fertilizable, IConditionalGrowingBlock {
    public BranchesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LEAFY, false).with(WATERLOGGED, false).with(DISTANCE, 7).with(PERSISTENT, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LEAFY);
        stateManager.add(WATERLOGGED);
        stateManager.add(DISTANCE);
        stateManager.add(PERSISTENT);
    }

    public static final BooleanProperty LEAFY = BooleanProperty.of("leafy");

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int rand = random.nextInt(4);
        Direction branchDir = Direction.fromHorizontal(rand);
        BlockPos targetPos = pos.offset(branchDir);
        BlockState targetState = world.getBlockState(targetPos);
        world.setBlockState(pos, state.with(LEAFY, true));
        var branchLeaves = WeatheringHelper.getLeavesFromBranches(state).orElse(null);
        if (branchLeaves == null) return;
        if (targetState.isAir() && state.get(LEAFY)) {
            world.setBlockState(targetPos, branchLeaves.with(LeavesBlock.PERSISTENT, false));
        }
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return super.canFillWithFluid(world, pos, state, fluid);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.get(LEAFY);
    }
}
