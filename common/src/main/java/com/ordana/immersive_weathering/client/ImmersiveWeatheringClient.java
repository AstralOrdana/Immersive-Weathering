package com.ordana.immersive_weathering.client;

import com.ordana.immersive_weathering.client.particles.EmberParticle;
import com.ordana.immersive_weathering.client.particles.LeafParticle;
import com.ordana.immersive_weathering.platform.ClientPlatform;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModEntities;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Function;

public class ImmersiveWeatheringClient {

    public static void initClient() {

        ClientPlatform.registerRenderType(ModBlocks.ROOTED_GRASS_BLOCK.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_COBBLESTONE, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_COBBLESTONE_SLAB, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_COBBLESTONE_STAIRS, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_COBBLESTONE_WALL, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.INFESTED_MOSSY_STONE_BRICKS, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_STONE_BRICKS, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_STONE_BRICK_SLAB, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_STONE_BRICK_STAIRS, RenderType.cutout());
        ClientPlatform.registerRenderType(Blocks.MOSSY_STONE_BRICK_WALL, RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_BRICKS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_BRICK_SLAB.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_BRICK_STAIRS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_BRICK_WALL.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_STONE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_STONE_SLAB.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_STONE_STAIRS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.MOSSY_STONE_WALL.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.VITRIFIED_SAND.get(), RenderType.translucent());

        ClientPlatform.registerRenderType(ModBlocks.EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.RUSTED_IRON_BARS.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.WAXED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.WAXED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.WAXED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.WAXED_RUSTED_IRON_BARS.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.ICICLE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FULGURITE.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.OAK_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.SPRUCE_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.BIRCH_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.JUNGLE_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.ACACIA_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.DARK_OAK_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.AZALEA_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.AZALEA_FLOWER_PILE.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.WEEDS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.IVY.get(), RenderType.cutout());

        ClientPlatform.registerRenderType(ModBlocks.SOOT.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.SOOT.get(), RenderType.translucent());
        ClientPlatform.registerRenderType(ModBlocks.FROST.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FROST.get(), RenderType.translucent());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_GLASS.get(), RenderType.translucent());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_GLASS_PANE.get(), RenderType.translucent());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_GRASS.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.FROSTY_FERN.get(), RenderType.cutout());
        ClientPlatform.registerRenderType(ModBlocks.THIN_ICE.get(), RenderType.translucent());

        ClientPlatform.registerRenderType(ModBlocks.HANGING_ROOTS_WALL.get(), RenderType.cutout());
    }

    @FunctionalInterface
    public interface EntityRendererReg {
        <E extends Entity> void register(EntityType<? extends E> entity, EntityRendererProvider<E> renderer);
    }

    public static void onRegisterEntityRenderTypes(EntityRendererReg event) {
        event.register(ModEntities.FALLING_ICICLE.get(), FallingBlockRendererGeneric::new);
        event.register(ModEntities.FALLING_LAYER.get(), FallingBlockRendererGeneric::new);
    }

    @FunctionalInterface
    public interface ParticleRendererReg {
        <T extends ParticleOptions> void register(ParticleType<T> type, Function<SpriteSet, ParticleProvider<T>> particleFactory);
    }

    public static void onRegisterParticles(ParticleRendererReg event) {
        event.register(ModParticles.EMBERSPARK.get(), EmberParticle.EmberFactory::new);
        event.register(ModParticles.EMBER.get(), EmberParticle.EmberFactory::new);

        event.register(ModParticles.SOOT.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.OAK_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.SPRUCE_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.BIRCH_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.JUNGLE_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.ACACIA_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.DARK_OAK_LEAF.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.AZALEA_LEAF.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.AZALEA_FLOWER.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.MULCH.get(), LeafParticle.SimpleLeafParticle::new);
        event.register(ModParticles.NULCH.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.SCRAPE_RUST.get(), ScrapeRustFactory::new);

        event.register(ModParticles.OAK_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.BIRCH_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.SPRUCE_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.DARK_OAK_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.JUNGLE_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.ACACIA_BARK.get(), LeafParticle.ColoredLeafParticle::new);
        event.register(ModParticles.NETHER_SCALE.get(), LeafParticle.SimpleLeafParticle::new);

        event.register(ModParticles.MOSS.get(), LeafParticle.SimpleLeafParticle::new);
       // event.register(ModParticles.CROWN_BEE.get(), LeafParticle.SimpleLeafParticle::new);
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

    @FunctionalInterface
    public interface BlockColorReg {
        void register(BlockColor color, Block block);
    }

    public static void onRegisterBlockColors(BlockColorReg event) {

        final BlockColor defaultGrassColor = (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0D, 0D);
        final BlockColor defaultFoliageColor = (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.getDefaultColor();

        event.register(defaultFoliageColor, ModBlocks.OAK_LEAF_PILE.get());
        event.register((state, world, pos, tintIndex) -> FoliageColor.getEvergreenColor(), ModBlocks.SPRUCE_LEAF_PILE.get());
        event.register((state, world, pos, tintIndex) -> FoliageColor.getBirchColor(), ModBlocks.BIRCH_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.JUNGLE_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.ACACIA_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.DARK_OAK_LEAF_PILE.get());

        event.register(defaultGrassColor, ModBlocks.FROSTY_GRASS.get());
        event.register(defaultGrassColor, ModBlocks.FROSTY_FERN.get());
        event.register(defaultGrassColor, ModBlocks.ROOTED_GRASS_BLOCK.get());

        event.register(defaultGrassColor, Blocks.MOSSY_COBBLESTONE);
        event.register(defaultGrassColor, Blocks.MOSSY_COBBLESTONE_SLAB);
        event.register(defaultGrassColor, Blocks.MOSSY_COBBLESTONE_STAIRS);
        event.register(defaultGrassColor, Blocks.MOSSY_COBBLESTONE_WALL);

        event.register(defaultGrassColor, Blocks.INFESTED_MOSSY_STONE_BRICKS);
        event.register(defaultGrassColor, Blocks.MOSSY_STONE_BRICKS);
        event.register(defaultGrassColor, Blocks.MOSSY_STONE_BRICK_SLAB);
        event.register(defaultGrassColor, Blocks.MOSSY_STONE_BRICK_STAIRS);
        event.register(defaultGrassColor, Blocks.MOSSY_STONE_BRICK_WALL);

        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICKS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_SLAB.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_STAIRS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_WALL.get());

        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_SLAB.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_STAIRS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_WALL.get());

    }

    @FunctionalInterface
    public interface ItemColorReg {
        void register(ItemColor color, ItemLike... block);
    }

    public static void onRegisterItemColors(ItemColorReg event) {
        final ItemColor defaultGrassColor = (stack, tintIndex) -> GrassColor.get(0.5D, 0.5D);
        event.register(defaultGrassColor, ModBlocks.ROOTED_GRASS_BLOCK.get());
        event.register(defaultGrassColor, Items.MOSSY_COBBLESTONE, Items.MOSSY_COBBLESTONE_SLAB);
        event.register(defaultGrassColor, Items.MOSSY_COBBLESTONE_STAIRS);
        event.register(defaultGrassColor, Items.MOSSY_COBBLESTONE_WALL);
        event.register(defaultGrassColor, Items.INFESTED_MOSSY_STONE_BRICKS);
        event.register(defaultGrassColor, Items.MOSSY_STONE_BRICKS);
        event.register(defaultGrassColor, Items.MOSSY_STONE_BRICK_SLAB);
        event.register(defaultGrassColor, Items.MOSSY_STONE_BRICK_STAIRS);
        event.register(defaultGrassColor, Items.MOSSY_STONE_BRICK_WALL);
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICKS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_SLAB.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_STAIRS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_BRICK_WALL.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_SLAB.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_STAIRS.get());
        event.register(defaultGrassColor, ModBlocks.MOSSY_STONE_WALL.get());

        final ItemColor defaultFoliageColor = (stack, tintIndex) -> FoliageColor.getDefaultColor();

        event.register(defaultFoliageColor, ModBlocks.OAK_LEAF_PILE.get());
        event.register((stack, tintIndex) -> FoliageColor.getEvergreenColor(), ModBlocks.SPRUCE_LEAF_PILE.get());
        event.register((stack, tintIndex) -> FoliageColor.getBirchColor(), ModBlocks.BIRCH_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.JUNGLE_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.ACACIA_LEAF_PILE.get());
        event.register(defaultFoliageColor, ModBlocks.DARK_OAK_LEAF_PILE.get());

    }

}
