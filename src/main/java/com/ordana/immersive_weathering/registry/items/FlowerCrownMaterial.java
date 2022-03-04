package com.ordana.immersive_weathering.registry.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

class FlowerCrownMaterial implements ArmorMaterial {

    private static final int[] DURABILITY = {256, 256, 256, 256};
    private static final int[] PROTECTION = {1, 1, 1, 1};
    public static final FlowerCrownMaterial INSTANCE = new FlowerCrownMaterial();

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return DURABILITY[slot.getIndex()];
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return PROTECTION[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 60;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.AZALEA_FLOWERS);
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
