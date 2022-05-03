package com.ordana.immersive_weathering.configs;

import com.ordana.immersive_weathering.common.blocks.LeafPilesRegistry;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigs {

    public static ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.BooleanValue RESOURCE_PACK_SUPPORT;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        if(LeafPilesRegistry.isDynamic)
        RESOURCE_PACK_SUPPORT = builder.comment("Allows dynamic resource generations for leaf piles and bark to pick textures from installed resource pack. Will only really work well if those texture pack have a similar format to vanilla otherwise it will result in glitched textures")
                .define("dynamic_textures_from_resource_packs", false);

        SPEC = builder.build();
    }
}
