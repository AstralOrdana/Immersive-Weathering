package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.server.DataPackContents;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DataPackContents.class)
public abstract class DataPackContentMixin {

    @Inject(method = "refresh", at = @At("TAIL"))
    private void onTagReload(DynamicRegistryManager registryAccess, CallbackInfo ci){
        BlockGrowthHandler.rebuild(registryAccess);
    }
}
