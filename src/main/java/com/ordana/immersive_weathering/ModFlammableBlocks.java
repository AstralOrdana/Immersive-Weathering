package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Blocks;

public class ModFlammableBlocks {
    public static void registerFlammable() {
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.OAK_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SPRUCE_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BIRCH_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.JUNGLE_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ACACIA_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.DARK_OAK_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.AZALEA_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WEEDS, 60, 100);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MULCH_BLOCK, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MULCH, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(Blocks.COBWEB, 60, 100);
    }
}
