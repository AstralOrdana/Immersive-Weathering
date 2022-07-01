package com.ordana.immersive_weathering.registry_delete.blocks;

import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.lighting.LayerLightEngine;

import java.util.Random;

public class ModMyceliumBlock extends MyceliumBlock implements BonemealableBlock, IConditionalGrowingBlock {
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");

    public ModMyceliumBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FERTILE, true).setValue(SNOWY, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FERTILE);
        builder.add(SNOWY);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.hasProperty(FERTILE) && state.getValue(FERTILE);
    }

    private static boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && (Integer)blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(world, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockPos).is(FluidTags.WATER);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(FERTILE)) {
            if (!canBeGrass(state, world, pos)) {
                world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
            if (state.is(Blocks.DIRT)) return;
            else if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if ((world.getBlockState(blockPos).is(Blocks.DIRT) || (world.getBlockState(blockPos).is(Blocks.GRASS_BLOCK))) && canPropagate(blockState, world, blockPos)) {
                        world.setBlockAndUpdate(blockPos, blockState.setValue(SpreadingSnowyDirtBlock.SNOWY, world.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, Blocks.MYCELIUM.defaultBlockState().setValue(FERTILE, true));
    }
}
