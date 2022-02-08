package com.ordana.immersive_weathering;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent MOSS_CLUMP = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).alwaysEdible().snack().statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1), 0.1F).build();
    public static final FoodComponent GOLDEN_MOSS_CLUMP = (new FoodComponent.Builder()).hunger(6).saturationModifier(0.8F).alwaysEdible().snack().statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1600, 2), 1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1), 1F).statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1600, 2), 1F).build();
}
