package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import com.ordana.immersive_weathering.data.fluid_generators.IFluidGenerator;
import com.ordana.immersive_weathering.data.position_tests.PositionRuleTest;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.network.NetworkHandler;
import com.ordana.immersive_weathering.reg.ModSoundEvents;
import com.ordana.immersive_weathering.reg.*;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.network.ModMessages;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImmersiveWeathering {

    public static final String MOD_ID = "immersive_weathering";
    public static boolean hasDynamic = PlatformHelper.isModLoaded("selene");

    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    //called either on mod creation on fabric or mod setup on forge
    public static void commonInit() {

        ModMessages.registerMessages();

        CommonConfigs.init();
        NetworkHandler.registerMessages();

        if(PlatformHelper.getEnv().isClient()){
            ClientConfigs.init();
        }

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModParticles.init();
        ModRuleTests.init();
        ModFeatures.init();
        ModSoundEvents.init();


        PlatformHelper.addServerReloadListener(BlockGrowthHandler.RELOAD_INSTANCE, res("block_growths"));
        PlatformHelper.addServerReloadListener(FluidGeneratorsHandler.RELOAD_INSTANCE, res("fluid_generators"));
    }

    public static void commonSetup() {
        PositionRuleTest.register();
        IFluidGenerator.register();

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


    {
        //stuff for temp test:
        //biome temp, light level, cold blocks around, day

    }
}
