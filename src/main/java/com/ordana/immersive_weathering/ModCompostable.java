package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class ModCompostable {
    public static void registerCompostable() {
        CompostingChanceRegistry.INSTANCE.add(ModItems.MOSS_CLUMP, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.OAK_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SPRUCE_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BIRCH_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.JUNGLE_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ACACIA_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.DARK_OAK_BARK, 0.8f);
    }
}
