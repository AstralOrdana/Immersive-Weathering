package com.ordana.immersive_weathering.reg;

import net.mehvahdjukaar.moonlight.api.platform.RegHelper;

public class ModCompostable {
    public static void register() {
        ModItems.BARK.values().forEach(b -> RegHelper.registerCompostable(b, 0.8f));
        ModBlocks.LEAF_PILES.values().forEach(b -> RegHelper.registerCompostable(b, 0.3f));
        RegHelper.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        RegHelper.registerCompostable(ModBlocks.MULCH_BLOCK.get(), 1f);
        RegHelper.registerCompostable(ModBlocks.NULCH_BLOCK.get(), 1f);
        RegHelper.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.FLOWER_CROWN.get(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.WEEDS.get(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.IVY.get(), 0.3f);
    }
}
