package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.events.ModEvents;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Blocks;

public class ImmersiveWeatheringFabric implements ModInitializer {

    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;


    @Override
    public void onInitialize() {

        ImmersiveWeathering.commonInit();
        ImmersiveWeathering.commonSetup();
        ImmersiveWeathering.commonRegistration();


        ModRegistry.registerEntries();

        ModWaxables.getValues().forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);

        if (CommonConfigs.FLAMMABLE_COBWEBS.get()) {
            FlammableBlockRegistry.getDefaultInstance().add(Blocks.COBWEB, 100, 100);
        }

        //todo
        //UseBlockCallback.EVENT.register((p, l, h, r) -> ModEvents.invokeEvents(p.getItemInHand(h), p, l, h, r));

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(BlockGrowthHandler.getInstance(FabricBlockGrowthManager::new));


        FabricLoader.getInstance().getModContainer(ImmersiveWeatheringFabric.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(ImmersiveWeathering.res("better_brick_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(ImmersiveWeathering.res("better_brick_blocks"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(ImmersiveWeathering.res("visual_waxed_iron_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(ImmersiveWeathering.res("biome_tinted_mossy_blocks"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }

    private static class FabricBlockGrowthManager extends BlockGrowthHandler implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return ImmersiveWeathering.res("block_growths");
        }
    }

}
