package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModFoods;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //new BlockItem\((.*?),(.*?);
    //regBlockItem($1.get(), $2);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeathering.MOD_ID);

    public static RegistryObject<BlockItem> regBlockItem(RegistryObject<Block> blockSup, Item.Properties properties) {
       return ITEMS.register(blockSup.getId().getPath(), () -> new BlockItem(blockSup.get(),  properties));
    }


    public static final RegistryObject<BlockItem> ICICLE = regBlockItem(ModBlocks.ICICLE.get(),  new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> OAK_LEAF_PILE = regBlockItem(ModBlocks.OAK_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> SPRUCE_LEAF_PILE = regBlockItem(ModBlocks.SPRUCE_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> BIRCH_LEAF_PILE = regBlockItem(ModBlocks.BIRCH_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> JUNGLE_LEAF_PILE = regBlockItem(ModBlocks.JUNGLE_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> ACACIA_LEAF_PILE = regBlockItem(ModBlocks.ACACIA_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> DARK_OAK_LEAF_PILE = regBlockItem(ModBlocks.DARK_OAK_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> AZALEA_LEAF_PILE = regBlockItem(ModBlocks.AZALEA_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> FLOWERING_AZALEA_LEAF_PILE = regBlockItem(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> AZALEA_FLOWER_PILE = regBlockItem(ModBlocks.AZALEA_FLOWER_PILE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final Item STEEL_WOOL = new Item(new Item.Properties().durability(128).tab(CreativeModeTab.TAB_TOOLS));

    public static final RegistryObject<BlockItem> WEEDS = regBlockItem(ModBlocks.WEEDS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> SOOT = regBlockItem(ModBlocks.SOOT.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> ASH_BLOCK = regBlockItem(ModBlocks.ASH_BLOCK.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> MOSSY_BRICKS = regBlockItem(ModBlocks.MOSSY_BRICKS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_STAIRS = regBlockItem(ModBlocks.MOSSY_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_SLAB = regBlockItem(ModBlocks.MOSSY_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_WALL = regBlockItem(ModBlocks.MOSSY_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> MOSSY_STONE = regBlockItem(ModBlocks.MOSSY_STONE.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSSY_STONE_STAIRS = regBlockItem(ModBlocks.MOSSY_STONE_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MOSSY_STONE_SLAB = regBlockItem(ModBlocks.MOSSY_STONE_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> CRACKED_BRICKS = regBlockItem(ModBlocks.CRACKED_BRICKS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_STAIRS = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_SLAB = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_WALL = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_WALL.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> MULCH_BLOCK = regBlockItem(ModBlocks.MULCH_BLOCK.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> NULCH_BLOCK = regBlockItem(ModBlocks.NULCH_BLOCK.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> MULCH = regBlockItem(ModBlocks.MULCH.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> NULCH = regBlockItem(ModBlocks.NULCH.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final Item STONE_BRICK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item BLACKSTONE_BRICK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item DEEPSLATE_BRICK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item DEEPSLATE_TILE = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));

    public static final Item AZALEA_FLOWERS = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item FLOWER_CROWN = new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));
    public static final Item MOSS_CLUMP = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.MOSS_CLUMP));
    public static final Item GOLDEN_MOSS_CLUMP = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.GOLDEN_MOSS_CLUMP));

    public static final Item OAK_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item BIRCH_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item SPRUCE_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item JUNGLE_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item DARK_OAK_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item ACACIA_BARK = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item CRIMSON_SCALES = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));
    public static final Item WARPED_SCALES = new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS));


    //cut iron
    public static final RegistryObject<BlockItem> CUT_IRON = regBlockItem(ModBlocks.CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON = regBlockItem(ModBlocks.EXPOSED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON = regBlockItem(ModBlocks.WEATHERED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON = regBlockItem(ModBlocks.RUSTED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CUT_IRON_STAIRS = regBlockItem(ModBlocks.CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.EXPOSED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WEATHERED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.RUSTED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> CUT_IRON_SLAB = regBlockItem(ModBlocks.CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON_SLAB = regBlockItem(ModBlocks.EXPOSED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WEATHERED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON_SLAB = regBlockItem(ModBlocks.RUSTED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON = regBlockItem(ModBlocks.WAXED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    //plate iron
    public static final RegistryObject<BlockItem> PLATE_IRON = regBlockItem(ModBlocks.PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON = regBlockItem(ModBlocks.RUSTED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> PLATE_IRON_STAIRS = regBlockItem(ModBlocks.PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.RUSTED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> PLATE_IRON_SLAB = regBlockItem(ModBlocks.PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.RUSTED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB.get(),  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_DOOR = regBlockItem(ModBlocks.EXPOSED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_DOOR = regBlockItem(ModBlocks.WEATHERED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_DOOR = regBlockItem(ModBlocks.RUSTED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_TRAPDOOR = regBlockItem(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_TRAPDOOR = regBlockItem(ModBlocks.RUSTED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_BARS = regBlockItem(ModBlocks.EXPOSED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_BARS = regBlockItem(ModBlocks.WEATHERED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> RUSTED_IRON_BARS = regBlockItem(ModBlocks.RUSTED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> WAXED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<BlockItem> WAXED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(),  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<BlockItem> WAXED_IRON_BARS = regBlockItem(ModBlocks.WAXED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_BARS = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_BARS = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_BARS = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_BARS.get(),  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static void registerItems() {

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "icicle"), ICICLE);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "oak_leaf_pile"), OAK_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "spruce_leaf_pile"), SPRUCE_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "birch_leaf_pile"), BIRCH_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "jungle_leaf_pile"), JUNGLE_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "acacia_leaf_pile"), ACACIA_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "dark_oak_leaf_pile"), DARK_OAK_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "azalea_leaf_pile"), AZALEA_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "flowering_azalea_leaf_pile"), FLOWERING_AZALEA_LEAF_PILE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "azalea_flower_pile"), AZALEA_FLOWER_PILE);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "steel_wool"), STEEL_WOOL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weeds"), WEEDS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "soot"), SOOT);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "ash_block"), ASH_BLOCK);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_bricks"), CRACKED_BRICKS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_brick_stairs"), CRACKED_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_brick_slab"), CRACKED_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_brick_wall"), CRACKED_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_stairs"), CRACKED_STONE_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_slab"), CRACKED_STONE_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_wall"), CRACKED_STONE_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_stairs"), CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_slab"), CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_wall"), CRACKED_POLISHED_BLACKSTONE_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_stairs"), CRACKED_NETHER_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_slab"), CRACKED_NETHER_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_wall"), CRACKED_NETHER_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_stairs"), CRACKED_DEEPSLATE_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_slab"), CRACKED_DEEPSLATE_BRICK_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_wall"), CRACKED_DEEPSLATE_BRICK_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_stairs"), CRACKED_DEEPSLATE_TILE_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_slab"), CRACKED_DEEPSLATE_TILE_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_wall"), CRACKED_DEEPSLATE_TILE_WALL);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mulch_block"), MULCH_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "nulch_block"), NULCH_BLOCK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "mulch"), MULCH);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "nulch"), NULCH);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "stone_brick"), STONE_BRICK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "blackstone_brick"), BLACKSTONE_BRICK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "deepslate_brick"), DEEPSLATE_BRICK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "deepslate_tile"), DEEPSLATE_TILE);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "azalea_flowers"), AZALEA_FLOWERS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "flower_crown"), FLOWER_CROWN);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "moss_clump"), MOSS_CLUMP);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "golden_moss_clump"), GOLDEN_MOSS_CLUMP);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "oak_bark"), OAK_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "spruce_bark"), SPRUCE_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "birch_bark"), BIRCH_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "jungle_bark"), JUNGLE_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "acacia_bark"), ACACIA_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "dark_oak_bark"), DARK_OAK_BARK);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "crimson_scales"), CRIMSON_SCALES);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "warped_scales"), WARPED_SCALES);

        //plate iron
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "plate_iron"), PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_plate_iron"), EXPOSED_PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_plate_iron"), WEATHERED_PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_plate_iron"), RUSTED_PLATE_IRON);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "plate_iron_stairs"), PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_stairs"), EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_stairs"), WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_stairs"), RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "plate_iron_slab"), PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_slab"), EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_slab"), WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_slab"), RUSTED_PLATE_IRON_SLAB);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_plate_iron"), WAXED_PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron"), WAXED_EXPOSED_PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron"), WAXED_WEATHERED_PLATE_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron"), WAXED_RUSTED_PLATE_IRON);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_stairs"), WAXED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_stairs"), WAXED_EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_stairs"), WAXED_WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_stairs"), WAXED_RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_slab"), WAXED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_slab"), WAXED_EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_slab"), WAXED_WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_slab"), WAXED_RUSTED_PLATE_IRON_SLAB);

        //cut iron
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cut_iron"), CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_cut_iron"), EXPOSED_CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_cut_iron"), WEATHERED_CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_cut_iron"), RUSTED_CUT_IRON);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cut_iron_stairs"), CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_stairs"), EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_stairs"), WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_stairs"), RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "cut_iron_slab"), CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_slab"), EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_slab"), WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_slab"), RUSTED_CUT_IRON_SLAB);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_cut_iron"), WAXED_CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron"), WAXED_EXPOSED_CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron"), WAXED_WEATHERED_CUT_IRON);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron"), WAXED_RUSTED_CUT_IRON);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_stairs"), WAXED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_stairs"), WAXED_EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_stairs"), WAXED_WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_stairs"), WAXED_RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_slab"), WAXED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_slab"), WAXED_EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_slab"), WAXED_WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_slab"), WAXED_RUSTED_CUT_IRON_SLAB);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_iron_door"), EXPOSED_IRON_DOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_iron_door"), WEATHERED_IRON_DOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_iron_door"), RUSTED_IRON_DOOR);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_iron_trapdoor"), EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_iron_trapdoor"), WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_iron_trapdoor"), RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "exposed_iron_bars"), EXPOSED_IRON_BARS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "weathered_iron_bars"), WEATHERED_IRON_BARS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "rusted_iron_bars"), RUSTED_IRON_BARS);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_iron_door"), WAXED_IRON_DOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_door"), WAXED_EXPOSED_IRON_DOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_door"), WAXED_WEATHERED_IRON_DOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_door"), WAXED_RUSTED_IRON_DOOR);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_iron_trapdoor"), WAXED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_trapdoor"), WAXED_EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_trapdoor"), WAXED_WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_trapdoor"), WAXED_RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_iron_bars"), WAXED_IRON_BARS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_bars"), WAXED_EXPOSED_IRON_BARS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_bars"), WAXED_WEATHERED_IRON_BARS);
        Registry.register(Registry.ITEM, new ResourceLocation(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_bars"), WAXED_RUSTED_IRON_BARS);
    }
}
