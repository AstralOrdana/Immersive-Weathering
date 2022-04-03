package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class SoilBlock extends SnowyBlock implements Fertilizable {
    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");


    public SoilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FERTILE, true).with(SNOWY, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FERTILE);
        builder.add(SNOWY);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.contains(FERTILE) && state.get(FERTILE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.isOf(Blocks.PODZOL)) {
            if (state.get(FERTILE)) {
                var targetPos = pos.up();
                if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir()) {
                    if (!world.isChunkLoaded(pos)) return;
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                            p -> p.getBlock() == Blocks.FERN, 8)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockState(targetPos, Blocks.FERN.getDefaultState(), 2);
                        }
                        else {
                            world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        else if (state.isOf(ModBlocks.HUMUS)) {
            if (state.get(FERTILE)) {
                var targetPos = pos.up();
                if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir()) {
                    if (!world.isChunkLoaded(pos)) return;
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                            p -> p.getBlock() == Blocks.GRASS, 8)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockState(targetPos, Blocks.GRASS.getDefaultState(), 2);
                        }
                        else {
                            world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.PODZOL.getDefaultState().with(FERTILE, true));
        world.setBlockState(pos, state.getBlock().getDefaultState().with(FERTILE, true));

    }
}
