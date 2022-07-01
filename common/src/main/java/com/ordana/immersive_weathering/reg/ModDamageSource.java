package com.ordana.immersive_weathering.reg;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSource extends DamageSource {

    public static final DamageSource FALLING_ICICLE = new ModDamageSource("fallingIcicle").damageHelmet();
    public static final DamageSource ICICLE = new ModDamageSource("icicle").bypassArmor().setIsFall();

    protected ModDamageSource(String name) {
        super(name);
    }
}
