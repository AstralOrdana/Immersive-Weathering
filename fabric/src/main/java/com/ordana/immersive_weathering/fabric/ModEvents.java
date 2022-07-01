package com.ordana.immersive_weathering.fabric;

/*
public class ModEvents {
    public static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BRICKS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_SLABS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_STAIRS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_WALLS = new HashMap<>();
    private static final HashMap<Block, Block> STRIPPED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BARK = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_LOG = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_WOOD = new HashMap<>();
    private static final HashMap<Block, Block> RUSTED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> UNWAXED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> FLOWERY_BLOCKS = new HashMap<>();

    //TODO: add composter stuff

    static {
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICKS, Blocks.BRICKS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_SLAB, Blocks.BRICK_SLAB);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_STAIRS, Blocks.BRICK_STAIRS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_WALL, Blocks.BRICK_WALL);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE, Blocks.STONE);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_SLAB, Blocks.STONE_SLAB);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_STAIRS, Blocks.STONE_STAIRS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_WALL, ModBlocks.STONE_WALL);

        CRACKED_BLOCKS.put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.PRISMARINE_BRICKS, ModBlocks.CRACKED_PRISMARINE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.END_STONE_BRICKS, ModBlocks.CRACKED_END_STONE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);

        CRACKED_BLOCKS.put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.CRACKED_PRISMARINE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.END_STONE_BRICK_SLAB, ModBlocks.CRACKED_END_STONE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB);

        CRACKED_BLOCKS.put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.PRISMARINE_BRICK_STAIRS, ModBlocks.CRACKED_PRISMARINE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.END_STONE_BRICK_STAIRS, ModBlocks.CRACKED_END_STONE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS);

        CRACKED_BLOCKS.put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.END_STONE_BRICK_WALL, ModBlocks.CRACKED_END_STONE_BRICK_WALL);
        CRACKED_BLOCKS.put(ModBlocks.PRISMARINE_BRICK_WALL, ModBlocks.CRACKED_PRISMARINE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL);

        DROPPED_BRICKS.put(Blocks.STONE_BRICKS, ModItems.STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.PRISMARINE_BRICKS, ModItems.PRISMARINE_BRICK);
        DROPPED_BRICKS.put(Blocks.END_STONE_BRICKS, ModItems.END_STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK);
        DROPPED_BRICKS.put(Blocks.NETHER_BRICKS, Items.NETHER_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE);
        DROPPED_BRICKS.put(Blocks.BRICKS, Items.BRICK);

        DROPPED_BRICKS.put(Blocks.STONE_BRICK_SLAB, ModItems.STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.PRISMARINE_BRICK_SLAB, ModItems.PRISMARINE_BRICK);
        DROPPED_BRICKS.put(Blocks.END_STONE_BRICK_SLAB, ModItems.END_STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModItems.BLACKSTONE_BRICK);
        DROPPED_BRICKS.put(Blocks.NETHER_BRICK_SLAB, Items.NETHER_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_SLAB, ModItems.DEEPSLATE_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_SLAB, ModItems.DEEPSLATE_TILE);
        DROPPED_BRICKS.put(Blocks.BRICK_SLAB, Items.BRICK);

        DROPPED_BRICKS.put(Blocks.STONE_BRICK_STAIRS, ModItems.STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.PRISMARINE_BRICK_STAIRS, ModItems.PRISMARINE_BRICK);
        DROPPED_BRICKS.put(Blocks.END_STONE_BRICK_STAIRS, ModItems.END_STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModItems.BLACKSTONE_BRICK);
        DROPPED_BRICKS.put(Blocks.NETHER_BRICK_STAIRS, Items.NETHER_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_STAIRS, ModItems.DEEPSLATE_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_STAIRS, ModItems.DEEPSLATE_TILE);
        DROPPED_BRICKS.put(Blocks.BRICK_STAIRS, Items.BRICK);

        DROPPED_BRICKS.put(Blocks.STONE_BRICK_WALL, ModItems.STONE_BRICK);
        DROPPED_BRICKS.put(ModBlocks.PRISMARINE_BRICK_WALL, ModItems.PRISMARINE_BRICK);
        DROPPED_BRICKS.put(Blocks.END_STONE_BRICK_WALL, ModItems.END_STONE_BRICK);
        DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModItems.BLACKSTONE_BRICK);
        DROPPED_BRICKS.put(Blocks.NETHER_BRICK_WALL, Items.NETHER_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_WALL, ModItems.DEEPSLATE_BRICK);
        DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_WALL, ModItems.DEEPSLATE_TILE);
        DROPPED_BRICKS.put(Blocks.BRICK_WALL, Items.BRICK);

        BRICK_REPAIR.put(ModItems.STONE_BRICK, Blocks.STONE_BRICKS);
        BRICK_REPAIR.put(ModItems.PRISMARINE_BRICK, Blocks.PRISMARINE_BRICKS);
        BRICK_REPAIR.put(ModItems.END_STONE_BRICK, Blocks.END_STONE_BRICKS);
        BRICK_REPAIR.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICKS);
        BRICK_REPAIR.put(Items.NETHER_BRICK, Blocks.NETHER_BRICKS);
        BRICK_REPAIR.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICKS);
        BRICK_REPAIR.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILES);
        BRICK_REPAIR.put(Items.BRICK, Blocks.BRICKS);

        BRICK_REPAIR_SLABS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(ModItems.PRISMARINE_BRICK, Blocks.PRISMARINE_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(ModItems.END_STONE_BRICK, Blocks.END_STONE_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_SLAB);
        BRICK_REPAIR_SLABS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_SLAB);
        BRICK_REPAIR_SLABS.put(Items.BRICK, Blocks.BRICK_SLAB);

        BRICK_REPAIR_STAIRS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(ModItems.PRISMARINE_BRICK, Blocks.PRISMARINE_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(ModItems.END_STONE_BRICK, Blocks.END_STONE_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_STAIRS);
        BRICK_REPAIR_STAIRS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_STAIRS);
        BRICK_REPAIR_STAIRS.put(Items.BRICK, Blocks.BRICK_STAIRS);

        BRICK_REPAIR_WALLS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(ModItems.PRISMARINE_BRICK, ModBlocks.PRISMARINE_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(ModItems.END_STONE_BRICK, Blocks.END_STONE_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_WALL);
        BRICK_REPAIR_WALLS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_WALL);
        BRICK_REPAIR_WALLS.put(Items.BRICK, Blocks.BRICK_WALL);

        STRIPPED_BLOCKS.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        STRIPPED_BLOCKS.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        STRIPPED_BLOCKS.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        STRIPPED_BLOCKS.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        STRIPPED_BLOCKS.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        STRIPPED_BLOCKS.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        STRIPPED_BLOCKS.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        STRIPPED_BLOCKS.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        STRIPPED_BLOCKS.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);

        DROPPED_BARK.put(Blocks.OAK_LOG, ModItems.OAK_BARK);
        DROPPED_BARK.put(Blocks.SPRUCE_LOG, ModItems.SPRUCE_BARK);
        DROPPED_BARK.put(Blocks.JUNGLE_LOG, ModItems.JUNGLE_BARK);
        DROPPED_BARK.put(Blocks.BIRCH_LOG, ModItems.BIRCH_BARK);
        DROPPED_BARK.put(Blocks.DARK_OAK_LOG, ModItems.DARK_OAK_BARK);
        DROPPED_BARK.put(Blocks.ACACIA_LOG, ModItems.ACACIA_BARK);
        DROPPED_BARK.put(Blocks.WARPED_STEM, ModItems.WARPED_SCALES);
        DROPPED_BARK.put(Blocks.CRIMSON_STEM, ModItems.CRIMSON_SCALES);
        DROPPED_BARK.put(Blocks.OAK_WOOD, ModItems.OAK_BARK);
        DROPPED_BARK.put(Blocks.SPRUCE_WOOD, ModItems.SPRUCE_BARK);
        DROPPED_BARK.put(Blocks.JUNGLE_WOOD, ModItems.JUNGLE_BARK);
        DROPPED_BARK.put(Blocks.BIRCH_WOOD, ModItems.BIRCH_BARK);
        DROPPED_BARK.put(Blocks.DARK_OAK_WOOD, ModItems.DARK_OAK_BARK);
        DROPPED_BARK.put(Blocks.ACACIA_WOOD, ModItems.ACACIA_BARK);
        DROPPED_BARK.put(Blocks.WARPED_HYPHAE, ModItems.WARPED_SCALES);
        DROPPED_BARK.put(Blocks.CRIMSON_HYPHAE, ModItems.CRIMSON_SCALES);

        UNSTRIP_LOG.put(ModItems.OAK_BARK, Blocks.OAK_LOG);
        UNSTRIP_LOG.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_LOG);
        UNSTRIP_LOG.put(ModItems.BIRCH_BARK, Blocks.BIRCH_LOG);
        UNSTRIP_LOG.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_LOG);
        UNSTRIP_LOG.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_LOG);
        UNSTRIP_LOG.put(ModItems.ACACIA_BARK, Blocks.ACACIA_LOG);
        UNSTRIP_LOG.put(ModItems.WARPED_SCALES, Blocks.WARPED_STEM);
        UNSTRIP_LOG.put(ModItems.CRIMSON_SCALES, Blocks.CRIMSON_STEM);
        UNSTRIP_WOOD.put(ModItems.OAK_BARK, Blocks.OAK_WOOD);
        UNSTRIP_WOOD.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_WOOD);
        UNSTRIP_WOOD.put(ModItems.BIRCH_BARK, Blocks.BIRCH_WOOD);
        UNSTRIP_WOOD.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_WOOD);
        UNSTRIP_WOOD.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_WOOD);
        UNSTRIP_WOOD.put(ModItems.ACACIA_BARK, Blocks.ACACIA_WOOD);
        UNSTRIP_WOOD.put(ModItems.WARPED_SCALES, Blocks.WARPED_HYPHAE);
        UNSTRIP_WOOD.put(ModItems.CRIMSON_SCALES, Blocks.CRIMSON_HYPHAE);

        UNWAXED_BLOCKS.put(ModBlocks.WAXED_CUT_IRON, ModBlocks.CUT_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_CUT_IRON, ModBlocks.EXPOSED_CUT_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_CUT_IRON, ModBlocks.WEATHERED_CUT_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_CUT_IRON, ModBlocks.RUSTED_CUT_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_CUT_IRON_STAIRS, ModBlocks.CUT_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS, ModBlocks.EXPOSED_CUT_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS, ModBlocks.WEATHERED_CUT_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS, ModBlocks.RUSTED_CUT_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_CUT_IRON_SLAB, ModBlocks.CUT_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB, ModBlocks.EXPOSED_CUT_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB, ModBlocks.WEATHERED_CUT_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB, ModBlocks.RUSTED_CUT_IRON_SLAB);

        UNWAXED_BLOCKS.put(ModBlocks.WAXED_PLATE_IRON, ModBlocks.PLATE_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_PLATE_IRON, ModBlocks.EXPOSED_PLATE_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_PLATE_IRON, ModBlocks.WEATHERED_PLATE_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_PLATE_IRON, ModBlocks.RUSTED_PLATE_IRON);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_PLATE_IRON_STAIRS, ModBlocks.PLATE_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS, ModBlocks.EXPOSED_PLATE_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS, ModBlocks.WEATHERED_PLATE_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS, ModBlocks.RUSTED_PLATE_IRON_STAIRS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_PLATE_IRON_SLAB, ModBlocks.PLATE_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB, ModBlocks.EXPOSED_PLATE_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB, ModBlocks.WEATHERED_PLATE_IRON_SLAB);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB, ModBlocks.RUSTED_PLATE_IRON_SLAB);

        UNWAXED_BLOCKS.put(ModBlocks.WAXED_IRON_DOOR, Blocks.IRON_DOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_IRON_DOOR, ModBlocks.RUSTED_IRON_DOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_IRON_TRAPDOOR, Blocks.IRON_TRAPDOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR, ModBlocks.RUSTED_IRON_TRAPDOOR);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_IRON_BARS, Blocks.IRON_BARS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_EXPOSED_IRON_BARS, ModBlocks.EXPOSED_IRON_BARS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_WEATHERED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS);
        UNWAXED_BLOCKS.put(ModBlocks.WAXED_RUSTED_IRON_BARS, ModBlocks.RUSTED_IRON_BARS);

        UNWAXED_BLOCKS.put(Blocks.WAXED_COPPER_BLOCK, Blocks.COPPER_BLOCK);
        UNWAXED_BLOCKS.put(Blocks.WAXED_EXPOSED_COPPER, Blocks.EXPOSED_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_WEATHERED_COPPER, Blocks.WEATHERED_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_OXIDIZED_COPPER, Blocks.OXIDIZED_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_CUT_COPPER, Blocks.CUT_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
        UNWAXED_BLOCKS.put(Blocks.WAXED_CUT_COPPER_SLAB, Blocks.CUT_COPPER_SLAB);
        UNWAXED_BLOCKS.put(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB);
        UNWAXED_BLOCKS.put(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB);
        UNWAXED_BLOCKS.put(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
        UNWAXED_BLOCKS.put(Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.CUT_COPPER_STAIRS);
        UNWAXED_BLOCKS.put(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS);
        UNWAXED_BLOCKS.put(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS);
        UNWAXED_BLOCKS.put(Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);

        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON, ModBlocks.EXPOSED_CUT_IRON);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON, ModBlocks.WEATHERED_CUT_IRON);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON, ModBlocks.RUSTED_CUT_IRON);
        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_STAIRS, ModBlocks.EXPOSED_CUT_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_STAIRS, ModBlocks.WEATHERED_CUT_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_STAIRS, ModBlocks.RUSTED_CUT_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_SLAB, ModBlocks.EXPOSED_CUT_IRON_SLAB);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_SLAB, ModBlocks.WEATHERED_CUT_IRON_SLAB);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_SLAB, ModBlocks.RUSTED_CUT_IRON_SLAB);
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON, ModBlocks.EXPOSED_PLATE_IRON);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON, ModBlocks.WEATHERED_PLATE_IRON);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON, ModBlocks.RUSTED_PLATE_IRON);
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_STAIRS, ModBlocks.EXPOSED_PLATE_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS, ModBlocks.WEATHERED_PLATE_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS, ModBlocks.RUSTED_PLATE_IRON_STAIRS);
        RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_SLAB, ModBlocks.EXPOSED_PLATE_IRON_SLAB);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_SLAB, ModBlocks.WEATHERED_PLATE_IRON_SLAB);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_SLAB, ModBlocks.RUSTED_PLATE_IRON_SLAB);
        RUSTED_BLOCKS.put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.RUSTED_IRON_DOOR);
        RUSTED_BLOCKS.put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.RUSTED_IRON_TRAPDOOR);
        RUSTED_BLOCKS.put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS);
        RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS);
        RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_BARS, ModBlocks.RUSTED_IRON_BARS);

        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA);
        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES);
        FLOWERY_BLOCKS.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, ModBlocks.AZALEA_LEAF_PILE);
    }

    public static void registerEvents() {
        UseBlockCallback.EVENT.add((player, world, hand, hitResult) -> {

            BlockPos targetPos = hitResult.getBlockPos();
            BlockPos fixedPos = targetPos.relative(hitResult.getDirection());
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = player.getItemInHand(hand);

            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.cauldronWashing) {
                if (heldItem.getItem() == Items.DIRT) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(Items.CLAY));
                        if (world.random.nextFloat() < 0.5f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.GRAVEL));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.GRAVEL) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(Items.FLINT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.IRON_NUGGET));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.CLAY) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.SILT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.SAND));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.pistonSliming) {
                if (heldItem.getItem() == Items.SLIME_BALL) {
                    if (targetBlock.is(Blocks.PISTON) && !targetBlock.getValue(PistonBaseBlock.EXTENDED)) {
                        world.playSound(player, targetPos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.STICKY_PISTON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.FROST) {
                if (targetBlock.is(Blocks.GLASS)) {
                    world.playSound(player, targetPos, SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, ModBlocks.FROSTY_GLASS.defaultBlockState().setValue(FrostyGrassBlock.NATURAL, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(Blocks.GLASS_PANE)) {
                    world.playSound(player, targetPos, SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, ModBlocks.FROSTY_GLASS_PANE.withPropertiesOf(targetBlock).setValue(FrostyGrassBlock.NATURAL, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(Blocks.GRASS)) {
                    world.playSound(player, targetPos, SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, ModBlocks.FROSTY_GRASS.withPropertiesOf(targetBlock).setValue(FrostyGrassBlock.NATURAL, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(Blocks.FERN)) {
                    world.playSound(player, targetPos, SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, ModBlocks.FROSTY_FERN.withPropertiesOf(targetBlock).setValue(FrostyGrassBlock.NATURAL, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShearsItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.soilShearing) {
                    if (targetBlock.hasProperty(ModGrassBlock.FERTILE) && targetBlock.getValue(ModGrassBlock.FERTILE) && targetBlock.hasProperty(SnowyDirtBlock.SNOWY) && !targetBlock.getValue(SnowyDirtBlock.SNOWY)) {
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, new BlockParticleOption(ParticleTypes.BLOCK, targetBlock), UniformInt.of(3,5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().defaultBlockState().setValue(ModGrassBlock.FERTILE, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if (targetBlock.hasProperty(IvyBlock.AGE) && targetBlock.getValue(IvyBlock.AGE) < IvyBlock.MAX_AGE) {
                    world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, new BlockParticleOption(ParticleTypes.BLOCK, targetBlock), UniformInt.of(3,5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(IvyBlock.AGE, IvyBlock.MAX_AGE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.azaleaShearing) {
                    if (targetBlock.is(ModTags.FLOWERY)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.AZALEA_FLOWERS));
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.AZALEA_FLOWER, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, FLOWERY_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossShearing) {
                    if (targetBlock.is(ModTags.MOSSY)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.MOSS_CLUMP));
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.MOSS, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.pistonSliming) {
                    if (targetBlock.is(Blocks.STICKY_PISTON) && !targetBlock.getValue(PistonBaseBlock.EXTENDED)) {
                        world.playSound(player, targetPos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
                        Block.popResource(world, fixedPos, new ItemStack(Items.SLIME_BALL));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, Blocks.PISTON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == Items.WATER_BUCKET) {
                if (targetBlock.is(ModBlocks.SILT) && (!targetBlock.getValue(SiltBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(SiltBlock.SOAKED, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.FLUVISOL) && (!targetBlock.getValue(FluvisolBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(FluvisolBlock.SOAKED, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.MULCH_BLOCK) && (targetBlock.getValue(MulchBlock.MOISTURE) == 0)) {
                    player.awardStat(Stats.ITEM_USED.get(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(MulchBlock.MOISTURE, 7));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.CRACKED_MUD) && (!targetBlock.getValue(CrackedMudBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(CrackedMudBlock.SOAKED, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.VERTISOL) && (!targetBlock.getValue(CrackedMudBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(CrackedMudBlock.SOAKED, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.LAVA_BUCKET) {
                if (targetBlock.is(ModBlocks.NULCH_BLOCK) && (!targetBlock.getValue(NulchBlock.MOLTEN))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.LAVA_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(NulchBlock.MOLTEN, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.BUCKET) {
                if (targetBlock.is(ModBlocks.NULCH_BLOCK) && (targetBlock.getValue(NulchBlock.MOLTEN))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.LAVA_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(NulchBlock.MOLTEN, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.SILT) && (targetBlock.getValue(SiltBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.WATER_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(SiltBlock.SOAKED, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.FLUVISOL) && (targetBlock.getValue(FluvisolBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.WATER_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(FluvisolBlock.SOAKED, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.MULCH_BLOCK) && (targetBlock.getValue(MulchBlock.MOISTURE) == 7)) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.WATER_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(MulchBlock.MOISTURE, 0));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.CRACKED_MUD) && (targetBlock.getValue(CrackedMudBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.WATER_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(CrackedMudBlock.SOAKED, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (targetBlock.is(ModBlocks.VERTISOL) && (targetBlock.getValue(CrackedMudBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.WATER_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(CrackedMudBlock.SOAKED, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                if (targetBlock.is(ModBlocks.SILT) && (targetBlock.getValue(SiltBlock.SOAKED))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
                    world.playSound(player, targetPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, ModItems.POND_WATER.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(SiltBlock.SOAKED, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShovelItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.shovelExtinguishing) {
                    if (targetBlock.is(Blocks.CAMPFIRE) && targetBlock.getValue(BlockStateProperties.LIT)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CampfireBlock.LIT, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if (targetBlock.is(Blocks.FIRE)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if ((targetBlock.is(ModBlocks.HUMUS) || targetBlock.is(ModBlocks.FLUVISOL) || targetBlock.is(ModBlocks.VERTISOL) || targetBlock.is(ModBlocks.CRYOSOL)) && !targetBlock.getValue(BlockStateProperties.SNOWY)) {
                    world.playSound(player, targetPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, Blocks.DIRT_PATH.defaultBlockState());
                    }
                    return InteractionResult.SUCCESS;
                }

            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.azaleaShearing) {
                if (heldItem.getItem() == ModItems.AZALEA_FLOWERS) {
                    if (targetBlock.is(ModTags.FLOWERABLE)) {
                        world.playSound(player, targetPos, SoundEvents.FLOWERING_AZALEA_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.AZALEA_FLOWER, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                                if (targetBlock.is(shorn)) {
                                    world.setBlockAndUpdate(targetPos, flowery.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossShearing) {
                if (heldItem.getItem() == ModItems.MOSS_CLUMP) {
                    if (targetBlock.is(ModTags.MOSSABLE)) {
                        world.playSound(player, targetPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            CLEANED_BLOCKS.forEach((mossy, clean) -> {
                                if (targetBlock.is(clean)) {
                                    world.setBlockAndUpdate(targetPos, mossy.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.charredBlockIgniting) {
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && !targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.EMBERSPARK, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, true));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossBurning) {
                    if (targetBlock.is(ModTags.MOSSY)) {
                        world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMALL_FLAME, UniformInt.of(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if (targetBlock.is(ModBlocks.FROSTY_GLASS)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, Blocks.GLASS.defaultBlockState());
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(ModBlocks.FROSTY_GLASS_PANE)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, Blocks.GLASS_PANE.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(ModBlocks.FROSTY_GRASS)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, Blocks.GRASS.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
                else if (targetBlock.is(ModBlocks.FROSTY_FERN)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, Blocks.FERN.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
            }

            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.spongeRusting) {
                if (heldItem.getItem() == Items.WET_SPONGE) {
                    if (targetBlock.is(ModTags.RUSTABLE)) {
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, RUSTED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.STEEL_WOOL) {
                if(targetBlock.is(ModTags.COPPER)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SCRAPE, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        WeatheringCopper.getPrevious(targetBlock).ifPresent(o-> world.setBlockAndUpdate(targetPos, o));
                    }
                    return InteractionResult.SUCCESS;
                }
                if(targetBlock.is(ModTags.EXPOSED_IRON) || targetBlock.is(ModTags.WEATHERED_IRON) || targetBlock.is(ModTags.RUSTED_IRON)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (targetBlock.is(rusty)) {
                                world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
                if(targetBlock.is(ModTags.WAXED_BLOCKS)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.WAX_OFF, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, UNWAXED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.pickaxeCracking) {
                if (heldItem.getItem() instanceof PickaxeItem) {
                    if (targetBlock.is(ModTags.CRACKABLE)) {
                        Block.popResource(world, fixedPos, new ItemStack(DROPPED_BRICKS.get(targetBlock.getBlock())));
                        world.playSound(player, targetPos, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, new BlockParticleOption(ParticleTypes.BLOCK, targetBlock), UniformInt.of(3,5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, CRACKED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if (BRICK_REPAIR.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (BRICK_REPAIR_SLABS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_SLABS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_SLABS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (BRICK_REPAIR_STAIRS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_STAIRS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_STAIRS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (BRICK_REPAIR_WALLS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_WALLS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_WALLS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof AxeItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeStripping) {
                    if (targetBlock.is(ModTags.RAW_LOGS)) {
                        Block.popResource(world, fixedPos, new ItemStack(DROPPED_BARK.get(targetBlock.getBlock())));
                        world.playSound(player, targetPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        var barkParticle = WeatheringHelper.getBarkParticle(targetBlock).orElse(null);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, barkParticle, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, STRIPPED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeScraping) {
                    if (targetBlock.is(ModTags.WEATHERED_IRON) || targetBlock.is(ModTags.RUSTED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        world.playSound(player, targetPos, SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    else if (targetBlock.is(ModTags.EXPOSED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            RUSTED_BLOCKS.forEach((clean, rusty) -> {
                                if (targetBlock.is(rusty)) {
                                    world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeStripping) {
                if (UNSTRIP_LOG.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_LOG.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_LOG.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (UNSTRIP_WOOD.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_WOOD.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_WOOD.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }
}


*/