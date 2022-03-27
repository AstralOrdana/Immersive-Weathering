package com.ordana.immersive_weathering.registry.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class IcicleToolMaterial implements ToolMaterial {

    public static final IcicleToolMaterial INSTANCE = new IcicleToolMaterial();

    public int getDurability() {
        return 3;
    }


    public float getMiningSpeedMultiplier() {
        return 8f;
    }


    public float getAttackDamage() {
        return 1f;
    }


    public int getMiningLevel() {
        return 1;
    }


    public int getEnchantability() {
        return 10;
    }


    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.ICICLE);
    }
}
