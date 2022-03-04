package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.registry.features.IcicleClusterFeatureConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFeatures {
    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, "immersive_weathering:icicle_cluster", new IcicleClusterFeature(IcicleClusterFeatureConfig.CODEC));
        Registry.register(Registry.FEATURE, "immersive_weathering:oak_leaf_pile", new BlockPileFeature(BlockPileConfiguration.CODEC));
        Registry.register(Registry.FEATURE, "immersive_weathering:dark_oak_leaf_pile", new BlockPileFeature(BlockPileConfiguration.CODEC));
        Registry.register(Registry.FEATURE, "immersive_weathering:spruce_leaf_pile", new BlockPileFeature(BlockPileConfiguration.CODEC));
        Registry.register(Registry.FEATURE, "immersive_weathering:birch_leaf_pile", new BlockPileFeature(BlockPileConfiguration.CODEC));

        ResourceKey<PlacedFeature> icicles = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation("immersive_weathering", "icicles"));
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.ICY), GenerationStep.Decoration.TOP_LAYER_MODIFICATION, icicles);
        BiomeModifications.addFeature(BiomeSelectors.tag(ModTags.ICE_CAVES), GenerationStep.Decoration.TOP_LAYER_MODIFICATION, icicles);

        ResourceKey<PlacedFeature> oak_leaf_pile = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation("immersive_weathering", "oak_leaf_pile"));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, oak_leaf_pile);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, oak_leaf_pile);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, oak_leaf_pile);

        ResourceKey<PlacedFeature> dark_oak_leaf_pile = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation("immersive_weathering", "dark_oak_leaf_pile"));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, dark_oak_leaf_pile);

        ResourceKey<PlacedFeature> birch_leaf_pile = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation("immersive_weathering", "birch_leaf_pile"));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, birch_leaf_pile);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST), GenerationStep.Decoration.VEGETAL_DECORATION, birch_leaf_pile);

        ResourceKey<PlacedFeature> spruce_leaf_pile = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation("immersive_weathering", "spruce_leaf_pile"));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_SPRUCE_TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, spruce_leaf_pile);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_PINE_TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, spruce_leaf_pile);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA), GenerationStep.Decoration.VEGETAL_DECORATION, spruce_leaf_pile);

    }
}
