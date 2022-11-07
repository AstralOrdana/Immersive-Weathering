package com.ordana.immersive_weathering.util;

import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

public class TemperatureManager {

    public static boolean snowGrowthCanGrowSnowyBlock(BlockPos pos, Holder<Biome> biome) {
        return biome.value().coldEnoughToSnow(pos);
    }

    public static boolean hasSandstorm(ServerLevel level, BlockPos pos, Holder<Biome> biome) {
        return level.isRaining() && biome.is(ModTags.HAS_SANDSTORM);
    }

}
