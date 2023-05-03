package com.ordana.immersive_weathering.mixins.fabric;

import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Deprecated(forRemoval = true)
@Mixin(ReloadableServerResources.class)
public abstract class DataPackContentMixin {

    @Inject(method = "updateRegistryTags*", at = @At("TAIL"))
    private void onTagReload(RegistryAccess registryAccess, CallbackInfo ci){
        BlockGrowthHandler.RELOAD_INSTANCE.rebuild(registryAccess);
        FluidGeneratorsHandler.RELOAD_INSTANCE.rebuild(registryAccess);
    }
}
