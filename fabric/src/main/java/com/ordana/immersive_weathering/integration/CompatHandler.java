package com.ordana.immersive_weathering.integration;

import net.fabricmc.loader.api.FabricLoader;

public class CompatHandler {

    public static boolean clothConfig = FabricLoader.getInstance().isModLoaded("cloth_config");
    public static boolean modMenu = FabricLoader.getInstance().isModLoaded("mod_menu");
}
