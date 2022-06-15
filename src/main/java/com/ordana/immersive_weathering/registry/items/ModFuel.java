package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuel {
    public static void registerFuel() {
        FuelRegistry.INSTANCE.add(ModItems.OAK_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.SPRUCE_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.BIRCH_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.JUNGLE_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.ACACIA_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.DARK_OAK_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.MANGROVE_BARK, 200);
        FuelRegistry.INSTANCE.add(ModItems.SOOT, 100);

        FuelRegistry.INSTANCE.add(ModItems.CHARRED_LOG, 800);
        FuelRegistry.INSTANCE.add(ModItems.CHARRED_PLANKS, 200);
        FuelRegistry.INSTANCE.add(ModItems.CHARRED_SLAB, 100);
        FuelRegistry.INSTANCE.add(ModItems.CHARRED_STAIRS, 200);
        FuelRegistry.INSTANCE.add(ModItems.CHARRED_FENCE, 200);
        FuelRegistry.INSTANCE.add(ModItems.CHARRED_FENCE_GATE, 200);
    }
}
