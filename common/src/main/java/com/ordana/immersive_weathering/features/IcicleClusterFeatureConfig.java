package com.ordana.immersive_weathering.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class IcicleClusterFeatureConfig implements FeatureConfiguration {
    public static final Codec<IcicleClusterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.floorToCeilingSearchRange;
        }), IntProvider.codec(1, 128).fieldOf("height").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.height;
        }), IntProvider.codec(1, 128).fieldOf("radius").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.radius;
        }), Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.maxStalagmiteStalactiteHeightDiff;
        }), Codec.intRange(1, 64).fieldOf("height_deviation").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.heightDeviation;
        }), IntProvider.codec(0, 128).fieldOf("icicle_block_layer_thickness").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.icicleBlockLayerThickness;
        }), FloatProvider.codec(0.0F, 2.0F).fieldOf("density").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.density;
        }), FloatProvider.codec(0.0F, 2.0F).fieldOf("wetness").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.wetness;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_icicle_column_at_max_distance_from_center").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.chanceOfIcicleColumnAtMaxDistanceFromCenter;
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_icicle_column").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.maxDistanceFromCenterAffectingChanceOfIcicleColumn;
        }), Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter((icicleClusterFeatureConfig) -> {
            return icicleClusterFeatureConfig.maxDistanceFromCenterAffectingHeightBias;
        })).apply(instance, IcicleClusterFeatureConfig::new);
    });
    public final int floorToCeilingSearchRange;
    public final IntProvider height;
    public final IntProvider radius;
    public final int maxStalagmiteStalactiteHeightDiff;
    public final int heightDeviation;
    public final IntProvider icicleBlockLayerThickness;
    public final FloatProvider density;
    public final FloatProvider wetness;
    public final float chanceOfIcicleColumnAtMaxDistanceFromCenter;
    public final int maxDistanceFromCenterAffectingChanceOfIcicleColumn;
    public final int maxDistanceFromCenterAffectingHeightBias;

    public IcicleClusterFeatureConfig(int floorToCeilingSearchRange, IntProvider height, IntProvider radius, int maxStalagmiteStalactiteHeightDiff, int heightDeviation, IntProvider icicleBlockLayerThickness, FloatProvider density, FloatProvider wetness, float wetnessMean, int maxDistanceFromCenterAffectingChanceOfIcicleColumn, int maxDistanceFromCenterAffectingHeightBias) {
        this.floorToCeilingSearchRange = floorToCeilingSearchRange;
        this.height = height;
        this.radius = radius;
        this.maxStalagmiteStalactiteHeightDiff = maxStalagmiteStalactiteHeightDiff;
        this.heightDeviation = heightDeviation;
        this.icicleBlockLayerThickness = icicleBlockLayerThickness;
        this.density = density;
        this.wetness = wetness;
        this.chanceOfIcicleColumnAtMaxDistanceFromCenter = wetnessMean;
        this.maxDistanceFromCenterAffectingChanceOfIcicleColumn = maxDistanceFromCenterAffectingChanceOfIcicleColumn;
        this.maxDistanceFromCenterAffectingHeightBias = maxDistanceFromCenterAffectingHeightBias;
    }
}