package com.ordana.immersive_weathering.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public final class ModTags {
    //blocks
    public static final TagKey<Block> MOSSY = registerBlockTag("mossy");
    public static final TagKey<Block> MOSSABLE = registerBlockTag("mossable");
    public static final TagKey<Block> CRACKED = registerBlockTag("cracked");
    public static final TagKey<Block> CRACKABLE = registerBlockTag("crackable");
    public static final TagKey<Block> MOSS_SOURCE = registerBlockTag("moss_source");
    public static final TagKey<Block> RAW_LOGS = registerBlockTag("raw_logs");
    public static final TagKey<Block> STRIPPED_LOGS = registerBlockTag("stripped_logs");
    public static final TagKey<Block> SMOKEY_BLOCKS = registerBlockTag("smokey_blocks");
    public static final TagKey<Block> SMALL_MUSHROOMS = registerBlockTag("small_mushrooms");
    public static final TagKey<Block> SMALL_PLANTS = registerBlockTag("small_plants");
    public static final TagKey<Block> FERTILE_BLOCKS = registerBlockTag("fertile_blocks");
    public static final TagKey<Block> ICE = registerBlockTag("ice");
    public static final TagKey<Block> MAGMA_SOURCE = registerBlockTag("magma_source");
    public static final TagKey<Block> CRACK_SOURCE = registerBlockTag("crack_source");
    public static final TagKey<Block> ICICLE_REPLACEABLE_BLOCKS = registerBlockTag("icicle_replaceable_blocks");
    public static final TagKey<Block> GRASS_GROWTH_REPLACEABLE = registerBlockTag("grass_growth_replaceable");
    public static final TagKey<Block> LEAF_PILES = registerBlockTag("leaf_piles");
    public static final TagKey<Block> LEAF_PILE_REPLACEABLE = registerBlockTag("leaf_pile_replaceable");
    public static final TagKey<Block> WEEDS_REPLACEABLE = registerBlockTag("weeds_replaceable");
    public static final TagKey<Block> WART_GROW_BLOCKS = registerBlockTag("wart_grow_blocks");

    public static final TagKey<Block> CLEAN_IRON = registerBlockTag("clean_iron");
    public static final TagKey<Block> EXPOSED_IRON = registerBlockTag("exposed_iron");
    public static final TagKey<Block> WEATHERED_IRON = registerBlockTag("weathered_iron");
    public static final TagKey<Block> RUSTED_IRON = registerBlockTag("rusted_iron");
    public static final TagKey<Block> RUSTABLE = registerBlockTag("rustable");
    public static final TagKey<Block> UNSCRAPEABLE = registerBlockTag("unscrapeable");
    public static final TagKey<Block> WAXED_BLOCKS = registerBlockTag("waxed_blocks");
    public static final TagKey<Block> COPPER = registerBlockTag("copper");
    public static final TagKey<Block> BARS = registerBlockTag("bars");

    public static final TagKey<Block> FLOWERY = registerBlockTag("flowery");
    public static final TagKey<Block> FLOWERABLE = registerBlockTag("flowerable");


    //items
    public static final TagKey<Item> BARK = registerItemTag("bark");
    public static final TagKey<Item> SCALES = registerItemTag("scales");

    //biomes
    public static final TagKey<Biome> ICY = registerBiomeTag("icy");
    public static final TagKey<Biome> HOT = registerBiomeTag("hot");
    public static final TagKey<Biome> WET = registerBiomeTag("wet");

    private ModTags() {
    }

    private static TagKey<Block> registerBlockTag(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier("immersive_weathering", id));
    }

    private static TagKey<Item> registerItemTag(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier("immersive_weathering", id));
    }

    private static TagKey<Biome> registerBiomeTag(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier("immersive_weathering", id));
    }
}
