package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.*;
import com.ordana.immersive_weathering.blocks.charred.*;
import com.ordana.immersive_weathering.blocks.cracked.*;
import com.ordana.immersive_weathering.blocks.frosted.FrostBlock;
import com.ordana.immersive_weathering.blocks.frosted.FrostyGlassBlock;
import com.ordana.immersive_weathering.blocks.frosted.FrostyGlassPaneBlock;
import com.ordana.immersive_weathering.blocks.frosted.FrostyGrassBlock;
import com.ordana.immersive_weathering.blocks.mossy.*;
import com.ordana.immersive_weathering.blocks.sandy.SandyBlock;
import com.ordana.immersive_weathering.blocks.sandy.SandySlabBlock;
import com.ordana.immersive_weathering.blocks.sandy.SandyStairsBlock;
import com.ordana.immersive_weathering.blocks.sandy.SandyWallBlock;
import com.ordana.immersive_weathering.blocks.snowy.SnowyBlock;
import com.ordana.immersive_weathering.blocks.snowy.SnowySlabBlock;
import com.ordana.immersive_weathering.blocks.snowy.SnowyStairsBlock;
import com.ordana.immersive_weathering.blocks.snowy.SnowyWallBlock;
import net.mehvahdjukaar.moonlight.api.item.FuelBlockItem;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class ModBlocks {

    public static void init() {
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    public static <T extends Block> Supplier<T> regBlock(String name, Supplier<T> block) {
        return RegHelper.registerBlock(ImmersiveWeathering.res(name), block);
    }

    public static <T extends Block> Supplier<T> regWithItem(String name, Supplier<T> blockFactory) {
        Supplier<T> block = regBlock(name, blockFactory);
        regBlockItem(name, block, new Item.Properties());
        return block;
    }

    public static <T extends Block> Supplier<T> regWithBurnableItem(String name, Supplier<T> blockFactory, Supplier<Integer> time) {
        Supplier<T> block = regBlock(name, blockFactory);
        regBurnableBlockItem(name, block, new Item.Properties(), time);
        return block;
    }

    public static Supplier<BlockItem> regBlockItem(String name, Supplier<? extends Block> blockSup, Item.Properties properties) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), () -> new BlockItem(blockSup.get(), properties));
    }

    public static Supplier<BlockItem> regBurnableBlockItem(String name, Supplier<? extends Block> blockSup, Item.Properties properties, Supplier<Integer> time) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), () -> new FuelBlockItem(blockSup.get(), properties, time));
    }

    //predicates

    private static ToIntFunction<BlockState> litLightLevel(int litLevel) {
        return (state) -> state.getValue(ModBlockProperties.SMOLDERING) ? litLevel : 0;
    }

    private static ToIntFunction<BlockState> moltenLightLevel(int litLevel) {
        return (state) -> state.getValue(ModBlockProperties.MOLTEN) ? litLevel : 0;
    }

    private static final BlockBehaviour.StateArgumentPredicate<EntityType<?>> CAN_SPAWN_ON_LEAVES = (a, b, c, t) ->
        t == EntityType.OCELOT || t == EntityType.PARROT;

    private static final BlockBehaviour.StatePredicate NEVER = (s, w, p) -> false;


    public static final Properties LEAF_PILE_PROPERTIES = Properties.of()
        .randomTicks().instabreak().sound(SoundType.GRASS)
        .noOcclusion().isValidSpawn(CAN_SPAWN_ON_LEAVES)
        .isSuffocating(NEVER).isViewBlocking(NEVER);

    //layer stuff

    public static final Supplier<Block> SAND_LAYER_BLOCK = regWithItem("sand_layer_block", () ->
        new SandLayerBlock(14406560, Properties.copy(Blocks.SAND).strength(0.5f)
            .sound(SoundType.SAND).isSuffocating(NEVER)
            .isViewBlocking((blockState, blockView, blockPos) -> blockState.getValue(LayerBlock.LAYERS_8) >= 8)
            .noOcclusion().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RED_SAND_LAYER_BLOCK = regWithItem("red_sand_layer_block", () ->
        new SandLayerBlock(11098145, Properties.copy(Blocks.RED_SAND).strength(0.5f)
            .sound(SoundType.SAND).isSuffocating(NEVER)
            .isViewBlocking((blockState, blockView, blockPos) -> blockState.getValue(LayerBlock.LAYERS_8) >= 8)
            .noOcclusion().requiresCorrectToolForDrops()));


    //mossy blocks

    public static final Supplier<Block> MOSSY_BRICKS = regWithItem("mossy_bricks", () ->
        new MossyBlock(Mossable.MossLevel.MOSSY, Properties.copy(Blocks.BRICKS)
            .requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final Supplier<Block> MOSSY_BRICK_STAIRS = regWithItem("mossy_brick_stairs", () ->
        new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS, Properties.copy(MOSSY_BRICKS.get())));
    public static final Supplier<Block> MOSSY_BRICK_SLAB = regWithItem("mossy_brick_slab", () ->
        new MossySlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_BRICKS.get())));
    public static final Supplier<Block> MOSSY_BRICK_WALL = regWithItem("mossy_brick_wall", () ->
        new MossyWallBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_BRICKS.get())));

    public static final Supplier<Block> MOSSY_STONE = regWithItem("mossy_stone", () ->
        new MossyBlock(Mossable.MossLevel.MOSSY, Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
    public static final Supplier<Block> MOSSY_CHISELED_STONE_BRICKS = regWithItem("mossy_chiseled_stone_bricks", () ->
        new MossyBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_STAIRS = regWithItem("mossy_stone_stairs", () ->
        new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_SLAB = regWithItem("mossy_stone_slab", () ->
        new MossySlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_WALL = regWithItem("mossy_stone_wall", () ->
        new MossyWallBlock(Mossable.MossLevel.MOSSY, Properties.copy(Blocks.COBBLESTONE_WALL)));


    //snowy blocks

    public static final Supplier<Block> SNOWY_STONE = regWithItem("snowy_stone", () ->
        new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_STAIRS = regWithItem("snowy_stone_stairs", () ->
        new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_SLAB = regWithItem("snowy_stone_slab", () ->
        new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_WALL = regWithItem("snowy_stone_wall", () ->
        new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    public static final Supplier<Block> SNOWY_COBBLESTONE = regWithItem("snowy_cobblestone", () ->
        new SnowyBlock(Properties.copy(Blocks.COBBLESTONE).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_STAIRS = regWithItem("snowy_cobblestone_stairs", () ->
        new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_SLAB = regWithItem("snowy_cobblestone_slab", () ->
        new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_WALL = regWithItem("snowy_cobblestone_wall", () ->
        new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    public static final Supplier<Block> SNOWY_STONE_BRICKS = regWithItem("snowy_stone_bricks", () ->
        new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_CHISELED_STONE_BRICKS = regWithItem("snowy_chiseled_stone_bricks", () ->
        new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_STAIRS = regWithItem("snowy_stone_brick_stairs", () ->
        new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_SLAB = regWithItem("snowy_stone_brick_slab", () ->
        new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_WALL = regWithItem("snowy_stone_brick_wall", () ->
        new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //sandy blocks

    public static final Supplier<Block> SANDY_STONE = regWithItem("sandy_stone", () ->
        new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_STAIRS = regWithItem("sandy_stone_stairs", () ->
        new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_SLAB = regWithItem("sandy_stone_slab", () ->
        new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_WALL = regWithItem("sandy_stone_wall", () ->
        new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    public static final Supplier<Block> SANDY_COBBLESTONE = regWithItem("sandy_cobblestone", () ->
        new SandyBlock(Properties.copy(Blocks.COBBLESTONE).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_STAIRS = regWithItem("sandy_cobblestone_stairs", () ->
        new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_SLAB = regWithItem("sandy_cobblestone_slab", () ->
        new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_WALL = regWithItem("sandy_cobblestone_wall", () ->
        new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    public static final Supplier<Block> SANDY_STONE_BRICKS = regWithItem("sandy_stone_bricks", () ->
        new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_CHISELED_STONE_BRICKS = regWithItem("sandy_chiseled_stone_bricks", () ->
        new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_STAIRS = regWithItem("sandy_stone_brick_stairs", () ->
        new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_SLAB = regWithItem("sandy_stone_brick_slab", () ->
        new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_WALL = regWithItem("sandy_stone_brick_wall", () ->
        new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //cracked blocks

    public static final Supplier<Block> CRACKED_BRICKS = regWithItem("cracked_bricks", () ->
        new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
            Properties.copy(Blocks.BRICKS).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final Supplier<Block> CRACKED_BRICK_STAIRS = regWithItem("cracked_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_BRICKS, () -> Items.BRICK,
            Properties.copy(CRACKED_BRICKS.get())));
    public static final Supplier<Block> CRACKED_BRICK_SLAB = regWithItem("cracked_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
            Properties.copy(CRACKED_BRICKS.get())));
    public static final Supplier<Block> CRACKED_BRICK_WALL = regWithItem("cracked_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
            Properties.copy(CRACKED_BRICKS.get())));

    public static final Supplier<Block> CRACKED_STONE_BRICK_STAIRS = regWithItem("cracked_stone_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_STONE_BRICKS, ModItems.STONE_BRICK,
            Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final Supplier<Block> CRACKED_STONE_BRICK_SLAB = regWithItem("cracked_stone_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
            Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final Supplier<Block> CRACKED_STONE_BRICK_WALL = regWithItem("cracked_stone_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
            Properties.copy(Blocks.CRACKED_STONE_BRICKS)));

    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = regWithItem("cracked_polished_blackstone_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK,
            Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = regWithItem("cracked_polished_blackstone_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
            Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = regWithItem("cracked_polished_blackstone_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
            Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));

    public static final Supplier<Block> CRACKED_NETHER_BRICK_STAIRS = regWithItem("cracked_nether_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_NETHER_BRICKS, () -> Items.NETHER_BRICK,
            Properties.copy(Blocks.NETHER_BRICKS)));
    public static final Supplier<Block> CRACKED_NETHER_BRICK_SLAB = regWithItem("cracked_nether_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
            Properties.copy(Blocks.NETHER_BRICKS)));
    public static final Supplier<Block> CRACKED_NETHER_BRICK_WALL = regWithItem("cracked_nether_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
            Properties.copy(Blocks.NETHER_BRICKS)));

    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = regWithItem("cracked_deepslate_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_SLAB = regWithItem("cracked_deepslate_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_WALL = regWithItem("cracked_deepslate_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));

    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_STAIRS = regWithItem("cracked_deepslate_tile_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_SLAB = regWithItem("cracked_deepslate_tile_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_WALL = regWithItem("cracked_deepslate_tile_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
            Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));

    public static final Supplier<Block> CRACKED_END_STONE_BRICKS = regWithItem("cracked_end_stone_bricks", () ->
        new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_STAIRS = regWithItem("cracked_end_stone_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_SLAB = regWithItem("cracked_end_stone_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_WALL = regWithItem("cracked_end_stone_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));


    public static final Supplier<Block> CRACKED_PRISMARINE_BRICKS = regWithItem("cracked_prismarine_bricks", () ->
        new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_STAIRS = regWithItem("cracked_prismarine_brick_stairs", () ->
        new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_SLAB = regWithItem("cracked_prismarine_brick_slab", () ->
        new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_WALL = regWithItem("cracked_prismarine_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));



    //vitrified sand

    public static final Supplier<Block> FULGURITE = regWithItem("fulgurite", () ->
        new FulguriteBlock(7, 3, Properties.copy(Blocks.GLASS)
            .instabreak().lightLevel((s) -> s.getValue(FulguriteBlock.POWERED) ? 5 : 0)
            .dynamicShape().requiresCorrectToolForDrops()));

    public static final Supplier<Block> VITRIFIED_SAND = regWithItem("vitrified_sand", () ->
        new GlassBlock(Properties.copy(Blocks.GLASS)
            .strength(2f, 6f).sound(SoundType.TUFF)
            .requiresCorrectToolForDrops().noOcclusion().isViewBlocking((s, l, p) -> false)));


    //frost and ice

    public static final Supplier<Block> ICICLE = regBlock("icicle", () ->
        new IcicleBlock(Properties.copy(Blocks.ICE).randomTicks().instabreak()
            .sound(SoundType.GLASS).noOcclusion().dynamicShape()));
    public static final Supplier<Block> THIN_ICE = regBlock("thin_ice", () ->
        new ThinIceBlock(Properties.copy(Blocks.ICE)
            .isViewBlocking(NEVER).isSuffocating(NEVER).isViewBlocking(NEVER)));

    public static final Supplier<Block> FROST = regBlock("frost", () ->
        new FrostBlock(Properties.copy(Blocks.SNOW)
            .randomTicks().instabreak().sound(SoundType.POWDER_SNOW).noOcclusion().noCollission()));
    public static final Supplier<Block> FROSTY_GRASS = regWithItem("frosty_grass", () ->
        new FrostyGrassBlock(Properties.copy(Blocks.GRASS)
            .randomTicks().sound(SoundType.POWDER_SNOW)));
    public static final Supplier<Block> FROSTY_FERN = regWithItem("frosty_fern", () ->
        new FrostyGrassBlock(Properties.copy(FROSTY_GRASS.get())));
    public static final Supplier<Block> FROSTY_GLASS = regWithItem("frosty_glass", () ->
        new FrostyGlassBlock(Properties.copy(Blocks.GLASS).randomTicks()));
    public static final Supplier<Block> FROSTY_GLASS_PANE = regWithItem("frosty_glass_pane", () ->
        new FrostyGlassPaneBlock(Properties.copy(Blocks.GLASS_PANE).randomTicks()));


    //charred blocks
    public static final Supplier<Block> SOOT = regWithItem("soot", () ->
        new SootBlock(Properties.copy(Blocks.SCULK_VEIN).noCollission().instabreak().sound(SoundType.SNOW).randomTicks()));

    public static final Supplier<Block> CHARRED_LOG = regWithBurnableItem("charred_log", () ->
        new CharredPillarBlock(Properties.copy(Blocks.BASALT)
            .strength(1.5f, 0.5f).sound(SoundType.BASALT)
            .lightLevel(litLightLevel(5)).randomTicks()), () -> 1600);
    public static final Supplier<Block> CHARRED_PLANKS = regWithBurnableItem("charred_planks", () ->
        new CharredBlock(Properties.copy(CHARRED_LOG.get())), () -> 400);
    public static final Supplier<Block> CHARRED_SLAB = regWithBurnableItem("charred_slab", () ->
        new CharredSlabBlock(Properties.copy(CHARRED_LOG.get())), () -> 200);
    public static final Supplier<Block> CHARRED_STAIRS = regWithBurnableItem("charred_stairs", () ->
        new CharredStairsBlock(CHARRED_PLANKS, Properties.copy(CHARRED_LOG.get())), () -> 200);
    public static final Supplier<Block> CHARRED_FENCE = regWithBurnableItem("charred_fence", () ->
        new CharredFenceBlock(Properties.copy(CHARRED_LOG.get())), () -> 200);
    public static final Supplier<Block> CHARRED_FENCE_GATE = regWithBurnableItem("charred_fence_gate", () ->
        new CharredFenceGateBlock(Properties.copy(CHARRED_LOG.get()), WoodType.OAK), () -> 200);

    //vanilla completion blocks
    public static final Supplier<Block> STONE_WALL = regWithItem("stone_wall", () ->
        new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, Properties.copy(Blocks.COBBLESTONE_WALL)));

    public static final Supplier<Block> CHISELED_PRISMARINE_BRICKS = regWithItem("chiseled_prismarine_bricks", () ->
        new Block(Properties.copy(Blocks.PRISMARINE)));

    public static final Supplier<Block> PRISMARINE_BRICK_WALL = regWithItem("prismarine_brick_wall", () ->
        new CrackedWallBlock(Crackable.CrackLevel.UNCRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));

    public static final Supplier<Block> DARK_PRISMARINE_WALL = regWithItem("dark_prismarine_wall", () ->
        new WallBlock(Properties.copy(Blocks.DARK_PRISMARINE)));

    public static final Supplier<Block> TINTED_GLASS_PANE = regWithItem("tinted_glass_pane", () ->
        new TintedGlassPane(Properties.of().strength(0.3F).sound(SoundType.GLASS).noOcclusion().isRedstoneConductor(NEVER).isSuffocating(NEVER).isViewBlocking(NEVER)));


    private static BlockBehaviour.Properties noTick(Block copyFrom){
        var p = BlockBehaviour.Properties.copy(copyFrom);
        p.isRandomlyTicking = false;
        return p;
    }
}
