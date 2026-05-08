package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.features.IcicleClusterFeatureConfig;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
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
        ResourceKey<PlacedFeature> icicles = ResourceKey.create(Registries.PLACED_FEATURE, ImmersiveWeathering.res("icicles"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModTags.ICY, icicles);

        ResourceKey<PlacedFeature> frost_patch = ResourceKey.create(Registries.PLACED_FEATURE, ImmersiveWeathering.res("frost_patch"));
        IWPlatformStuff.addFeatureToBiome(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ModTags.ICY, frost_patch);
    }
}
