package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {

    protected CampfireMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "isSignalFireBaseBlock", at = @At("HEAD"), cancellable = true)
    private void isSmokeSource(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(ModTags.SMOKEY_BLOCKS)) cir.setReturnValue(true);
    }
}