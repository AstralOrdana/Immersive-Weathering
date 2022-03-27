package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;

public class ModMyceliumBlock extends MyceliumBlock implements Fertilizable {
    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");

    public ModMyceliumBlock(Settings settings) {
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

    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        }
        if (state.isOf(Blocks.DIRT)) return;
        else if (world.getLightLevel(pos.up()) >= 9) {
            BlockState blockState = this.getDefaultState();
            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockPos upPos = blockPos.up();
                BlockState upState = world.getBlockState(upPos);
                if ((world.getBlockState(blockPos).isOf(Blocks.DIRT) || (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK))) && canSpread(blockState, world, blockPos) && (upState.isAir())) {
                    world.setBlockState(blockPos, blockState.with(SpreadableBlock.SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                }
            }
        }
        if (state.get(FERTILE)) {
            if (state.isOf(Blocks.MYCELIUM)) {
                BlockPos targetPos = pos.up();
                if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir()) {
                    if (!world.isChunkLoaded(pos)) return;
                    if (!WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, world, b -> b.isIn(ModTags.SMALL_MUSHROOMS), 2)) {
                        world.setBlockState(targetPos, (random.nextFloat() > 0.5f ?
                                Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM).getDefaultState(), 2);

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
        world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState().with(FERTILE, true));
    }
}
