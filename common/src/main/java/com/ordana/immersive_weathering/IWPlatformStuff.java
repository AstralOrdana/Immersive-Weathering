package com.ordana.immersive_weathering;

import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.forge.IWPlatformStuffImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Contract;

public class IWPlatformStuff {

    @Contract
    public static FlowingFluid getFlowingFluid(LiquidBlock liquidBlock) {
        return IWPlatformStuffImpl.getFlowingFluid(liquidBlock);
    }

    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
        IWPlatformStuffImpl.addFeatureToBiome(step, tagKey, feature);
    }

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        IWPlatformStuffImpl.addExtraMossyBlocks(builder);
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        IWPlatformStuffImpl.addExtraCrackedBlocks(builder);
    }
}