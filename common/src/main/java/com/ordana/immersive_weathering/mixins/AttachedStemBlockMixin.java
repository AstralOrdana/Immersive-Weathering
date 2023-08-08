package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AttachedStemBlock.class)
public abstract class AttachedStemBlockMixin extends Block {

    protected AttachedStemBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "mayPlaceOn", at = @At(value = "HEAD"), cancellable = true)
    protected void mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(ModBlocks.MULCH_BLOCK.get())) cir.setReturnValue(true);
    }
}
