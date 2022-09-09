package net.mehvahdjukaar.moonlight.api.platform.fabric;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RegHelperImpl {

    public static final Map<Registry<?>, Map<String, RegistryQueue<?>>> REGISTRIES = new LinkedHashMap<>();

    //order is important here
    static {
        REGISTRIES.put(Registry.SOUND_EVENT, new LinkedHashMap<>());
        REGISTRIES.put(Registry.BLOCK, new LinkedHashMap<>());
        REGISTRIES.put(Registry.ENTITY_TYPE, new LinkedHashMap<>());
        REGISTRIES.put(Registry.ITEM, new LinkedHashMap<>());
        REGISTRIES.put(Registry.BLOCK_ENTITY_TYPE, new LinkedHashMap<>());
        REGISTRIES.put(Registry.PLACEMENT_MODIFIERS, new LinkedHashMap<>());
        REGISTRIES.put(Registry.STRUCTURE_PIECE, new LinkedHashMap<>());
        REGISTRIES.put(Registry.FEATURE, new LinkedHashMap<>());
        REGISTRIES.put(BuiltinRegistries.CONFIGURED_FEATURE, new LinkedHashMap<>());
        REGISTRIES.put(BuiltinRegistries.PLACED_FEATURE, new LinkedHashMap<>());
    }

    //call from mod setup
    public static void registerEntries() {
        for (var m : REGISTRIES.entrySet()) {
            m.getValue().values().forEach(RegistryQueue::initializeEntries);
            if (m.getKey() == Registry.BLOCK) {
                //dynamic block registration after all blocks
                //  BlockSetInternalImpl.registerEntries();
            }
        }
        //register entities attributes now
        ATTRIBUTE_REGISTRATIONS.forEach(e -> e.accept(FabricDefaultAttributeRegistry::register));
    }


    @SuppressWarnings("unchecked")
    public static <T, E extends T> RegSupplier<E> register(ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        //if (true) return registerAsync(name, supplier, reg);
        String modId = name.getNamespace();
        var m = REGISTRIES.computeIfAbsent(reg, h -> new LinkedHashMap<>());
        RegistryQueue<T> registry = (RegistryQueue<T>) m.computeIfAbsent(modId, c -> new RegistryQueue<>(reg));
        return registry.add(supplier, name);
    }

    public static <T, E extends T> RegSupplier<E> registerAsync(ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        RegistryQueue.EntryWrapper<E, T> entry = new RegistryQueue.EntryWrapper<>(name, supplier, reg);
        entry.initialize();
        return entry;
    }

    public static RegSupplier<SimpleParticleType> registerParticle(ResourceLocation name) {
        return register(name, FabricParticleTypes::simple, Registry.PARTICLE_TYPE);
    }

    public static <T extends Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, int updateInterval) {
        Supplier<EntityType<T>> s = () -> EntityType.Builder.of(factory, category).sized(width, height).build(name.toString());
        return register(name, s, Registry.ENTITY_TYPE);
    }

    public static void registerItemBurnTime(Item item, int burnTime) {
        FuelRegistry.INSTANCE.add(item, burnTime);
    }

    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
        FlammableBlockRegistry.getDefaultInstance().add(item, fireSpread, flammability);
    }


    public static void addAttributeRegistration(Consumer<RegHelper.AttributeEvent> eventListener) {
        ATTRIBUTE_REGISTRATIONS.add(eventListener);
    }

    private static final List<Consumer<RegHelper.AttributeEvent>> ATTRIBUTE_REGISTRATIONS = new ArrayList<>();


}
