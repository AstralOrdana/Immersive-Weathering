package com.ordana.immersive_weathering.client;

import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.client.particles.EmberParticle;
import com.ordana.immersive_weathering.client.particles.LeafParticle;
import com.ordana.immersive_weathering.items.FlowerCrownItem;
import com.ordana.immersive_weathering.reg.*;
import net.mehvahdjukaar.moonlight.api.client.renderer.FallingBlockRendererGeneric;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;

public class ImmersiveWeatheringClient {

    public static void init() {
        ClientPlatformHelper.addEntityRenderersRegistration(ImmersiveWeatheringClient::registerEntityRenderers);
        ClientPlatformHelper.addBlockColorsRegistration(ImmersiveWeatheringClient::registerBlockColors);
        ClientPlatformHelper.addItemColorsRegistration(ImmersiveWeatheringClient::registerItemColors);
        ClientPlatformHelper.addParticleRegistration(ImmersiveWeatheringClient::registerParticles);
    }

    public static void setup() {

        ClientPlatformHelper.registerRenderType(ModBlocks.ROOTED_GRASS_BLOCK.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_COBBLESTONE, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_COBBLESTONE_SLAB, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_COBBLESTONE_STAIRS, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_COBBLESTONE_WALL, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.INFESTED_MOSSY_STONE_BRICKS, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_STONE_BRICKS, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_STONE_BRICK_SLAB, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_STONE_BRICK_STAIRS, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(Blocks.MOSSY_STONE_BRICK_WALL, RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_BRICKS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_BRICK_SLAB.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_BRICK_STAIRS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_BRICK_WALL.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_STONE.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_STONE_SLAB.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_STONE_STAIRS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.MOSSY_STONE_WALL.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.VITRIFIED_SAND.get(), RenderType.translucent());

        ClientPlatformHelper.registerRenderType(ModBlocks.EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.RUSTED_IRON_BARS.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_BARS.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.ICICLE.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.FULGURITE.get(), RenderType.cutout());

        ModBlocks.LEAF_PILES.values().forEach(b ->
                ClientPlatformHelper.registerRenderType(b, RenderType.cutout()));
        ClientPlatformHelper.registerRenderType(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.WEEDS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.IVY.get(), RenderType.cutout());

        ClientPlatformHelper.registerRenderType(ModBlocks.MOSS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.SOOT.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.SOOT.get(), RenderType.translucent());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROST.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROST.get(), RenderType.translucent());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.translucent());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.translucent());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_GRASS.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.FROSTY_FERN.get(), RenderType.cutout());
        ClientPlatformHelper.registerRenderType(ModBlocks.THIN_ICE.get(), RenderType.translucent());

        ClientPlatformHelper.registerRenderType(ModBlocks.HANGING_ROOTS_WALL.get(), RenderType.cutout());

        ClientPlatformHelper.registerItemProperty(ModItems.FLOWER_CROWN.get(), ImmersiveWeathering.res("supporter"),
                (stack, world, entity, s) -> FlowerCrownItem.getItemTextureIndex(stack));
    }


    private static void registerEntityRenderers(ClientPlatformHelper.EntityRendererEvent event) {
        event.register(ModEntities.FALLING_ICICLE.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_LAYER.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_PROPAGULE.get(), FallingBlockRendererGeneric::new);
    }

    private static void registerParticles(ClientPlatformHelper.ParticleEvent event) {
        event.register(ModParticles.EMBERSPARK.get(), EmberParticle.EmberFactory::new);
        event.register(ModParticles.EMBER.get(), EmberParticle.EmberFactory::new);

        event.register(ModParticles.SOOT.get(), LeafParticle.SimpleLeafParticle::new);

        ModParticles.FALLING_LEAVES_PARTICLES.forEach((t, p) -> {
            if (t.id.getPath().contains("azalea")) event.register(p, LeafParticle.SimpleLeafParticle::new);
            else event.register(p, LeafParticle.ColoredLeafParticle::new);
        });

        event.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.MULCH.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.NULCH.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.SCRAPE_RUST.get(), ScrapeRustFactory::new);

        event.register(ModParticles.OAK_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.BIRCH_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.SPRUCE_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.DARK_OAK_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.JUNGLE_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.ACACIA_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.MANGROVE_BARK.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.NETHER_SCALE.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.MOSS.get(), LeafParticle.SimpleLeafParticle::new);

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

    private static void registerBlockColors(ClientPlatformHelper.BlockColorEvent event) {

        final BlockColor defaultGrassColor = (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0D, 0D);
        final BlockColor defaultFoliageColor = (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();


        ModBlocks.LEAF_PILES.forEach((type, leafPile) -> {
            var leaf = type.leaves;
            if (leaf == Blocks.BIRCH_LEAVES) {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, (state, world, pos, tintIndex) -> FoliageColor.getBirchColor());

            } else if (leaf == Blocks.SPRUCE_LEAVES) {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, (state, world, pos, tintIndex) -> FoliageColor.getEvergreenColor());

            } else if (leaf == Blocks.AZALEA_LEAVES || leaf == Blocks.FLOWERING_AZALEA_LEAVES) {
                return;
            } else {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, defaultFoliageColor);
            }
        });

        IWPlatformStuff.copyColorFrom(event, ModBlocks.FROSTY_GRASS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.FROSTY_FERN.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.ROOTED_GRASS_BLOCK.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICKS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_SLAB.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_STAIRS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_WALL.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_SLAB.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_STAIRS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_WALL.get(), Blocks.GRASS_BLOCK, defaultGrassColor);


        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_COBBLESTONE, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_COBBLESTONE_WALL, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_STONE_BRICKS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Blocks.MOSSY_STONE_BRICK_WALL, Blocks.GRASS_BLOCK, defaultGrassColor);

    }


    private static void registerItemColors(ClientPlatformHelper.ItemColorEvent event) {
        final ItemColor defaultGrassColor = (stack, tintIndex) -> GrassColor.get(0.5D, 0.5D);
        //todo: REPLACE ALL OF THESE LIKE THIS
        IWPlatformStuff.copyColorFrom(event, ModBlocks.ROOTED_GRASS_BLOCK.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_COBBLESTONE, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_COBBLESTONE_SLAB, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_COBBLESTONE_STAIRS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_COBBLESTONE_WALL, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.INFESTED_MOSSY_STONE_BRICKS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_STONE_BRICKS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_STONE_BRICK_SLAB, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_STONE_BRICK_STAIRS, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, Items.MOSSY_STONE_BRICK_WALL, Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICKS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_SLAB.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_STAIRS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_BRICK_WALL.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_SLAB.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_STAIRS.get(), Blocks.GRASS_BLOCK, defaultGrassColor);
        IWPlatformStuff.copyColorFrom(event, ModBlocks.MOSSY_STONE_WALL.get(), Blocks.GRASS_BLOCK, defaultGrassColor);


        final ItemColor defaultFoliageColor = (stack, tintIndex) -> FoliageColor.getDefaultColor();

        ModItems.LEAF_PILES.forEach((type, leafPile) -> {
            var leaf = type.leaves;
            if (leaf == Blocks.BIRCH_LEAVES) {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, (s, i) -> FoliageColor.getBirchColor());
            } else if (leaf == Blocks.SPRUCE_LEAVES) {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, (s, tintIndex) -> FoliageColor.getEvergreenColor());
            } else {
                IWPlatformStuff.copyColorFrom(event, leafPile, leaf, defaultFoliageColor);
            }
        });

    }

}
