package net.mehvahdjukaar.moonlight.api.platform.configs.fabric.values;

import com.google.gson.JsonObject;
import com.ordana.immersive_weathering.ImmersiveWeathering;

public class DoubleConfigValue extends ConfigValue<Double> {

    private final Double min;
    private final Double max;

    public DoubleConfigValue(String name, Double defaultValue, Double min, Double max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(Double value) {
        return value >= min && value <= max;
    }

    @Override
    public void loadFromJson(JsonObject element) {
        if (element.has(this.name)) {
            try {
                this.value = element.get(this.name).getAsDouble();
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

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }
}
