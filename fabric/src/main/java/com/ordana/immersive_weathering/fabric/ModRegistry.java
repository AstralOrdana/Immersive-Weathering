package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModRegistry {

    //frick fabric way. we add in sequential order so we dont cause issues during class loading

    public static final RegistryQueue<Block> BLOCKS = new RegistryQueue<>(Registry.BLOCK);
    public static final RegistryQueue<Item> ITEMS = new RegistryQueue<>(Registry.ITEM);
    public static final RegistryQueue<BlockEntityType<?>> BLOCK_ENTITIES = new RegistryQueue<>(Registry.BLOCK_ENTITY_TYPE);
    public static final RegistryQueue<EntityType<?>> ENTITIES = new RegistryQueue<>(Registry.ENTITY_TYPE);
    public static final RegistryQueue<ParticleType<?>> PARTICLES = new RegistryQueue<>(Registry.PARTICLE_TYPE);
    public static final RegistryQueue<Feature<?>> FEATURES = new RegistryQueue<>(Registry.FEATURE);

    public static void registerEntries() {
        BLOCKS.initializeEntries();
        ITEMS.initializeEntries();
        BLOCK_ENTITIES.initializeEntries();
        ENTITIES.initializeEntries();
        PARTICLES.initializeEntries();
        FEATURES.initializeEntries();
    }

    public static class RegistryQueue<T> {
        private final Registry<T> registry;
        private final List<EntryWrapper<? extends T>> entries = new ArrayList<>();

        public RegistryQueue(Registry<T> registry) {
            this.registry = registry;
        }

        public <A extends T> Supplier<A> add(Supplier<A> factory) {
            EntryWrapper<A> wrapper = new EntryWrapper<>(factory);
            entries.add(wrapper);
            return wrapper;
        }

        void initializeEntries() {
            entries.forEach(EntryWrapper::run);
        }
    }

    static class EntryWrapper<T> implements Supplier<T> {
        private Supplier<T> regSupplier;
        private T entry;

        public EntryWrapper(Supplier<T> registration) {
            this.regSupplier = registration;
        }

        @Override
        public T get() {
            return entry;
        }

        void run(){
            this.entry = regSupplier.get();
            regSupplier = null;
        }

    }
}
