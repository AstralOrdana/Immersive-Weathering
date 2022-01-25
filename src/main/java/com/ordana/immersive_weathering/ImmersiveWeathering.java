package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ImmersiveWeathering implements ModInitializer {

    public static final String MOD_ID = "immersive_weathering";
    public static final Tag<Block> UNMOSSABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "unmossable"));
    public static final Tag<Block> CRACKED = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "cracked"));
    public static final Tag<Block> CRACKABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "crackable"));
    public static final Tag<Block> MOSS_SOURCE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "moss_source"));
    public static final Tag<Block> MOSS_SOURCE_STONE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "moss_source_stone"));

    @Override
    public void onInitialize() {
        ModEvents.registerEvents();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }
}
