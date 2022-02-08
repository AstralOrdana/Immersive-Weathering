package com.ordana.immersive_weathering;

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
        FuelRegistry.INSTANCE.add(ModItems.WOODCHIPS_BLOCK, 3200);
    }
}
