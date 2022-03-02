package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(RootedDirtBlock.class)
public class RootedDirtBlockMixin extends Block {

    public RootedDirtBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var targetPos = pos.down();
        if (world.getBlockState(pos).isOf(Blocks.ROOTED_DIRT)) {
            if (BlockPos.streamOutwards(pos, 4, 4, 4)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.HANGING_ROOTS::equals)
                    .toList().size() <= 8) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                        world.setBlockState(targetPos, Blocks.HANGING_ROOTS.getDefaultState());
                    }
                }
            }
        }
    }
}
