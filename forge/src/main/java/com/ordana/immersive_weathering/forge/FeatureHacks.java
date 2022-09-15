package com.ordana.immersive_weathering.forge;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.reg.ModTags;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

//remove in 1.19
//credits to Tslat
public class FeatureHacks {

    public static void init() {
    }

    public static final Supplier<Feature<?>> VANILLA_JSON_FEATURE = RegHelper.registerFeature(
            ImmersiveWeathering.res("vanilla_json"),
            () -> new VanillaJsonFeature(VanillaJsonFeature.VanillaJsonFeatureConfig.CODEC));

    public static final Map<String, FeatureContainer> FEATURE_PLACERS = new HashMap<>();

    static {
        add("icicles");
        add("frost_patch");
        add("cryosol_patch");
        add("humus_patch");
        add("rooted_ceiling");
        add("fluvisol_patch_submerged");
        add("fluvisol_patch_surface");
        add("fluvisol_patch_dry");
        add("silt_disk");
        add("dry_lakebed");
        add("dry_lakebed_large");
        add("sandstone_blobs");
        add("sand_patch");
        add("sandy_clay_patch");
        add("sand_pile");
        add("desert_fossil");
        add("terracotta_blobs");
        add("vertisol_patch");
        add("ivy_patch");
    }

    @NotNull
    private static void add(String n) {
        FEATURE_PLACERS.put(n, FeatureContainer.vanillaJsonFeature(ImmersiveWeathering.res(n)));
    }

    private static final Map<ResourceKey<?>, TagKey<?>> FEATURES_PER_BIOME = new HashMap<>();

    public static void registerVanillaBiomeFeatures(final BiomeLoadingEvent ev, TagKey<Biome> biomeTagKey,
                                                    ResourceKey<PlacedFeature> resourceKey, GenerationStep.Decoration step) {
        if (ev.getName() == null)
            return;
        FEATURES_PER_BIOME.put(resourceKey, biomeTagKey);
        if (isTagged(biomeTagKey, ev.getName())) {
            BiomeGenerationSettingsBuilder builder = ev.getGeneration();
            var f = FEATURE_PLACERS.get(resourceKey.location().getPath());
            if (f != null) {
                builder.addFeature(step, Holder.direct(f.placedFeature().get()));
            }
        }

    }

