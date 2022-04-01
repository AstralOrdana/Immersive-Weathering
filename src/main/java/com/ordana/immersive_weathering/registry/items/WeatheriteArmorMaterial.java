package com.ordana.immersive_weathering.registry.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

class WeatheriteArmorMaterial implements ArmorMaterial {

    public static final WeatheriteArmorMaterial INSTANCE = new WeatheriteArmorMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return 42069;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 69;
    }

    @Override
    public int getEnchantability() {
        return 420;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.WEATHERITE_INGOT);
    }

    @Override
    public String getName() {
        return "weatherite";
    }

    @Override
    public float getToughness() {
        return 69f;
    }

    @Override
    public float getKnockbackResistance() {
        return 69;
    }
}
