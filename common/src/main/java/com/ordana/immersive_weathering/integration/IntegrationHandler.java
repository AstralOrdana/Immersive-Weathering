package com.ordana.immersive_weathering.integration;

import com.ordana.immersive_weathering.platform.CommonPlatform;
import net.fabricmc.loader.api.FabricLoader;

public class IntegrationHandler {
    public static final boolean quark = CommonPlatform.isModLoaded("quark");
    public static final boolean sereneSeasons = CommonPlatform.isModLoaded("sereneseasons");
    public static boolean clothConfig = CommonPlatform.isModLoaded("cloth_config");
    public static boolean modMenu = CommonPlatform.isModLoaded("mod_menu");
}
