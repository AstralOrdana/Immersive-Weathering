package com.ordana.immersive_weathering.util;

import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public class TemperatureManager {
//TODO: add more uses. consider local and global temperature
    //biome coldEnoght to snow internally calls get temp <0.15

    //same temperature as jungle
    private static final float SNOW_MELT_TEMPERATURE = 0.95f;
    private static final float SNOW_GROW_TEMPERATURE = 0.15f; //one uses for coldEnoughtToSnow

    public static boolean snowGrowthCanGrowSnowyBlock(BlockPos pos, Supplier<Holder<Biome>> biome) {
        return biome.get().value().coldEnoughToSnow(pos);
    }

    public static boolean canSnowMelt(BlockPos pos, Level level) {
        //TODO: rethink. Use biome temperature using biome.getTemperature as well as taking into accoutn more local conditions
        Holder<Biome> biome = level.getBiome(pos);
        return biome.is(BiomeTags.SNOW_GOLEM_MELTS) || //Unneded really, just here for redundancy
                getTemperature(pos, biome.value()) >= SNOW_MELT_TEMPERATURE;
    }


    public static boolean hasSandstorm(ServerLevel level, BlockPos pos) {
        return level.isRaining() && level.getBiome(pos).is(ModTags.HAS_SANDSTORM);
    }

    private static float getTemperature(BlockPos pos, Biome biome) {
        return biome.getTemperature(pos);
    }

    public static float getLocalTemperature(ServerLevel level, BlockPos pos, Biome biome){
        float local = getTemperature(pos, biome);

        return local;
    }


    /**
     * Use Cases:
     *
     * Snowy blocks melting
     * Snowy blocks forming
     * Icicle melting
     * Icicle forming
     *
     *
     */

}
