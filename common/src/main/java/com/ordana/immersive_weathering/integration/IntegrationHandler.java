package com.ordana.immersive_weathering.integration;

import com.ordana.immersive_weathering.platform.CommonPlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;

public class IntegrationHandler {
    public static final boolean quark = PlatformHelper.isModLoaded("quark");
    public static final boolean sereneSeasons = PlatformHelper.isModLoaded("sereneseasons");
    public static boolean clothConfig = PlatformHelper.isModLoaded("cloth_config");
    public static boolean modMenu = PlatformHelper.isModLoaded("mod_menu");
}
