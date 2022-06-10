package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.particles.EmberParticle;
import com.ordana.immersive_weathering.registry.particles.LeafParticle;
import com.ordana.immersive_weathering.registry.entities.ModEntities;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class ImmersiveWeatheringClient implements ClientModInitializer {

    public static class ScrapeRustFactory extends GlowParticle.ScrapeFactory {

        public ScrapeRustFactory(SpriteProvider spriteSet) {
            super(spriteSet);
        }

        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double p_172207_, double p_172208_, double p_172209_, double p_172210_, double p_172211_, double p_172212_) {
            Particle p = super.createParticle(particleType, level, p_172207_, p_172208_, p_172209_, p_172210_, p_172211_, p_172212_);
            if(p!=null) {
                if (level.random.nextBoolean()) {
                    p.setColor(196/255f, 118/255f, 73/255f);
                } else {
                    p.setColor(176/255f, 63/255f, 40/255f);
                }
            }
            return p;
        }
    }

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.FALLING_ICICLE, FallingBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FALLING_ASH, FallingBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FALLING_SAND_LAYER, FallingBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FALLING_LEAF_LAYER, FallingBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FALLING_PROPAGULE, FallingBlockEntityRenderer::new);

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier("immersive_weathering", "particle/ember_0"));
            registry.register(new Identifier("immersive_weathering", "particle/soot_0"));
            registry.register(new Identifier("immersive_weathering", "particle/ember_1"));
            registry.register(new Identifier("immersive_weathering", "particle/soot_1"));
            registry.register(new Identifier("immersive_weathering", "particle/emberspark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/emberspark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/moss_0"));
            registry.register(new Identifier("immersive_weathering", "particle/moss_1"));

            registry.register(new Identifier("immersive_weathering", "particle/oak_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/mangrove_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_flower_0"));
            registry.register(new Identifier("immersive_weathering", "particle/oak_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/mangrove_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_flower_1"));


            registry.register(new Identifier("immersive_weathering", "particle/oak_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/mangrove_bark_0"));
            registry.register(new Identifier("immersive_weathering", "particle/nether_scale_0"));
            registry.register(new Identifier("immersive_weathering", "particle/oak_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/mangrove_bark_1"));
            registry.register(new Identifier("immersive_weathering", "particle/nether_scale_1"));
            registry.register(new Identifier("immersive_weathering", "particle/oak_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/mangrove_bark_2"));
            registry.register(new Identifier("immersive_weathering", "particle/nether_scale_2"));


            //flower crowns
            registry.register(new Identifier("immersive_weathering", "particle/bee_0"));
            registry.register(new Identifier("immersive_weathering", "particle/bee_1"));

        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.EMBER, EmberParticle.EmberFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOT, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.EMBERSPARK, EmberParticle.EmberFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MOSS, LeafParticle.SimpleLeafParticle::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.OAK_LEAF, LeafParticle.ColoredLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SPRUCE_LEAF, LeafParticle.SpruceLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BIRCH_LEAF, LeafParticle.BirchLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.JUNGLE_LEAF, LeafParticle.ColoredLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ACACIA_LEAF, LeafParticle.ColoredLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.DARK_OAK_LEAF, LeafParticle.ColoredLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MANGROVE_LEAF, LeafParticle.ColoredLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AZALEA_LEAF, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AZALEA_FLOWER, LeafParticle.SimpleLeafParticle::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.OAK_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SPRUCE_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BIRCH_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.JUNGLE_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ACACIA_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.DARK_OAK_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MANGROVE_BARK, LeafParticle.SimpleLeafParticle::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.NETHER_SCALE, LeafParticle.SimpleLeafParticle::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.SCRAPE_RUST, ScrapeRustFactory::new);

        //flower crowns
        ParticleFactoryRegistry.getInstance().register(ModParticles.CROWN_BEE, LeafParticle.SimpleLeafParticle::new);



        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.OAK_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), ModBlocks.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), ModBlocks.BIRCH_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.ACACIA_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.DARK_OAK_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.MANGROVE_LEAF_PILE);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.FROSTY_GRASS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.FROSTY_FERN);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.ROOTED_GRASS_BLOCK);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_COBBLESTONE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_COBBLESTONE_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_COBBLESTONE_STAIRS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_COBBLESTONE_WALL);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.INFESTED_MOSSY_STONE_BRICKS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_STONE_BRICKS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_STONE_BRICK_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_STONE_BRICK_STAIRS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), Blocks.MOSSY_STONE_BRICK_WALL);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_BRICKS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_BRICK_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_BRICK_STAIRS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_BRICK_WALL);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_STONE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_STONE_SLAB);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_STONE_STAIRS);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0D, 0D), ModBlocks.MOSSY_STONE_WALL);


        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getDefaultColor();
        }, ModItems.OAK_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getSpruceColor();
        }, ModItems.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getBirchColor();
        }, ModItems.BIRCH_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getDefaultColor();
        }, ModItems.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getDefaultColor();
        }, ModItems.ACACIA_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getDefaultColor();
        }, ModItems.DARK_OAK_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return FoliageColors.getDefaultColor();
        }, ModItems.MANGROVE_LEAF_PILE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.ROOTED_GRASS_BLOCK);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_COBBLESTONE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_COBBLESTONE_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_COBBLESTONE_STAIRS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_COBBLESTONE_WALL);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.INFESTED_MOSSY_STONE_BRICKS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_STONE_BRICKS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_STONE_BRICK_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_STONE_BRICK_STAIRS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), Items.MOSSY_STONE_BRICK_WALL);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_BRICKS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_BRICK_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_BRICK_STAIRS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_BRICK_WALL);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_STONE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_STONE_SLAB);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_STONE_STAIRS);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D,0.5D), ModItems.MOSSY_STONE_WALL);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOTED_GRASS_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_COBBLESTONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_COBBLESTONE_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_COBBLESTONE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_COBBLESTONE_WALL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.INFESTED_MOSSY_STONE_BRICKS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_STONE_BRICKS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_STONE_BRICK_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_STONE_BRICK_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.MOSSY_STONE_BRICK_WALL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_BRICKS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_BRICK_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_BRICK_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_BRICK_WALL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_STONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_STONE_SLAB, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_STONE_STAIRS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSSY_STONE_WALL, RenderLayer.getCutout());


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VITRIFIED_SAND, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FULGURITE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPRUCE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BIRCH_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUNGLE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ACACIA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AZALEA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AZALEA_FLOWER_PILE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEEDS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.IVY, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOOT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOOT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROST, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_GLASS_PANE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTY_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.THIN_ICE, RenderLayer.getTranslucent());

    }
}
