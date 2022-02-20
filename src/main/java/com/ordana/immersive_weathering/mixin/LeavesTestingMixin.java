package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.LeavesBlockTesting;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
class LeavesTestingMixin {
    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        {
            LeavesBlockTesting.randomTick(state, world, pos, random);
        }
    }
}
