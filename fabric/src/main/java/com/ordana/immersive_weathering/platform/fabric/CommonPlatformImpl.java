package com.ordana.immersive_weathering.platform.fabric;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommonPlatformImpl {

    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }


    public static Collection<LeafPileBlock> getLeafPiles() {
        return List.of();
    }

    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
        BiomeModifications.addFeature(BiomeSelectors.tag(tagKey), step, feature);

    }

    @Nullable
    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        return null;
    }

    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
    }

    @Nullable
    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        return null;
    }


}
