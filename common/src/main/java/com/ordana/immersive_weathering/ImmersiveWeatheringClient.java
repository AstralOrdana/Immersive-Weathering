package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.client.particles.EmberParticle;
import com.ordana.immersive_weathering.client.particles.LeafParticle;
import com.ordana.immersive_weathering.client.particles.NormalGravityParticle;
import com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler;
import com.ordana.immersive_weathering.items.FlowerCrownItem;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModEntities;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.mehvahdjukaar.moonlight.api.client.renderer.FallingBlockRendererGeneric;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.math.colors.RGBColor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ImmersiveWeatheringClient {

    public static void init() {
        ClientHelper.addClientSetup(ImmersiveWeatheringClient::setup);
        ClientDynamicResourcesHandler.INSTANCE.register();

        ClientHelper.registerOptionalTexturePack(ImmersiveWeathering.res("better_brick_items"), Component.literal("Better Brick Items"), false);
        ClientHelper.registerOptionalTexturePack(ImmersiveWeathering.res("better_brick_blocks"));
        ClientHelper.registerOptionalTexturePack(ImmersiveWeathering.res("visual_waxed_iron_items"));

        ClientHelper.addEntityRenderersRegistration(ImmersiveWeatheringClient::registerEntityRenderers);
        ClientHelper.addBlockColorsRegistration(ImmersiveWeatheringClient::registerBlockColors);
        ClientHelper.addItemColorsRegistration(ImmersiveWeatheringClient::registerItemColors);
        ClientHelper.addParticleRegistration(ImmersiveWeatheringClient::registerParticles);
    }

    public static void setup() {

        ClientHelper.registerRenderType(ModBlocks.GRASSY_PERMAFROST.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.GRASSY_SILT.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.GRASSY_EARTHEN_CLAY.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.GRASSY_SANDY_DIRT.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.ROOTED_GRASS_BLOCK.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.VITRIFIED_SAND.get(), RenderType.translucent());

        ClientHelper.registerRenderType(ModBlocks.EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.EXPOSED_IRON_BARS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WEATHERED_IRON_BARS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.RUSTED_IRON_BARS.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.WAXED_IRON_DOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.WAXED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientHelper.registerRenderType(ModBlocks.WAXED_IRON_BARS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_BARS.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.ICICLE.get(), RenderType.cutout());
        ClientHelper.registerRenderType(ModBlocks.FULGURITE.get(), RenderType.cutout());

        ModBlocks.LEAF_PILES.values().forEach(b -> ClientHelper.registerRenderType(b, RenderType.cutoutMipped()));
        ClientHelper.registerRenderType(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.WEEDS.get(), RenderType.cutoutMipped());
        ClientHelper.registerRenderType(ModBlocks.IVY.get(), RenderType.cutoutMipped());

        ClientHelper.registerRenderType(ModBlocks.MOSS.get(), RenderType.cutoutMipped());
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

        ClientHelper.registerRenderType(ModBlocks.HANGING_ROOTS_WALL.get(), RenderType.cutoutMipped());

        ItemProperties.register(ModItems.FLOWER_CROWN.get(), ImmersiveWeathering.res("supporter"),
                (stack, world, entity, s) -> FlowerCrownItem.getItemTextureIndex(stack));
    }


    private static void registerEntityRenderers(ClientHelper.EntityRendererEvent event) {
        event.register(ModEntities.FALLING_ICICLE.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_LAYER.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_PROPAGULE.get(), FallingBlockRendererGeneric::new);
    }

    private static void registerParticles(ClientHelper.ParticleEvent event) {
        event.register(ModParticles.EMBERSPARK.get(), EmberParticle.EmberFactory::new);
        event.register(ModParticles.SCRAPE_RUST.get(), ScrapeRustFactory::new);
        event.register(ModParticles.MOSS.get(), NormalGravityParticle.Particle::new);
        event.register(ModParticles.GRAVITY_AZALEA_FLOWER.get(), NormalGravityParticle.Particle::new);

        event.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BEE.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_JAR.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BOB.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.FLOWER_ACE.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_ARO.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_BI.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_ENBY.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_GAY.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_LESBIAN.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_RAINBOW.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_TRANS.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_GENDERQUEER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_FLUID.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_INTERSEX.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_PAN.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.FLOWER_FLAX.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_NEKOMASTER.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.FLOWER_AKASHII.get(), LeafParticle.SimpleLeafParticle::new);
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
        ModBlocks.LEAF_PILES.forEach((type, leafPile) -> {
            event.register((blockState, blockAndTintGetter, blockPos, i) -> {
                return getLeafTypeColor(event, type, blockState, blockAndTintGetter, blockPos, i);
            }, leafPile);
        });

        event.register((blockState, level, blockPos, i) -> {
                    if (i == 0) return -1;
                    return event.getColor(Blocks.GRASS_BLOCK.defaultBlockState(), level, blockPos, i);
                },

                ModBlocks.GRASSY_PERMAFROST.get(),
                ModBlocks.GRASSY_SILT.get(),
                ModBlocks.GRASSY_EARTHEN_CLAY.get(),
                ModBlocks.GRASSY_SANDY_DIRT.get(),
                ModBlocks.ROOTED_GRASS_BLOCK.get(),
                ModBlocks.FROSTY_GRASS.get(),
                ModBlocks.FROSTY_FERN.get());
    }

    private static int getLeafTypeColor(ClientHelper.BlockColorEvent event, LeavesType type, BlockState state, BlockAndTintGetter level, BlockPos pos, int i) {
        int original = event.getColor(type.leaves.defaultBlockState(), level, pos, i);

        //interpolate between color and brown
        float percentage = state.getValue(LeafPileBlock.AGE) / 10f;
        int brown = 0x7D5212;
        return new RGBColor(original).asLAB().mixWith(new RGBColor(brown).asLAB(), percentage).asRGB().toInt();
    }


    private static void registerItemColors(ClientHelper.ItemColorEvent event) {

        event.register((itemStack, i) -> event.getColor(Items.GRASS_BLOCK.getDefaultInstance(), i),
                ModBlocks.ROOTED_GRASS_BLOCK.get(),
                ModBlocks.GRASSY_SILT.get(),
                ModBlocks.GRASSY_PERMAFROST.get(),
                ModBlocks.GRASSY_SANDY_DIRT.get(),
                ModBlocks.GRASSY_EARTHEN_CLAY.get());

        ModItems.LEAF_PILES.forEach((type, leafPile) -> {
            event.register((itemStack, i) -> event.getColor(type.leaves.asItem().getDefaultInstance(), i), leafPile);
        });

    }

}
