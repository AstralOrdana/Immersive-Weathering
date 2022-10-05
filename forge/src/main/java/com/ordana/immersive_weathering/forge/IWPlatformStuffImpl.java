package com.ordana.immersive_weathering.forge;

import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.IdentityHashMap;

public class IWPlatformStuffImpl {

    public static final IdentityHashMap<Block, Block> COPY_BLOCK_COLORS = new IdentityHashMap<>();
    public static final IdentityHashMap<Item, Item> COPY_ITEM_COLORS = new IdentityHashMap<>();


    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {

        Block a = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_hedge"));
        Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_hedge"));
        if (a != null && b != null) {
            builder.put(a, b);
        }
        Block c = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_leaf_carpet"));
        Block d = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_leaf_carpet"));
        if (c != null && d != null) {
            builder.put(c, d);
        }


    }

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

    public static void copyColorFrom(ClientPlatformHelper.BlockColorEvent event, Block block, Block colorFrom, BlockColor fallbackColor) {
        COPY_BLOCK_COLORS.put(block, colorFrom);
    }

    public static void copyColorFrom(ClientPlatformHelper.ItemColorEvent event, ItemLike block, ItemLike colorFrom, ItemColor fallbackColor) {
        COPY_ITEM_COLORS.put(block.asItem(), colorFrom.asItem());
    }

}
