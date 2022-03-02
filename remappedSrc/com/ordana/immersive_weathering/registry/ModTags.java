package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public final class ModTags {
    public static final TagKey<Block> MOSSY = register("mossy");

    public static final Tag<Block> MOSSY = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "mossy"));
    public static final Tag<Block> MOSSABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "mossable"));
    public static final Tag<Block> CRACKED = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "cracked"));
    public static final Tag<Block> CRACKABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "crackable"));
    public static final Tag<Block> MOSS_SOURCE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "moss_source"));
    public static final Tag<Block> RAW_LOGS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "raw_logs"));
    public static final Tag<Block> LEAF_PILES = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "leaf_piles"));
    public static final Tag<Block> SMOKEY_BLOCKS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "smokey_blocks"));
    public static final Tag<Block> SMALL_MUSHROOMS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "small_mushrooms"));
    public static final Tag<Block> SMALL_PLANTS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "small_plants"));
    public static final Tag<Block> FERTILE_BLOCKS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "fertile_blocks"));
    public static final Tag<Block> CORALS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "corals"));
    public static final Tag<Block> ICE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "ice"));
    public static final Tag<Block> MAGMA_SOURCE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "magma_source"));
    public static final Tag<Block> ICICLE_REPLACEABLE_BLOCKS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "icicle_replaceable_blocks"));

    public static final Tag<Block> CLEAN_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "clean_iron"));
    public static final Tag<Block> EXPOSED_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron"));
    public static final Tag<Block> WEATHERED_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron"));
    public static final Tag<Block> RUSTED_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron"));
    public static final Tag<Block> RUSTABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "rustable"));

    public static final Tag<Block> FLOWERY = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "flowery"));
    public static final Tag<Block> FLOWERABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "flowerable"));

    public static final Tag<Item> BARK = TagFactory.ITEM.create(new Identifier(ImmersiveWeathering.MOD_ID, "bark"));
    public static final Tag<Item> SCALES = TagFactory.ITEM.create(new Identifier(ImmersiveWeathering.MOD_ID, "scales"));

    public static final Tag<Biome> ICY = TagFactory.BIOME.create(new Identifier(ImmersiveWeathering.MOD_ID, "icy"));

    private ModTags() {
    }

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier("fabric", id));
    }
}
