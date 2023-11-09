package com.ordana.immersive_weathering.forge;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;

import java.io.IOException;

/**
 * Authors: MehVahdJukaar, Ordana, Keybounce,
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeatheringForge {
    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;

    public ImmersiveWeatheringForge() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        //ModDynamicRegistry.init(bus);

        ImmersiveWeathering.commonInit();

        /**
         * Update stuff:
         * Configs
         * sand later
         * ash layer
         * leaf layer
         */

        //TODO: fix layers texture generation
        //TODO: fix grass growth replacing double plants and add tag


        if (PlatHelper.getEnv().isClient()) {
            ImmersiveWeatheringClient.init();
        }
    }



    @SubscribeEvent
    public void interModCommunication(InterModEnqueueEvent event) {
        event.enqueueWork(() -> {
            //   InterModComms.sendTo("curios", "REGISTER_TYPE", () -> new SlotTypeMessage.Builder("head").build());
        });
    }

    @SubscribeEvent
    public void registerOverrides(RegisterEvent event) {
        //override
        if (event.getRegistryKey() == ForgeRegistries.ITEMS.getRegistryKey())
            event.getForgeRegistry().register(new ResourceLocation("minecraft:hanging_roots"),
                    new CeilingAndWallBlockItem(Blocks.HANGING_ROOTS, ModBlocks.HANGING_ROOTS_WALL.get(),
                            new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
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

    @SubscribeEvent
    public void addPackFinders(AddPackFindersEvent event) {

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerBuiltinResourcePack(event, Component.literal("Biome Tinted Mossy Blocks"), "biome_tinted_mossy_blocks");

            registerBuiltinResourcePack(event, Component.literal("Better Brick Items"), "better_brick_items");
            registerBuiltinResourcePack(event, Component.literal("Better Brick blocks"), "better_brick_blocks");
            registerBuiltinResourcePack(event, Component.literal("Visual Waxed Iron Items"), "visual_waxed_iron_items");
        }
    }

    private static void registerBuiltinResourcePack(AddPackFindersEvent event, MutableComponent name, String folder) {
        event.addRepositorySource((consumer, constructor) -> {
            String path = ImmersiveWeathering.res(folder).toString();
            IModFile file = ModList.get().getModFileById(ImmersiveWeatheringForge.MOD_ID).getFile();
            try (PathPackResources pack = new PathPackResources(
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
