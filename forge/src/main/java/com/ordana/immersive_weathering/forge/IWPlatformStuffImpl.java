package com.ordana.immersive_weathering.forge;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.blocks.rustable.*;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IWPlatformStuffImpl {

    /*
    public static Block createSpecialBlock(IWPlatformStuff.BlockType type, BlockBehaviour.Properties properties, Object... extraParams) {
        return switch (type) {
            case RUSTABLE_BLOCK -> new RustableBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_STAIRS ->
                    new RustableStairsBlock((Rustable.RustLevel) extraParams[0], (Supplier<Block>) extraParams[1], properties);
            case RUSTABLE_BARS -> new RustableBarsBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_DOOR -> new RustableDoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_SLAB -> new RustableSlabBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_TRAPDOOR -> new RustableTrapdoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_VERTICAL_SLAB -> new RustableVerticalSlabBlock((Rustable.RustLevel) extraParams[0], properties);
        };
    }

     */

    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {

        Block a = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_hedge"));
        Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_hedge"));
        if (a != null && b != null) {
            builder.put(a, b);
        }
        Block c = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_leaf_carpet"));
        Block d = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_leaf_carpet"));
        if (c != null && d != null) {
            builder.put(c, d);
        }


    }

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        if (IntegrationHandler.quark) {
            QuarkPlugin.addAllVerticalSlabs(builder);
         }
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
         if (IntegrationHandler.quark) {
            QuarkPlugin.addAllVerticalSlabs(builder);
         }
    }


    public static FlowingFluid getFlowingFluid(LiquidBlock block) {
        return block.getFluid();
    }


    public static Collection<LeafPileBlock> getLeafPiles() {
        return List.of();
    }

    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {

        Consumer<BiomeLoadingEvent> c = e -> FeatureHacks.registerVanillaBiomeFeatures(e, tagKey, feature, step);
        MinecraftForge.EVENT_BUS.addListener(c);
    }

    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        return Map.of();
        //  return ModDynamicRegistry.getLeafToLeafPileMap();
    }


    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        return Map.of();
        //  return ModDynamicRegistry.getLeavesToParticleMap();
    }

    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
        // builder.putAll(ModDynamicRegistry.getBarkMap());
    }



}
