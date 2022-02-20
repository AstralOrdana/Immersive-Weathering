package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;

public class ImmersiveWeatheringClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPRUCE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BIRCH_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUNGLE_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ACACIA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_OAK_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AZALEA_LEAF_PILE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.OAK_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.BIRCH_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.ACACIA_LEAF_PILE);
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();
            }, ModBlocks.DARK_OAK_LEAF_PILE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.OAK_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getSpruceColor();
        }, ModItems.SPRUCE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getBirchColor();
        }, ModItems.BIRCH_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.JUNGLE_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.ACACIA_LEAF_PILE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return FoliageColors.getDefaultColor();
        }, ModItems.DARK_OAK_LEAF_PILE);


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPOSED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WEATHERED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUSTED_IRON_BARS, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_EXPOSED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_WEATHERED_IRON_BARS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WAXED_RUSTED_IRON_BARS, RenderLayer.getCutout());
    }
}
