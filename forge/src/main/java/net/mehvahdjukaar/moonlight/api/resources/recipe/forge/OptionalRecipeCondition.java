package net.mehvahdjukaar.moonlight.api.resources.recipe.forge;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import java.util.function.Predicate;

/**
 * Simple recipe condition implementation for conditional recipes
 */
public class OptionalRecipeCondition implements IConditionSerializer<OptionalRecipeCondition.Instance> {

    private final ResourceLocation id;
    private final Predicate<String> predicate;

    public OptionalRecipeCondition(ResourceLocation id, Predicate<String> predicate) {
        this.id = id;
        this.predicate = predicate;
    }

    @Override
    public void write(JsonObject json, Instance value) {
        json.addProperty(id.getPath(), value.condition);
    }

    @Override
    public Instance read(JsonObject json) {
        return new Instance(json.getAsJsonPrimitive(id.getPath()).getAsString());
    }

    @Override
    public ResourceLocation getID() {
        return id;
    }


    protected final class Instance implements ICondition {

        private final String condition;

        private Instance(String condition) {
            this.condition = condition;
        }

        @Override
        public ResourceLocation getID() {
            return OptionalRecipeCondition.this.getID();
        }

        @Override
        public boolean test(IContext context) {
            return OptionalRecipeCondition.this.predicate.test(condition);
        }

        @Override
        public boolean test() {
            return OptionalRecipeCondition.this.predicate.test(condition);
        }
    }
}
