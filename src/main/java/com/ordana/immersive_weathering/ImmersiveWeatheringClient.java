package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.client.EmberParticle;
import com.ordana.immersive_weathering.registry.client.LeafParticle;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveWeatheringClient {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
/*
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation("immersive_weathering", "particle/ember_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/soot_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/ember_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/soot_1"));

            registry.register(new ResourceLocation("immersive_weathering", "particle/oak_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/birch_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/spruce_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/jungle_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/acacia_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/dark_oak_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/azalea_leaf_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/azalea_flower_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/oak_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/birch_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/spruce_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/jungle_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/acacia_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/dark_oak_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/azalea_leaf_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/azalea_flower_1"));

            registry.register(new ResourceLocation("immersive_weathering", "particle/mulch_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/nulch_0"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/mulch_1"));
            registry.register(new ResourceLocation("immersive_weathering", "particle/nulch_1"));
        }));*/


        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICICLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.OAK_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPRUCE_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BIRCH_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JUNGLE_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACACIA_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DARK_OAK_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AZALEA_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEEDS.get(), RenderType.cutout());
                /*
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RUSTED_IRON_BARS.get(), RenderType.cutout());


        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_IRON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_IRON_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WAXED_RUSTED_IRON_BARS.get(), RenderType.cutout());
        */
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {

        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

        particleEngine.register(ModParticles.EMBER.get(), EmberParticle.EmberFactory::new);
        particleEngine.register(ModParticles.SOOT.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.OAK_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.SPRUCE_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.BIRCH_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.JUNGLE_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.ACACIA_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.DARK_OAK_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.AZALEA_LEAF.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.MULCH.get(), LeafParticle.LeafFactory::new);
        particleEngine.register(ModParticles.NULCH.get(), LeafParticle.LeafFactory::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        BlockColor foliageColor = (state, world, pos, tintIndex) -> world != null && pos != null ?
                BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();

        colors.register(foliageColor, ModBlocks.OAK_LEAF_PILE.get(), ModBlocks.SPRUCE_LEAF_PILE.get(), ModBlocks.ACACIA_LEAF_PILE.get(),
                ModBlocks.JUNGLE_LEAF_PILE.get(), ModBlocks.DARK_OAK_LEAF_PILE.get(), ModBlocks.BIRCH_LEAF_PILE.get());

    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        colors.register((s, t) -> FoliageColor.getDefaultColor(),
                ModItems.OAK_LEAF_PILE.get(), ModItems.JUNGLE_LEAF_PILE.get(), ModItems.DARK_OAK_LEAF_PILE.get(), ModItems.ACACIA_LEAF_PILE.get());
        colors.register((s, t) -> FoliageColor.getBirchColor(), ModItems.BIRCH_LEAF_PILE.get());
        colors.register((s, t) -> FoliageColor.getEvergreenColor(), ModItems.SPRUCE_LEAF_PILE.get());
    }

}
