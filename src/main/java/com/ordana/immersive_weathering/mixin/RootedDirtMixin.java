package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtMixin extends Block implements BonemealableBlock {

    public RootedDirtMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.1f) {
            if (!world.isAreaLoaded(pos, 4)) return;

            //spread roots downwards
            if (WeatheringHelper.canRootsSpread(pos, 5, 2, world, 15)) {
                Direction rootDirection = WeatheringHelper.ROOT_DIRECTIONS.getRandomValue(random).get();
                var targetPos = pos.relative(rootDirection);
                if (world.getBlockState(targetPos).is(Blocks.DIRT)) {
                    world.setBlock(targetPos, Blocks.ROOTED_DIRT.defaultBlockState(), 3);
                }
            }


            //spawn hanging roots
            Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
            BlockPos targetPos = pos.relative(dir);
            BlockState targetState = world.getBlockState(targetPos);
            BlockState newState = dir == Direction.DOWN ? Blocks.HANGING_ROOTS.defaultBlockState() :
                    ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
            if (targetState.is(Blocks.WATER)) {
                newState = newState.setValue(BlockStateProperties.WATERLOGGED, true);
            } else if (!targetState.isAir()) {
                return;
            }
            if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, world, b -> b.is(Blocks.HANGING_ROOTS), 8)) {
                world.setBlockAndUpdate(targetPos, newState);
            }
        }

    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos targetPos = pos.relative(dir);
        BlockState targetState = world.getBlockState(targetPos);
        BlockState newState = dir == Direction.DOWN ? Blocks.HANGING_ROOTS.defaultBlockState() :
                ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
        if (targetState.is(Blocks.WATER)) {
            newState = newState.setValue(BlockStateProperties.WATERLOGGED, true);
        } else if (!targetState.isAir()) {
            return;
        }
        world.setBlockAndUpdate(targetPos, newState);
    }
}
