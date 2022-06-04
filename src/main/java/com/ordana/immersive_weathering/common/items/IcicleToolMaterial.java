package com.ordana.immersive_weathering.common.items;

import com.ordana.immersive_weathering.common.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class IcicleToolMaterial implements Tier {

    public static final IcicleToolMaterial INSTANCE = new IcicleToolMaterial();

    @Override
    public int getUses() {
        return 3;
    }

    @Override
    public float getSpeed() {
        return 8f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 1f;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.ICICLE.get());
    }
}
