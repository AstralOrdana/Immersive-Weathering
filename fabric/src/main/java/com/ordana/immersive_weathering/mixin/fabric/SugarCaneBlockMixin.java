package com.ordana.immersive_weathering.mixin.fabric;

import com.ordana.immersive_weathering.blocks.soil.CrackedMudBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlaceAt(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.below());
        if ((blockState.is(ModBlocks.CRACKED_MUD.get()) && blockState.getValue(CrackedMudBlock.SOAKED)) || (blockState.is(ModBlocks.MULCH_BLOCK.get()))) {
            cir.setReturnValue(true);
        }
    }
}