    public record FeatureContainer(Supplier<? extends Feature<? extends FeatureConfiguration>> feature,
                                   NonNullLazy<ConfiguredFeature<? extends FeatureConfiguration, ? extends Feature<? extends FeatureConfiguration>>> configuredFeature,
                                   NonNullLazy<PlacedFeature> placedFeature) {

        static FeatureContainer vanillaJsonFeature(ResourceLocation placedFeatureId) {
            NonNullLazy<ConfiguredFeature<? extends FeatureConfiguration, ? extends Feature<? extends FeatureConfiguration>>> configuredFeature = NonNullLazy.of(() -> new ConfiguredFeature(VANILLA_JSON_FEATURE.get(), new VanillaJsonFeature.VanillaJsonFeatureConfig(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, placedFeatureId))));

            return new FeatureContainer(VANILLA_JSON_FEATURE, configuredFeature, NonNullLazy.of(() -> new PlacedFeature(Holder.direct(configuredFeature.get()), List.of())));
        }

    }

    public static class VanillaJsonFeature extends Feature<VanillaJsonFeature.VanillaJsonFeatureConfig> {
        public VanillaJsonFeature(Codec<VanillaJsonFeatureConfig> codec) {
            super(codec);
        }

        @Override
        public boolean place(FeaturePlaceContext<VanillaJsonFeatureConfig> context) {
            WorldGenLevel level = context.level();

            return level.registryAccess().registryOrThrow(Registry.PLACED_FEATURE_REGISTRY).get(context.config().feature()).place(level, context.chunkGenerator(), context.random(), context.origin());
        }

        public record VanillaJsonFeatureConfig(ResourceKey<PlacedFeature> feature) implements FeatureConfiguration {
            public static final Codec<VanillaJsonFeatureConfig> CODEC = ResourceKey.codec(Registry.PLACED_FEATURE_REGISTRY)
                    .xmap(VanillaJsonFeatureConfig::new, VanillaJsonFeatureConfig::feature)
                    .fieldOf("feature")
                    .codec();
        }
    }


    private static final Map<TagKey<Biome>, List<String>> BUILTIN_TAGS = ImmutableMap.of(
            ModTags.HAS_FLUVISOL, ImmutableList.of("minecraft:swamp", "minecraft:river"),
            ModTags.HAS_HUMUS, ImmutableList.of("minecraft:dark_forest"),
            ModTags.HAS_IVY, ImmutableList.of("minecraft:forest",
                    "minecraft:windswept_forest",
                    "minecraft:flower_forest",
                    "minecraft:dark_forest",
                    "minecraft:birch_forest",
                    "minecraft:old_growth_birch_forest"),
            ModTags.HAS_LAKEBED, ImmutableList.of("minecraft:badlands",
                    "minecraft:eroded_badlands",
                    "minecraft:wooded_badlands",
                    "minecraft:desert"),
            ModTags.HAS_VERITOSOL, ImmutableList.of("minecraft:badlands",
                    "minecraft:eroded_badlands",
                    "minecraft:wooded_badlands"),
            ModTags.HOT, ImmutableList.of("minecraft:badlands",
                    "minecraft:wooded_badlands",
                    "minecraft:eroded_badlands",
                    "minecraft:savanna",
                    "minecraft:windswept_savanna",
                    "minecraft:savanna_plateau",
                    "minecraft:desert"),
            ModTags.ICY, ImmutableList.of("minecraft:ice_spikes",
                    "minecraft:frozen_peaks",
                    "minecraft:jagged_peaks",
                    "minecraft:snowy_slopes",
                    "minecraft:snowy_plains",
                    "minecraft:snowy_taiga",
                    "minecraft:frozen_ocean",
                    "minecraft:deep_frozen_ocean",
                    "minecraft:frozen_river"),
            ModTags.WET, ImmutableList.of("minecraft:swamp",
                    "minecraft:jungle",
                    "minecraft:sparse_jungle"),
            ModTags.UNDERGROUND_DESERT, ImmutableList.of("minecraft:desert")
    );

    public static boolean isTagged(TagKey<Biome> tag, ResourceLocation biome) {
        var l = BUILTIN_TAGS.get(tag);
        if (l == null) {
            int a = 1;
            return false;
        }
        return l.contains(biome.toString());
    }


    public static Supplier<PlacementModifierType<HardcodedBiomeFilter>> TYPE = RegHelper.register(ImmersiveWeathering.res("biome_tag"),
            () -> () -> HardcodedBiomeFilter.CODEC
            , Registry.PLACEMENT_MODIFIERS);

    public static class HardcodedBiomeFilter extends PlacementFilter {

        private final TagKey<Biome> tag;

        public static final Codec<HardcodedBiomeFilter> CODEC = RecordCodecBuilder.create((i) -> i.group(
                TagKey.codec(Registry.BIOME_REGISTRY).fieldOf("tag").forGetter(e -> e.tag)
        ).apply(i, HardcodedBiomeFilter::new));

        private HardcodedBiomeFilter(TagKey<Biome> tag) {
            this.tag = tag;
            if (BuiltinRegistries.BIOME.getTag(tag).isEmpty()) {
                //  ImmersiveWeathering.LOGGER.error("Biome tag for structure placement {} was empty! check for spelling errors", tag);
            }
        }

        @Override
        protected boolean shouldPlace(PlacementContext context, Random random, BlockPos pos) {
            Holder<Biome> holder = context.getLevel().getBiome(pos);
            return holder.is(tag);
        }

        public PlacementModifierType<?> type() {
            return TYPE.get();
        }
    }

}
