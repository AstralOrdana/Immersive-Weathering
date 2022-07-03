package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.configs.ConfigBuilder;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.function.Supplier;

public class ForgeConfigBuilder extends ConfigBuilder {

    private final ForgeConfigSpec.Builder builder;

    public ForgeConfigBuilder(String name, ConfigType type) {
        super(name, type);
        this.builder = new ForgeConfigSpec.Builder();
    }

    @Override
    public void buildAndRegister() {
        ModConfig.Type t = this.type == ConfigType.COMMON ? ModConfig.Type.COMMON : ModConfig.Type.CLIENT;
        ModLoadingContext.get().registerConfig(t, this.builder.build());
    }

    @Override
    public ForgeConfigBuilder push(String category) {
        builder.push(category);
        return this;
    }

    @Override
    public ForgeConfigBuilder pop() {
        builder.pop();
        return this;
    }

    @Override
    public Supplier<Boolean> define(String name, boolean defaultValue) {
        var value = builder.translation(tooltipKey(name).getKey()).define(name, defaultValue);
        return value::get;
    }

    @Override
    public Supplier<Double> define(String name, double defaultValue, double min, double max) {
        var value = builder.translation(tooltipKey(name).getKey()).defineInRange(name, defaultValue, min, max);
        return value::get;
    }

    @Override
    public Supplier<Integer> define(String name, int defaultValue, int min, int max) {
        var value = builder.translation(tooltipKey(name).getKey()).defineInRange(name, defaultValue, min, max);
        return value::get;
    }

    @Override
    public Supplier<String> define(String name, String defaultValue) {
        ForgeConfigSpec.ConfigValue<String> value = builder.translation(tooltipKey(name).getKey()).define(name, defaultValue);
        return value::get;
    }

    @Override
    public <V extends Enum<V>> Supplier<V> define(String name, V defaultValue) {
        var value = builder.translation(tooltipKey(name).getKey()).defineEnum(name, defaultValue);
        return value::get;
    }
}
