package com.ordana.immersive_weathering.registry.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class WeatheriteToolMaterial implements ToolMaterial {

    public static final WeatheriteToolMaterial INSTANCE = new WeatheriteToolMaterial();

    public int getDurability() {
        return 42069;
    }


    public float getMiningSpeedMultiplier() {
        return 69f;
    }


    public float getAttackDamage() {
        return 1f;
    }


    public int getMiningLevel() {
        return 5;
    }


    public int getEnchantability() {
        return 69;
    }


    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.WEATHERITE_INGOT);
    }
}
