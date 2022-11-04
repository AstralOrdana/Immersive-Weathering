package com.ordana.immersive_weathering.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class TemperatureManager {

    public static boolean snowGrowthCanGrowSnowyBlock(BlockPos pos, Holder<Biome> biome) {
        return biome.value().coldEnoughToSnow(pos);
    }

}
