package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.*;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: Ordana, MehVahdJukaar
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeathering {

    public static final String MOD_ID = "immersive_weathering";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public ImmersiveWeathering() {


        ModBlocks.registerBlocks();
        ModFlammableBlocks.registerFlammable();
        ModWaxable.registerWaxable();
        ModItems.registerItems();
        ModCompostable.registerCompostable();
        ModFuel.registerFuel();
        ModParticles.registerParticles();
        ModEvents.registerEvents();
        ModFeatures.registerFeatures();
        FabricLoader.getInstance().getModContainer(ImmersiveWeathering.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:better_brick_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:better_brick_blocks"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("immersive_weathering:visual_waxed_iron_items"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }

}
