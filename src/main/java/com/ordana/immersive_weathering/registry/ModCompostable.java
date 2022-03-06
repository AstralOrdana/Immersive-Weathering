package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.items.ModItems;
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
        ComposterBlock.COMPOSTABLES.put(ModItems.MULCH_BLOCK.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModItems.NULCH_BLOCK.get(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModItems.MULCH.get(), 0.4f);
        ComposterBlock.COMPOSTABLES.put(ModItems.NULCH.get(), 0.4f);
        ComposterBlock.COMPOSTABLES.put(ModItems.MOSS_CLUMP.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModItems.OAK_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SPRUCE_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.BIRCH_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.JUNGLE_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.ACACIA_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.DARK_OAK_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.AZALEA_LEAF_PILE.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FLOWERING_AZALEA_LEAF_PILE.get(), 0.3f);
    }
}
