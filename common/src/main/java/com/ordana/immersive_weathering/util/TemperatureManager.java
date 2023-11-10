package com.ordana.immersive_weathering.util;

import java.util.function.Supplier;

import com.ordana.immersive_weathering.reg.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public class TemperatureManager {

    public static boolean snowGrowthCanGrowSnowyBlock(BlockPos pos, Supplier<Holder<Biome>> biome) {
        return biome.get().value().coldEnoughToSnow(pos);
    }

    public static boolean canSnowMelt(BlockPos pos, Supplier<Holder<Biome>> biome) {
        return biome.get().value().warmEnoughToRain(pos);
    }

    public static boolean hasSandstorm(ServerLevel level, BlockPos pos, Supplier<Holder<Biome>> biome) {
        return level.isRaining() && biome.get().is(ModTags.HAS_SANDSTORM);
    }

}
