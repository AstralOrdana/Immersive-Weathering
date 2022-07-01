package com.ordana.immersive_weathering.configs;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Locale;
import java.util.function.Supplier;

public abstract class ConfigBuilderWrapper {

    private final String name;
    protected final ConfigType type;

    public ConfigBuilderWrapper(String name, ConfigType type) {
        this.name = name;
        this.type = type;
    }

    public enum ConfigType {
        CLIENT, COMMON;

        public String getFileName(){
            return ImmersiveWeathering.MOD_ID+"-"+this.toString().toLowerCase(Locale.ROOT)+".json";
        }
    }

    public abstract void buildAndRegister();

    public String getName() {
        return name;
    }

    public abstract ConfigBuilderWrapper push(String category);

    public ConfigBuilderWrapper pushPage(String category) {
        push(category);
        return this;
    }

    public abstract ConfigBuilderWrapper pop();

    public abstract Supplier<Boolean> define(String name, boolean defaultValue);

    public abstract Supplier<Double> define(String name, double defaultValue, double min, double max);

    public abstract Supplier<Integer> define(String name, int defaultValue, int min, int max);

    public abstract Supplier<String> define(String name, String defaultValue);

    public abstract <V extends Enum<V>> Supplier<V> define(String name, V defaultValue);


    public static TranslatableComponent descriptionKey(String name) {
        return new TranslatableComponent("text.immersive_weathering." + name);
    }

    public static TranslatableComponent tooltipKey(String name) {
        return new TranslatableComponent("text.immersive_weathering." + name + ".descriptionKey");
    }
}
