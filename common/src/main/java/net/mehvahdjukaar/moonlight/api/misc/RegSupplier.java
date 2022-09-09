package net.mehvahdjukaar.moonlight.api.misc;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.function.Supplier;

/**
 * Basically a registry object wrapper and simple supplier with id and holder functionality for fabric
 */
public interface RegSupplier<T> extends Supplier<T> {

    @Override
    T get();

    ResourceLocation getId();

    Holder<T> getHolder();

    default boolean is(TagKey<T> tag) {
        return this.getHolder().is(tag);
    }
}
