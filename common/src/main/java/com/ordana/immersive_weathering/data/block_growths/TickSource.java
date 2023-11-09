package com.ordana.immersive_weathering.data.block_growths;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum TickSource implements StringRepresentable {
    BLOCK_TICK("block_tick"),
    RAIN("rain"),
    SNOW("snow"),
    LIGHTNING("lightning_strike"),
    CLEAR_SKY("clear_sky");

    public static final Codec<TickSource> CODEC = StringRepresentable.fromEnum(TickSource::values);

    private final String name;

    TickSource(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
