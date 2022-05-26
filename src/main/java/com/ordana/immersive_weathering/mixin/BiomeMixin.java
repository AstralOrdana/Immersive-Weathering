package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.block_growth.TemperatureAccessWidener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Biome.class)
public class BiomeMixin implements TemperatureAccessWidener {
    @Override
    public float getTempForPredicate(BlockPos pos) {
        return 0;
    }
}
