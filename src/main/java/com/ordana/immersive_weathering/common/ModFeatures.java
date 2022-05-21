package com.ordana.immersive_weathering.common;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.common.features.IcicleClusterFeatureConfig;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
            ForgeRegistries.FEATURES, ImmersiveWeathering.MOD_ID);

    public static final RegistryObject<Feature<IcicleClusterFeatureConfig>> ICICLE_CLUSTER = FEATURES.register(
            "icicle_cluster", () -> new IcicleClusterFeature(IcicleClusterFeatureConfig.CODEC));

    //in code because the json defined one dont want to work....
    private static void registerLeafPile(String name) {
        String n = "immersive_weathering:" + name + "_leaf_pile";
        final Holder<ConfiguredFeature<BlockPileConfiguration, ?>> PILE =
                FeatureUtils.register(n, Feature.BLOCK_PILE,
                        new BlockPileConfiguration(BlockStateProvider.simple(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(n)))));

        PlacementUtils.register("immersive_weathering:" + name + "_leaf_pile", PILE,
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                NoiseBasedCountPlacement.of(2, 50, 0.7),
                NoiseThresholdCountPlacement.of(0.3, 1, 5),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

    }

    private static void registerHumusPatches(String name) {
        final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> PATCH =
                FeatureUtils.register("immersive_weathering:" + name, Feature.VEGETATION_PATCH,
                        new VegetationPatchConfiguration(
                                BlockTags.DIRT,
                                BlockStateProvider.simple(ModBlocks.HUMUS.get()),
                                PlacementUtils.inlinePlaced(FeatureUtils.register("immersive_weathering:no_op", Feature.NO_OP)),
                                CaveSurface.FLOOR,
                                ConstantInt.of(1),
                                0.0F,
                                5,
                                0.8F,
                                UniformInt.of(2, 6),
                                0.5F));

        PlacementUtils.register("immersive_weathering:" + name, PATCH,
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                NoiseBasedCountPlacement.of(15, 50, -0.1),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

    }

    public static void init() {
        registerLeafPile("oak");
        registerLeafPile("dark_oak");
        registerLeafPile("birch");
        registerLeafPile("spruce");
        registerHumusPatches("humus_patches");


        //same as dripstone
        //TODO: make unique
        Holder<ConfiguredFeature<IcicleClusterFeatureConfig, ?>> icicles = FeatureUtils.register(
                "immersive_weathering:icicles", ICICLE_CLUSTER.get(),
                new IcicleClusterFeatureConfig(
                        12,
                        UniformInt.of(3, 6),
                        UniformInt.of(2, 8),
                        1,
                        3,
                        UniformInt.of(2, 4),
                        UniformFloat.of(0.3F, 0.7F),
                        ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F),
                        0.1F,
                        3,
                        8));

        ICICLES = PlacementUtils.register("immersive_weathering:icicles", icicles,
                NoiseBasedCountPlacement.of(10, 50, 0.1),
                NoiseThresholdCountPlacement.of(0.3, 1, 10),
                CountOnEveryLayerPlacement.of(3),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(92), VerticalAnchor.belowTop(1)),
                BiomeFilter.biome());

        CAVE_ICICLES = PlacementUtils.register("immersive_weathering:icicles_ice_caves", icicles,
                NoiseBasedCountPlacement.of(10, 50, 0.1),
                NoiseThresholdCountPlacement.of(0.3, 1, 10),
                CountOnEveryLayerPlacement.of(3),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(1), VerticalAnchor.belowTop(1)),
                BiomeFilter.biome());

    }

    private static Holder<PlacedFeature> ICICLES;
    private static Holder<PlacedFeature> CAVE_ICICLES;


    private static void addFeature(BiomeLoadingEvent event, String name, GenerationStep.Decoration step) {
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res(name));
        var feature = BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(key);
        addFeature(event, feature, step);
    }

    private static void addFeature(BiomeLoadingEvent event, Holder<PlacedFeature> feature, GenerationStep.Decoration step) {
        event.getGeneration().addFeature(step, feature);
    }

    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {

        ResourceKey<Biome> key = ResourceKey.create(ForgeRegistries.Keys.BIOMES, event.getName());
        //Holder<Biome> holder = BuiltinRegistries.BIOME.getHolderOrThrow(key);

        Biome.BiomeCategory category = event.getCategory();

        if(ServerConfigs.ICICLES_PATCHES.get()) {
            if (BiomeDictionary.hasType(key, BiomeDictionary.Type.SNOWY) || category == Biome.BiomeCategory.ICY) {
                addFeature(event, ICICLES, GenerationStep.Decoration.TOP_LAYER_MODIFICATION);
                if (category == Biome.BiomeCategory.UNDERGROUND) {
                    addFeature(event, CAVE_ICICLES, GenerationStep.Decoration.UNDERGROUND_DECORATION);
                }
            }
        }

        /*
        if(ServerConfigs.LEAF_PILES_PATCHES.get()) {
            if (key == Biomes.FOREST || key == Biomes.WINDSWEPT_FOREST || key == Biomes.FLOWER_FOREST) {
                addFeature(event, "oak_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
            } else if (key == Biomes.DARK_FOREST) {
                addFeature(event, "dark_oak_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
            } else if (key == Biomes.BIRCH_FOREST || key == Biomes.OLD_GROWTH_BIRCH_FOREST) {
                addFeature(event, "birch_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
            } else if (key == Biomes.OLD_GROWTH_SPRUCE_TAIGA || key == Biomes.OLD_GROWTH_PINE_TAIGA || key == Biomes.TAIGA) {
                addFeature(event, "spruce_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
            }
        }*/
        if(ServerConfigs.HUMUS_PATCHES.get()) {
            if (key == Biomes.DARK_FOREST) {
                addFeature(event, "humus_patches", GenerationStep.Decoration.LOCAL_MODIFICATIONS);
            }
        }
    }
}
