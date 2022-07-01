package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ImmersiveWeathering.MOD_ID);


    public static void init(IEventBus bus) {
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.BLOCK_ENTITIES.register(bus);
        ModRegistry.ENTITIES.register(bus);
        ModRegistry.PARTICLES.register(bus);
        ModRegistry.FEATURES.register(bus);
        bus.addGenericListener(Item.class, ModRegistry::registerOverrides);
    }

    public static void registerOverrides(RegistryEvent.Register<Item> event){
        //override
        event.getRegistry().register(
                new CeilingAndWallBlockItem(Blocks.HANGING_ROOTS, ModBlocks.HANGING_ROOTS_WALL.get(),
                        new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)).setRegistryName("minecraft:hanging_roots"));
    }
}
