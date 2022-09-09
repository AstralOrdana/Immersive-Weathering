package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.platform.RegistryPlatform;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;

public class ModCompostable {
    public static void register() {

        RegHelper.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.OAK_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModItems.SPRUCE_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModItems.BIRCH_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModItems.JUNGLE_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModItems.ACACIA_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModItems.DARK_OAK_BARK.get(), 0.8f);
        RegHelper.registerCompostable(ModBlocks.MULCH_BLOCK.get(), 1f);
        RegHelper.registerCompostable(ModBlocks.NULCH_BLOCK.get(), 1f);
        RegHelper.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegHelper.registerCompostable(ModItems.FLOWER_CROWN.get(), 0.3f);
        RegHelper.registerCompostable(ModBlocks.IVY.get(), 0.3f);

        //TODO: re add
        //  LeafPilesRegistry.LEAF_PILES.get().values().forEach(l -> RegistryPlatform.register (l, 0.3f));
    }
}
