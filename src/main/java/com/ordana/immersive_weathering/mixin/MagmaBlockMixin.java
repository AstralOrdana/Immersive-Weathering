package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
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
        if (random.nextFloat() > 0.5f) {
            if (!world.isChunkLoaded(pos)) return;
            if (WeatheringHelper.canMagmaSpread(pos, 3, world, 12)) {
                var targetPos = pos.offset(Direction.random(random));
                if (world.getBlockState(targetPos).isOf(Blocks.NETHERRACK)) {
                    world.setBlockState(targetPos, Blocks.MAGMA_BLOCK.getDefaultState(), 3);
                }
            }
        }
    }
}
