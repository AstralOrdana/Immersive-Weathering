package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
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
        var targetPos = pos.below();
        if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).is(Blocks.AIR)) {
            if (!world.isAreaLoaded(pos, 4)) return;
            if (WeatheringHelper.hasEnoughBlocksAround(pos, 4, world, b -> b.is(Blocks.HANGING_ROOTS), 8)) {
                world.setBlockAndUpdate(targetPos, Blocks.HANGING_ROOTS.defaultBlockState());
            }
        }

    }
}
