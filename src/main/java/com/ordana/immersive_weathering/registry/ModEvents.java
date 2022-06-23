package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.*;
import com.ordana.immersive_weathering.registry.blocks.charred.*;
import com.ordana.immersive_weathering.registry.blocks.sandy.SandyBlock;
import com.ordana.immersive_weathering.registry.items.ModItems;
import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.*;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.HashMap;
import java.util.Random;

public class ModEvents {
    public static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BRICKS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR = new HashMap<>();
    private static final HashMap<Item, Block> CHISELED_BRICK_REPAIR = new HashMap<>();
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
    private static final HashMap<Block, Item> SHORN_LEAVES = new HashMap<>();
    public static final HashMap<Block, Block> SNOWY_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> SANDY_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> UNSNOWY_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> UNSANDY_BLOCKS = new HashMap<>();

    static {
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
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
        CRACKED_BLOCKS.put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.CRACKED_CHISELED_STONE_BRICKS);
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
        DROPPED_BRICKS.put(Blocks.CHISELED_STONE_BRICKS, ModItems.STONE_BRICK);
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

        CHISELED_BRICK_REPAIR.put(ModItems.STONE_BRICK, Blocks.CHISELED_STONE_BRICKS);

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
        STRIPPED_BLOCKS.put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        STRIPPED_BLOCKS.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        STRIPPED_BLOCKS.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        STRIPPED_BLOCKS.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        STRIPPED_BLOCKS.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        STRIPPED_BLOCKS.put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        STRIPPED_BLOCKS.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        STRIPPED_BLOCKS.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);

        DROPPED_BARK.put(Blocks.OAK_LOG, ModItems.OAK_BARK);
        DROPPED_BARK.put(Blocks.SPRUCE_LOG, ModItems.SPRUCE_BARK);
        DROPPED_BARK.put(Blocks.JUNGLE_LOG, ModItems.JUNGLE_BARK);
        DROPPED_BARK.put(Blocks.BIRCH_LOG, ModItems.BIRCH_BARK);
        DROPPED_BARK.put(Blocks.DARK_OAK_LOG, ModItems.DARK_OAK_BARK);
        DROPPED_BARK.put(Blocks.ACACIA_LOG, ModItems.ACACIA_BARK);
        DROPPED_BARK.put(Blocks.MANGROVE_LOG, ModItems.MANGROVE_BARK);
        DROPPED_BARK.put(Blocks.WARPED_STEM, ModItems.WARPED_SCALES);
        DROPPED_BARK.put(Blocks.CRIMSON_STEM, ModItems.CRIMSON_SCALES);
        DROPPED_BARK.put(Blocks.OAK_WOOD, ModItems.OAK_BARK);
        DROPPED_BARK.put(Blocks.SPRUCE_WOOD, ModItems.SPRUCE_BARK);
        DROPPED_BARK.put(Blocks.JUNGLE_WOOD, ModItems.JUNGLE_BARK);
        DROPPED_BARK.put(Blocks.BIRCH_WOOD, ModItems.BIRCH_BARK);
        DROPPED_BARK.put(Blocks.DARK_OAK_WOOD, ModItems.DARK_OAK_BARK);
        DROPPED_BARK.put(Blocks.ACACIA_WOOD, ModItems.ACACIA_BARK);
        DROPPED_BARK.put(Blocks.MANGROVE_WOOD, ModItems.MANGROVE_BARK);
        DROPPED_BARK.put(Blocks.WARPED_HYPHAE, ModItems.WARPED_SCALES);
        DROPPED_BARK.put(Blocks.CRIMSON_HYPHAE, ModItems.CRIMSON_SCALES);

        UNSTRIP_LOG.put(ModItems.OAK_BARK, Blocks.OAK_LOG);
        UNSTRIP_LOG.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_LOG);
        UNSTRIP_LOG.put(ModItems.BIRCH_BARK, Blocks.BIRCH_LOG);
        UNSTRIP_LOG.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_LOG);
        UNSTRIP_LOG.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_LOG);
        UNSTRIP_LOG.put(ModItems.ACACIA_BARK, Blocks.ACACIA_LOG);
        UNSTRIP_LOG.put(ModItems.MANGROVE_BARK, Blocks.MANGROVE_LOG);
        UNSTRIP_LOG.put(ModItems.WARPED_SCALES, Blocks.WARPED_STEM);
        UNSTRIP_LOG.put(ModItems.CRIMSON_SCALES, Blocks.CRIMSON_STEM);
        UNSTRIP_WOOD.put(ModItems.OAK_BARK, Blocks.OAK_WOOD);
        UNSTRIP_WOOD.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_WOOD);
        UNSTRIP_WOOD.put(ModItems.BIRCH_BARK, Blocks.BIRCH_WOOD);
        UNSTRIP_WOOD.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_WOOD);
        UNSTRIP_WOOD.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_WOOD);
        UNSTRIP_WOOD.put(ModItems.ACACIA_BARK, Blocks.ACACIA_WOOD);
        UNSTRIP_WOOD.put(ModItems.MANGROVE_BARK, Blocks.MANGROVE_WOOD);
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

        SHORN_LEAVES.put(ModBlocks.OAK_BRANCHES, ModItems.OAK_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.BIRCH_BRANCHES, ModItems.BIRCH_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.SPRUCE_BRANCHES, ModItems.SPRUCE_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.JUNGLE_BRANCHES, ModItems.JUNGLE_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.DARK_OAK_BRANCHES, ModItems.DARK_OAK_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.ACACIA_BRANCHES, ModItems.ACACIA_LEAF_PILE);
        SHORN_LEAVES.put(ModBlocks.MANGROVE_BRANCHES, ModItems.MANGROVE_LEAF_PILE);

        SNOWY_BLOCKS.put(Blocks.GRASS, ModBlocks.FROSTY_GRASS);
        SNOWY_BLOCKS.put(Blocks.FERN, ModBlocks.FROSTY_FERN);
        SNOWY_BLOCKS.put(Blocks.GLASS, ModBlocks.FROSTY_GLASS);
        SNOWY_BLOCKS.put(Blocks.GLASS_PANE, ModBlocks.FROSTY_GLASS_PANE);
        SNOWY_BLOCKS.put(Blocks.STONE, ModBlocks.SNOWY_STONE);
        SNOWY_BLOCKS.put(Blocks.STONE_STAIRS, ModBlocks.SNOWY_STONE_STAIRS);
        SNOWY_BLOCKS.put(Blocks.STONE_SLAB, ModBlocks.SNOWY_STONE_SLAB);
        SNOWY_BLOCKS.put(ModBlocks.STONE_WALL, ModBlocks.SNOWY_STONE_WALL);
        SNOWY_BLOCKS.put(Blocks.COBBLESTONE, ModBlocks.SNOWY_COBBLESTONE);
        SNOWY_BLOCKS.put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SNOWY_COBBLESTONE_STAIRS);
        SNOWY_BLOCKS.put(Blocks.COBBLESTONE_SLAB, ModBlocks.SNOWY_COBBLESTONE_SLAB);
        SNOWY_BLOCKS.put(Blocks.COBBLESTONE_WALL, ModBlocks.SNOWY_COBBLESTONE_WALL);
        SNOWY_BLOCKS.put(Blocks.STONE_BRICKS, ModBlocks.SNOWY_STONE_BRICKS);
        SNOWY_BLOCKS.put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SNOWY_CHISELED_STONE_BRICKS);
        SNOWY_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SNOWY_STONE_BRICK_STAIRS);
        SNOWY_BLOCKS.put(Blocks.STONE_BRICK_SLAB, ModBlocks.SNOWY_STONE_BRICK_SLAB);
        SNOWY_BLOCKS.put(Blocks.STONE_BRICK_WALL, ModBlocks.SNOWY_STONE_BRICK_WALL);

        UNSNOWY_BLOCKS.put(ModBlocks.FROSTY_GRASS, Blocks.GRASS);
        UNSNOWY_BLOCKS.put(ModBlocks.FROSTY_FERN, Blocks.FERN);
        UNSNOWY_BLOCKS.put(ModBlocks.FROSTY_GLASS, Blocks.GLASS);
        UNSNOWY_BLOCKS.put(ModBlocks.FROSTY_GLASS_PANE, Blocks.GLASS_PANE);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE, Blocks.STONE);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_STAIRS, Blocks.STONE_STAIRS);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_SLAB, Blocks.STONE_SLAB);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_WALL, ModBlocks.STONE_WALL);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_COBBLESTONE, Blocks.COBBLESTONE);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_BRICKS, Blocks.STONE_BRICKS);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
        UNSNOWY_BLOCKS.put(ModBlocks.SNOWY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);

        SANDY_BLOCKS.put(Blocks.STONE, ModBlocks.SANDY_STONE);
        SANDY_BLOCKS.put(Blocks.STONE_STAIRS, ModBlocks.SANDY_STONE_STAIRS);
        SANDY_BLOCKS.put(Blocks.STONE_SLAB, ModBlocks.SANDY_STONE_SLAB);
        SANDY_BLOCKS.put(ModBlocks.STONE_WALL, ModBlocks.SANDY_STONE_WALL);
        SANDY_BLOCKS.put(Blocks.COBBLESTONE, ModBlocks.SANDY_COBBLESTONE);
        SANDY_BLOCKS.put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SANDY_COBBLESTONE_STAIRS);
        SANDY_BLOCKS.put(Blocks.COBBLESTONE_SLAB, ModBlocks.SANDY_COBBLESTONE_SLAB);
        SANDY_BLOCKS.put(Blocks.COBBLESTONE_WALL, ModBlocks.SANDY_COBBLESTONE_WALL);
        SANDY_BLOCKS.put(Blocks.STONE_BRICKS, ModBlocks.SANDY_STONE_BRICKS);
        SANDY_BLOCKS.put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SANDY_CHISELED_STONE_BRICKS);
        SANDY_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SANDY_STONE_BRICK_STAIRS);
        SANDY_BLOCKS.put(Blocks.STONE_BRICK_SLAB, ModBlocks.SANDY_STONE_BRICK_SLAB);
        SANDY_BLOCKS.put(Blocks.STONE_BRICK_WALL, ModBlocks.SANDY_STONE_BRICK_WALL);

        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE, Blocks.STONE);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_STAIRS, Blocks.STONE_STAIRS);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_SLAB, Blocks.STONE_SLAB);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_WALL, ModBlocks.STONE_WALL);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_COBBLESTONE, Blocks.COBBLESTONE);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_BRICKS, Blocks.STONE_BRICKS);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
        UNSANDY_BLOCKS.put(ModBlocks.SANDY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);
    }

    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            BlockPos targetPos = hitResult.getBlockPos();
            BlockPos fixedPos = targetPos.offset(hitResult.getSide());
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = player.getStackInHand(hand);

            if(ImmersiveWeathering.getConfig().itemUsesConfig.cauldronWashing) {
                if (heldItem.getItem() == Items.DIRT) {
                    if (targetBlock.isOf(Blocks.WATER_CAULDRON) && (targetBlock.get(LeveledCauldronBlock.LEVEL) >= 3)) {
                        Block.dropStack(world, fixedPos, new ItemStack(Items.CLAY));
                        if (world.random.nextFloat() < 0.5f) {
                            Block.dropStack(world, fixedPos, new ItemStack(Items.GRAVEL));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            world.setBlockState(targetPos, Blocks.CAULDRON.getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.GRAVEL) {
                    if (targetBlock.isOf(Blocks.WATER_CAULDRON) && (targetBlock.get(LeveledCauldronBlock.LEVEL) >= 3)) {
                        Block.dropStack(world, fixedPos, new ItemStack(Items.FLINT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.dropStack(world, fixedPos, new ItemStack(Items.IRON_NUGGET));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            world.setBlockState(targetPos, Blocks.CAULDRON.getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.CLAY) {
                    if (targetBlock.isOf(Blocks.WATER_CAULDRON) && (targetBlock.get(LeveledCauldronBlock.LEVEL) >= 3)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.SILT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.dropStack(world, fixedPos, new ItemStack(Items.SAND));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            world.setBlockState(targetPos, Blocks.CAULDRON.getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeathering.getConfig().itemUsesConfig.pistonSliming) {
                if (heldItem.getItem() == Items.SLIME_BALL) {
                    if (targetBlock.isOf(Blocks.PISTON) && !targetBlock.get(PistonBlock.EXTENDED)) {
                        world.playSound(player, targetPos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.ITEM_SLIME, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            world.setBlockState(targetPos, Blocks.STICKY_PISTON.getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.FROST) {
                if (targetBlock.isIn(ModTags.FROSTABLE)) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_POWDER_SNOW_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, SNOWY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock).with(FrostyGrassBlock.NATURAL, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.SNOWBALL) {
                if (targetBlock.isIn(ModTags.SNOWABLE)) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_POWDER_SNOW_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, SNOWY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == ModItems.SAND_LAYER_BLOCK) {
                if (targetBlock.isIn(ModTags.SANDABLE) || ((targetBlock.isIn(ModTags.SANDY) && targetBlock.get(SandyBlock.SANDINESS) < 1))) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_SAND_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        if (targetBlock.contains(SandyBlock.SANDINESS) && targetBlock.get(SandyBlock.SANDINESS) == 0) {
                            world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(SandyBlock.SANDINESS, 1));
                        }
                        else world.setBlockState(targetPos, SANDY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock).with(SandyBlock.SANDINESS, 0));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShearsItem) {
                if(ImmersiveWeathering.getConfig().itemUsesConfig.soilShearing) {
                    if (targetBlock.contains(ModGrassBlock.FERTILE) && targetBlock.get(ModGrassBlock.FERTILE) && targetBlock.contains(SnowyBlock.SNOWY) && !targetBlock.get(SnowyBlock.SNOWY)) {
                        world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, new BlockStateParticleEffect(ParticleTypes.BLOCK, targetBlock), UniformIntProvider.create(3,5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, targetBlock.getBlock().getDefaultState().with(ModGrassBlock.FERTILE, false));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if (targetBlock.contains(BranchesBlock.LEAFY) && targetBlock.get(BranchesBlock.LEAFY)) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    var leafParticle = WeatheringHelper.getBranchLeafParticle(targetBlock).orElse(null);
                    if (leafParticle == null) return ActionResult.PASS;
                    ParticleUtil.spawnParticle(world, targetPos, leafParticle, UniformIntProvider.create(3,5));
                    Block.dropStack(world, fixedPos, new ItemStack(SHORN_LEAVES.get(targetBlock.getBlock())));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, targetBlock.getBlock().getDefaultState().with(BranchesBlock.LEAFY, false));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.contains(IvyBlock.AGE) && targetBlock.get(IvyBlock.AGE) < IvyBlock.MAX_AGE) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, new BlockStateParticleEffect(ParticleTypes.BLOCK, targetBlock), UniformIntProvider.create(3,5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(IvyBlock.AGE, IvyBlock.MAX_AGE));
                    }
                    return ActionResult.SUCCESS;
                }
                if(ImmersiveWeathering.getConfig().itemUsesConfig.azaleaShearing) {
                    if (targetBlock.isIn(ModTags.FLOWERY)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.AZALEA_FLOWERS));
                        world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.AZALEA_FLOWER, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, FLOWERY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeathering.getConfig().itemUsesConfig.mossShearing) {
                    if (targetBlock.isIn(ModTags.MOSSY)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.MOSS_CLUMP));
                        world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.MOSS, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeathering.getConfig().itemUsesConfig.pistonSliming) {
                    if (targetBlock.isOf(Blocks.STICKY_PISTON) && !targetBlock.get(PistonBlock.EXTENDED)) {
                        world.playSound(player, targetPos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.ITEM_SLIME, UniformIntProvider.create(3, 5));
                        Block.dropStack(world, fixedPos, new ItemStack(Items.SLIME_BALL));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, Blocks.PISTON.getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == Items.WATER_BUCKET) {
                if (targetBlock.isOf(ModBlocks.SILT) && (!targetBlock.get(SiltBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(SiltBlock.SOAKED, Boolean.TRUE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.FLUVISOL) && (!targetBlock.get(FluvisolBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(FluvisolBlock.SOAKED, Boolean.TRUE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.MULCH_BLOCK) && (targetBlock.get(MulchBlock.MOISTURE) == 0)) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(MulchBlock.MOISTURE, 7));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.CRACKED_MUD) && (!targetBlock.get(CrackedMudBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(CrackedMudBlock.SOAKED, Boolean.TRUE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.VERTISOL) && (!targetBlock.get(CrackedMudBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.WATER_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(CrackedMudBlock.SOAKED, Boolean.TRUE));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.LAVA_BUCKET) {
                if (targetBlock.isOf(ModBlocks.NULCH_BLOCK) && (!targetBlock.get(NulchBlock.MOLTEN))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.LAVA_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.LAVA, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(NulchBlock.MOLTEN, Boolean.TRUE));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.BUCKET) {
                if (targetBlock.isOf(ModBlocks.NULCH_BLOCK) && (targetBlock.get(NulchBlock.MOLTEN))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.LAVA, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.LAVA_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(NulchBlock.MOLTEN, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.SILT) && (targetBlock.get(SiltBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.WATER_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(SiltBlock.SOAKED, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.FLUVISOL) && (targetBlock.get(FluvisolBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.WATER_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(FluvisolBlock.SOAKED, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.MULCH_BLOCK) && (targetBlock.get(MulchBlock.MOISTURE) == 7)) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.WATER_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(MulchBlock.MOISTURE, 0));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.CRACKED_MUD) && (targetBlock.get(CrackedMudBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.WATER_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(CrackedMudBlock.SOAKED, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isOf(ModBlocks.VERTISOL) && (targetBlock.get(CrackedMudBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, Items.WATER_BUCKET.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(CrackedMudBlock.SOAKED, Boolean.FALSE));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.GLASS_BOTTLE) {
                if ((targetBlock.isOf(ModBlocks.SILT) || targetBlock.isOf(ModBlocks.FLUVISOL)) && (targetBlock.get(SiltBlock.SOAKED))) {
                    player.incrementStat(Stats.USED.getOrCreateStat(Items.GLASS_BOTTLE));
                    world.playSound(player, targetPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SPLASH, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUsage.exchangeStack(heldItem, player, ModItems.POND_WATER.getDefaultStack());
                        player.setStackInHand(hand, itemStack2);
                        world.setBlockState(targetPos, targetBlock.with(SiltBlock.SOAKED, false));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShovelItem) {
                if(ImmersiveWeathering.getConfig().itemUsesConfig.shovelExtinguishing) {
                    if (targetBlock.isOf(Blocks.CAMPFIRE) && targetBlock.get(Properties.LIT)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SOOT, UniformIntProvider.create(3, 5));
                        if(player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(CampfireBlock.LIT, false));
                        }
                        return ActionResult.SUCCESS;
                    }
                    if (targetBlock.isOf(Blocks.FIRE)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SOOT, UniformIntProvider.create(3, 5));
                        if(player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            world.setBlockState(targetPos, Blocks.AIR.getDefaultState());
                        }
                        return ActionResult.SUCCESS;
                    }
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && targetBlock.get(CharredBlock.SMOLDERING)) {
                        Block.dropStack(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SOOT, UniformIntProvider.create(3, 5));
                        if(player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(CharredBlock.SMOLDERING, false));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if ((targetBlock.isOf(ModBlocks.HUMUS) || targetBlock.isOf(ModBlocks.FLUVISOL) || targetBlock.isOf(ModBlocks.VERTISOL) || targetBlock.isOf(ModBlocks.CRYOSOL)) && !targetBlock.get(Properties.SNOWY)) {
                    world.playSound(player, targetPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, Blocks.DIRT_PATH.getDefaultState());
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isIn(ModTags.SANDY) && targetBlock.get(SandyBlock.SANDINESS) == 0) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    Block.dropStack(world, fixedPos, new ItemStack(ModItems.SAND_LAYER_BLOCK));
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, UNSANDY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isIn(ModTags.SANDY) && targetBlock.get(SandyBlock.SANDINESS) == 1) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    Block.dropStack(world, fixedPos, new ItemStack(ModItems.SAND_LAYER_BLOCK));
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(SandyBlock.SANDINESS, 0));
                    }
                    return ActionResult.SUCCESS;
                }
                if (targetBlock.isIn(ModTags.FROSTY) || targetBlock.isIn(ModTags.SNOWY)) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_POWDER_SNOW_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (!targetBlock.isIn(ModTags.FROSTY)) {
                        Block.dropStack(world, fixedPos, new ItemStack(Items.SNOWBALL));
                    }
                    ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SNOWFLAKE, UniformIntProvider.create(3, 5));
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, UNSNOWY_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if(ImmersiveWeathering.getConfig().itemUsesConfig.azaleaShearing) {
                if (heldItem.getItem() == ModItems.AZALEA_FLOWERS) {
                    if (targetBlock.isIn(ModTags.FLOWERABLE)) {
                        world.playSound(player, targetPos, SoundEvents.BLOCK_FLOWERING_AZALEA_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.AZALEA_FLOWER, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                                if (targetBlock.isOf(shorn)) {
                                    world.setBlockState(targetPos, flowery.getStateWithProperties(targetBlock));
                                }
                            });
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeathering.getConfig().itemUsesConfig.mossShearing) {
                if (heldItem.getItem() == ModItems.MOSS_CLUMP) {
                    if (targetBlock.isIn(ModTags.MOSSABLE)) {
                        world.playSound(player, targetPos, SoundEvents.BLOCK_MOSS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.decrement(1);
                            CLEANED_BLOCKS.forEach((mossy, clean) -> {
                                if (targetBlock.isOf(clean)) {
                                    world.setBlockState(targetPos, mossy.getStateWithProperties(targetBlock));
                                }
                            });
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                if(ImmersiveWeathering.getConfig().itemUsesConfig.charredBlockIgniting) {
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && !targetBlock.get(CharredBlock.SMOLDERING)) {
                        world.playSound(player, targetPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.EMBERSPARK, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, targetBlock.getBlock().getStateWithProperties(targetBlock).with(CharredBlock.SMOLDERING, true));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeathering.getConfig().itemUsesConfig.mossBurning) {
                    if (targetBlock.isIn(ModTags.MOSSY)) {
                        world.playSound(player, targetPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMALL_FLAME, UniformIntProvider.create(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }

            if(ImmersiveWeathering.getConfig().itemUsesConfig.spongeRusting) {
                if (heldItem.getItem() == Items.WET_SPONGE) {
                    if (targetBlock.isIn(ModTags.RUSTABLE)) {
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SCRAPE_RUST, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            world.setBlockState(targetPos, RUSTED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.STEEL_WOOL) {
                if(targetBlock.isIn(ModTags.COPPER)) {
                    world.playSound(player, targetPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.SCRAPE, UniformIntProvider.create(3,5));
                    if(player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        Oxidizable.getDecreasedOxidationState(targetBlock).ifPresent(o-> world.setBlockState(targetPos, o));
                    }
                    return ActionResult.SUCCESS;
                }
                if(targetBlock.isIn(ModTags.EXPOSED_IRON) || targetBlock.isIn(ModTags.WEATHERED_IRON) || targetBlock.isIn(ModTags.RUSTED_IRON)) {
                    world.playSound(player, targetPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ModParticles.SCRAPE_RUST, UniformIntProvider.create(3,5));
                    if(player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (targetBlock.isOf(rusty)) {
                                world.setBlockState(targetPos, clean.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    return ActionResult.SUCCESS;
                }
                if(targetBlock.isIn(ModTags.WAXED_BLOCKS)) {
                    world.playSound(player, targetPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    ParticleUtil.spawnParticle(world, targetPos, ParticleTypes.WAX_OFF, UniformIntProvider.create(3,5));
                    if(player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                        world.setBlockState(targetPos, UNWAXED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if(ImmersiveWeathering.getConfig().itemUsesConfig.pickaxeCracking) {
                if (heldItem.getItem() instanceof PickaxeItem) {
                    if (targetBlock.isIn(ModTags.CRACKABLE)) {
                        Block.dropStack(world, fixedPos, new ItemStack(DROPPED_BRICKS.get(targetBlock.getBlock())));
                        world.playSound(player, targetPos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, new BlockStateParticleEffect(ParticleTypes.BLOCK, targetBlock), UniformIntProvider.create(3,5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, CRACKED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if (BRICK_REPAIR.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(BRICK_REPAIR.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (BRICK_REPAIR_SLABS.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(BRICK_REPAIR_SLABS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_SLABS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (BRICK_REPAIR_STAIRS.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(BRICK_REPAIR_STAIRS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_STAIRS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (BRICK_REPAIR_WALLS.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(BRICK_REPAIR_WALLS.get(heldItem.getItem())))) {
                    Block fixedBlock = BRICK_REPAIR_WALLS.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return ActionResult.SUCCESS;
                }
                if (CHISELED_BRICK_REPAIR.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(CHISELED_BRICK_REPAIR.get(heldItem.getItem())))) {
                    Block fixedBlock = CHISELED_BRICK_REPAIR.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock).with(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof AxeItem) {
                if(ImmersiveWeathering.getConfig().itemUsesConfig.axeStripping) {
                    if (targetBlock.isIn(ModTags.RAW_LOGS)) {
                        Block.dropStack(world, fixedPos, new ItemStack(DROPPED_BARK.get(targetBlock.getBlock())));
                        world.playSound(player, targetPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        var barkParticle = WeatheringHelper.getBarkParticle(targetBlock).orElse(null);
                        ParticleUtil.spawnParticle(world, targetPos, barkParticle, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            world.setBlockState(targetPos, STRIPPED_BLOCKS.get(targetBlock.getBlock()).getStateWithProperties(targetBlock));
                        }
                        return ActionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeathering.getConfig().itemUsesConfig.axeScraping) {
                    if (targetBlock.isIn(ModTags.WEATHERED_IRON) || targetBlock.isIn(ModTags.RUSTED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        world.playSound(player, targetPos, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SCRAPE_RUST, UniformIntProvider.create(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        }
                        return ActionResult.SUCCESS;
                    }
                    else if (targetBlock.isIn(ModTags.EXPOSED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        ParticleUtil.spawnParticle(world, targetPos, ModParticles.SCRAPE_RUST, UniformIntProvider.create(3, 5));
                        if (player instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.damage(1, net.minecraft.util.math.random.Random.create(), null);
                            RUSTED_BLOCKS.forEach((clean, rusty) -> {
                                if (targetBlock.isOf(rusty)) {
                                    world.setBlockState(targetPos, clean.getStateWithProperties(targetBlock));
                                }
                            });
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeathering.getConfig().itemUsesConfig.axeStripping) {
                if (UNSTRIP_LOG.containsKey(heldItem.getItem()) && targetBlock.isOf(STRIPPED_BLOCKS.get(UNSTRIP_LOG.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_LOG.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
                if (UNSTRIP_WOOD.containsKey(heldItem.getItem()) && targetBlock.isOf(STRIPPED_BLOCKS.get(UNSTRIP_WOOD.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_WOOD.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.decrement(1);
                        world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}


