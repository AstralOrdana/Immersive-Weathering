package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.ModEntities;
import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.blocks.LeafPilesRegistry;
import com.ordana.immersive_weathering.common.particles.EmberParticle;
import com.ordana.immersive_weathering.common.particles.LeafParticle;
import com.ordana.immersive_weathering.mixin.BlockColorAccessor;
import com.ordana.immersive_weathering.mixin.ItemColorAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.Map;

@Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveWeatheringClient {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {

        LeafPilesRegistry.LEAF_PILES.get().values().forEach(l ->
                ItemBlockRenderTypes.setRenderLayer(l, RenderType.cutout()));

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.VITRIFIED_SAND.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ICICLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WEEDS.get(), RenderType.cutout());

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

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.HANGING_ROOTS_WALL.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.FALLING_ICICLE.get(), FallingBlockRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {

        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

        particleEngine.register(ModParticles.EMBER.get(), EmberParticle.EmberFactory::new);
        particleEngine.register(ModParticles.SOOT.get(), LeafParticle.SimpleLeafParticle::new);
        particleEngine.register(ModParticles.OAK_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.SPRUCE_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.BIRCH_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.JUNGLE_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.ACACIA_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.DARK_OAK_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        particleEngine.register(ModParticles.AZALEA_LEAF.get(), LeafParticle.SimpleLeafParticle::new);
        particleEngine.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.SimpleLeafParticle::new);


        particleEngine.register(ModParticles.MULCH.get(), LeafParticle.SimpleLeafParticle::new);
        particleEngine.register(ModParticles.NULCH.get(), LeafParticle.SimpleLeafParticle::new);

        particleEngine.register(ModParticles.SCRAPE_RUST.get(), ScrapeRustFactory::new);
    }

    public static class ScrapeRustFactory extends GlowParticle.ScrapeProvider {

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

    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {

        BlockColors colors = event.getBlockColors();

        /*

        BlockColor foliageColor = (state, world, pos, tintIndex) -> world != null && pos != null ?
                BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();

        colors.register(foliageColor, ModBlocks.OAK_LEAF_PILE.get(), ModBlocks.ACACIA_LEAF_PILE.get(),
                ModBlocks.JUNGLE_LEAF_PILE.get(), ModBlocks.DARK_OAK_LEAF_PILE.get());

        colors.register((a,b,c,d)->FoliageColor.getBirchColor(), ModBlocks.BIRCH_LEAF_PILE.get());
        colors.register((a,b,c,d)->FoliageColor.getEvergreenColor(), ModBlocks.SPRUCE_LEAF_PILE.get());
        */

    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        /*
        colors.register((s, t) -> FoliageColor.getDefaultColor(),
                ModItems.OAK_LEAF_PILE.get(), ModItems.JUNGLE_LEAF_PILE.get(), ModItems.DARK_OAK_LEAF_PILE.get(), ModItems.ACACIA_LEAF_PILE.get());
        colors.register((s, t) -> FoliageColor.getBirchColor(), ModItems.BIRCH_LEAF_PILE.get());
        colors.register((s, t) -> FoliageColor.getEvergreenColor(), ModItems.SPRUCE_LEAF_PILE.get());
        */
    }

    @Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientTicker {

        private static boolean clientTicked = false;

        @SubscribeEvent
        public static void firstClientTick(TickEvent.ClientTickEvent event) {
            if (!clientTicked && event.phase == TickEvent.Phase.END) {
                // if(ModList.get().isLoaded("quark")) QuarkPlugin.onFirstClientTick();

                try {
                    BlockColors bc = Minecraft.getInstance().getBlockColors();
                    Map<IRegistryDelegate<Block>, BlockColor> blockColorMap = ((BlockColorAccessor) bc).getBlockColors();

                    LeafPilesRegistry.LEAF_PILES.get().forEach((key, value) -> {
                        BlockState leafState = key.defaultBlockState();
                        BlockColor color = blockColorMap.get(key.delegate);
                        if (color != null) {
                            BlockColor newBc = (s, t, p, i) -> color.getColor(leafState, t, p, i);
                            bc.register(newBc, value);
                        }
                    });

                } catch (Exception ignored) {
                }

                try {
                    ItemColors ic = Minecraft.getInstance().getItemColors();
                    Map<IRegistryDelegate<Item>, ItemColor> itemColorMap = ((ItemColorAccessor) ic).getItemColors();

                    LeafPilesRegistry.LEAF_PILES.get().forEach((key, value) -> {
                        ItemStack leafItem = new ItemStack(key);
                        ItemColor baseColor = itemColorMap.get(leafItem.getItem().delegate);
                        if (baseColor != null) {
                            ItemColor newIc = (s, i) -> baseColor.getColor(leafItem, i);
                            ic.register(newIc, value);
                        }
                    });

                } catch (Exception ignored) {
                }

                clientTicked = true;
            }

        }
    }


}
