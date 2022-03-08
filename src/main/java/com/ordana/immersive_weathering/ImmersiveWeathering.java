package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.*;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * Author: Ordana, Keybee, MehVahdJukaar
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeathering {

    public static final String MOD_ID = "immersive_weathering";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public ImmersiveWeathering() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModBlocks.BLOCKS_OVERRIDE.register(bus);
        ModItems.ITEMS.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModFeatures.FEATURES.register(bus);
        MinecraftForge.EVENT_BUS.register(ModFeatures.class);
        bus.addListener(ImmersiveWeathering::init);



    }

    public static void init(final FMLCommonSetupEvent event) {
        ModWaxable.registerWaxable();
        ModCompostable.registerCompostable();
        ModFeatures.init();

        //ModEvents.registerEvents();

        /*
        FabricLoader.getInstance().getModContainer(ImmersiveWeathering.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:better_brick_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:better_brick_blocks"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:visual_waxed_iron_items"), modContainer, ResourcePackActivationType.NORMAL);
        });
        */
    }

}
