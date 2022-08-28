package com.ordana.immersive_weathering.integration.forge;

import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Map;

public class SereneSeasonsCompatImpl {
    /*
    private static ForgeConfigSpec.ConfigValue<List<Season.SubSeason>> ALLOWED_SUB_SEASONS;
    private static Map<Season, Float> TEMP_CHANGE_FOR_SEASON = Map.of(
            Season.AUTUMN, -0.2f,
            Season.WINTER, -0.5f,
            Season.SPRING, +0.1f,
            Season.SUMMER, +0.2f
    );*/
    public static float getNewTemp(float old, Level level) {
      return old;
    }
}
