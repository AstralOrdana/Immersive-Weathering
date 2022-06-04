package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.common.*;
import com.ordana.immersive_weathering.common.blocks.LeafPilesRegistry;
import com.ordana.immersive_weathering.common.ModItems;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import com.ordana.immersive_weathering.block_growth.rute_test.BlockSetMatchTest;
import com.ordana.immersive_weathering.block_growth.rute_test.FluidMatchTest;
import com.ordana.immersive_weathering.block_growth.rute_test.LogMatchTest;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Author: Ordana, Keybounce, MehVahdJukaar
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeathering {

    public static final String MOD_ID = "immersive_weathering";

    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public ImmersiveWeathering() {
        //TODO: fix layers texture generation
        //TODO: fix grass growth replacing double plants and add tag
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModBlocks.BLOCKS_OVERRIDE.register(bus);
        ModEntities.BLOCK_ENTITIES.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModItems.ITEMS.register(bus);
        ModItems.ITEMS_OVERRIDE.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModFeatures.FEATURES.register(bus);
        MinecraftForge.EVENT_BUS.register(ModFeatures.class);

        LeafPilesRegistry.registerBus(bus);


        bus.addListener(ImmersiveWeathering::init);
        bus.addListener(ImmersiveWeathering::addPackFinders);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfigs.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfigs.SPEC);

    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            ModCompostable.registerCompostable();
            ModFeatures.init();

            FluidMatchTest.init();
            LogMatchTest.init();
            BlockSetMatchTest.init();
        });
    }
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerBuiltinResourcePack(event, new TextComponent("Better Brick Items"), "better_brick_items");
            registerBuiltinResourcePack(event, new TextComponent("Better Brick blocks"), "better_brick_blocks");
            registerBuiltinResourcePack(event, new TextComponent("Visual Waxed Iron Items"), "visual_waxed_iron_items");
        }
    }

    private static void registerBuiltinResourcePack(AddPackFindersEvent event, MutableComponent name, String folder) {
        event.addRepositorySource((consumer, constructor) -> {
            String path = ImmersiveWeathering.res(folder).toString();
            IModFile file = ModList.get().getModFileById(ImmersiveWeathering.MOD_ID).getFile();
            try (PathResourcePack pack = new PathResourcePack(
                    path,
                    file.findResource("resourcepacks/" + folder));) {

                consumer.accept(constructor.create(
                        ImmersiveWeathering.res(folder).toString(),
                        name,
                        false,
                        () -> pack,
                        pack.getMetadataSection(PackMetadataSection.SERIALIZER),
                        Pack.Position.TOP,
                        PackSource.BUILT_IN,
                        false));

            } catch (IOException e) {
                if (!DatagenModLoader.isRunningDataGen())
                    e.printStackTrace();
            }
        });
    }

}
