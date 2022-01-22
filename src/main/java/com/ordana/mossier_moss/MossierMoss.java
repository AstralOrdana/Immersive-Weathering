package com.ordana.mossier_moss;

import com.ordana.mossier_moss.registry.blocks.ModBlocks;
import com.ordana.mossier_moss.registry.items.ModItems;
import net.fabricmc.api.ModInitializer;

public class MossierMoss implements ModInitializer {

    public static final String MOD_ID = "mossier_moss";

    @Override
    public void onInitialize() {

        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }
}
