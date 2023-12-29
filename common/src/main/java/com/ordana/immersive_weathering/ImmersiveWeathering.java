package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import com.ordana.immersive_weathering.data.fluid_generators.ModFluidGenerators;
import com.ordana.immersive_weathering.data.position_tests.ModPositionRuleTests;
import com.ordana.immersive_weathering.dynamicpack.ServerDynamicResourcesHandler;
import com.ordana.immersive_weathering.events.ModEvents;
import com.ordana.immersive_weathering.events.ModLootInjects;
import com.ordana.immersive_weathering.network.NetworkHandler;
import com.ordana.immersive_weathering.reg.*;
import net.mehvahdjukaar.moonlight.api.events.IFireConsumeBlockEvent;
import net.mehvahdjukaar.moonlight.api.events.ILightningStruckBlockEvent;
import net.mehvahdjukaar.moonlight.api.events.MoonlightEventsHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImmersiveWeathering {

    public static final String MOD_ID = "immersive_weathering";

    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    //called either on mod creation on fabric or mod setup on forge
    public static void commonInit() {
        //"SelfMulchMixin",
        //"SelfNulchMixin",?

        CommonConfigs.init();
        if (PlatHelper.getPhysicalSide().isClient()) {
            ClientConfigs.init();
            ImmersiveWeatheringClient.init();
        }

        PlatHelper.addCommonSetup(ImmersiveWeathering::setup);

        ServerDynamicResourcesHandler.INSTANCE.register();


        MoonlightEventsHelper.addListener(ModEvents::onFireConsume, IFireConsumeBlockEvent.class);
        MoonlightEventsHelper.addListener(ModEvents::onLightningHit, ILightningStruckBlockEvent.class);

        RegHelper.addLootTableInjects(ModLootInjects::onLootInject);

        ModCreativeTab.init();

        NetworkHandler.init();

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModParticles.init();
        ModRuleTests.init();
        ModFeatures.init();
        ModSoundEvents.init();

        PlatHelper.addServerReloadListener(BlockGrowthHandler.RELOAD_INSTANCE, res("block_growths"));
        PlatHelper.addServerReloadListener(FluidGeneratorsHandler.RELOAD_INSTANCE, res("fluid_generators"));
    }

    public static void setup() {
        ModPositionRuleTests.register();
        ModFluidGenerators.register();
        ModCompostable.register();
    }

    // dispenser interactions like de rusting
    //TODO: disabled conditional growth. add command system
    //figure out thin ice situation
    //thin ice repair and solidify when snow??
    //CHARRED stuff falling in water
    //thin ice melts receiding
    //block sounds
    //thin ice crack sounds
    //TODO: add wet test
//biome and season dependent leaf piles (tags)

    {
        //stuff for temp test:
        //biome temp, light level, cold blocks around, day

    }
}
