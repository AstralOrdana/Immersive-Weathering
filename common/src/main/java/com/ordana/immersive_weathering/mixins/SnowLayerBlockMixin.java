package com.ordana.immersive_weathering.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin extends Block {

    protected SnowLayerBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At(value = "HEAD"))
    protected void melt(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        if (serverLevel.isRainingAt(blockPos.above())) {
            serverLevel.removeBlock(blockPos, false);
        }
    }
}
