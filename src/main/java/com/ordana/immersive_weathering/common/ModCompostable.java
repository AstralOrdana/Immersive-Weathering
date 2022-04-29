package com.ordana.immersive_weathering.common;

import com.ordana.immersive_weathering.common.blocks.LeafPilesRegistry;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.world.level.block.ComposterBlock;

public class ModCompostable {
    public static void registerCompostable() {
        ComposterBlock.COMPOSTABLES.put(ModItems.MOSS_CLUMP.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModItems.OAK_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SPRUCE_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.BIRCH_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.JUNGLE_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.ACACIA_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.DARK_OAK_BARK.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.MULCH_BLOCK.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.NULCH_BLOCK.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModItems.MOSS_CLUMP.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FLOWER_CROWN.get(), 0.3f);

        LeafPilesRegistry.LEAF_PILES.get().values().forEach(l -> ComposterBlock.COMPOSTABLES.put(l, 0.3f));
    }
}
