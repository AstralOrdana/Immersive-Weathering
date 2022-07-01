package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.platform.RegistryPlatform;

public class ModCompostable {
    public static void register() {

        RegistryPlatform.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegistryPlatform.registerCompostable(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        RegistryPlatform.registerCompostable(ModItems.OAK_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModItems.SPRUCE_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModItems.BIRCH_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModItems.JUNGLE_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModItems.ACACIA_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModItems.DARK_OAK_BARK.get(), 0.8f);
        RegistryPlatform.registerCompostable(ModBlocks.MULCH_BLOCK.get(), 1f);
        RegistryPlatform.registerCompostable(ModBlocks.NULCH_BLOCK.get(), 1f);
        RegistryPlatform.registerCompostable(ModItems.MOSS_CLUMP.get(), 0.5f);
        RegistryPlatform.registerCompostable(ModItems.FLOWER_CROWN.get(), 0.3f);
        RegistryPlatform.registerCompostable(ModBlocks.IVY.get(), 0.3f);

        //TODO: re add
        //  LeafPilesRegistry.LEAF_PILES.get().values().forEach(l -> RegistryPlatform.register (l, 0.3f));
    }
}
