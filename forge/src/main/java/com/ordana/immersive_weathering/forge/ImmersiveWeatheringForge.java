package com.ordana.immersive_weathering.forge;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.forge.dynamic.ModDynamicRegistry;
import com.ordana.immersive_weathering.reg.LeafPilesRegistry;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Author: Ordana, Keybounce, MehVahdJukaar
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeatheringForge {
    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;

    public ImmersiveWeatheringForge() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModRegistry.init(bus);
        ModDynamicRegistry.init(bus);



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

        bus.addListener(ImmersiveWeatheringForge::init);
        bus.addGenericListener(Item.class, ImmersiveWeatheringForge::registerAdditional);
        bus.addListener(ImmersiveWeatheringForge::addPackFinders);
    }



    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            ImmersiveWeathering.commonSetup();
            registerWaxables();
        });
    }


    public static void registerAdditional(RegistryEvent.Register<Item> event){
        ImmersiveWeathering.commonRegistration();
        FeatureHacks.register();
    }

    private static void registerWaxables() {
        try {
            Field waxables = ObfuscationReflectionHelper.findField(HoneycombItem.class, "WAXABLES");
            waxables.setAccessible(true);
            var oldWaxables = HoneycombItem.WAXABLES.get();
            waxables.set(null, Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
                    .putAll(oldWaxables)
                    .putAll(ModWaxables.getValues()).build()));

            Field inverseWaxable = ObfuscationReflectionHelper.findField(HoneycombItem.class, "WAX_OFF_BY_BLOCK");
            inverseWaxable.setAccessible(true);
            inverseWaxable.set(null, Suppliers.memoize(() -> (HoneycombItem.WAXABLES.get()).inverse()));

        }catch (Exception e){
            ImmersiveWeathering.LOGGER.error("Failed to register Waxables");
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
