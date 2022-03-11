package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModHangingRootsBlock;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtBlockMixin extends Block implements Fertilizable {

    public RootedDirtBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Shadow
    public abstract boolean canGrow(World world, Random random, BlockPos pos, BlockState state);

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Direction rootDir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos rootPos = pos.offset(rootDir);
        BlockState targetState = world.getBlockState(rootPos);
        BlockState toPlace = Blocks.HANGING_ROOTS.getDefaultState();
        if(targetState.isOf(Blocks.WATER)) {
            toPlace = toPlace.with(ModHangingRootsBlock.WATERLOGGED, true);
        }
        else if(!targetState.isAir())return;
        if (rootDir == Direction.DOWN) {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.HANGING, true), 3);
        }
        else {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.FACING, (rootDir)).with(ModHangingRootsBlock.HANGING, Boolean.FALSE), 3);
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isChunkLoaded(pos)) return;
        if (!WeatheringHelper.hasEnoughBlocksAround(pos, 2, world, b->b.isOf(Blocks.ROOTED_DIRT),14)) {
            if (WeatheringHelper.canRootsSpread(pos, 5, 2, world, 16)) {
                Direction rootDirection = WeatheringHelper.ROOT_DIRECTIONS.getDataOrEmpty(random).get();
                var targetPos = pos.offset(rootDirection);
                if (world.getBlockState(targetPos).isOf(Blocks.DIRT)) {
                    world.setBlockState(targetPos, Blocks.ROOTED_DIRT.getDefaultState(), 3);
                }
            }
        }
        if (random.nextFloat() < 0.5f) {
            if (world.getBlockState(pos).isOf(Blocks.ROOTED_DIRT)) {
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, world, b->b.isOf(Blocks.HANGING_ROOTS),8)) {
                    Direction rootDir = Direction.values()[1 + random.nextInt(5)].getOpposite();
                    BlockPos rootPos = pos.offset(rootDir);
                    BlockState targetState = world.getBlockState(rootPos);
                    BlockState toPlace = Blocks.HANGING_ROOTS.getDefaultState();
                    if(targetState.isOf(Blocks.WATER)) {
                        toPlace = toPlace.with(ModHangingRootsBlock.WATERLOGGED, true);
                    }
                    else if(!targetState.isAir())return;
                    if (rootDir == Direction.DOWN) {
                        world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.HANGING, true), 3);
                    }
                    else {
                        world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.FACING, (rootDir)).with(ModHangingRootsBlock.HANGING, Boolean.FALSE), 3);
                    }
                }
            }
        }
    }
}
