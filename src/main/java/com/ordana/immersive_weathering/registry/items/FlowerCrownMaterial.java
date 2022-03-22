package com.ordana.immersive_weathering.registry.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

class FlowerCrownMaterial implements ArmorMaterial {

    public static final FlowerCrownMaterial INSTANCE = new FlowerCrownMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return 256;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 64;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.AZALEA_FLOWERS);
    }

    @Override
    public String getName() {
        return "flower";
    }

    @Override
    public float getToughness() {
        return 0f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
