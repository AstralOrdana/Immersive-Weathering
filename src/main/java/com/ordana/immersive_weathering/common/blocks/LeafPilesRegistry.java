package com.ordana.immersive_weathering.common.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.integration.dynamic_stuff.ModDynamicRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

import java.util.Map;
import java.util.function.Supplier;

public class LeafPilesRegistry {

    public static boolean isDynamic = ModList.get().isLoaded("selene");

    public static final Supplier<Map<Block, LeafPileBlock>> LEAF_PILES = Suppliers.memoize(() -> {

                if (isDynamic) {
                    return ModDynamicRegistry.getLeafToLeafPileMap();
                }
                else return ImmutableMap.<Block, LeafPileBlock>builder()
                        .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE.get())
                        .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE.get())
                        .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE.get())
                        .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE.get())
                        .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE.get())
                        .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE.get())
                        .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE.get())
                        .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get())
                        .build();
            }
    );

    public static void registerBus(IEventBus bus){
        if(isDynamic){
            ModDynamicRegistry.init(bus);
        }
    }

}
