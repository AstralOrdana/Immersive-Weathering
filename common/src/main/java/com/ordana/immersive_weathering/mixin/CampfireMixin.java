package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {

    protected CampfireMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "isSmokeSource", at = @At("HEAD"), cancellable = true)
    private void isSmokeSource(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModTags.SMOKEY_BLOCKS)) cir.setReturnValue(true);
    }
}