package com.ordana.immersive_weathering.integration;

import com.ordana.immersive_weathering.configs.fabric.ConfigSpec;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ClothConfigCompat.makeScreen(parent, ConfigSpec.COMMON_INSTANCE);
    }
}