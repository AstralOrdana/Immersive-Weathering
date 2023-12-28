package com.ordana.immersive_weathering.forge;

import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
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

import java.util.IdentityHashMap;

public class IWPlatformStuffImpl {

    public static final IdentityHashMap<Block, Block> COPY_BLOCK_COLORS = new IdentityHashMap<>();
    public static final IdentityHashMap<Item, Item> COPY_ITEM_COLORS = new IdentityHashMap<>();

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        if (IntegrationHandler.quark) {
            QuarkPlugin.addAllVerticalSlabs(builder);
        }
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        if (IntegrationHandler.quark) {
            QuarkPlugin.addAllVerticalSlabs(builder);
        }
    }

    public static FlowingFluid getFlowingFluid(LiquidBlock block) {
        return block.getFluid();
    }


    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
    }

    public static void copyColorFrom(ClientHelper.BlockColorEvent event, Block block, Block colorFrom, BlockColor fallbackColor) {
        COPY_BLOCK_COLORS.put(block, colorFrom);
    }

    public static void copyColorFrom(ClientHelper.ItemColorEvent event, ItemLike block, ItemLike colorFrom, ItemColor fallbackColor) {
        COPY_ITEM_COLORS.put(block.asItem(), colorFrom.asItem());
    }

}
