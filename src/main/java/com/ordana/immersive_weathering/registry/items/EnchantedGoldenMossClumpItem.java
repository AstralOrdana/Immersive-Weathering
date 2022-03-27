package com.ordana.immersive_weathering.registry.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedGoldenMossClumpItem extends Item {
    public EnchantedGoldenMossClumpItem(Item.Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
