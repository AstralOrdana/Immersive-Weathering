package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtBlockMixin extends Block implements BonemealableBlock {

    protected RootedDirtBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        boolean space = false;
        for (Direction dir : Direction.values()) {
            var targetState = level.getBlockState(pos.relative(dir));
            if (dir != Direction.UP &&
                targetState.canBeReplaced() &&
                !targetState.is(Blocks.HANGING_ROOTS) &&
                !targetState.is(ModBlocks.HANGING_ROOTS_WALL.get())) space = true;
        }
        return space;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        WeatheringHelper.growHangingRoots(level, random, pos);
    }


}
