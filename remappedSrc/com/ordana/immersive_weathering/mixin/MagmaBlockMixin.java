package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MagmaBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin {
    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        for (var direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            if (BlockPos.streamOutwards(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.LAVA::equals)) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.MAGMA_BLOCK::equals)
                        .toList().size() <= 8) {
                    float f = 0.5f;
                    if (random.nextFloat() > 0.5f) {
                        if (world.getBlockState(targetPos).isOf(Blocks.NETHERRACK)) {
                            world.setBlockState(targetPos, Blocks.MAGMA_BLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
