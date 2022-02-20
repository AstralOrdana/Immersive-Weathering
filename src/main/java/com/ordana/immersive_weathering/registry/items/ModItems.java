package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.ModFoodComponents;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final BlockItem OAK_LEAF_PILE = new BlockItem(ModBlocks.OAK_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem SPRUCE_LEAF_PILE = new BlockItem(ModBlocks.SPRUCE_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem BIRCH_LEAF_PILE = new BlockItem(ModBlocks.BIRCH_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem JUNGLE_LEAF_PILE = new BlockItem(ModBlocks.JUNGLE_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem ACACIA_LEAF_PILE = new BlockItem(ModBlocks.ACACIA_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem DARK_OAK_LEAF_PILE = new BlockItem(ModBlocks.DARK_OAK_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem AZALEA_LEAF_PILE = new BlockItem(ModBlocks.AZALEA_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem FLOWERING_AZALEA_LEAF_PILE = new BlockItem(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final Item STEEL_WOOL = new BoneMealItem(new Item.Settings().maxDamage(128).group(ItemGroup.TOOLS));

    public static final BlockItem WEEDS = new BlockItem(ModBlocks.WEEDS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem SOOT = new BlockItem(ModBlocks.SOOT, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem ASH_BLOCK = new BlockItem(ModBlocks.ASH_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final BlockItem MOSSY_BRICKS = new BlockItem(ModBlocks.MOSSY_BRICKS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MOSSY_BRICK_STAIRS = new BlockItem(ModBlocks.MOSSY_BRICK_STAIRS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MOSSY_BRICK_SLAB = new BlockItem(ModBlocks.MOSSY_BRICK_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MOSSY_BRICK_WALL = new BlockItem(ModBlocks.MOSSY_BRICK_WALL, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final BlockItem MOSSY_STONE = new BlockItem(ModBlocks.MOSSY_STONE, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MOSSY_STONE_STAIRS = new BlockItem(ModBlocks.MOSSY_STONE_STAIRS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MOSSY_STONE_SLAB = new BlockItem(ModBlocks.MOSSY_STONE_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final BlockItem CRACKED_BRICKS = new BlockItem(ModBlocks.CRACKED_BRICKS, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final BlockItem MULCH_BLOCK = new BlockItem(ModBlocks.MULCH_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem NULCH_BLOCK = new BlockItem(ModBlocks.NULCH_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem MULCH = new BlockItem(ModBlocks.MULCH, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem NULCH = new BlockItem(ModBlocks.NULCH, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final Item STONE_BRICK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item BLACKSTONE_BRICK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item DEEPSLATE_BRICK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item DEEPSLATE_TILE = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

    public static final Item MOSS_CLUMP = new Item(new Item.Settings().group(ItemGroup.MATERIALS).food(ModFoodComponents.MOSS_CLUMP));
    public static final Item GOLDEN_MOSS_CLUMP = new Item(new Item.Settings().group(ItemGroup.MATERIALS).food(ModFoodComponents.GOLDEN_MOSS_CLUMP));

    public static final Item OAK_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item BIRCH_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item SPRUCE_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item JUNGLE_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item DARK_OAK_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item ACACIA_BARK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item CRIMSON_SCALES = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
    public static final Item WARPED_SCALES = new Item(new Item.Settings().group(ItemGroup.MATERIALS));


    //cut iron
    public static final BlockItem CUT_IRON = new BlockItem(ModBlocks.CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_CUT_IRON = new BlockItem(ModBlocks.EXPOSED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_CUT_IRON = new BlockItem(ModBlocks.WEATHERED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_CUT_IRON = new BlockItem(ModBlocks.RUSTED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem CUT_IRON_STAIRS = new BlockItem(ModBlocks.CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.EXPOSED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.WEATHERED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.RUSTED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem CUT_IRON_SLAB = new BlockItem(ModBlocks.CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_CUT_IRON_SLAB = new BlockItem(ModBlocks.EXPOSED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_CUT_IRON_SLAB = new BlockItem(ModBlocks.WEATHERED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_CUT_IRON_SLAB = new BlockItem(ModBlocks.RUSTED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_CUT_IRON = new BlockItem(ModBlocks.WAXED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_CUT_IRON = new BlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_CUT_IRON = new BlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_CUT_IRON = new BlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_CUT_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_CUT_IRON_SLAB = new BlockItem(ModBlocks.WAXED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_CUT_IRON_SLAB = new BlockItem(ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_CUT_IRON_SLAB = new BlockItem(ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_CUT_IRON_SLAB = new BlockItem(ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    //plate iron
    public static final BlockItem PLATE_IRON = new BlockItem(ModBlocks.PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_PLATE_IRON = new BlockItem(ModBlocks.EXPOSED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_PLATE_IRON = new BlockItem(ModBlocks.WEATHERED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_PLATE_IRON = new BlockItem(ModBlocks.RUSTED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem PLATE_IRON_STAIRS = new BlockItem(ModBlocks.PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.EXPOSED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.WEATHERED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.RUSTED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem PLATE_IRON_SLAB = new BlockItem(ModBlocks.PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem EXPOSED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.EXPOSED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WEATHERED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.WEATHERED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem RUSTED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.RUSTED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_PLATE_IRON = new BlockItem(ModBlocks.WAXED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_PLATE_IRON = new BlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_PLATE_IRON = new BlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_PLATE_IRON = new BlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_PLATE_IRON_STAIRS = new BlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem WAXED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.WAXED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_EXPOSED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_WEATHERED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));
    public static final BlockItem WAXED_RUSTED_PLATE_IRON_SLAB = new BlockItem(ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

    public static final BlockItem EXPOSED_IRON_DOOR = new BlockItem(ModBlocks.EXPOSED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WEATHERED_IRON_DOOR = new BlockItem(ModBlocks.WEATHERED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem RUSTED_IRON_DOOR = new BlockItem(ModBlocks.RUSTED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));

    public static final BlockItem EXPOSED_IRON_TRAPDOOR = new BlockItem(ModBlocks.EXPOSED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WEATHERED_IRON_TRAPDOOR = new BlockItem(ModBlocks.WEATHERED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem RUSTED_IRON_TRAPDOOR = new BlockItem(ModBlocks.RUSTED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));

    public static final BlockItem EXPOSED_IRON_BARS = new BlockItem(ModBlocks.EXPOSED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem WEATHERED_IRON_BARS = new BlockItem(ModBlocks.WEATHERED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem RUSTED_IRON_BARS = new BlockItem(ModBlocks.RUSTED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static final BlockItem WAXED_IRON_DOOR = new BlockItem(ModBlocks.WAXED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_EXPOSED_IRON_DOOR = new BlockItem(ModBlocks.WAXED_EXPOSED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_WEATHERED_IRON_DOOR = new BlockItem(ModBlocks.WAXED_WEATHERED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_RUSTED_IRON_DOOR = new BlockItem(ModBlocks.WAXED_RUSTED_IRON_DOOR, new Item.Settings().group(ItemGroup.REDSTONE));

    public static final BlockItem WAXED_IRON_TRAPDOOR = new BlockItem(ModBlocks.WAXED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_EXPOSED_IRON_TRAPDOOR = new BlockItem(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_WEATHERED_IRON_TRAPDOOR = new BlockItem(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem WAXED_RUSTED_IRON_TRAPDOOR = new BlockItem(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE));

    public static final BlockItem WAXED_IRON_BARS = new BlockItem(ModBlocks.WAXED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem WAXED_EXPOSED_IRON_BARS = new BlockItem(ModBlocks.WAXED_EXPOSED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem WAXED_WEATHERED_IRON_BARS = new BlockItem(ModBlocks.WAXED_WEATHERED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));
    public static final BlockItem WAXED_RUSTED_IRON_BARS = new BlockItem(ModBlocks.WAXED_RUSTED_IRON_BARS, new Item.Settings().group(ItemGroup.DECORATIONS));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "oak_leaf_pile"), OAK_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "spruce_leaf_pile"), SPRUCE_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "birch_leaf_pile"), BIRCH_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "jungle_leaf_pile"), JUNGLE_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "acacia_leaf_pile"), ACACIA_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "dark_oak_leaf_pile"), DARK_OAK_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "azalea_leaf_pile"), AZALEA_LEAF_PILE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "flowering_azalea_leaf_pile"), FLOWERING_AZALEA_LEAF_PILE);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "steel_wool"), STEEL_WOOL);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weeds"), WEEDS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "soot"), SOOT);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "ash_block"), ASH_BLOCK);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_bricks"), CRACKED_BRICKS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mulch_block"), MULCH_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "nulch_block"), NULCH_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "mulch"), MULCH);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "nulch"), NULCH);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "stone_brick"), STONE_BRICK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "blackstone_brick"), BLACKSTONE_BRICK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "deepslate_brick"), DEEPSLATE_BRICK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "deepslate_tile"), DEEPSLATE_TILE);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "moss_clump"), MOSS_CLUMP);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "golden_moss_clump"), GOLDEN_MOSS_CLUMP);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "oak_bark"), OAK_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "spruce_bark"), SPRUCE_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "birch_bark"), BIRCH_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "jungle_bark"), JUNGLE_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "acacia_bark"), ACACIA_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "dark_oak_bark"), DARK_OAK_BARK);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "crimson_scales"), CRIMSON_SCALES);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "warped_scales"), WARPED_SCALES);

        //plate iron
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron"), PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron"), EXPOSED_PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron"), WEATHERED_PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron"), RUSTED_PLATE_IRON);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron_stairs"), PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_stairs"), EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_stairs"), WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_stairs"), RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron_slab"), PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_slab"), EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_slab"), WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_slab"), RUSTED_PLATE_IRON_SLAB);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron"), WAXED_PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron"), WAXED_EXPOSED_PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron"), WAXED_WEATHERED_PLATE_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron"), WAXED_RUSTED_PLATE_IRON);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_stairs"), WAXED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_stairs"), WAXED_EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_stairs"), WAXED_WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_stairs"), WAXED_RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_slab"), WAXED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_slab"), WAXED_EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_slab"), WAXED_WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_slab"), WAXED_RUSTED_PLATE_IRON_SLAB);

        //cut iron
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron"), CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron"), EXPOSED_CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron"), WEATHERED_CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron"), RUSTED_CUT_IRON);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron_stairs"), CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_stairs"), EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_stairs"), WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_stairs"), RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron_slab"), CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_slab"), EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_slab"), WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_slab"), RUSTED_CUT_IRON_SLAB);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron"), WAXED_CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron"), WAXED_EXPOSED_CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron"), WAXED_WEATHERED_CUT_IRON);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron"), WAXED_RUSTED_CUT_IRON);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_stairs"), WAXED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_stairs"), WAXED_EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_stairs"), WAXED_WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_stairs"), WAXED_RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_slab"), WAXED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_slab"), WAXED_EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_slab"), WAXED_WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_slab"), WAXED_RUSTED_CUT_IRON_SLAB);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_door"), EXPOSED_IRON_DOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_door"), WEATHERED_IRON_DOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_door"), RUSTED_IRON_DOOR);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_trapdoor"), EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_trapdoor"), WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_trapdoor"), RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_bars"), EXPOSED_IRON_BARS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_bars"), WEATHERED_IRON_BARS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_bars"), RUSTED_IRON_BARS);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_door"), WAXED_IRON_DOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_door"), WAXED_EXPOSED_IRON_DOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_door"), WAXED_WEATHERED_IRON_DOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_door"), WAXED_RUSTED_IRON_DOOR);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_trapdoor"), WAXED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_trapdoor"), WAXED_EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_trapdoor"), WAXED_WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_trapdoor"), WAXED_RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_bars"), WAXED_IRON_BARS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_bars"), WAXED_EXPOSED_IRON_BARS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_bars"), WAXED_WEATHERED_IRON_BARS);
        Registry.register(Registry.ITEM, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_bars"), WAXED_RUSTED_IRON_BARS);
    }
}
