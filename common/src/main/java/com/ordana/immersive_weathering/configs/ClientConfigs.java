package com.ordana.immersive_weathering.configs;

import com.ordana.immersive_weathering.platform.CommonPlatform;

import java.util.function.Supplier;

public class ClientConfigs {


    //client configs

    public static Supplier<Boolean> LEAF_DECAY_PARTICLES;
    public static Supplier<Boolean> FALLING_LEAF_PARTICLES;

    public static void init() {
        ConfigBuilderWrapper builder = CommonPlatform.getConfigBuilder("server", ConfigBuilderWrapper.ConfigType.CLIENT);

        builder.push("general");
        LEAF_DECAY_PARTICLES = builder.define("leaves_decay_particles", true);
        FALLING_LEAF_PARTICLES = builder.define("falling_leaf_particles", true);
        builder.pop();

        builder.buildAndRegister();
    }

}
