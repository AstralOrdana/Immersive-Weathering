package net.mehvahdjukaar.moonlight.api.platform.configs.fabric.values;

import com.google.gson.JsonObject;
import com.ordana.immersive_weathering.ImmersiveWeathering;

public class BoolConfigValue extends ConfigValue<Boolean> {

    public BoolConfigValue(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public boolean isValid(Boolean value) {
        return true;
    }

    @Override
    public void loadFromJson(JsonObject element) {
        if (element.has(this.name)) {
            try {
                this.value = element.get(this.name).getAsBoolean();
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
