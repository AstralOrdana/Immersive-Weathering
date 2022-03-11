package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.blocks.crackable.CrackableWallBlock;
import com.ordana.immersive_weathering.registry.blocks.mossable.*;
import com.ordana.immersive_weathering.registry.blocks.rustable.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.ICE).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS).nonOpaque().dynamicBounds());

    public static final Block OAK_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.OAK_LEAF));
    public static final Block BIRCH_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.BIRCH_LEAF));
    public static final Block SPRUCE_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, true, List.of(ModParticles.SPRUCE_LEAF));
    public static final Block JUNGLE_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.JUNGLE_LEAF));
    public static final Block ACACIA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.ACACIA_LEAF));
    public static final Block DARK_OAK_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.DARK_OAK_LEAF));
    public static final Block AZALEA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), false, false, List.of(ModParticles.AZALEA_LEAF));
    public static final Block FLOWERING_AZALEA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).breakInstantly().ticksRandomly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), true, false, List.of(ModParticles.AZALEA_LEAF, ModParticles.AZALEA_FLOWER));
    public static final Block AZALEA_FLOWER_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.FLOWERING_AZALEA).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision(ModBlocks::never), true, false, List.of(ModParticles.AZALEA_FLOWER));

    public static final Block ROCK_LICHEN = new RockLichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.LICHEN_GREEN).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN));
    public static final Block FOREST_LICHEN = new ForestLichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.LICHEN_GREEN).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN));
    public static final Block CINDER_LICHEN = new CinderLichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.LICHEN_GREEN).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN));

    public static final Block WEEDS = new WeedsBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block ASH_BLOCK = new AshBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).breakInstantly().sounds(BlockSoundGroup.SNOW).luminance(createLightLevelFromLitBlockState(6)).ticksRandomly());
    public static final Block SOOT = new SootBlock(FabricBlockSettings.of(Material.REPLACEABLE_UNDERWATER_PLANT, MapColor.BLACK).noCollision().breakInstantly().sounds(BlockSoundGroup.SNOW).luminance(createLightLevelFromLitBlockState(5)).ticksRandomly());

    public static final Block MOSSY_BRICKS = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, (Supplier<Block>) MOSSY_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_WALL = new MossyWallBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block MOSSY_STONE = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, (Supplier<Block>) MOSSY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block CRACKED_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_STAIRS = new ModStairs(CRACKED_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block CRACKED_PRISMARINE_BRICKS = new  Block(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_PRISMARINE_BRICK_STAIRS = new ModStairs(CRACKED_PRISMARINE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_PRISMARINE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_PRISMARINE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block PRISMARINE_BRICK_WALL = new CrackableWallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));

    public static final Block CRACKED_END_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3.0F, 9.0F));
    public static final Block CRACKED_END_STONE_BRICK_STAIRS = new ModStairs(CRACKED_END_STONE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3f, 9f));
    public static final Block CRACKED_END_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3f, 9f));
    public static final Block CRACKED_END_STONE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).requiresTool().strength(3f, 9f));

    public static final Block CRACKED_STONE_BRICK_STAIRS = new ModStairs(Blocks.CRACKED_STONE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_STONE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F, 6.0F));

    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = new ModStairs(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(1.5F, 6.0F));

    public static final Block CRACKED_NETHER_BRICK_STAIRS = new ModStairs(Blocks.CRACKED_NETHER_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS));
    public static final Block CRACKED_NETHER_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS));
    public static final Block CRACKED_NETHER_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS));

    public static final Block CRACKED_DEEPSLATE_BRICK_STAIRS = new ModStairs(Blocks.CRACKED_DEEPSLATE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_BRICKS));
    public static final Block CRACKED_DEEPSLATE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_BRICKS));
    public static final Block CRACKED_DEEPSLATE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_BRICKS));

    public static final Block CRACKED_DEEPSLATE_TILE_STAIRS = new ModStairs(Blocks.CRACKED_DEEPSLATE_TILES.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_TILES));
    public static final Block CRACKED_DEEPSLATE_TILE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_TILES));
    public static final Block CRACKED_DEEPSLATE_TILE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.DEEPSLATE_TILES));

    public static final Block MULCH_BLOCK = new MulchBlock(FabricBlockSettings.of(Material.WOOD).strength(1f, 1f).sounds(BlockSoundGroup.WOOD).ticksRandomly());
    public static final Block NULCH_BLOCK = new NulchBlock(FabricBlockSettings.of(Material.NETHER_WOOD).strength(1f, 1f).sounds(BlockSoundGroup.NETHER_STEM).luminance(createLightLevelFromMoltenBlockState(10)).ticksRandomly());
    public static final Block MULCH = new MulchCarpetBlock(FabricBlockSettings.of(Material.WOOD).strength(1f, 1f).sounds(BlockSoundGroup.WOOD), List.of(ModParticles.MULCH));
    public static final Block NULCH = new MulchCarpetBlock(FabricBlockSettings.of(Material.NETHER_WOOD).strength(1f, 1f).sounds(BlockSoundGroup.NETHER_STEM), List.of(ModParticles.NULCH));


    //cut iron
    public static final Block CUT_IRON = new RustableBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON = new RustableBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON = new RustableBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON = new RustableBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block CUT_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block CUT_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_CUT_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_CUT_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_CUT_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_CUT_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_CUT_IRON_STAIRS = new ModStairs(WAXED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_CUT_IRON_STAIRS = new ModStairs(WAXED_EXPOSED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_CUT_IRON_STAIRS = new ModStairs(WAXED_WEATHERED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_CUT_IRON_STAIRS = new ModStairs(WAXED_RUSTED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_CUT_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_CUT_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_CUT_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_CUT_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));


    //plate iron
    public static final Block PLATE_IRON = new RustableBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON = new RustableBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON = new RustableBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON = new RustableBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block PLATE_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON_STAIRS = new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block PLATE_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON_SLAB = new RustableSlabBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_PLATE_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_PLATE_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_PLATE_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_PLATE_IRON = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_PLATE_IRON_STAIRS = new ModStairs(WAXED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_PLATE_IRON_STAIRS = new ModStairs(WAXED_EXPOSED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_PLATE_IRON_STAIRS = new ModStairs(WAXED_WEATHERED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_PLATE_IRON_STAIRS = new ModStairs(WAXED_RUSTED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block WAXED_PLATE_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_EXPOSED_PLATE_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_WEATHERED_PLATE_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WAXED_RUSTED_PLATE_IRON_SLAB = new SlabBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block EXPOSED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block EXPOSED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block EXPOSED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());



    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(Properties.LIT) ? litLevel : 0;
    }

    private static ToIntFunction<BlockState> createLightLevelFromMoltenBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(NulchBlock.MOLTEN) ? litLevel : 0;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static void registerBlocks() {

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rock_lichen"), ROCK_LICHEN);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "forest_lichen"), FOREST_LICHEN);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cinder_lichen"), CINDER_LICHEN);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "icicle"), ICICLE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "oak_leaf_pile"), OAK_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "spruce_leaf_pile"), SPRUCE_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "birch_leaf_pile"), BIRCH_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "jungle_leaf_pile"), JUNGLE_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "acacia_leaf_pile"), ACACIA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "dark_oak_leaf_pile"), DARK_OAK_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "azalea_leaf_pile"), AZALEA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "flowering_azalea_leaf_pile"), FLOWERING_AZALEA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "azalea_flower_pile"), AZALEA_FLOWER_PILE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mulch_block"), MULCH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "nulch_block"), NULCH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mulch"), MULCH);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "nulch"), NULCH);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weeds"), WEEDS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "ash_block"), ASH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "soot"), SOOT);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);


        //cracked blocks
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_bricks"), CRACKED_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_stairs"), CRACKED_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_slab"), CRACKED_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_wall"), CRACKED_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_bricks"), CRACKED_PRISMARINE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_stairs"), CRACKED_PRISMARINE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_slab"), CRACKED_PRISMARINE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_wall"), CRACKED_PRISMARINE_BRICK_WALL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "prismarine_brick_wall"), PRISMARINE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_end_stone_bricks"), CRACKED_END_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_end_stone_brick_stairs"), CRACKED_END_STONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_end_stone_brick_slab"), CRACKED_END_STONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_end_stone_brick_wall"), CRACKED_END_STONE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_stairs"), CRACKED_STONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_slab"), CRACKED_STONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_brick_wall"), CRACKED_STONE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_stairs"), CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_slab"), CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_polished_blackstone_brick_wall"), CRACKED_POLISHED_BLACKSTONE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_stairs"), CRACKED_NETHER_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_slab"), CRACKED_NETHER_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_nether_brick_wall"), CRACKED_NETHER_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_stairs"), CRACKED_DEEPSLATE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_slab"), CRACKED_DEEPSLATE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_brick_wall"), CRACKED_DEEPSLATE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_stairs"), CRACKED_DEEPSLATE_TILE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_slab"), CRACKED_DEEPSLATE_TILE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_deepslate_tile_wall"), CRACKED_DEEPSLATE_TILE_WALL);


        //cut iron
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron"), CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron"), EXPOSED_CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron"), WEATHERED_CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron"), RUSTED_CUT_IRON);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron_stairs"), CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_stairs"), EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_stairs"), WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_stairs"), RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cut_iron_slab"), CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_cut_iron_slab"), EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_cut_iron_slab"), WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_cut_iron_slab"), RUSTED_CUT_IRON_SLAB);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron"), WAXED_CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron"), WAXED_EXPOSED_CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron"), WAXED_WEATHERED_CUT_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron"), WAXED_RUSTED_CUT_IRON);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_stairs"), WAXED_CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_stairs"), WAXED_EXPOSED_CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_stairs"), WAXED_WEATHERED_CUT_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_stairs"), WAXED_RUSTED_CUT_IRON_STAIRS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_cut_iron_slab"), WAXED_CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_cut_iron_slab"), WAXED_EXPOSED_CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_cut_iron_slab"), WAXED_WEATHERED_CUT_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_cut_iron_slab"), WAXED_RUSTED_CUT_IRON_SLAB);

        //plate iron
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron"), PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron"), EXPOSED_PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron"), WEATHERED_PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron"), RUSTED_PLATE_IRON);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron_stairs"), PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_stairs"), EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_stairs"), WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_stairs"), RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "plate_iron_slab"), PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_plate_iron_slab"), EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_plate_iron_slab"), WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_plate_iron_slab"), RUSTED_PLATE_IRON_SLAB);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron"), WAXED_PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron"), WAXED_EXPOSED_PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron"), WAXED_WEATHERED_PLATE_IRON);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron"), WAXED_RUSTED_PLATE_IRON);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_stairs"), WAXED_PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_stairs"), WAXED_EXPOSED_PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_stairs"), WAXED_WEATHERED_PLATE_IRON_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_stairs"), WAXED_RUSTED_PLATE_IRON_STAIRS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_plate_iron_slab"), WAXED_PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_plate_iron_slab"), WAXED_EXPOSED_PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_plate_iron_slab"), WAXED_WEATHERED_PLATE_IRON_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_plate_iron_slab"), WAXED_RUSTED_PLATE_IRON_SLAB);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_door"), EXPOSED_IRON_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_door"), WEATHERED_IRON_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_door"), RUSTED_IRON_DOOR);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_trapdoor"), EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_trapdoor"), WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_trapdoor"), RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron_bars"), EXPOSED_IRON_BARS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron_bars"), WEATHERED_IRON_BARS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rusted_iron_bars"), RUSTED_IRON_BARS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_door"), WAXED_IRON_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_door"), WAXED_EXPOSED_IRON_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_door"), WAXED_WEATHERED_IRON_DOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_door"), WAXED_RUSTED_IRON_DOOR);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_trapdoor"), WAXED_IRON_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_trapdoor"), WAXED_EXPOSED_IRON_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_trapdoor"), WAXED_WEATHERED_IRON_TRAPDOOR);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_trapdoor"), WAXED_RUSTED_IRON_TRAPDOOR);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_iron_bars"), WAXED_IRON_BARS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_exposed_iron_bars"), WAXED_EXPOSED_IRON_BARS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_weathered_iron_bars"), WAXED_WEATHERED_IRON_BARS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "waxed_rusted_iron_bars"), WAXED_RUSTED_IRON_BARS);

    }
}
