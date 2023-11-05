package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.features.IcicleClusterFeatureConfig;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class ModFeatures {

    public static final Supplier<Feature<IcicleClusterFeatureConfig>> ICICLE_FEATURE = RegHelper.registerFeature(
            ImmersiveWeathering.res("icicle_cluster"), () ->
                    new IcicleClusterFeature(IcicleClusterFeatureConfig.CODEC));

    public static void init() {

        //icy
        ResourceKey<PlacedFeature> icicles = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("icicles"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModTags.ICY, icicles);

        ResourceKey<PlacedFeature> frost_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("frost_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModTags.ICY, frost_patch);

        ResourceKey<PlacedFeature> cryosol_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("cryosol_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.ICY, cryosol_patch);


        //has humus
        ResourceKey<PlacedFeature> humus_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("humus_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_HUMUS, humus_patch);

        ResourceKey<PlacedFeature> rooted_ceiling = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("rooted_ceiling"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.VEGETAL_DECORATION, ModTags.HAS_HUMUS, rooted_ceiling);


        //has fluvisol
        ResourceKey<PlacedFeature> fluvisol_patch_submerged = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("fluvisol_patch_submerged"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_submerged);

        ResourceKey<PlacedFeature> fluvisol_patch_surface = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("fluvisol_patch_surface"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_surface);

        ResourceKey<PlacedFeature> fluvisol_patch_dry = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("fluvisol_patch_dry"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, fluvisol_patch_dry);

        ResourceKey<PlacedFeature> silt_disk = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,ImmersiveWeathering.res("silt_disk"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_FLUVISOL, silt_disk);


        //dry lakebed
        ResourceKey<PlacedFeature> dry_lakebed = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("dry_lakebed"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, dry_lakebed);

        ResourceKey<PlacedFeature> dry_lakebed_large = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("dry_lakebed_large"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, dry_lakebed_large);

        ResourceKey<PlacedFeature> desert_fossil = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("desert_fossil"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, desert_fossil);

        ResourceKey<PlacedFeature> terracotta_blobs = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("terracotta_blobs"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, terracotta_blobs);

        ResourceKey<PlacedFeature> vertisol_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("vertisol_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_LAKEBED, vertisol_patch);


        //underground desert
        ResourceKey<PlacedFeature> sandstone_blobs = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("sandstone_blobs"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.UNDERGROUND_DESERT, sandstone_blobs);

        ResourceKey<PlacedFeature> sand_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("sand_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.UNDERGROUND_DESERT, sand_patch);

        ResourceKey<PlacedFeature> sandy_clay_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("sandy_clay_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.UNDERGROUND_DESERT, sandy_clay_patch);

        ResourceKey<PlacedFeature> sand_pile = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("sand_pile"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.UNDERGROUND_DESERT, sand_pile);


        //ivy
        ResourceKey<PlacedFeature> ivy_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("ivy_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.VEGETAL_DECORATION, ModTags.HAS_IVY, ivy_patch);


        //moss
        ResourceKey<PlacedFeature> moss_patch = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("moss_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.VEGETAL_DECORATION, ModTags.HAS_MOSS, moss_patch);


        //NGVs
        ResourceKey<PlacedFeature> nether_gold_vein = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, ImmersiveWeathering.res("nether_gold_vein"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.RAW_GENERATION, ModTags.HAS_NETHER_VEINS, nether_gold_vein);

    }
}
