package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.BlockSetMatchTest;
import com.ordana.immersive_weathering.data.FluidMatchTest;
import com.ordana.immersive_weathering.data.LogMatchTest;
import com.ordana.immersive_weathering.registry.*;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.entity.ModEntities;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImmersiveWeathering implements ModInitializer {

    public static final String MOD_ID = "immersive_weathering";

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new BlockGrowthHandler());

        ModEntities.registerEntities();
        ModLootTables.registerLootTables();
        ModBlocks.registerBlocks();
        ModFlammableBlocks.registerFlammable();
        ModWaxable.registerWaxable();
        ModItems.registerItems();
        ModCompostable.registerCompostable();
        ModFuel.registerFuel();

        ModParticles.registerParticles();
        ModEvents.registerEvents();
        ModFeatures.registerFeatures();

        FluidMatchTest.init();
        LogMatchTest.init();
        BlockSetMatchTest.init();

        FabricLoader.getInstance().getModContainer(ImmersiveWeathering.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:better_brick_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:better_brick_blocks"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:visual_waxed_iron_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:biome_tinted_mossy_blocks"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }
}
