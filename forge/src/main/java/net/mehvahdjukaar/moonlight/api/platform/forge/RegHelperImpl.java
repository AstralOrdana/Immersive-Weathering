package net.mehvahdjukaar.moonlight.api.platform.forge;

import com.mojang.brigadier.CommandDispatcher;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.resources.recipe.forge.OptionalRecipeCondition;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class RegHelperImpl {

    public record EntryWrapper<T>(RegistryObject<T> registryObject) implements RegSupplier<T> {
        @Override
        public T get() {
            return registryObject.get();
        }

        @Override
        public ResourceLocation getId() {
            return registryObject.getId();
        }

        @Override
        public Holder<T> getHolder() {
            return registryObject.getHolder().get();
        }
    }

    //this might be accessed on multiple threads
    public static final Map<ResourceKey<? extends Registry<?>>, Map<String, DeferredRegister<?>>> REGISTRIES = new ConcurrentHashMap<>();

    public static <T, E extends T> RegSupplier<E> register(
            ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        return register(name, supplier, reg.key());
    }

    @SuppressWarnings("unchecked")
    public static <T, E extends T> RegSupplier<E> register(
            ResourceLocation name, Supplier<E> supplier, ResourceKey<? extends Registry<T>> regKey) {

        var m = REGISTRIES.computeIfAbsent(regKey, h -> new ConcurrentHashMap<>());
        String modId = ModLoadingContext.get().getActiveContainer().getModId();
        DeferredRegister<T> registry = (DeferredRegister<T>) m.computeIfAbsent(modId, c -> {
            DeferredRegister<T> r = DeferredRegister.create(regKey, modId);
            var bus = FMLJavaModLoadingContext.get().getModEventBus();
            r.register(bus);
            return r;
        });
        //forge we don't care about mod id since it's always the active container one
        return new EntryWrapper<>(registry.register(name.getPath(), supplier));
    }

    public static <T, E extends
            T> RegSupplier<E> registerAsync(ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        return register(name, supplier, reg);
    }

    public static RegSupplier<SimpleParticleType> registerParticle(ResourceLocation name) {
        return register(name, () -> new SimpleParticleType(true), Registry.PARTICLE_TYPE);
    }

    public static <T extends
            Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, EntityType.EntityFactory<T> factory, MobCategory category,
                                                                  float width, float height, int clientTrackingRange, int updateInterval) {
        return register(name, () -> EntityType.Builder.of(factory, category)
                .sized(width, height).build(name.toString()), Registry.ENTITY_TYPE);
    }

    public static void registerItemBurnTime(Item item, int burnTime) {
    }

    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
    }

    public static void registerVillagerTrades(VillagerProfession profession, int level, Consumer<
            List<VillagerTrades.ItemListing>> factories) {
        Consumer<VillagerTradesEvent> eventConsumer = event -> {
            if (event.getType() == profession) {
                var list = event.getTrades().get(level);
                factories.accept(list);
            }
        };
        MinecraftForge.EVENT_BUS.addListener(eventConsumer);
    }

    public static void registerWanderingTraderTrades(int level, Consumer<List<VillagerTrades.ItemListing>>
            factories) {
        //0 = common, 1 = rare
        Consumer<WandererTradesEvent> eventConsumer = event -> {
            if (level == 0) {
                factories.accept(event.getGenericTrades());
            } else {
                factories.accept(event.getRareTrades());
            }
        };
        MinecraftForge.EVENT_BUS.addListener(eventConsumer);
    }

    public static void addAttributeRegistration(Consumer<RegHelper.AttributeEvent> eventListener) {
        Consumer<EntityAttributeCreationEvent> eventConsumer = event -> {
            eventListener.accept((e, b) -> event.put(e, b.build()));
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addCommandRegistration(Consumer<CommandDispatcher<CommandSourceStack>> eventListener) {
        Consumer<RegisterCommandsEvent> eventConsumer = event -> {
            eventListener.accept(event.getDispatcher());
        };
        MinecraftForge.EVENT_BUS.addListener(eventConsumer);
    }

    public static void registerSimpleRecipeCondition(ResourceLocation id, Predicate<String> predicate) {
        CraftingHelper.register(new OptionalRecipeCondition(id, predicate));
    }

}
