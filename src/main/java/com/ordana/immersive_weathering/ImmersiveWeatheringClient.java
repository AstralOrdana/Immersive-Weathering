package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.client.EmberParticle;
import com.ordana.immersive_weathering.registry.client.LeafParticle;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
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

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier("immersive_weathering", "particle/ember_0"));
            registry.register(new Identifier("immersive_weathering", "particle/soot_0"));
            registry.register(new Identifier("immersive_weathering", "particle/ember_1"));
            registry.register(new Identifier("immersive_weathering", "particle/soot_1"));

            registry.register(new Identifier("immersive_weathering", "particle/oak_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_leaf_0"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_flower_0"));
            registry.register(new Identifier("immersive_weathering", "particle/oak_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/birch_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/spruce_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/jungle_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/acacia_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/dark_oak_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_leaf_1"));
            registry.register(new Identifier("immersive_weathering", "particle/azalea_flower_1"));

            registry.register(new Identifier("immersive_weathering", "particle/mulch_0"));
            registry.register(new Identifier("immersive_weathering", "particle/nulch_0"));
            registry.register(new Identifier("immersive_weathering", "particle/mulch_1"));
            registry.register(new Identifier("immersive_weathering", "particle/nulch_1"));
        }));
        ParticleFactoryRegistry.getInstance().register(ModParticles.EMBER, EmberParticle.EmberFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SOOT, LeafParticle.LeafFactory::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.OAK_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SPRUCE_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BIRCH_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.JUNGLE_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ACACIA_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.DARK_OAK_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AZALEA_LEAF, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AZALEA_FLOWER, LeafParticle.LeafFactory::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.MULCH, LeafParticle.LeafFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.NULCH, LeafParticle.LeafFactory::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.SCRAPE_RUST, ScrapeRustFactory::new);


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICICLE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPRUCE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BIRCH_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUNGLE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ACACIA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AZALEA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AZALEA_FLOWER_PILE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEEDS, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.OAK_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), ModBlocks.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), ModBlocks.BIRCH_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.ACACIA_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), ModBlocks.DARK_OAK_LEAF_PILE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.OAK_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getSpruceColor();
        }, ModItems.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getBirchColor();
        }, ModItems.BIRCH_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.ACACIA_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.DARK_OAK_LEAF_PILE);


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
    }
}
