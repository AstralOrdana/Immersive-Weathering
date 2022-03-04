package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    //> (.*?) = (.*?);
    //> $1 = reg("$1".toLowerCase(Locale.ROOT), ()->
    //$2)

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveWeathering.MOD_ID);

    public static RegistryObject<Block> reg(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }

    public static final RegistryObject<Block> ICICLE = reg("ICICLE".toLowerCase(Locale.ROOT), () ->
            new IcicleBlock(BlockBehaviour.Properties.of(Material.ICE).randomTicks().instabreak().sound(SoundType.GLASS).noOcclusion().dynamicShape()))

    public static final RegistryObject<Block> OAK_LEAF_PILE = reg("OAK_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> BIRCH_LEAF_PILE = reg("BIRCH_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> SPRUCE_LEAF_PILE = reg("SPRUCE_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> JUNGLE_LEAF_PILE = reg("JUNGLE_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> ACACIA_LEAF_PILE = reg("ACACIA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> DARK_OAK_LEAF_PILE = reg("DARK_OAK_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> AZALEA_LEAF_PILE = reg("AZALEA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.AZALEA_LEAVES).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> FLOWERING_AZALEA_LEAF_PILE = reg("FLOWERING_AZALEA_LEAF_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).instabreak().randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))
    public static final RegistryObject<Block> AZALEA_FLOWER_PILE = reg("AZALEA_FLOWER_PILE".toLowerCase(Locale.ROOT), () ->
            new LeafPileBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().instabreak().sound(SoundType.FLOWERING_AZALEA).noOcclusion().isValidSpawn(ModBlocks::canSpawnOnLeaves).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)))

    public static final RegistryObject<Block> WEEDS = reg("WEEDS".toLowerCase(Locale.ROOT), () ->
            new WeedsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)))
    public static final RegistryObject<Block> ASH_BLOCK = reg("ASH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new AshBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).instabreak().sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(6)).randomTicks()))
    public static final RegistryObject<Block> SOOT = reg("SOOT".toLowerCase(Locale.ROOT), () ->
            new SootBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_WATER_PLANT, MaterialColor.COLOR_BLACK).noCollission().instabreak().sound(SoundType.SNOW).lightLevel(createLightLevelFromLitBlockState(5)).randomTicks()))

    public static final RegistryObject<Block> MOSSY_BRICKS = reg("MOSSY_BRICKS".toLowerCase(Locale.ROOT), () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> MOSSY_BRICK_STAIRS = reg("MOSSY_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> MOSSY_BRICK_SLAB = reg("MOSSY_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> MOSSY_BRICK_WALL = reg("MOSSY_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))

    public static final RegistryObject<Block> MOSSY_STONE = reg("MOSSY_STONE".toLowerCase(Locale.ROOT), () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)))
    public static final RegistryObject<Block> MOSSY_STONE_STAIRS = reg("MOSSY_STONE_STAIRS".toLowerCase(Locale.ROOT), () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)))
    public static final RegistryObject<Block> MOSSY_STONE_SLAB = reg("MOSSY_STONE_SLAB".toLowerCase(Locale.ROOT), () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)))

    public static final RegistryObject<Block> CRACKED_BRICKS = reg("CRACKED_BRICKS".toLowerCase(Locale.ROOT), () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> CRACKED_BRICK_STAIRS = reg("CRACKED_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> CRACKED_BRICK_SLAB = reg("CRACKED_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))
    public static final RegistryObject<Block> CRACKED_BRICK_WALL = reg("CRACKED_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)))

    public static final RegistryObject<Block> CRACKED_STONE_BRICK_STAIRS = reg("CRACKED_STONE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_SLAB = reg("CRACKED_STONE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))
    public static final RegistryObject<Block> CRACKED_STONE_BRICK_WALL = reg("CRACKED_STONE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))

    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))
    public static final RegistryObject<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = reg("CRACKED_POLISHED_BLACKSTONE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5F, 6.0F)))

    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_STAIRS = reg("CRACKED_NETHER_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, Blocks.CRACKED_NETHER_BRICKS.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)))
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_SLAB = reg("CRACKED_NETHER_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)))
    public static final RegistryObject<Block> CRACKED_NETHER_BRICK_WALL = reg("CRACKED_NETHER_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER).requiresCorrectToolForDrops().strength(2.0F, 6.0F).sound(SoundType.NETHER_BRICKS)))

    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = reg("CRACKED_DEEPSLATE_BRICK_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)))
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_SLAB = reg("CRACKED_DEEPSLATE_BRICK_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)))
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_BRICK_WALL = reg("CRACKED_DEEPSLATE_BRICK_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_BRICKS)))

    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_STAIRS = reg("CRACKED_DEEPSLATE_TILE_STAIRS".toLowerCase(Locale.ROOT), () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)))
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_SLAB = reg("CRACKED_DEEPSLATE_TILE_SLAB".toLowerCase(Locale.ROOT), () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)))
    public static final RegistryObject<Block> CRACKED_DEEPSLATE_TILE_WALL = reg("CRACKED_DEEPSLATE_TILE_WALL".toLowerCase(Locale.ROOT), () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.DEEPSLATE_TILES)))

    public static final RegistryObject<Block> MULCH_BLOCK = reg("MULCH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new MulchBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1f, 1f).sound(SoundType.WOOD).randomTicks()))
    public static final RegistryObject<Block> NULCH_BLOCK = reg("NULCH_BLOCK".toLowerCase(Locale.ROOT), () ->
            new NulchBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD).strength(1f, 1f).sound(SoundType.STEM).lightLevel(createLightLevelFromMoltenBlockState(10)).randomTicks()))
    public static final RegistryObject<Block> MULCH = reg("MULCH".toLowerCase(Locale.ROOT), () ->
            new MulchCarpetBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1f, 1f).sound(SoundType.WOOD)))
    public static final RegistryObject<Block> NULCH = reg("NULCH".toLowerCase(Locale.ROOT), () ->
            new MulchCarpetBlock(BlockBehaviour.Properties.of(Material.NETHER_WOOD).strength(1f, 1f).sound(SoundType.STEM)))


    //cut iron
    public static final RegistryObject<Block> CUT_IRON = reg("CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_CUT_IRON = reg("EXPOSED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_CUT_IRON = reg("WEATHERED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_CUT_IRON = reg("RUSTED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> CUT_IRON_STAIRS = reg("CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_STAIRS = reg("EXPOSED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_STAIRS = reg("WEATHERED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_CUT_IRON_STAIRS = reg("RUSTED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> CUT_IRON_SLAB = reg("CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_CUT_IRON_SLAB = reg("EXPOSED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_CUT_IRON_SLAB = reg("WEATHERED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_CUT_IRON_SLAB = reg("RUSTED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_CUT_IRON = reg("WAXED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON = reg("WAXED_EXPOSED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON = reg("WAXED_WEATHERED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON = reg("WAXED_RUSTED_CUT_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_CUT_IRON_STAIRS = reg("WAXED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_STAIRS = reg("WAXED_EXPOSED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_EXPOSED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_STAIRS = reg("WAXED_WEATHERED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_WEATHERED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_STAIRS = reg("WAXED_RUSTED_CUT_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_RUSTED_CUT_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_CUT_IRON_SLAB = reg("WAXED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_CUT_IRON_SLAB = reg("WAXED_EXPOSED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_CUT_IRON_SLAB = reg("WAXED_WEATHERED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_CUT_IRON_SLAB = reg("WAXED_RUSTED_CUT_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))


    //plate iron
    public static final RegistryObject<Block> PLATE_IRON = reg("PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON = reg("EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON = reg("WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_PLATE_IRON = reg("RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> PLATE_IRON_STAIRS = reg("PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_STAIRS = reg("EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, EXPOSED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_STAIRS = reg("WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, WEATHERED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_STAIRS = reg("RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, RUSTED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> PLATE_IRON_SLAB = reg("PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> EXPOSED_PLATE_IRON_SLAB = reg("EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WEATHERED_PLATE_IRON_SLAB = reg("WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> RUSTED_PLATE_IRON_SLAB = reg("RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_PLATE_IRON = reg("WAXED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON = reg("WAXED_EXPOSED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON = reg("WAXED_WEATHERED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON = reg("WAXED_RUSTED_PLATE_IRON".toLowerCase(Locale.ROOT), () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_PLATE_IRON_STAIRS = reg("WAXED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_STAIRS = reg("WAXED_EXPOSED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_EXPOSED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_STAIRS = reg("WAXED_WEATHERED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_WEATHERED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_STAIRS = reg("WAXED_RUSTED_PLATE_IRON_STAIRS".toLowerCase(Locale.ROOT), () ->
            new ModStairs(WAXED_RUSTED_PLATE_IRON.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> WAXED_PLATE_IRON_SLAB = reg("WAXED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_EXPOSED_PLATE_IRON_SLAB = reg("WAXED_EXPOSED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_WEATHERED_PLATE_IRON_SLAB = reg("WAXED_WEATHERED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))
    public static final RegistryObject<Block> WAXED_RUSTED_PLATE_IRON_SLAB = reg("WAXED_RUSTED_PLATE_IRON_SLAB".toLowerCase(Locale.ROOT), () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER)))

    public static final RegistryObject<Block> EXPOSED_IRON_DOOR = reg("EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WEATHERED_IRON_DOOR = reg("WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> RUSTED_IRON_DOOR = reg("RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))

    public static final RegistryObject<Block> EXPOSED_IRON_TRAPDOOR = reg("EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WEATHERED_IRON_TRAPDOOR = reg("WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> RUSTED_IRON_TRAPDOOR = reg("RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))

    public static final RegistryObject<Block> EXPOSED_IRON_BARS = reg("EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WEATHERED_IRON_BARS = reg("WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> RUSTED_IRON_BARS = reg("RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))

    public static final RegistryObject<Block> WAXED_IRON_DOOR = reg("WAXED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableDoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_DOOR = reg("WAXED_EXPOSED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableDoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_DOOR = reg("WAXED_WEATHERED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableDoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_DOOR = reg("WAXED_RUSTED_IRON_DOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableDoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))

    public static final RegistryObject<Block> WAXED_IRON_TRAPDOOR = reg("WAXED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableTrapdoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_TRAPDOOR = reg("WAXED_EXPOSED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableTrapdoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_TRAPDOOR = reg("WAXED_WEATHERED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableTrapdoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_TRAPDOOR = reg("WAXED_RUSTED_IRON_TRAPDOOR".toLowerCase(Locale.ROOT), () ->
            new WaxedRustableTrapdoorBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))

    public static final RegistryObject<Block> WAXED_IRON_BARS = reg("WAXED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new WaxedBarsBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_EXPOSED_IRON_BARS = reg("WAXED_EXPOSED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new WaxedBarsBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_WEATHERED_IRON_BARS = reg("WAXED_WEATHERED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new WaxedBarsBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))
    public static final RegistryObject<Block> WAXED_RUSTED_IRON_BARS = reg("WAXED_RUSTED_IRON_BARS".toLowerCase(Locale.ROOT), () ->
            new WaxedBarsBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(5f, 6f).sound(SoundType.COPPER).noOcclusion()))


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
