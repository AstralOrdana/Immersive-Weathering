package com.ordana.immersive_weathering.block_growth;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TickSource implements StringRepresentable {
    BLOCK_TICK("block_tick"),
    RAIN("rain"),
    SNOW("snow"),
    LIGHTNING("lightning");

    public static final Codec<TickSource> CODEC = StringRepresentable.fromEnum(TickSource::values, TickSource::byName);
    private static final Map<String, TickSource> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(TickSource::getName, (source) -> source));

    private final String name;

    TickSource(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static TickSource byName(String s) {
        return BY_NAME.get(s);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
