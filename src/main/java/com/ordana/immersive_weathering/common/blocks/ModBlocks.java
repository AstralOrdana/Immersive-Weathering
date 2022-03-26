package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.blocks.crackable.*;
import com.ordana.immersive_weathering.common.blocks.mossable.*;
import com.ordana.immersive_weathering.common.blocks.rustable.*;
import com.ordana.immersive_weathering.common.entity.FallingIcicleBlockEntity;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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


    public static RegistryObject<Block> reg(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static RegistryObject<Block> regWithItem(String name, Supplier<Block> supplier) {
        var b = BLOCKS.register(name, supplier);
        ModItems.regBlockItem(b,new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
        return b;
    }

    public static RegistryObject<Block> regOverride(Block original, Function<BlockBehaviour.Properties, Block> factory) {
        return regOverride(original, () -> factory.apply(BlockBehaviour.Properties.copy(original)));
    }

    public static RegistryObject<Block> regOverride(Block original, Supplier<Block> supplier) {
        return BLOCKS_OVERRIDE.register(original.getRegistryName().getPath(), supplier);
    }

    public static final RegistryObject<Block> ICICLE = reg("icicle", () ->
            new IcicleBlock(BlockBehaviour.Properties.of(Material.ICE).randomTicks().instabreak().sound(SoundType.GLASS).noOcclusion().dynamicShape()));

    public static final RegistryObject<Block> OAK_LEAF_PILE = reg("OAK_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves)
                    .isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never),
                    false, false, List.of(ModParticles.OAK_LEAF)));

    public static final RegistryObject<Block> BIRCH_LEAF_PILE = reg("BIRCH_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, List.of(ModParticles.BIRCH_LEAF)));

    public static final RegistryObject<Block> SPRUCE_LEAF_PILE = reg("SPRUCE_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, true, List.of(ModParticles.SPRUCE_LEAF)));

    public static final RegistryObject<Block> JUNGLE_LEAF_PILE = reg("JUNGLE_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, List.of(ModParticles.JUNGLE_LEAF)));

    public static final RegistryObject<Block> ACACIA_LEAF_PILE = reg("ACACIA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, List.of(ModParticles.ACACIA_LEAF)));

    public static final RegistryObject<Block> DARK_OAK_LEAF_PILE = reg("DARK_OAK_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()), false, false, List.of(ModParticles.DARK_OAK_LEAF)));

    public static final RegistryObject<Block> AZALEA_LEAF_PILE = reg("AZALEA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(OAK_LEAF_PILE.get()).sound(SoundType.AZALEA_LEAVES), false, false, List.of(ModParticles.AZALEA_LEAF)));

    public static final RegistryObject<Block> FLOWERING_AZALEA_LEAF_PILE = reg("FLOWERING_AZALEA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(AZALEA_LEAF_PILE.get()), true, false, List.of(ModParticles.AZALEA_LEAF, ModParticles.AZALEA_FLOWER)));

    public static final RegistryObject<Block> AZALEA_FLOWER_PILE = reg("AZALEA_FLOWER_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.copy(AZALEA_LEAF_PILE.get()), true, false, List.of(ModParticles.AZALEA_FLOWER)));

    public static final RegistryObject<Block> WEEDS = reg("WEEDS".toLowerCase(Locale.ROOT), () ->
            new WeedsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ASH_BLOCK = reg("ASH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new AshBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).instabreak().sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(6)).randomTicks()));
    public static final RegistryObject<Block> SOOT = reg("SOOT".toLowerCase(Locale.ROOT), () ->
            new SootBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_WATER_PLANT, MaterialColor.COLOR_BLACK).noCollission().instabreak().sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(5)).randomTicks()));

    public static final RegistryObject<Block> MOSSY_BRICKS = reg("MOSSY_BRICKS".toLowerCase(Locale.ROOT), () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> MOSSY_BRICK_STAIRS = reg("MOSSY_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> MOSSY_BRICK_SLAB = reg("MOSSY_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> MOSSY_BRICK_WALL = reg("MOSSY_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));

    public static final RegistryObject<Block> MOSSY_STONE = reg("mossy_stone", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
    public static final RegistryObject<Block> MOSSY_STONE_STAIRS = reg("MOSSY_STONE_STAIRS".toLowerCase(Locale.ROOT), () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
    public static final RegistryObject<Block> MOSSY_STONE_SLAB = reg("MOSSY_STONE_SLAB".toLowerCase(Locale.ROOT), () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));

    public static final RegistryObject<Block> CRACKED_BRICKS = reg("cracked_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> CRACKED_BRICK_STAIRS = reg("CRACKED_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_BRICKS, () -> Items.BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> CRACKED_BRICK_SLAB = reg("CRACKED_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final RegistryObject<Block> CRACKED_BRICK_WALL = reg("CRACKED_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIRS = reg("CRACKED_STONE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_STONE_BRICKS, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = reg("CRACKED_STONE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = reg("CRACKED_STONE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_STAIRS = reg("CRACKED_NETHER_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_NETHER_BRICKS, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_SLAB = reg("CRACKED_NETHER_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_WALL = reg("CRACKED_NETHER_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)));

    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = reg("CRACKED_DEEPSLATE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_SLAB = reg("CRACKED_DEEPSLATE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_WALL = reg("CRACKED_DEEPSLATE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)));

    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_STAIRS = reg("CRACKED_DEEPSLATE_TILE_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_SLAB = reg("CRACKED_DEEPSLATE_TILE_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)));
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_WALL = reg("CRACKED_DEEPSLATE_TILE_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)));

    public static final RegistryObject<Block> MULCH_BLOCK = reg("MULCH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new MulchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1f, 1f).sound(SoundType.WOOD).randomTicks()));
    public static final RegistryObject<Block> NULCH_BLOCK = reg("NULCH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new NulchBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD).strength(1f, 1f).sound(SoundType.STEM).lightLevel(createLightLevelFromMoltenBlockState(10)).randomTicks()));
    public static final RegistryObject<Block> MULCH = reg("MULCH".toLowerCase(Locale.ROOT), () ->
            new MulchCarpetBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1f, 1f).sound(SoundType.WOOD), ModParticles.MULCH));
    public static final RegistryObject<Block> NULCH = reg("NULCH".toLowerCase(Locale.ROOT), () ->
            new MulchCarpetBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD).strength(1f, 1f).sound(SoundType.STEM), ModParticles.NULCH));


    //cut iron
    public static final RegistryObject<Block> CUT_IRON = reg("CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON = reg("EXPOSED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON = reg("WEATHERED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_CUT_IRON = reg("RUSTED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> CUT_IRON_STAIRS = reg("CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_STAIRS = reg("EXPOSED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_STAIRS = reg("WEATHERED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_CUT_IRON_STAIRS = reg("RUSTED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> CUT_IRON_SLAB = reg("CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_SLAB = reg("EXPOSED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_SLAB = reg("WEATHERED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_CUT_IRON_SLAB = reg("RUSTED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_CUT_IRON = reg("WAXED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON = reg("WAXED_EXPOSED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON = reg("WAXED_WEATHERED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON = reg("WAXED_RUSTED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_CUT_IRON_STAIRS = reg("WAXED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_STAIRS = reg("WAXED_EXPOSED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_EXPOSED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_STAIRS = reg("WAXED_WEATHERED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_WEATHERED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_STAIRS = reg("WAXED_RUSTED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_RUSTED_CUT_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_CUT_IRON_SLAB = reg("WAXED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_SLAB = reg("WAXED_EXPOSED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_SLAB = reg("WAXED_WEATHERED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_SLAB = reg("WAXED_RUSTED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));


    //plate iron
    public static final RegistryObject<Block> PLATE_IRON = reg("PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON = reg("EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON = reg("WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON = reg("RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> PLATE_IRON_STAIRS = reg("PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_STAIRS = reg("EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_STAIRS = reg("WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_STAIRS = reg("RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> PLATE_IRON_SLAB = reg("PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_SLAB = reg("EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_SLAB = reg("WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_SLAB = reg("RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_PLATE_IRON = reg("WAXED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON = reg("WAXED_EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON = reg("WAXED_WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON = reg("WAXED_RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_PLATE_IRON_STAIRS = reg("WAXED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_STAIRS = reg("WAXED_EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_EXPOSED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_STAIRS = reg("WAXED_WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_WEATHERED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_STAIRS = reg("WAXED_RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairBlock(WAXED_RUSTED_PLATE_IRON, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WAXED_PLATE_IRON_SLAB = reg("WAXED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_SLAB = reg("WAXED_EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_SLAB = reg("WAXED_WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_SLAB = reg("WAXED_RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)));


    public static final RegistryObject<Block> EXPOSED_IRON_DOOR = reg("EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_DOOR = reg("WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> RUSTED_IRON_DOOR = reg("RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> EXPOSED_IRON_TRAPDOOR = reg("EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_TRAPDOOR = reg("WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> RUSTED_IRON_TRAPDOOR = reg("RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> EXPOSED_IRON_BARS = reg("EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WEATHERED_IRON_BARS = reg("WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> RUSTED_IRON_BARS = reg("RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));


    public static final RegistryObject<Block> WAXED_IRON_DOOR = reg("WAXED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_DOOR = reg("WAXED_EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_DOOR = reg("WAXED_WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_DOOR = reg("WAXED_RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> WAXED_IRON_TRAPDOOR = reg("WAXED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_TRAPDOOR = reg("WAXED_EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_TRAPDOOR = reg("WAXED_WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_TRAPDOOR = reg("WAXED_RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> WAXED_IRON_BARS = reg("WAXED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_BARS = reg("WAXED_EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_BARS = reg("WAXED_WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_BARS = reg("WAXED_RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()));

    public static final RegistryObject<Block> HANGING_ROOTS_WALL = reg("hanging_roots_wall", () ->
            new WallRootsBlock(BlockBehaviour.Properties.copy(Blocks.HANGING_ROOTS)));


    public static final RegistryObject<Block> CRACKED_END_STONE_BRICKS = regWithItem("CRACKED_END_STONE_BRICKS".toLowerCase(Locale.ROOT), () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_STAIRS = regWithItem("CRACKED_END_STONE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_SLAB = regWithItem("CRACKED_END_STONE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));
    public static final RegistryObject<Block> CRACKED_END_STONE_BRICK_WALL = regWithItem("CRACKED_END_STONE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, BlockBehaviour.Properties.copy(Blocks.END_STONE)));

    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICKS = regWithItem("CRACKED_PRISMARINE_BRICKS".toLowerCase(Locale.ROOT), () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_STAIRS = regWithItem("CRACKED_PRISMARINE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_SLAB = regWithItem("CRACKED_PRISMARINE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));
    public static final RegistryObject<Block> CRACKED_PRISMARINE_BRICK_WALL = regWithItem("CRACKED_PRISMARINE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));

    public static final RegistryObject<Block> PRISMARINE_BRICK_WALL = regWithItem("PRISMARINE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.UNCRACKED, ModItems.PRISMARINE_BRICK, BlockBehaviour.Properties.copy(Blocks.PRISMARINE)));


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
