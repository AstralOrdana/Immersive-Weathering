package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtBlockMixin extends Block {

    public RootedDirtBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.008f) {
            if (!world.isAreaLoaded(pos, 4)) return;
            Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
            BlockPos targetPos = pos.relative(dir);
            BlockState targetState = world.getBlockState(targetPos);
            BlockState newState = dir == Direction.DOWN ? Blocks.HANGING_ROOTS.defaultBlockState() :
                    ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir.getOpposite());
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
}
