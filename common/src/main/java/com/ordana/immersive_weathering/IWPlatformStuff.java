package com.ordana.immersive_weathering;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class IWPlatformStuff {

    @Contract
    @ExpectPlatform
    public static FlowingFluid getFlowingFluid(LiquidBlock liquidBlock) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void copyColorFrom(ClientPlatformHelper.BlockColorEvent event, Block block, Block colorFrom, BlockColor fallbackColor) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void copyColorFrom(ClientPlatformHelper.ItemColorEvent event, ItemLike item, ItemLike colorFrom, ItemColor fallbackColor) {
        throw new AssertionError();
    }
}