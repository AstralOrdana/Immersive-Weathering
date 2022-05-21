package com.ordana.immersive_weathering.common;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.blocks.*;
import com.ordana.immersive_weathering.common.blocks.crackable.*;
import com.ordana.immersive_weathering.common.blocks.mossable.*;
import com.ordana.immersive_weathering.common.blocks.rustable.*;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    //> (.*?) = (.*?);
    //> $1 = reg("$1".toLowerCase(Locale.ROOT), ()->
    //$2)

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS_OVERRIDE = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");


    public static <T extends Block> RegistryObject<T> reg(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static RegistryObject<Block> regWithItem(String name, Supplier<Block> supplier) {
        return regWithItem(name, supplier, CreativeModeTab.TAB_BUILDING_BLOCKS);
    }

    public static RegistryObject<Block> regWithItem(String name, Supplier<Block> supplier, CreativeModeTab tab) {
        var b = BLOCKS.register(name, supplier);
        ModItems.regBlockItem(b, new Item.Properties().tab(tab));
        return b;
    }

    public static RegistryObject<Block> regWithItem(String name, Supplier<Block> supplier, String requiredMod) {
        var b = BLOCKS.register(name, supplier);
        CreativeModeTab tab = ModList.get().isLoaded(requiredMod) ? CreativeModeTab.TAB_BUILDING_BLOCKS : null;
        ModItems.regBlockItem(b, new Item.Properties().tab(tab));
        return b;
    }

    public static RegistryObject<Block> regOverride(Block original, Function<BlockBehaviour.Properties, Block> factory) {
        return regOverride(original, () -> factory.apply(BlockBehaviour.Properties.copy(original)));
    }

    public static RegistryObject<Block> regOverride(Block original, Supplier<Block> supplier) {
        return BLOCKS_OVERRIDE.register(original.getRegistryName().getPath(), supplier);
    }


    public static final RegistryObject<LeafPileBlock> OAK_LEAF_PILE = reg("oak_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves)
                    .isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never),
                    false, false, true, List.of(ModParticles.OAK_LEAF)));

    public static final RegistryObject<LeafPileBlock> BIRCH_LEAF_PILE = reg("birch_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, true,
                    List.of(ModParticles.BIRCH_LEAF)));

    public static final RegistryObject<LeafPileBlock> SPRUCE_LEAF_PILE = reg("spruce_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, true, false,
                    List.of(ModParticles.SPRUCE_LEAF)));

    public static final RegistryObject<LeafPileBlock> JUNGLE_LEAF_PILE = reg("jungle_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, true,
                    List.of(ModParticles.JUNGLE_LEAF)));

    public static final RegistryObject<LeafPileBlock> ACACIA_LEAF_PILE = reg("acacia_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, false,
                    List.of(ModParticles.ACACIA_LEAF)));

    public static final RegistryObject<LeafPileBlock> DARK_OAK_LEAF_PILE = reg("dark_oak_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, true,
                    List.of(ModParticles.DARK_OAK_LEAF)));

    public static final RegistryObject<LeafPileBlock> AZALEA_LEAF_PILE = reg("azalea_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()).sound(SoundType.AZALEA_LEAVES), false, false, false,
                    List.of(ModParticles.AZALEA_LEAF)));

    public static final RegistryObject<LeafPileBlock> FLOWERING_AZALEA_LEAF_PILE = reg("flowering_azalea_leaf_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(AZALEA_LEAF_PILE.get()), true, false, false,
                    List.of(ModParticles.AZALEA_LEAF, ModParticles.AZALEA_FLOWER)));

    public static final RegistryObject<LeafPileBlock> AZALEA_FLOWER_PILE = reg("azalea_flower_pile", () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(AZALEA_LEAF_PILE.get()), true, false, false,
                    List.of(ModParticles.AZALEA_FLOWER)));

    public static final RegistryObject<Block> ICICLE = reg("icicle", () ->
            new IcicleBlock(BlockBehaviour.Properties.of(Material.ICE).randomTicks().instabreak().sound(SoundType.GLASS).noOcclusion().dynamicShape()));


    public static final RegistryObject<Block> WEEDS = regWithItem("weeds", () ->
            new WeedsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)),
            CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> ASH_BLOCK = regWithItem("soot_block", () ->
            new AshBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(0.5f)
                    .sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(6)).randomTicks()));
    public static final RegistryObject<Block> SOOT = regWithItem("soot", () ->
            new SootLayerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_WATER_PLANT, MaterialColor.COLOR_BLACK).noCollission()
                    .requiresCorrectToolForDrops().instabreak().sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(5)).randomTicks()),
            CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> MOSSY_BRICKS = regWithItem("mossy_bricks", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> MOSSY_BRICK_STAIRS = regWithItem("mossy_brick_stairs", () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS, BlockBehaviour.Properties.copy(MOSSY_BRICKS.get())));
    public static final RegistryObject<Block> MOSSY_BRICK_SLAB = regWithItem("mossy_brick_slab", () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.copy(MOSSY_BRICKS.get())));
    public static final RegistryObject<Block> MOSSY_BRICK_WALL = regWithItem("mossy_brick_wall", () ->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.copy(MOSSY_BRICKS.get())));
    public static final RegistryObject<Block> MOSSY_BRICK_VERTICAL_SLAB = regWithItem("mossy_brick_vertical_slab", () ->
            new MossyVerticalSlabBlock(Mossable.MossLevel.MOSSY,BlockBehaviour.Properties.copy(MOSSY_BRICKS.get())), "quark");


    public static final RegistryObject<Block> MOSSY_STONE = regWithItem("mossy_stone", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
    public static final RegistryObject<Block> MOSSY_STONE_STAIRS = regWithItem("mossy_stone_stairs", () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE, BlockBehaviour.Properties.copy(MOSSY_STONE.get())));
    public static final RegistryObject<Block> MOSSY_STONE_SLAB = regWithItem("mossy_stone_slab", () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY,  BlockBehaviour.Properties.copy(MOSSY_STONE.get())));
    public static final RegistryObject<Block> MOSSY_STONE_VERTICAL_SLAB = regWithItem("mossy_stone_vertical_slab", () ->
            new MossyVerticalSlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.copy(MOSSY_STONE_STAIRS.get())), "quark");

    public static final RegistryObject<Block> CRACKED_BRICKS = regWithItem("cracked_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> CRACKED_BRICK_STAIRS = regWithItem("cracked_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_BRICKS, () -> Items.BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_BRICK_SLAB = regWithItem("cracked_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_BRICK_WALL = regWithItem("cracked_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_BRICKS.get())));
    public static final RegistryObject<Block> CRACKED_STONE_VERTICAL_SLAB = regWithItem("cracked_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_BRICK_SLAB.get())), "quark");


    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIRS = regWithItem("cracked_stone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_STONE_BRICKS, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = regWithItem("cracked_stone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = regWithItem("cracked_stone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_stone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)), "quark");


    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = regWithItem("cracked_polished_blackstone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = regWithItem("cracked_polished_blackstone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = regWithItem("cracked_polished_blackstone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_polished_blackstone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)), "quark");


    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_STAIRS = regWithItem("cracked_nether_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_NETHER_BRICKS, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_SLAB = regWithItem("cracked_nether_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_WALL = regWithItem("cracked_nether_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_VERTICAL_SLAB = regWithItem("cracked_nether_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED,  () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)), "quark");


    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = regWithItem("cracked_deepslate_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_SLAB = regWithItem("cracked_deepslate_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_WALL = regWithItem("cracked_deepslate_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB = regWithItem("cracked_deepslate_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED,   ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)), "quark");


    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_STAIRS = regWithItem("cracked_deepslate_tile_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_SLAB = regWithItem("cracked_deepslate_tile_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_WALL = regWithItem("cracked_deepslate_tile_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_VERTICAL_SLAB = regWithItem("cracked_deepslate_tile_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)), "quark");

    public static final RegistryObject<Block> MULCH_BLOCK = regWithItem("mulch_block", () ->
            new MulchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1f, 1f)
                    .sound(SoundType.ROOTED_DIRT).randomTicks()));
    public static final RegistryObject<Block> NULCH_BLOCK = regWithItem("nulch_block", () ->
            new NulchBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD).strength(1f, 1f)
                    .sound(SoundType.NETHER_WART).lightLevel(createLightLevelFromMoltenBlockState(10)).randomTicks()));

    //cut iron
    public static final RegistryObject<Block> CUT_IRON = regWithItem("cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON = regWithItem("exposed_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON = regWithItem("weathered_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_CUT_IRON = regWithItem("rusted_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> CUT_IRON_STAIRS = regWithItem("cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_STAIRS = regWithItem("exposed_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_STAIRS = regWithItem("weathered_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_CUT_IRON_STAIRS = regWithItem("rusted_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> CUT_IRON_SLAB = regWithItem("cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_SLAB = regWithItem("exposed_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_SLAB = regWithItem("weathered_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_CUT_IRON_SLAB = regWithItem("rusted_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_CUT_IRON = regWithItem("WAXED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON = regWithItem("WAXED_EXPOSED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON = regWithItem("WAXED_WEATHERED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON = regWithItem("WAXED_RUSTED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_CUT_IRON_STAIRS = regWithItem("WAXED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_STAIRS = regWithItem("WAXED_EXPOSED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_EXPOSED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_STAIRS = regWithItem("WAXED_WEATHERED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_WEATHERED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_STAIRS = regWithItem("WAXED_RUSTED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_RUSTED_CUT_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_CUT_IRON_SLAB = regWithItem("WAXED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_SLAB = regWithItem("WAXED_EXPOSED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_SLAB = regWithItem("WAXED_WEATHERED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_SLAB = regWithItem("WAXED_RUSTED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));


    //plate iron
    public static final RegistryObject<Block> PLATE_IRON = regWithItem("PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON = regWithItem("EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON = regWithItem("WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON = regWithItem("RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> PLATE_IRON_STAIRS = regWithItem("PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_STAIRS = regWithItem("EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_STAIRS = regWithItem("WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_STAIRS = regWithItem("RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> PLATE_IRON_SLAB = regWithItem("PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_SLAB = regWithItem("EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_SLAB = regWithItem("WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_SLAB = regWithItem("RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_PLATE_IRON = regWithItem("WAXED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON = regWithItem("WAXED_EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON = regWithItem("WAXED_WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON = regWithItem("WAXED_RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_PLATE_IRON_STAIRS = regWithItem("WAXED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_STAIRS = regWithItem("WAXED_EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_EXPOSED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_STAIRS = regWithItem("WAXED_WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_WEATHERED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_STAIRS = regWithItem("WAXED_RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_RUSTED_PLATE_IRON, BlockBehaviour.Properties.copy(CUT_IRON.get())));

    public static final RegistryObject<Block> WAXED_PLATE_IRON_SLAB = regWithItem("WAXED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_SLAB = regWithItem("WAXED_EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_SLAB = regWithItem("WAXED_WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_SLAB = regWithItem("WAXED_RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())));


    public static final RegistryObject<Block> EXPOSED_IRON_DOOR = regWithItem("EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_DOOR = regWithItem("WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(EXPOSED_IRON_DOOR.get())),CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> RUSTED_IRON_DOOR = regWithItem("RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(EXPOSED_IRON_DOOR.get())),CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> EXPOSED_IRON_TRAPDOOR = regWithItem("EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_TRAPDOOR = regWithItem("WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(EXPOSED_IRON_TRAPDOOR.get())),CreativeModeTab.TAB_DECORATIONS);
    public static final RegistryObject<Block> RUSTED_IRON_TRAPDOOR = regWithItem("RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(EXPOSED_IRON_TRAPDOOR.get())),CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> EXPOSED_IRON_BARS = regWithItem("EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_BARS = regWithItem("WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> RUSTED_IRON_BARS = regWithItem("RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));


    public static final RegistryObject<Block> WAXED_IRON_DOOR = regWithItem("WAXED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_DOOR = regWithItem("WAXED_EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_DOOR = regWithItem("WAXED_WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_DOOR = regWithItem("WAXED_RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> WAXED_IRON_TRAPDOOR = regWithItem("WAXED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_TRAPDOOR = regWithItem("WAXED_EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_TRAPDOOR = regWithItem("WAXED_WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_TRAPDOOR = regWithItem("WAXED_RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> WAXED_IRON_BARS = regWithItem("WAXED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_BARS = regWithItem("WAXED_EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_BARS = regWithItem("WAXED_WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_BARS = regWithItem("WAXED_RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));




    public static final RegistryObject<Block> CRACKED_END_STONE_BRICKS = regWithItem("cracked_end_stone_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_STAIRS = regWithItem("cracked_end_stone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_SLAB = regWithItem("cracked_end_stone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_WALL = regWithItem("cracked_end_stone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_end_stone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_END_STONE_BRICK_SLAB.get())), "quark");


    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICKS = regWithItem("CRACKED_PRISMARINE_BRICKS".toLowerCase(Locale.ROOT), () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_STAIRS = regWithItem("CRACKED_PRISMARINE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_SLAB = regWithItem("CRACKED_PRISMARINE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_WALL = regWithItem("CRACKED_PRISMARINE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_VERTICAL_SLAB = regWithItem("cracked_prismarine_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK,
                    BlockBehaviour.Properties.copy(CRACKED_PRISMARINE_BRICK_SLAB.get())), "quark");


    public static final RegistryObject<Block> PRISMARINE_BRICK_WALL = regWithItem("prismarine_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.UNCRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));


    public static final RegistryObject<Block> VITRIFIED_SAND = regWithItem("vitrified_sand", () ->
            new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.TERRACOTTA_YELLOW)
                    .strength(2f, 6f).sound(SoundType.TUFF).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> HUMUS = regWithItem("humus", () ->
            new SoilBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.TERRACOTTA_GREEN)
                    .strength(0.5f).sound(SoundType.GRAVEL)));

    //vertical slab

    public static final RegistryObject<Block> CUT_IRON_VERTICAL_SLAB = regWithItem("cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_VERTICAL_SLAB = regWithItem("exposed_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_VERTICAL_SLAB = regWithItem("weathered_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> RUSTED_CUT_IRON_VERTICAL_SLAB = regWithItem("rusted_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");

    public static final RegistryObject<Block> WAXED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_exposed_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_weathered_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_rusted_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");

    public static final RegistryObject<Block> PLATE_IRON_VERTICAL_SLAB = regWithItem("plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_VERTICAL_SLAB = regWithItem("exposed_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_VERTICAL_SLAB = regWithItem("weathered_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_VERTICAL_SLAB = regWithItem("rusted_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");

    public static final RegistryObject<Block> WAXED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_exposed_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_weathered_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_rusted_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(BlockBehaviour.Properties.copy(CUT_IRON.get())), "quark");


    public static final RegistryObject<Block> HANGING_ROOTS_WALL = reg("hanging_roots_wall", () ->
            new WallRootsBlock(BlockBehaviour.Properties.copy(Blocks.HANGING_ROOTS)));

    //-----overrides------
    /*
    public static final RegistryObject<Block> STONE_BRICKS = regOverride(Blocks.STONE_BRICKS, (p) ->
            new MossableBlock(Mossable.MossLevel.UNAFFECTED,p));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICKS = regOverride(Blocks.POLISHED_BLACKSTONE_BRICKS, (p) ->
            new CrackableBlock(Crackable.CrackLevel.UNCRACKED,p));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICK_SLAB = regOverride(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, (p) ->
            new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,p));

    public static final RegistryObject<Block> POLISHED_BLACKSTONE_BRICK_STAIRS = regOverride(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, (p) ->
            new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED,POLISHED_BLACKSTONE_BRICKS,p));
    */

    //not all are here since they need an extra states. overriding what I can avoiding mixins... Could probably have left these there bah


//TODO: just replace with mixins
    /*
    public static final RegistryObject<Block> MOSSY_STONE_BRICKS = regOverride(Blocks.MOSSY_STONE_BRICKS, (p)->
            new MossyBlock(Mossable.MossLevel.MOSSY, p));

    public static final RegistryObject<Block> MOSSY_STONE_BRICK_SLAB = regOverride(Blocks.MOSSY_STONE_BRICK_SLAB, (p)->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, p));

    public static final RegistryObject<Block> MOSSY_STONE_BRICK_STAIRS = regOverride(Blocks.MOSSY_STONE_BRICK_STAIRS, (p)->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY,MOSSY_STONE_BRICKS, p));

    public static final RegistryObject<Block> MOSSY_STONE_BRICK_WALL = regOverride(Blocks.MOSSY_STONE_BRICK_WALL, (p)->
        new MossyWallBlock(Mossable.MossLevel.MOSSY, p));


    public static final RegistryObject<Block> MOSSY_COBBLESTONE = regOverride(Blocks.MOSSY_COBBLESTONE, (p)->
            new MossyBlock(Mossable.MossLevel.MOSSY, p));

    public static final RegistryObject<Block> MOSSY_COBBLESTONE_SLAB = regOverride(Blocks.MOSSY_COBBLESTONE_SLAB, (p)->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, p));

    public static final RegistryObject<Block> MOSSY_COBBLESTONE_STAIRS = regOverride(Blocks.MOSSY_COBBLESTONE_STAIRS, (p)->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY,MOSSY_COBBLESTONE, p));

    public static final RegistryObject<Block> MOSSY_COBBLESTONE_WALL = regOverride(Blocks.MOSSY_COBBLESTONE_WALL, (p)->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, p));


    public static final RegistryObject<Block> IRON_DOOR = regOverride(Blocks.IRON_DOOR, (p) ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, p.sound(SoundType.COPPER)));

    public static final RegistryObject<Block> IRON_TRAPDOOR = regOverride(Blocks.IRON_TRAPDOOR, (p) ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, p.sound(SoundType.COPPER)));

    public static final RegistryObject<Block> IRON_BARS = regOverride(Blocks.IRON_BARS, (p) ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, p.sound(SoundType.COPPER)));
*/


    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return (state) -> (Boolean) state.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }

    private static ToIntFunction<BlockState> createLightLevelFromMoltenBlockState(int litLevel) {
        return (state) -> (Boolean) state.getValue(NulchBlock.MOLTEN) ? litLevel : 0;
    }

    private static Boolean canSpawnOnLeaves(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

}
