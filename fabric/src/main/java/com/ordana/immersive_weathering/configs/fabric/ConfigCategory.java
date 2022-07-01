package com.ordana.immersive_weathering.configs.fabric;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ordana.immersive_weathering.ImmersiveWeathering;

import java.util.List;

public class ConfigCategory extends ConfigEntry {

    private final List<ConfigEntry> values;

    public ConfigCategory(String name, ImmutableList<ConfigEntry> values) {
        super(name);
        this.values = values;
    }

    public List<ConfigEntry> getValues() {
        return values;
    }

    @Override
    public void loadFromJson(JsonObject object) {
        if (object.has(this.name)) {
            JsonElement o = object.get(this.name);
            if (o instanceof JsonObject jo) {
                values.forEach(l -> l.loadFromJson(jo));
            }
            return;
        }
        ImmersiveWeathering.LOGGER.warn("Config file had missing category {}", this.name);
    }

    @Override
    public void saveToJson(JsonObject object) {
        JsonObject category = new JsonObject();
        values.forEach(l -> l.saveToJson(category));
        object.add(this.name, category);
    }


}
