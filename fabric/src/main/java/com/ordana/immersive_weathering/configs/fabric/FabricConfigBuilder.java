package com.ordana.immersive_weathering.configs.fabric;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.configs.ConfigBuilderWrapper;
import com.ordana.immersive_weathering.configs.fabric.values.BoolConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.DoubleConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.EnumConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.IntConfigValue;

import java.util.function.Supplier;

public class FabricConfigBuilder extends ConfigBuilderWrapper {

    private final ImmutableList.Builder<ConfigCategory> categoriesBuilder = new ImmutableList.Builder<>();
    private Pair<String, ImmutableList.Builder<ConfigEntry>> currentCategoryBuilder;

    public FabricConfigBuilder(String name, ConfigBuilderWrapper.ConfigType type) {
        super(name, type);
    }

    @Override
    public void buildAndRegister() {
        assert currentCategoryBuilder == null;
        ConfigSpec spec = new ConfigSpec(this.getName(), categoriesBuilder.build(), type.getFileName());
        spec.loadConfig();
        spec.saveConfig();
        if (type == ConfigType.COMMON) {
            ConfigSpec.COMMON_INSTANCE = spec;
        } else {
            ConfigSpec.CLIENT_INSTANCE = spec;
        }
    }

    @Override
    public FabricConfigBuilder push(String translation) {
        assert currentCategoryBuilder == null;
        currentCategoryBuilder = Pair.of(translation, new ImmutableList.Builder<>());
        return this;
    }

    @Override
    public FabricConfigBuilder pop() {
        assert currentCategoryBuilder != null;
        categoriesBuilder.add(new ConfigCategory(currentCategoryBuilder.getFirst(), currentCategoryBuilder.getSecond().build()));
        this.currentCategoryBuilder = null;
        return this;
    }

    @Override
    public Supplier<Boolean> define(String name, boolean defaultValue) {
        assert currentCategoryBuilder != null;
        var config = new BoolConfigValue(name, defaultValue);
        this.currentCategoryBuilder.getSecond().add(config);
        return config;
    }

    @Override
    public Supplier<Double> define(String name, double defaultValue, double min, double max) {
        assert currentCategoryBuilder != null;
        var config = new DoubleConfigValue(name, defaultValue, min, max);
        this.currentCategoryBuilder.getSecond().add(config);
        return config;
    }

    @Override
    public Supplier<Integer> define(String name, int defaultValue, int min, int max) {
        assert currentCategoryBuilder != null;
        var config = new IntConfigValue(name, defaultValue, min, max);
        this.currentCategoryBuilder.getSecond().add(config);
        return config;
    }

    @Override
    public Supplier<String> define(String name, String defaultValue) {
        assert currentCategoryBuilder != null;
        var config = new StringConfigValue(name, defaultValue);
        this.currentCategoryBuilder.getSecond().add(config);
        return config;
    }

    @Override
    public <V extends Enum<V>> Supplier<V> define(String name, V defaultValue) {
        assert currentCategoryBuilder != null;
        var config = new EnumConfigValue<>(name, defaultValue);
        this.currentCategoryBuilder.getSecond().add(config);
        return config;
    }

}
