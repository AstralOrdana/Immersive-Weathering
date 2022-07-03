package com.ordana.immersive_weathering.mixins.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Biome.class)
public interface BiomeAccessor {

    @Invoker
    float invokeGetTemperature(BlockPos pos);
}
