package com.ordana.immersive_weathering.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoods {
    public static final FoodComponent POND_WATER = (new FoodComponent.Builder()).hunger(0).saturationModifier(0F).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 2), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 1), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 2), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 2), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 1200, 2), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1600, 2), 1F)
            .build();

    public static final FoodComponent AZALEA_FLOWER = (new FoodComponent.Builder()).hunger(0).saturationModifier(0F).alwaysEdible().snack().build();

    public static final FoodComponent MOSS_CLUMP = (new FoodComponent.Builder()).hunger(1).saturationModifier(0.2F).alwaysEdible().snack().build();

    public static final FoodComponent GOLDEN_MOSS_CLUMP = (new FoodComponent.Builder()).hunger(4).saturationModifier(0.6F).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1), 1F).build();

    public static final FoodComponent ENCHANTED_GOLDEN_MOSS_CLUMP = (new FoodComponent.Builder()).hunger(6).saturationModifier(0.8F).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1600, 2), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1), 1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1600, 2), 1F).build();

    public static final FoodComponent ICICLE = (new FoodComponent.Builder()).hunger(0).saturationModifier(0F).alwaysEdible().snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 80, 1,false,true), 1F).build();
}
