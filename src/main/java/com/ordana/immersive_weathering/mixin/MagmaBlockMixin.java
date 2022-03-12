package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin {

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        if (random.nextFloat() > 0.5f) {
            if (!world.isAreaLoaded(pos, 2)) return;
            var targetPos = pos.relative(Direction.getRandom(random));
            if (world.getBlockState(targetPos).is(Blocks.NETHERRACK)) {
                if (WeatheringHelper.canMagmaSpread(pos, 3, world, 8)) {
                    world.setBlockAndUpdate(targetPos, Blocks.MAGMA_BLOCK.defaultBlockState());
                }
            }
        }
    }
}