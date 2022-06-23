package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.blocks.charred.*;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.registry.blocks.crackable.CrackableWallBlock;
import com.ordana.immersive_weathering.registry.blocks.mossable.*;
import com.ordana.immersive_weathering.registry.blocks.rotten.*;
import com.ordana.immersive_weathering.registry.blocks.rustable.*;
import com.ordana.immersive_weathering.registry.blocks.sandy.SandyBlock;
import com.ordana.immersive_weathering.registry.blocks.sandy.SandySlabBlock;
import com.ordana.immersive_weathering.registry.blocks.sandy.SandyStairsBlock;
import com.ordana.immersive_weathering.registry.blocks.sandy.SandyWallBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.List;
import java.util.function.ToIntFunction;

public class ModBlocks {

    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.ICE).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS).nonOpaque().dynamicBounds().requiresTool());
    public static final Block FROST = new FrostBlock(FabricBlockSettings.of(Material.SNOW_LAYER).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.POWDER_SNOW).nonOpaque().noCollision());
    public static final Block FROSTY_GRASS = new FrostyGrassBlock(FabricBlockSettings.of(Material.SNOW_LAYER).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.POWDER_SNOW).nonOpaque().dynamicBounds().noCollision());
    public static final Block FROSTY_FERN = new FrostyGrassBlock(FabricBlockSettings.of(Material.SNOW_LAYER).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.POWDER_SNOW).nonOpaque().dynamicBounds().noCollision());
    public static final Block FROSTY_GLASS = new FrostyGlassBlock(FabricBlockSettings.of(Material.GLASS).ticksRandomly().strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(ModBlocks::never).solidBlock(ModBlocks::never).suffocates(ModBlocks::never).blockVision(ModBlocks::never));
    public static final Block FROSTY_GLASS_PANE = new FrostyGlassPaneBlock(FabricBlockSettings.of(Material.GLASS).ticksRandomly().strength(0.3f).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(ModBlocks::never).suffocates(ModBlocks::never).blockVision(ModBlocks::never));
    public static final Block THIN_ICE = new ThinIceBlock(FabricBlockSettings.of(Material.ICE).ticksRandomly().strength(0.3f).slipperiness(0.98f).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(ModBlocks::never).suffocates(ModBlocks::never).blockVision(ModBlocks::never));

    public static final Block OAK_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, true, List.of(ModParticles.OAK_LEAF));
    public static final Block BIRCH_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, true, List.of(ModParticles.BIRCH_LEAF));
    public static final Block SPRUCE_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, true, false, List.of(ModParticles.SPRUCE_LEAF));
    public static final Block JUNGLE_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, true, List.of(ModParticles.JUNGLE_LEAF));
    public static final Block ACACIA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, false, List.of(ModParticles.ACACIA_LEAF));
    public static final Block DARK_OAK_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, true, List.of(ModParticles.DARK_OAK_LEAF));
    public static final Block MANGROVE_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, true, List.of(ModParticles.MANGROVE_LEAF));
    public static final Block AZALEA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), false, false, false, List.of(ModParticles.AZALEA_LEAF));
    public static final Block FLOWERING_AZALEA_LEAF_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).breakInstantly().ticksRandomly().sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), true, false, false, List.of(ModParticles.AZALEA_LEAF, ModParticles.AZALEA_FLOWER));
    public static final Block AZALEA_FLOWER_PILE = new LeafPileBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.FLOWERING_AZALEA).nonOpaque().allowsSpawning(ModBlocks::canSpawnOnLeaves).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(LeafPileBlock.LAYERS) >= 8), true, false, false, List.of(ModParticles.AZALEA_FLOWER));

    public static final Block OAK_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.OAK_TAN).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block BIRCH_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.PALE_YELLOW).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block SPRUCE_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.SPRUCE_BROWN).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block JUNGLE_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.BROWN).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block ACACIA_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.ORANGE).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block DARK_OAK_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.BROWN).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());
    public static final Block MANGROVE_BRANCHES = new BranchesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.DARK_RED).ticksRandomly().strength(1f).sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque());

    public static final Block MOSS = new MossMultifaceBlock(FabricBlockSettings.of(Material.MOSS_BLOCK).ticksRandomly().breakInstantly().sounds(BlockSoundGroup.MOSS_BLOCK).nonOpaque().noCollision());
    public static final Block IVY = new IvyBlock(FabricBlockSettings.of(Material.PLANT).noCollision().strength(0.2f).sounds(BlockSoundGroup.AZALEA_LEAVES));
    public static final Block WEEDS = new WeedsBlock(FabricBlockSettings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block MULCH_BLOCK = new MulchBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.DIRT_BROWN).strength(1f, 1f).sounds(BlockSoundGroup.ROOTED_DIRT).ticksRandomly());
    public static final Block NULCH_BLOCK = new NulchBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.DARK_CRIMSON).strength(1f, 1f).sounds(BlockSoundGroup.WART_BLOCK).ticksRandomly().luminance(createLightLevelFromMoltenBlockState(10)).emissiveLighting(ModBlocks::emissiveIfMolten));

    public static final Block HUMUS = new SoilBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DARK_GREEN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL).ticksRandomly());
    public static final Block FLUVISOL = new FluvisolBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DEEPSLATE_GRAY).strength(0.5F).sounds(BlockSoundGroup.WART_BLOCK).ticksRandomly());
    public static final Block SILT = new SiltBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DEEPSLATE_GRAY).strength(0.5F).sounds(BlockSoundGroup.WART_BLOCK).ticksRandomly());
    public static final Block VERTISOL = new CrackedMudBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.BASALT).ticksRandomly());
    public static final Block CRACKED_MUD = new CrackedMudBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(2.5F).sounds(BlockSoundGroup.BASALT).ticksRandomly());
    public static final Block CRYOSOL = new SoilBlock(FabricBlockSettings.of(Material.SOIL, MapColor.WHITE).strength(0.5F).sounds(BlockSoundGroup.TUFF).ticksRandomly());
    public static final Block PERMAFROST = new PermafrostBlock(FabricBlockSettings.of(Material.DENSE_ICE, MapColor.LIGHT_BLUE_GRAY).strength(3F).slipperiness(1F).sounds(BlockSoundGroup.TUFF).ticksRandomly());

    public static final Block ROOTED_GRASS_BLOCK = new RootedGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.5F).sounds(BlockSoundGroup.ROOTED_DIRT));

    public static final Block FULGURITE = new FulguriteBlock(7, 3, FabricBlockSettings.of(Material.GLASS).breakInstantly().sounds(BlockSoundGroup.GLASS).nonOpaque().dynamicBounds().requiresTool().luminance((state) -> 5));
    public static final Block VITRIFIED_SAND = new GlassBlock(FabricBlockSettings.of(Material.GLASS, MapColor.PALE_YELLOW).strength(2f, 6f).sounds(BlockSoundGroup.TUFF).nonOpaque().requiresTool());
    public static final Block QUICKSAND = new QuicksandBlock(FabricBlockSettings.of(ModMaterials.QUICKSAND, MapColor.PALE_YELLOW).strength(10f).sounds(BlockSoundGroup.SAND).dynamicBounds().suffocates(ModBlocks::always).blockVision(ModBlocks::always));
    public static final Block SAND_LAYER_BLOCK = new SandLayerBlock(Blocks.SAND.getDefaultState(),14406560, FabricBlockSettings.of(Material.SNOW_LAYER, MapColor.PALE_YELLOW).strength(0.5f).sounds(BlockSoundGroup.SAND).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(SandLayerBlock.LAYERS) >= 8).nonOpaque());
    public static final Block RED_SAND_LAYER_BLOCK = new SandLayerBlock(Blocks.RED_SAND.getDefaultState(),11098145, FabricBlockSettings.of(Material.SNOW_LAYER, MapColor.ORANGE).strength(0.5f).sounds(BlockSoundGroup.SAND).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(SandLayerBlock.LAYERS) >= 8).nonOpaque());

    public static final Block ASH_LAYER_BLOCK = new AshLayerBlock(FabricBlockSettings.of(Material.SNOW_LAYER, MapColor.BLACK).breakInstantly().sounds(BlockSoundGroup.SNOW).suffocates(ModBlocks::never).blockVision((blockState, blockView, blockPos) -> blockState.get(AshLayerBlock.LAYERS) >= 8).nonOpaque());
    public static final Block ASH_BLOCK = new AshBlock(FabricBlockSettings.of(Material.SNOW_BLOCK, MapColor.BLACK).breakInstantly().sounds(BlockSoundGroup.SNOW));
    public static final Block SOOT = new SootBlock(FabricBlockSettings.of(Material.SNOW_LAYER, MapColor.BLACK).noCollision().breakInstantly().sounds(BlockSoundGroup.SNOW).ticksRandomly());

    public static final Block CHARRED_LOG = new CharredPillarBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));
    public static final Block CHARRED_PLANKS = new CharredBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));
    public static final Block CHARRED_SLAB = new CharredSlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));
    public static final Block CHARRED_STAIRS = new CharredStairsBlock(CHARRED_PLANKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));
    public static final Block CHARRED_FENCE = new CharredFenceBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));
    public static final Block CHARRED_FENCE_GATE = new CharredFenceGateBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK).strength(1.5f, 0.5f).sounds(BlockSoundGroup.BASALT).luminance(createLightLevelFromSmolderingBlockState(7)).ticksRandomly().emissiveLighting(ModBlocks::emissiveIfSmoldering));

    public static final Block ROTTEN_LOG = new RottenPillarBlock(FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());
    public static final Block ROTTEN_PLANKS = new RottenBlock(FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());
    public static final Block ROTTEN_SLAB = new RottenSlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());
    public static final Block ROTTEN_STAIRS = new RottenStairsBlock(ROTTEN_PLANKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());
    public static final Block ROTTEN_FENCE = new RottenFenceBlock(FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());
    public static final Block ROTTEN_FENCE_GATE = new RottenFenceGateBlock(FabricBlockSettings.of(Material.STONE, MapColor.LICHEN_GREEN).strength(1.5f, 0.5f).sounds(BlockSoundGroup.NETHER_STEM).ticksRandomly());

    public static final Block SNOW_BRICKS = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).requiresTool().strength(0.2F).sounds(BlockSoundGroup.SNOW));
    public static final Block SNOW_BRICK_STAIRS = new ModStairs(SNOW_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.SNOW_BLOCK).requiresTool().strength(0.2F).sounds(BlockSoundGroup.SNOW));
    public static final Block SNOW_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.SNOW_BLOCK).requiresTool().strength(0.2F).sounds(BlockSoundGroup.SNOW));
    public static final Block SNOW_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.SNOW_BLOCK).requiresTool().strength(0.2F).sounds(BlockSoundGroup.SNOW));

    public static final Block SNOWY_STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_STAIRS = new ModStairs(SNOWY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block SNOWY_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_COBBLESTONE_STAIRS = new ModStairs(SNOWY_COBBLESTONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_COBBLESTONE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_COBBLESTONE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block SNOWY_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_CHISELED_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_BRICK_STAIRS = new ModStairs(SNOWY_STONE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SNOWY_STONE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block SANDY_STONE = new SandyBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_STAIRS = new SandyStairsBlock(SANDY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_SLAB = new SandySlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_WALL = new SandyWallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block SANDY_COBBLESTONE = new SandyBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_COBBLESTONE_STAIRS = new SandyStairsBlock(SANDY_COBBLESTONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_COBBLESTONE_SLAB = new SandySlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_COBBLESTONE_WALL = new SandyWallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block SANDY_STONE_BRICKS = new SandyBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_CHISELED_STONE_BRICKS = new SandyBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_BRICK_STAIRS = new SandyStairsBlock(SANDY_STONE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_BRICK_SLAB = new SandySlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block SANDY_STONE_BRICK_WALL = new SandyWallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block MOSSY_BRICKS = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_WALL = new MossyWallBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block MOSSY_STONE = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_WALL = new MossyWallBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block STONE_WALL = new CrackableMossableWallBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_CHISELED_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_CHISELED_STONE_BRICKS = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block CRACKED_STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_STONE_STAIRS = new ModStairs(CRACKED_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_STONE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_STONE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block CRACKED_BRICKS = new Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_STAIRS = new ModStairs(CRACKED_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block CRACKED_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block CRACKED_PRISMARINE_BRICKS = new  Block(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5F, 6.0F));
    public static final Block CRACKED_PRISMARINE_BRICK_STAIRS = new ModStairs(CRACKED_PRISMARINE_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_PRISMARINE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block CRACKED_PRISMARINE_BRICK_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block PRISMARINE_BRICK_WALL = new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block DARK_PRISMARINE_WALL = new WallBlock(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5f, 6f));
    public static final Block CHISELED_PRISMARINE_BRICKS = new  Block(FabricBlockSettings.of(Material.STONE, MapColor.DIAMOND_BLUE).requiresTool().strength(1.5F, 6.0F));

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

    public static final Block EXPOSED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WEATHERED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block RUSTED_IRON_DOOR = new RustableDoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static final Block EXPOSED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WEATHERED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block RUSTED_IRON_TRAPDOOR = new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static final Block EXPOSED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WEATHERED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block RUSTED_IRON_BARS = new RustableBarsBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static final Block WAXED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_DOOR = new WaxedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static final Block WAXED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_TRAPDOOR = new WaxedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    public static final Block WAXED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.METAL).nonOpaque());

    private static ToIntFunction<BlockState> createLightLevelFromSmolderingBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(CharredBlock.SMOLDERING) ? litLevel : 0;
    }

    private static ToIntFunction<BlockState> createLightLevelFromMoltenBlockState(int litLevel) {
        return (state) -> (Boolean)state.get(NulchBlock.MOLTEN) ? litLevel : 0;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean emissiveIfSmoldering(BlockState state, BlockView world, BlockPos pos) {
        return state.get(CharredBlock.SMOLDERING);
    }

    private static boolean emissiveIfMolten(BlockState state, BlockView world, BlockPos pos) {
        return state.get(NulchBlock.MOLTEN);
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static void registerBlocks() {

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "icicle"), ICICLE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "frost"), FROST);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "frosty_grass"), FROSTY_GRASS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "frosty_fern"), FROSTY_FERN);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "frosty_glass"), FROSTY_GLASS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "frosty_glass_pane"), FROSTY_GLASS_PANE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "thin_ice"), THIN_ICE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "vitrified_sand"), VITRIFIED_SAND);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "fulgurite"), FULGURITE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "quicksand"), QUICKSAND);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "oak_leaf_pile"), OAK_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "spruce_leaf_pile"), SPRUCE_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "birch_leaf_pile"), BIRCH_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "jungle_leaf_pile"), JUNGLE_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "acacia_leaf_pile"), ACACIA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "dark_oak_leaf_pile"), DARK_OAK_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mangrove_leaf_pile"), MANGROVE_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "azalea_leaf_pile"), AZALEA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "flowering_azalea_leaf_pile"), FLOWERING_AZALEA_LEAF_PILE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "azalea_flower_pile"), AZALEA_FLOWER_PILE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "oak_branches"), OAK_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "spruce_branches"), SPRUCE_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "birch_branches"), BIRCH_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "jungle_branches"), JUNGLE_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "acacia_branches"), ACACIA_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "dark_oak_branches"), DARK_OAK_BRANCHES);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mangrove_branches"), MANGROVE_BRANCHES);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "humus"), HUMUS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "fluvisol"), FLUVISOL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "silt"), SILT);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "vertisol"), VERTISOL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_mud"), CRACKED_MUD);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cryosol"), CRYOSOL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "permafrost"), PERMAFROST);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mulch_block"), MULCH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "nulch_block"), NULCH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rooted_grass_block"), ROOTED_GRASS_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "ivy"), IVY);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "weeds"), WEEDS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "moss"), MOSS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sand_layer_block"), SAND_LAYER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "red_sand_layer_block"), RED_SAND_LAYER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "ash_layer_block"), ASH_LAYER_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "ash_block"), ASH_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "soot"), SOOT);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_log"), CHARRED_LOG);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_planks"), CHARRED_PLANKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_slab"), CHARRED_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_stairs"), CHARRED_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_fence"), CHARRED_FENCE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "charred_fence_gate"), CHARRED_FENCE_GATE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_log"), ROTTEN_LOG);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_planks"), ROTTEN_PLANKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_slab"), ROTTEN_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_stairs"), ROTTEN_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_fence"), ROTTEN_FENCE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "rotten_fence_gate"), ROTTEN_FENCE_GATE);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snow_bricks"), SNOW_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snow_brick_stairs"), SNOW_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snow_brick_slab"), SNOW_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snow_brick_wall"), SNOW_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);


        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "stone_wall"), STONE_WALL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_chiseled_stone_bricks"), MOSSY_CHISELED_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_chiseled_stone_bricks"), CRACKED_CHISELED_STONE_BRICKS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_wall"), MOSSY_STONE_WALL);


        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone"), SNOWY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_stairs"), SNOWY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_slab"), SNOWY_STONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_wall"), SNOWY_STONE_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_cobblestone"), SNOWY_COBBLESTONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_cobblestone_stairs"), SNOWY_COBBLESTONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_cobblestone_slab"), SNOWY_COBBLESTONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_cobblestone_wall"), SNOWY_COBBLESTONE_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_bricks"), SNOWY_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_chiseled_stone_bricks"), SNOWY_CHISELED_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_brick_stairs"), SNOWY_STONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_brick_slab"), SNOWY_STONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "snowy_stone_brick_wall"), SNOWY_STONE_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone"), SANDY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_stairs"), SANDY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_slab"), SANDY_STONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_wall"), SANDY_STONE_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_cobblestone"), SANDY_COBBLESTONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_cobblestone_stairs"), SANDY_COBBLESTONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_cobblestone_slab"), SANDY_COBBLESTONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_cobblestone_wall"), SANDY_COBBLESTONE_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_bricks"), SANDY_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_chiseled_stone_bricks"), SANDY_CHISELED_STONE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_brick_stairs"), SANDY_STONE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_brick_slab"), SANDY_STONE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "sandy_stone_brick_wall"), SANDY_STONE_BRICK_WALL);

        //cracked blocks

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone"), CRACKED_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_stairs"), CRACKED_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_slab"), CRACKED_STONE_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_stone_wall"), CRACKED_STONE_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_bricks"), CRACKED_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_stairs"), CRACKED_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_slab"), CRACKED_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_brick_wall"), CRACKED_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_bricks"), CRACKED_PRISMARINE_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_stairs"), CRACKED_PRISMARINE_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_slab"), CRACKED_PRISMARINE_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_prismarine_brick_wall"), CRACKED_PRISMARINE_BRICK_WALL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "prismarine_brick_wall"), PRISMARINE_BRICK_WALL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "dark_prismarine_wall"), DARK_PRISMARINE_WALL);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "chiseled_prismarine_bricks"), CHISELED_PRISMARINE_BRICKS);

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
