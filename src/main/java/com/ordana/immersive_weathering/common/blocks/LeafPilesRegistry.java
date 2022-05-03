package com.ordana.immersive_weathering.common.blocks;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.items.ModItems;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import com.ordana.immersive_weathering.integration.dynamic_stuff.ModDynamicRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class LeafPilesRegistry {

    public static boolean isDynamic = ModList.get().isLoaded("selene");

    public static final Supplier<Map<Block, LeafPileBlock>> LEAF_PILES = Suppliers.memoize(() -> {

                if (isDynamic) {
                    return ModDynamicRegistry.getLeafToLeafPileMap();
                } else return ImmutableMap.<Block, LeafPileBlock>builder()
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

    public static final Supplier<Map<Block, Pair<Item, Block>>> STRIPPED_TO_BARK = Suppliers.memoize(() -> {

        var builder = ImmutableMap.<Block, Pair<Item, Block>>builder()
                .put(Blocks.STRIPPED_OAK_LOG, Pair.of(ModItems.OAK_BARK.get(), Blocks.OAK_LOG))
                .put(Blocks.STRIPPED_OAK_WOOD, Pair.of(ModItems.OAK_BARK.get(), Blocks.OAK_WOOD))
                .put(Blocks.STRIPPED_BIRCH_LOG, Pair.of(ModItems.BIRCH_BARK.get(), Blocks.BIRCH_LOG))
                .put(Blocks.STRIPPED_BIRCH_WOOD, Pair.of(ModItems.BIRCH_BARK.get(), Blocks.BIRCH_WOOD))
                .put(Blocks.STRIPPED_SPRUCE_LOG, Pair.of(ModItems.SPRUCE_BARK.get(), Blocks.SPRUCE_LOG))
                .put(Blocks.STRIPPED_SPRUCE_WOOD, Pair.of(ModItems.SPRUCE_BARK.get(), Blocks.SPRUCE_WOOD))
                .put(Blocks.STRIPPED_JUNGLE_LOG, Pair.of(ModItems.JUNGLE_BARK.get(), Blocks.JUNGLE_LOG))
                .put(Blocks.STRIPPED_JUNGLE_WOOD, Pair.of(ModItems.JUNGLE_BARK.get(), Blocks.JUNGLE_WOOD))
                .put(Blocks.STRIPPED_DARK_OAK_LOG, Pair.of(ModItems.DARK_OAK_BARK.get(), Blocks.DARK_OAK_LOG))
                .put(Blocks.STRIPPED_DARK_OAK_WOOD, Pair.of(ModItems.DARK_OAK_BARK.get(), Blocks.DARK_OAK_WALL_SIGN))
                .put(Blocks.STRIPPED_ACACIA_LOG, Pair.of(ModItems.ACACIA_BARK.get(), Blocks.ACACIA_LOG))
                .put(Blocks.STRIPPED_ACACIA_WOOD, Pair.of(ModItems.ACACIA_BARK.get(), Blocks.ACACIA_WOOD))
                .put(Blocks.STRIPPED_CRIMSON_STEM, Pair.of(ModItems.CRIMSON_SCALES.get(), Blocks.CRIMSON_STEM))
                .put(Blocks.STRIPPED_CRIMSON_HYPHAE, Pair.of(ModItems.CRIMSON_SCALES.get(), Blocks.CRIMSON_HYPHAE))
                .put(Blocks.STRIPPED_WARPED_STEM, Pair.of(ModItems.WARPED_SCALES.get(), Blocks.WARPED_STEM))
                .put(Blocks.STRIPPED_WARPED_HYPHAE, Pair.of(ModItems.WARPED_SCALES.get(), Blocks.WARPED_HYPHAE));
        if (isDynamic) {
            builder.putAll(ModDynamicRegistry.getBarkMap());
        }
        return builder.build();
    });

    public static void registerBus(IEventBus bus) {
        if (isDynamic) {
            ModDynamicRegistry.init(bus);
        }
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        Block b = state.getBlock();
        if (ServerConfigs.LEAF_PILES_BLACKLIST.get().contains(b.getRegistryName().toString()))
            return Optional.empty();
        return Optional.ofNullable(LeafPilesRegistry.LEAF_PILES.get().get(b));
    }

}
