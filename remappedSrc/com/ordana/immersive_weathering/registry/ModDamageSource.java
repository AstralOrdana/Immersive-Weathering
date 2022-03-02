package com.ordana.immersive_weathering.registry;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {
    public static final DamageSource FALLING_ICICLE = (new ModDamageSource("fallingIcicle")).setFallingBlock();
    public static final DamageSource ICICLE = (new ModDamageSource("icicle")).setBypassesArmor().setFromFalling();

    protected ModDamageSource(String name) {
        super(name);
    }
}
