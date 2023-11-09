package com.ordana.immersive_weathering.integrations;

import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;

public class IntegrationHandler {
    public static final boolean quark = PlatHelper.isModLoaded("quark");
    public static final boolean sereneSeasons = PlatHelper.isModLoaded("sereneseasons");
    public static boolean clothConfig = PlatHelper.isModLoaded("cloth_config");
    public static boolean modMenu = PlatHelper.isModLoaded("mod_menu");
}
