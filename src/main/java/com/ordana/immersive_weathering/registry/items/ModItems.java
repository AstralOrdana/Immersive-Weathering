package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModFoods;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
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
    //regBlockItem($1, $2);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeathering.MOD_ID);

    public static RegistryObject<BlockItem> regBlockItem(RegistryObject<Block> blockSup, Item.Properties properties, int burnTIme) {
        return ITEMS.register(blockSup.getId().getPath(), () -> new BurnableBlockItem(blockSup.get(), properties,burnTIme));
    }
    public static RegistryObject<BlockItem> regBlockItem(RegistryObject<Block> blockSup, Item.Properties properties) {
        return ITEMS.register(blockSup.getId().getPath(), () -> new BlockItem(blockSup.get(), properties));
    }


    public static final RegistryObject<BlockItem> ICICLE = regBlockItem(ModBlocks.ICICLE, new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<BlockItem> OAK_LEAF_PILE = regBlockItem(ModBlocks.OAK_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> SPRUCE_LEAF_PILE = regBlockItem(ModBlocks.SPRUCE_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> BIRCH_LEAF_PILE = regBlockItem(ModBlocks.BIRCH_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> JUNGLE_LEAF_PILE = regBlockItem(ModBlocks.JUNGLE_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> ACACIA_LEAF_PILE = regBlockItem(ModBlocks.ACACIA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> DARK_OAK_LEAF_PILE = regBlockItem(ModBlocks.DARK_OAK_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> AZALEA_LEAF_PILE = regBlockItem(ModBlocks.AZALEA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> FLOWERING_AZALEA_LEAF_PILE = regBlockItem(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> AZALEA_FLOWER_PILE = regBlockItem(ModBlocks.AZALEA_FLOWER_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<BlockItem> WEEDS = regBlockItem(ModBlocks.WEEDS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> SOOT = regBlockItem(ModBlocks.SOOT, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> ASH_BLOCK = regBlockItem(ModBlocks.ASH_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<BlockItem> MOSSY_BRICKS = regBlockItem(ModBlocks.MOSSY_BRICKS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_STAIRS = regBlockItem(ModBlocks.MOSSY_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_SLAB = regBlockItem(ModBlocks.MOSSY_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MOSSY_BRICK_WALL = regBlockItem(ModBlocks.MOSSY_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<BlockItem> MOSSY_STONE = regBlockItem(ModBlocks.MOSSY_STONE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MOSSY_STONE_STAIRS = regBlockItem(ModBlocks.MOSSY_STONE_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MOSSY_STONE_SLAB = regBlockItem(ModBlocks.MOSSY_STONE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<BlockItem> CRACKED_BRICKS = regBlockItem(ModBlocks.CRACKED_BRICKS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_STONE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_STONE_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_NETHER_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_NETHER_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_STAIRS = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_SLAB = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_BRICK_WALL = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_STAIRS = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_SLAB = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> CRACKED_DEEPSLATE_TILE_WALL = regBlockItem(ModBlocks.CRACKED_DEEPSLATE_TILE_WALL, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> MULCH_BLOCK = regBlockItem(ModBlocks.MULCH_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS),3200);
    public static final RegistryObject<BlockItem> NULCH_BLOCK = regBlockItem(ModBlocks.NULCH_BLOCK, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> MULCH = regBlockItem(ModBlocks.MULCH, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS),800);
    public static final RegistryObject<BlockItem> NULCH = regBlockItem(ModBlocks.NULCH, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));

    public static final RegistryObject<Item> STONE_BRICK = ITEMS.register("stone_bricks", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> BLACKSTONE_BRICK = ITEMS.register("blackstone_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DEEPSLATE_BRICK = ITEMS.register("deepslate_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DEEPSLATE_TILE = ITEMS.register("deepslate_tile", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> AZALEA_FLOWERS = ITEMS.register("azalea_flowers", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> FLOWER_CROWN = ITEMS.register("flower_crown", () -> new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> MOSS_CLUMP = ITEMS.register("moss_clump", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.MOSS_CLUMP)));
    public static final RegistryObject<Item> GOLDEN_MOSS_CLUMP = ITEMS.register("golden_moss_clump", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.GOLDEN_MOSS_CLUMP)));

    public static final RegistryObject<Item> OAK_BARK = ITEMS.register("oak_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> BIRCH_BARK = ITEMS.register("birch_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> SPRUCE_BARK = ITEMS.register("spruce_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> JUNGLE_BARK = ITEMS.register("jungle_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> DARK_OAK_BARK = ITEMS.register("dark_oak_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> ACACIA_BARK = ITEMS.register("acacia_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS),200));
    public static final RegistryObject<Item> CRIMSON_SCALES = ITEMS.register("crimson_scales", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> WARPED_SCALES = ITEMS.register("warped_scales", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> STEEL_WOOL = ITEMS.register("steel_wool", ()->new Item(new Item.Properties().durability(128).tab(CreativeModeTab.TAB_TOOLS)));


    //cut iron
    public static final RegistryObject<BlockItem> CUT_IRON = regBlockItem(ModBlocks.CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON = regBlockItem(ModBlocks.EXPOSED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON = regBlockItem(ModBlocks.WEATHERED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON = regBlockItem(ModBlocks.RUSTED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CUT_IRON_STAIRS = regBlockItem(ModBlocks.CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.EXPOSED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WEATHERED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.RUSTED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> CUT_IRON_SLAB = regBlockItem(ModBlocks.CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_CUT_IRON_SLAB = regBlockItem(ModBlocks.EXPOSED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WEATHERED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_CUT_IRON_SLAB = regBlockItem(ModBlocks.RUSTED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON = regBlockItem(ModBlocks.WAXED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_CUT_IRON_SLAB = regBlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    //plate iron
    public static final RegistryObject<BlockItem> PLATE_IRON = regBlockItem(ModBlocks.PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON = regBlockItem(ModBlocks.RUSTED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> PLATE_IRON_STAIRS = regBlockItem(ModBlocks.PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.RUSTED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> PLATE_IRON_SLAB = regBlockItem(ModBlocks.PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> EXPOSED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.EXPOSED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WEATHERED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WEATHERED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> RUSTED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.RUSTED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON_STAIRS = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> WAXED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_PLATE_IRON_SLAB = regBlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_DOOR = regBlockItem(ModBlocks.EXPOSED_IRON_DOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_DOOR = regBlockItem(ModBlocks.WEATHERED_IRON_DOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> RUSTED_IRON_DOOR = regBlockItem(ModBlocks.RUSTED_IRON_DOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_TRAPDOOR = regBlockItem(ModBlocks.EXPOSED_IRON_TRAPDOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WEATHERED_IRON_TRAPDOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> RUSTED_IRON_TRAPDOOR = regBlockItem(ModBlocks.RUSTED_IRON_TRAPDOOR, new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));

    public static final RegistryObject<BlockItem> EXPOSED_IRON_BARS = regBlockItem(ModBlocks.EXPOSED_IRON_BARS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> WEATHERED_IRON_BARS = regBlockItem(ModBlocks.WEATHERED_IRON_BARS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> RUSTED_IRON_BARS = regBlockItem(ModBlocks.RUSTED_IRON_BARS, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));


    public static final RegistryObject<BlockItem> WAXED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_IRON_DOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_DOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_DOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_DOOR = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_DOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));

    public static final RegistryObject<BlockItem> WAXED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_IRON_TRAPDOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_TRAPDOOR = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR,  new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));

    public static final RegistryObject<BlockItem> WAXED_IRON_BARS = regBlockItem(ModBlocks.WAXED_IRON_BARS,  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> WAXED_EXPOSED_IRON_BARS = regBlockItem(ModBlocks.WAXED_EXPOSED_IRON_BARS,  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> WAXED_WEATHERED_IRON_BARS = regBlockItem(ModBlocks.WAXED_WEATHERED_IRON_BARS,  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> WAXED_RUSTED_IRON_BARS = regBlockItem(ModBlocks.WAXED_RUSTED_IRON_BARS,  new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));


}
