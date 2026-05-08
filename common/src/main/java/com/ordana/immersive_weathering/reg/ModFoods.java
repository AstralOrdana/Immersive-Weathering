package com.ordana.immersive_weathering.reg;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties ICICLE = (new FoodProperties.Builder())
            .nutrition(0).saturationMod(0F).alwaysEat().fast()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 1, false, false), 1F)
            .build();
}
