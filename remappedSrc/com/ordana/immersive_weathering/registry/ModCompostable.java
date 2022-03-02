package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class ModCompostable {
    public static void registerCompostable() {
        CompostingChanceRegistry.INSTANCE.add(ModItems.MOSS_CLUMP, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.AZALEA_FLOWERS, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.OAK_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SPRUCE_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BIRCH_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.JUNGLE_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ACACIA_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.DARK_OAK_BARK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MULCH_BLOCK, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.NULCH_BLOCK, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MULCH, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.NULCH, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MOSS_CLUMP, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.OAK_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SPRUCE_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BIRCH_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.JUNGLE_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ACACIA_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.DARK_OAK_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.AZALEA_LEAF_PILE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FLOWERING_AZALEA_LEAF_PILE, 0.3f);
    }
}
