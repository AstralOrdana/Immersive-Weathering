package com.ordana.immersive_weathering.items;

import net.mehvahdjukaar.moonlight.api.item.WoodBasedItem;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class FuelWoodBasedItem extends WoodBasedItem {

    private final int burnTime;

    public FuelWoodBasedItem(Item.Properties properties, WoodType woodType, int burnTime) {
        super(properties, woodType);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
        return burnTime;
    }
}