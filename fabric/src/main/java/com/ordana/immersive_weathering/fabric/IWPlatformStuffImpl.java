package com.ordana.immersive_weathering.fabric;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.fabric.rustable.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.storage.loot.LootPool;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class IWPlatformStuffImpl {

    @SuppressWarnings("unchecked")
    public static Block createSpecialBlock(IWPlatformStuff.BlockType type, BlockBehaviour.Properties properties, Object... extraParams) {
        return switch (type) {
            case RUSTABLE_BLOCK -> new RustableBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_STAIRS ->
                    new RustableStairsBlock((Rustable.RustLevel) extraParams[0], (Supplier<Block>) extraParams[1], properties);
            case RUSTABLE_BARS -> new RustableBarsBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_DOOR -> new RustableDoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_SLAB -> new RustableSlabBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_TRAPDOOR -> new RustableTrapdoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_VERTICAL_SLAB ->
                    new RustableVerticalSlabBlock((Rustable.RustLevel) extraParams[0], properties);
        };
    }


    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
    }


    public static Collection<LeafPileBlock> getLeafPiles() {
        return List.of();
    }

    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
        BiomeModifications.addFeature(BiomeSelectors.tag(tagKey), step, feature);

    }

    @Nullable
    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        return null;
    }

    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
    }

    @Nullable
    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        return null;
    }

    public static FlowingFluid getFlowingFluid(LiquidBlock liquidBlock) {
        return null;
    }



}
