package com.ordana.immersive_weathering.integration;

import net.minecraftforge.fml.ModList;

public class IntegrationHandler {
    public static final boolean quark = ModList.get().isLoaded("quark");
    public static final boolean sereneSeasons = ModList.get().isLoaded("sereneseasons");
}
