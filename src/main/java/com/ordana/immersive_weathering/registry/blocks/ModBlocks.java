package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block MOSSY_BRICKS = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_WALL = new MossyWallBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block MOSSY_STONE = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static final Block CRACKED_BRICKS = new CrackedBlock(Crackable.CrackLevel.CRACKED, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block WOODCHIPS_BLOCK = new WoodchipsBlock(FabricBlockSettings.of(Material.WOOD).strength(1f, 1f).sounds(BlockSoundGroup.WOOD));
    public static final Block SCALECHIPS_BLOCK = new WoodchipsBlock(FabricBlockSettings.of(Material.NETHER_WOOD).strength(1f, 1f).sounds(BlockSoundGroup.NETHER_STEM));


    //cut iron
    public static final Block CUT_IRON = new CleanRustableBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON = new ExposedRustableBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON = new WeatheredRustableBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON = new RustedRustableBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block CUT_IRON_STAIRS = new CleanRustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON_STAIRS = new ExposedRustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON_STAIRS = new WeatheredRustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON_STAIRS = new RustedRustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_CUT_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block CUT_IRON_SLAB = new CleanRustableSlabBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_CUT_IRON_SLAB = new ExposedRustableSlabBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_CUT_IRON_SLAB = new WeatheredRustableSlabBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_CUT_IRON_SLAB = new RustedRustableSlabBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

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
    public static final Block PLATE_IRON = new CleanRustableBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON = new ExposedRustableBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON = new WeatheredRustableBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON = new RustedRustableBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block PLATE_IRON_STAIRS = new CleanRustableStairsBlock(Rustable.RustLevel.UNAFFECTED, PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON_STAIRS = new ExposedRustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON_STAIRS = new WeatheredRustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON_STAIRS = new RustedRustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_PLATE_IRON.getDefaultState(), FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

    public static final Block PLATE_IRON_SLAB = new CleanRustableSlabBlock(Rustable.RustLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block EXPOSED_PLATE_IRON_SLAB = new ExposedRustableSlabBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block WEATHERED_PLATE_IRON_SLAB = new WeatheredRustableSlabBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));
    public static final Block RUSTED_PLATE_IRON_SLAB = new RustedRustableSlabBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER));

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

    public static final Block EXPOSED_IRON_DOOR = new ExposedRustableDoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_DOOR = new WeatheredRustableDoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_DOOR = new RustedRustableDoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block EXPOSED_IRON_TRAPDOOR = new ExposedRustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_TRAPDOOR = new WeatheredRustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_TRAPDOOR = new RustedRustableTrapdoorBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block EXPOSED_IRON_BARS = new ExposedRustableBarsBlock(Rustable.RustLevel.EXPOSED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WEATHERED_IRON_BARS = new WeatheredRustableBarsBlock(Rustable.RustLevel.WEATHERED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block RUSTED_IRON_BARS = new RustedRustableBarsBlock(Rustable.RustLevel.RUSTED, FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_DOOR = new WaxedCleanRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_DOOR = new WaxedExposedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_DOOR = new WaxedWeatheredRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_DOOR = new WaxedRustedRustableDoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_TRAPDOOR = new WaxedTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_TRAPDOOR = new WaxedExposedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_TRAPDOOR = new WaxedWeatheredRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_TRAPDOOR = new WaxedRustedRustableTrapdoorBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static final Block WAXED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_EXPOSED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_WEATHERED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());
    public static final Block WAXED_RUSTED_IRON_BARS = new WaxedBarsBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5f, 6f).sounds(BlockSoundGroup.COPPER).nonOpaque());

    public static void registerBlocks() {

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "cracked_bricks"), CRACKED_BRICKS);

        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "woodchips_block"), WOODCHIPS_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(ImmersiveWeathering.MOD_ID, "scalechips_block"), SCALECHIPS_BLOCK);


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
