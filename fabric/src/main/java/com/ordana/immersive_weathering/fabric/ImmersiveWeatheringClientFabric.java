package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

import java.util.function.Function;

public class ImmersiveWeatheringClientFabric implements ClientModInitializer {

    public static void initClient() {
        ImmersiveWeatheringClient.init();
        ImmersiveWeatheringClient.setup();

        /*
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(ImmersiveWeathering.res("particle/ember_0"));
            registry.register(ImmersiveWeathering.res("particle/soot_0"));
            registry.register(ImmersiveWeathering.res("particle/ember_1"));
            registry.register(ImmersiveWeathering.res("particle/soot_1"));
            registry.register(ImmersiveWeathering.res("particle/emberspark_0"));
            registry.register(ImmersiveWeathering.res("particle/emberspark_1"));
            registry.register(ImmersiveWeathering.res("particle/moss_0"));
            registry.register(ImmersiveWeathering.res("particle/moss_1"));

            registry.register(ImmersiveWeathering.res("particle/oak_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/birch_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/spruce_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/jungle_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/acacia_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/dark_oak_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/azalea_leaf_0"));
            registry.register(ImmersiveWeathering.res("particle/azalea_flower_0"));
            registry.register(ImmersiveWeathering.res("particle/oak_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/birch_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/spruce_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/jungle_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/acacia_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/dark_oak_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/azalea_leaf_1"));
            registry.register(ImmersiveWeathering.res("particle/azalea_flower_1"));


            registry.register(ImmersiveWeathering.res("particle/oak_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/birch_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/spruce_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/jungle_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/acacia_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/dark_oak_bark_0"));
            registry.register(ImmersiveWeathering.res("particle/nether_scale_0"));
            registry.register(ImmersiveWeathering.res("particle/oak_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/birch_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/spruce_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/jungle_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/acacia_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/dark_oak_bark_1"));
            registry.register(ImmersiveWeathering.res("particle/nether_scale_1"));
            registry.register(ImmersiveWeathering.res("particle/oak_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/birch_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/spruce_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/jungle_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/acacia_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/dark_oak_bark_2"));
            registry.register(ImmersiveWeathering.res("particle/nether_scale_2"));

        }));
         */


    }

    private static <T extends ParticleOptions> void registerParticle(ParticleType<T> type, Function<SpriteSet,
            ParticleProvider<T>> registration) {
        ParticleFactoryRegistry.getInstance().register(type, registration::apply);
    }

    @Override
    public void onInitializeClient() {
        //dont use

    }
}
