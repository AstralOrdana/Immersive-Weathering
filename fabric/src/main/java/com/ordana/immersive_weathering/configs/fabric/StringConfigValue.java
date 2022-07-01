package com.ordana.immersive_weathering.configs.fabric;

import com.google.gson.JsonObject;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.fabric.values.ConfigValue;

public class StringConfigValue extends ConfigValue<String> {

    public StringConfigValue(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean isValid(String value) {
        return true;
    }

    @Override
    public void loadFromJson(JsonObject element) {
        if (element.has(this.name)) {
            try {
                this.value = element.get(this.name).getAsString();
                if (this.isValid(value)) return;
                //if not valid it defaults
                this.value = defaultValue;
            } catch (Exception ignored) {
            }
            ImmersiveWeathering.LOGGER.warn("Config file had incorrect entry {}, correcting", this.name);
        } else {
            ImmersiveWeathering.LOGGER.warn("Config file had missing entry {}", this.name);
        }
    }

    @Override
    public void saveToJson(JsonObject object) {
        if (this.value == null) this.value = defaultValue;
        object.addProperty(this.name, this.value);
    }


}
