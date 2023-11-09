package com.ordana.immersive_weathering.reg;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource extends DamageSource {

    //public static final DamageSource FALLING_ICICLE = new ModDamageSource("fallingIcicle");
    //public static final DamageSource ICICLE = new ModDamageSource("icicle");

    public ModDamageSource(Holder<DamageType> type, @Nullable Entity causingEntity, @Nullable Entity directEntity) {
        super(type, causingEntity, directEntity);
    }
}
