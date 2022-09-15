package com.ordana.immersive_weathering.forge;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import java.io.IOException;

/**
 * Author: Ordana, Keybounce, MehVahdJukaar
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeatheringForge {
    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;

    public ImmersiveWeatheringForge() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //ModDynamicRegistry.init(bus);

        ImmersiveWeathering.commonInit();
        FeatureHacks.init();

        /**
         * Update stuff:
         * Configs
         * sand later
         * ash layer
         * leaf layer
         */

        //TODO: fix layers texture generation
        //TODO: fix grass growth replacing double plants and add tag

        bus.addListener(ImmersiveWeatheringForge::setup);
        bus.addListener(ImmersiveWeatheringForge::addPackFinders);
        bus.addGenericListener(Item.class, ImmersiveWeatheringForge::registerOverrides);


        if (PlatformHelper.getEnv().isClient()) {
            ImmersiveWeatheringClient.init();
        }
    }

    public static void registerOverrides(RegistryEvent.Register<Item> event) {
        //override
        event.getRegistry().register(
                new CeilingAndWallBlockItem(Blocks.HANGING_ROOTS, ModBlocks.HANGING_ROOTS_WALL.get(),
                        new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)).setRegistryName("minecraft:hanging_roots"));
    }


    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ImmersiveWeathering.commonSetup();
            registerWaxables();
        });
    }


    private static void registerWaxables() {
        try {
            var oldWaxables = HoneycombItem.WAXABLES.get();
            HoneycombItem.WAXABLES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
                    .putAll(oldWaxables)
                    .putAll(ModWaxables.getValues()).build());

            HoneycombItem.WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> (HoneycombItem.WAXABLES.get()).inverse());

        } catch (Exception e) {
            ImmersiveWeathering.LOGGER.error("Failed to register Waxables: ", e);
        }

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
            IModFile file = ModList.get().getModFileById(ImmersiveWeatheringForge.MOD_ID).getFile();
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
