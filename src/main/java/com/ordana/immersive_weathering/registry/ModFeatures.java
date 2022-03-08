package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.features.IcicleClusterFeature;
import com.ordana.immersive_weathering.registry.features.IcicleClusterFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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

    public static final RegistryObject<Feature<BlockPileConfiguration>> OAK = FEATURES.register(
            "oak_leaf_pile", ()-> new BlockPileFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> DARK_OAK = FEATURES.register(
            "dark_oak_leaf_pile", ()-> new BlockPileFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> SPRUCE = FEATURES.register(
            "spruce_leaf_pile", ()-> new BlockPileFeature(BlockPileConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockPileConfiguration>> BIRCH = FEATURES.register(
            "birch_leaf_pile", ()-> new BlockPileFeature(BlockPileConfiguration.CODEC));

    private static void addFeature(BiomeLoadingEvent event, String name, GenerationStep.Decoration step) {
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                ImmersiveWeathering.res(name));
        var feature = BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(key);
        event.getGeneration().addFeature(step, feature);
    }

    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        ResourceKey<Biome> key = ResourceKey.create(ForgeRegistries.Keys.BIOMES, event.getName());

        Holder<Biome> holder = BuiltinRegistries.BIOME.getHolderOrThrow(key);

        if (holder.is(ModTags.ICY) || holder.is(ModTags.ICE_CAVES)) {
            addFeature(event, "icicles", GenerationStep.Decoration.TOP_LAYER_MODIFICATION);
        } else if (key == Biomes.FOREST || key == Biomes.WINDSWEPT_FOREST || key == Biomes.FLOWER_FOREST) {
            addFeature(event, "oak_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
        } else if (key == Biomes.DARK_FOREST) {
            addFeature(event, "dark_oak_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
        } else if (key == Biomes.BIRCH_FOREST || key == Biomes.OLD_GROWTH_BIRCH_FOREST) {
            addFeature(event, "birch_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
        } else if (key == Biomes.OLD_GROWTH_SPRUCE_TAIGA || key == Biomes.OLD_GROWTH_PINE_TAIGA || key == Biomes.TAIGA) {
            addFeature(event, "spruce_leaf_pile", GenerationStep.Decoration.VEGETAL_DECORATION);
        }
    }
}
