package com.ordana.immersive_weathering.reg;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(1).saturationModifier(0.2F).alwaysEdible().fast()
            .build();

    public static final FoodProperties GOLDEN_MOSS_CLUMP = (new FoodProperties.Builder())
            .nutrition(4).saturationModifier(0.6F).alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1F)
            .build();

    public static final FoodProperties ENCHANTED_GOLDEN_MOSS_CLUMP = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.8F).alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1600, 2), 1F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1F)
            .effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 1600, 2), 1F).build();


    public static final FoodProperties ICICLE = (new FoodProperties.Builder())
            .nutrition(0).saturationModifier(0F).alwaysEdible().fast()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 1, false, false), 1F)
            .build();

    public static final FoodProperties AZALEA_FLOWER = (new FoodProperties.Builder()).nutrition(0).saturationModifier(0F).alwaysEdible().fast().build();


}
