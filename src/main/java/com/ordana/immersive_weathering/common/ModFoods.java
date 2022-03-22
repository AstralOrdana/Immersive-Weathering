package com.ordana.immersive_weathering.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.2F).alwaysEat().fast()
            .effect(()->new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 0.1F).build();
    public static final FoodProperties GOLDEN_MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.8F).alwaysEat().fast()
            .effect(()->new MobEffectInstance(MobEffects.ABSORPTION, 1600, 2), 1F)
            .effect(()->new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1F)
            .effect(()->new MobEffectInstance(MobEffects.WATER_BREATHING, 1600, 2), 1F).build();
    public static final FoodProperties ICICLE = (new FoodProperties.Builder())
            .nutrition(0).saturationMod(0F).alwaysEat().fast()
            .effect(()->new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 1,false,true), 1F)
            .build();
}
