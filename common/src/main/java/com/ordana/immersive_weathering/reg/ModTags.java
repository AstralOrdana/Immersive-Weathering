package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class ModTags {

    //blocks
    public static final TagKey<Block> MOSSY = registerBlockTag("mossy");
    public static final TagKey<Block> MOSSABLE = registerBlockTag("mossable");
    public static final TagKey<Block> CRACKED = registerBlockTag("cracked");
    public static final TagKey<Block> CRACKABLE = registerBlockTag("crackable");
    public static final TagKey<Block> MOSS_SOURCE = registerBlockTag("moss_source");
    public static final TagKey<Block> LEAF_PILES = registerBlockTag("leaf_piles");
    public static final TagKey<Block> SMOKEY_BLOCKS = registerBlockTag("smokey_blocks");
    public static final TagKey<Block> CHARRED_BLOCKS = registerBlockTag("charred_blocks");
    public static final TagKey<Block> SMALL_MUSHROOMS = registerBlockTag("small_mushrooms");
    public static final TagKey<Block> SMALL_PLANTS = registerBlockTag("small_plants");
    public static final TagKey<Block> ICE = registerBlockTag("ice");
    public static final TagKey<Block> ASH = registerBlockTag("ash");
    public static final TagKey<Block> MAGMA_SOURCE = registerBlockTag("magma_source");
    public static final TagKey<Block> CRACK_SOURCE = registerBlockTag("crack_source");
    public static final TagKey<Block> ICICLE_REPLACEABLE_BLOCKS = registerBlockTag("icicle_replaceable_blocks");
    public static final TagKey<Block> GRASS_GROWTH_REPLACEABLE = registerBlockTag("grass_growth_replaceable");
    public static final TagKey<Block> WEED_REPLACEABLE = registerBlockTag("weeds_replaceable");
    public static final TagKey<Block> LEAFY_LEAVES = registerBlockTag("deciduous_leaf_piles");
    public static final TagKey<Block> FIRE_REPLACEABLE = registerBlockTag("fire_replaceable");

    public static final TagKey<Block> CLEAN_IRON = registerBlockTag("clean_iron");
    public static final TagKey<Block> EXPOSED_IRON = registerBlockTag("exposed_iron");
    public static final TagKey<Block> WEATHERED_IRON = registerBlockTag("weathered_iron");
    public static final TagKey<Block> RUSTED_IRON = registerBlockTag("rusted_iron");
    public static final TagKey<Block> RUSTABLE = registerBlockTag("rustable");
    public static final TagKey<Block> WAXED_BLOCKS = registerBlockTag("waxed_blocks");

    public static final TagKey<Block> FLOWERY = registerBlockTag("flowery");
    public static final TagKey<Block> FLOWERABLE = registerBlockTag("flowerable");

    public static final TagKey<Block> SAND_CAVE_FLOOR = registerBlockTag("sand_cave_floor");

    //items
    public static final TagKey<Item> BARK = registerItemTag("bark");
    public static final TagKey<Item> SCALES = registerItemTag("scales");


    //biomes
    public static final TagKey<Biome> ICY = registerBiomeTag("icy");
    public static final TagKey<Biome> HOT = registerBiomeTag("hot");
    public static final TagKey<Biome> WET = registerBiomeTag("wet");
    public static final TagKey<Biome> HAS_HUMUS = registerBiomeTag("has_humus");
    public static final TagKey<Biome> HAS_FLUVISOL = registerBiomeTag("has_fluvisol");
    public static final TagKey<Biome> HAS_LAKEBED = registerBiomeTag("has_lakebed");
    public static final TagKey<Biome> HAS_VERITOSOL = registerBiomeTag("has_veritosol");
    public static final TagKey<Biome> HAS_IVY = registerBiomeTag("has_ivy");
    public static final TagKey<Biome> UNDERGROUND_DESERT = registerBiomeTag("underground_desert");


    //entity type
    /**
     * For water permafrost and icicles. All animals with fur or that live near cold biomes
     */
    public static final TagKey<EntityType<?>> LIGHT_FREEZE_IMMUNE = registerEntityTag("light_freeze_immune");

    //fabric only.
    //TODO: remove unneded from forge
    public static final TagKey<Block> RAW_LOGS = registerBlockTag("raw_logs");
    public static final TagKey<Block> STRIPPED_LOGS = registerBlockTag("stripped_logs");

    public static final TagKey<Block> FERTILE_BLOCKS = registerBlockTag("fertile_blocks");
    public static final TagKey<Block> VANILLA_LEAVES = registerBlockTag("vanilla_leaves");
    public static final TagKey<Block> LEAF_PILE_REPLACEABLE = registerBlockTag("leaf_pile_replaceable");
    public static final TagKey<Block> WEEDS_REPLACEABLE = registerBlockTag("weeds_replaceable");
    public static final TagKey<Block> WART_GROW_BLOCKS = registerBlockTag("wart_grow_blocks");

    public static final TagKey<Block> COPPER = registerBlockTag("copper");
    public static final TagKey<Block> BARS = registerBlockTag("bars");


    private ModTags() {
    }

    private static TagKey<Block> registerBlockTag(String id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, ImmersiveWeathering.res(id));
    }

    private static TagKey<Item> registerItemTag(String id) {
        return TagKey.create(Registry.ITEM_REGISTRY, ImmersiveWeathering.res(id));
    }

    private static TagKey<Biome> registerBiomeTag(String id) {
        return TagKey.create(Registry.BIOME_REGISTRY, ImmersiveWeathering.res(id));
    }

    private static TagKey<EntityType<?>> registerEntityTag(String id) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, ImmersiveWeathering.res(id));
    }
}
