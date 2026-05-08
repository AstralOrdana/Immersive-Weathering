package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.client.particles.EmberParticle;
import com.ordana.immersive_weathering.client.particles.NormalGravityParticle;
import com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModEntities;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.mehvahdjukaar.moonlight.api.client.renderer.FallingBlockRendererGeneric;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.math.colors.RGBColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ImmersiveWeatheringClient {

    public static void init() {
        ClientHelper.addClientSetup(ImmersiveWeatheringClient::setup);
        ClientDynamicResourcesHandler.INSTANCE.register();

        ClientHelper.registerOptionalTexturePack(ImmersiveWeathering.res("visual_waxed_iron_items"));

        ClientHelper.addEntityRenderersRegistration(ImmersiveWeatheringClient::registerEntityRenderers);
        ClientHelper.addBlockColorsRegistration(ImmersiveWeatheringClient::registerBlockColors);
        ClientHelper.addParticleRegistration(ImmersiveWeatheringClient::registerParticles);
    }

    public static void setup() {
        ClientHelper.registerRenderType(ModBlocks.VITRIFIED_SAND.get(), RenderType.translucent());

        ClientHelper.registerRenderType(ModBlocks.ICICLE.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.FULGURITE.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.SOOT.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.SOOT.get(), RenderType.translucent());
        ClientHelper.registerRenderType(ModBlocks.FROST.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.FROST.get(), RenderType.translucent());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.translucent());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.translucent());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_GRASS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.FROSTY_FERN.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.THIN_ICE.get(), RenderType.translucent());
        ClientHelper.registerRenderType(ModBlocks.TINTED_GLASS_PANE.get(), RenderType.translucent());
    }


    private static void registerEntityRenderers(ClientHelper.EntityRendererEvent event) {
        event.register(ModEntities.FALLING_ICICLE.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_LAYER.get(), FallingBlockRendererGeneric::new);
    }

    private static void registerParticles(ClientHelper.ParticleEvent event) {
        event.register(ModParticles.EMBERSPARK.get(), EmberParticle.EmberFactory::new);
    }

    private static class ScrapeRustFactory extends GlowParticle.ScrapeProvider {

        public ScrapeRustFactory(SpriteSet spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double p_172207_, double p_172208_, double p_172209_, double p_172210_, double p_172211_, double p_172212_) {
            Particle p = super.createParticle(particleType, level, p_172207_, p_172208_, p_172209_, p_172210_, p_172211_, p_172212_);
            if (p != null) {
                if (level.random.nextBoolean()) {
                    p.setColor(196 / 255f, 118 / 255f, 73 / 255f);
                } else {
                    p.setColor(176 / 255f, 63 / 255f, 40 / 255f);
                }
            }
            return p;
        }
    }

    @EventCalled
    private static void registerBlockColors(ClientHelper.BlockColorEvent event) {
                event.register((blockState, level, blockPos, i) -> {
                if (i == 0) return -1;
                return event.getColor(Blocks.GRASS_BLOCK.defaultBlockState(), level, blockPos, i);
            },
            ModBlocks.FROSTY_GRASS.get(),
            ModBlocks.FROSTY_FERN.get());
    }
}
