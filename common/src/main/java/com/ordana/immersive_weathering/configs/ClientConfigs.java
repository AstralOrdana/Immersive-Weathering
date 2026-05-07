package com.ordana.immersive_weathering.configs;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class ClientConfigs {

    public static Object CLIENT_SPEC;


    //client configs

    public static Supplier<Boolean> LEAF_DECAY_PARTICLES;
    public static Supplier<Boolean> FALLING_LEAF_PARTICLES;
    public static Supplier<Boolean> LEAF_DECAY_SOUND;

    public static Supplier<Double> FALLING_LEAF_PARTICLE_RATE;
    public static Supplier<Double> RAINY_FALLING_LEAF_PARTICLE_RATE;
    public static Supplier<Double> STORMY_FALLING_LEAF_PARTICLE_RATE;

    public static void init() {
        ConfigBuilder builder = ConfigBuilder.create(ImmersiveWeathering.res("client"), ConfigType.CLIENT);

        builder.push("general");
        LEAF_DECAY_PARTICLES = builder.define("leaves_decay_particles", true);
        FALLING_LEAF_PARTICLES = builder.define("falling_leaf_particles", true);
        LEAF_DECAY_SOUND = builder.define("decay_sound", true);

        FALLING_LEAF_PARTICLE_RATE = builder.define("falling_leaf_rate", 0.08f, 0f, 1f);
        RAINY_FALLING_LEAF_PARTICLE_RATE = builder.define("rainy_falling_leaf_rate", 0.2f, 0f, 1f);
        STORMY_FALLING_LEAF_PARTICLE_RATE = builder.define("stormy_falling_leaf_rate", 0.4f, 0f, 1f);
        builder.pop();

        CLIENT_SPEC = buildAndLoad(builder);

    }

    private static Object buildAndLoad(ConfigBuilder builder) {
        Object configHolder = invokeRequired(builder, "buildAndRegister", "build");
        invokeIfPresent(configHolder, "loadFromFile", "forceLoad");
        return configHolder;
    }

    private static Object invokeRequired(Object target, String... methodNames) {
        for (String methodName : methodNames) {
            try {
                Method method = target.getClass().getMethod(methodName);
                return method.invoke(target);
            } catch (ReflectiveOperationException ignored) {
            }
        }
        throw new IllegalStateException("Could not call any of " + String.join(", ", methodNames) + " on " + target.getClass().getName());
    }

    private static void invokeIfPresent(Object target, String... methodNames) {
        for (String methodName : methodNames) {
            try {
                Method method = target.getClass().getMethod(methodName);
                method.invoke(target);
                return;
            } catch (ReflectiveOperationException ignored) {
            }
        }
    }

}
