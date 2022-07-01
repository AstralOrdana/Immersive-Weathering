package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.features.IcicleClusterFeatureConfig;
import com.ordana.immersive_weathering.features.IvyFeature;
import com.ordana.immersive_weathering.platform.CommonPlatform;
import com.ordana.immersive_weathering.platform.RegistryPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class ModFeatures {

    public static final Supplier<Feature<IcicleClusterFeatureConfig>> ICICLE_FEATURE = RegistryPlatform.registerFeature("icicle_cluster", () ->
            new IcicleClusterFeature(IcicleClusterFeatureConfig.CODEC));

    public static final Supplier<Feature<GlowLichenConfiguration>> IVY_FEATURE = RegistryPlatform.registerFeature("ivy_feature", () ->
            new IvyFeature(GlowLichenConfiguration.CODEC));

    public static void init() {

        ResourceKey<PlacedFeature> icicles = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("icicles"));
        if (CommonConfigs.ICICLE_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModTags.ICY, icicles);
        }

        ResourceKey<PlacedFeature> cryosol_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("cryosol_patch"));
        if (CommonConfigs.CRYOSOL_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.ICY, cryosol_patch);
        }

        ResourceKey<PlacedFeature> humus_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("humus_patch"));
        if (CommonConfigs.HUMUS_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_HUMUS, humus_patch);
        }

        ResourceKey<PlacedFeature> rooted_ceiling = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("rooted_ceiling"));
        if (CommonConfigs.ROOTS_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.VEGETAL_DECORATION, ModTags.HAS_HUMUS, rooted_ceiling);
        }

        ResourceKey<PlacedFeature> fluvisol_patch_submerged = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("fluvisol_patch_submerged"));
        ResourceKey<PlacedFeature> fluvisol_patch_surface = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("fluvisol_patch_surface"));
        ResourceKey<PlacedFeature> fluvisol_patch_dry = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("fluvisol_patch_dry"));
        if (CommonConfigs.FLUVISOL_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_dry);
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_surface);
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_submerged);
        }

        ResourceKey<PlacedFeature> silt_disk = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("silt_disk"));
        if (CommonConfigs.SILT_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, silt_disk);
        }

        ResourceKey<PlacedFeature> dry_lakebed = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("dry_lakebed"));
        ResourceKey<PlacedFeature> dry_lakebed_large = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("dry_lakebed_large"));
        if (CommonConfigs.LAKEBED_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, dry_lakebed_large);
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, dry_lakebed);
        }

        ResourceKey<PlacedFeature> vertisol_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("vertisol_patch"));
        if (CommonConfigs.VERITOSOL_FEATURE.get()) {
            CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_VERITOSOL, vertisol_patch);
        }


        ResourceKey<PlacedFeature> ivy_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res("ivy_patch"));
        CommonPlatform.addFeatureToBiome(GenerationStep.Decoration.VEGETAL_DECORATION, ModTags.HAS_IVY, ivy_patch);


    }
}
