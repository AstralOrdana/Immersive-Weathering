package net.mehvahdjukaar.moonlight.api.platform.configs.fabric.values;

import com.google.gson.JsonObject;
import com.ordana.immersive_weathering.ImmersiveWeathering;

public class EnumConfigValue<T extends Enum<T>> extends ConfigValue<T> {

    private final T[] acceptedValues;

    public EnumConfigValue(String name, T defaultValue) {
        super(name, defaultValue);
        this.acceptedValues = defaultValue.getDeclaringClass().getEnumConstants();
    }

    @Override
    public boolean isValid(T value) {
        return true;
    }

    public Class<T> getEnum(){
        return this.defaultValue.getDeclaringClass();
    }

    @Override
    public void loadFromJson(JsonObject element) {
        if (element.has(this.name)) {
            try {
                String s = element.get(this.name).getAsString();
                for(var v : acceptedValues){
                    if(v.name().equals(s)){
                        this.value = v;
                        return;
                    }
                }
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
        object.addProperty(this.name, this.value.name());
    }


}
