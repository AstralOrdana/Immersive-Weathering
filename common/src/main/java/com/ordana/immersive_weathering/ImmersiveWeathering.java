package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.liquid_generators.LiquidGeneratorHandler;
import com.ordana.immersive_weathering.block_growth.position_test.PositionRuleTest;
import com.ordana.immersive_weathering.block_growth.rute_test.*;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.*;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.network.ModMessages;
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

        ModMessages.registerMessages();

        CommonConfigs.init();

        if(PlatformHelper.getEnv().isClient()){
            ClientConfigs.init();
        }

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModParticles.init();
        ModFeatures.init();


        PlatformHelper.addServerReloadListener(BlockGrowthHandler.RELOAD_INSTANCE, res("block_growths"));
        PlatformHelper.addServerReloadListener(LiquidGeneratorHandler.RELOAD_INSTANCE, res("liquid_generators"));
    }

    public static void commonSetup() {

    }

    public static void additionalRegistration() {
        //TODO: move to regHelper
        PositionRuleTest.register();
        //rule tests
        FluidMatchTest.register();
        LogMatchTest.register();
        BlockSetMatchTest.register();
        BurnableTest.register();
        BlockPropertyTest.register();

        ModCompostable.register();
    }

    //farmers plant on mulch
    //hanging roots item override (mixin for fabric override for forge)
    //RE add lightning strike growths
    //TODO: disabled conditional growth. add command system
    //figure out thin ice situation
    //thin ice repair and solidify when snow??
    //CHARRED stuff falling in water
    //thin ice melts receiding
    //block sounds
    //thin ice crack sounds
    //TODO: add wet test
    //add forge loot table modifiers


    {
        //stuff for temp test:
        //biome temp, light level, cold blocks around, day

    }
}
