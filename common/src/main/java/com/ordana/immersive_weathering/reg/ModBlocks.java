package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.*;
import com.ordana.immersive_weathering.blocks.charred.*;
import com.ordana.immersive_weathering.blocks.crackable.*;
import com.ordana.immersive_weathering.blocks.frostable.FrostBlock;
import com.ordana.immersive_weathering.blocks.frostable.FrostyGlassBlock;
import com.ordana.immersive_weathering.blocks.frostable.FrostyGlassPaneBlock;
import com.ordana.immersive_weathering.blocks.frostable.FrostyGrassBlock;
import com.ordana.immersive_weathering.blocks.mossable.*;
import com.ordana.immersive_weathering.blocks.rustable.*;
import com.ordana.immersive_weathering.blocks.sandy.*;
import com.ordana.immersive_weathering.blocks.snowy.*;
import com.ordana.immersive_weathering.blocks.soil.*;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.mehvahdjukaar.moonlight.api.block.VerticalSlabBlock;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static com.ordana.immersive_weathering.reg.ModCreativeTab.getTab;

@SuppressWarnings("unused")
public class ModBlocks {

    public static void init() {
        BlockSetAPI.addDynamicBlockRegistration(ModBlocks::registerLeafPiles, LeavesType.class);
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    public static <T extends Block> Supplier<T> regBlock(String name, Supplier<T> block) {
        return RegHelper.registerBlock(ImmersiveWeathering.res(name), block);
    }

    public static <T extends Block> Supplier<T> regWithItem(String name, Supplier<T> block) {
        return regWithItem(name, block, getTab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    }

    public static <T extends Block> Supplier<T> regWithItem(String name, Supplier<T> blockFactory, CreativeModeTab tab) {
        Supplier<T> block = regBlock(name, blockFactory);
        regBlockItem(name, block, new Item.Properties().tab(tab));
        return block;
    }

    public static <T extends Block> Supplier<T> regWithItem(String name, Supplier<T> block, String requiredMod) {
        CreativeModeTab tab = isCompatBlockEanbled(requiredMod) ? getTab(CreativeModeTab.TAB_BUILDING_BLOCKS) : null;
        return regWithItem(name, block, tab);
    }

    private static boolean isCompatBlockEanbled(String requiredMod) {
        if(Objects.equals(requiredMod, "quark")) {
            if (PlatformHelper.getPlatform().isFabric()) {
                return requiredMod.equals("amogus");
            }else{
                return IntegrationHandler.quark && QuarkPlugin.isVerticalSlabsOn();
            }
        }
        return PlatformHelper.isModLoaded(requiredMod);
    }


    public static Supplier<BlockItem> regBlockItem(String name, Supplier<? extends Block> blockSup, Item.Properties properties) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), () -> new BlockItem(blockSup.get(), properties));
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


    public static Properties LEAF_PILE_PROPERTIES = Properties.of(Material.REPLACEABLE_PLANT)
            .randomTicks().instabreak().sound(SoundType.GRASS)
            .noOcclusion().isValidSpawn(CAN_SPAWN_ON_LEAVES)
            .isSuffocating(NEVER).isViewBlocking(NEVER);


    public static final Map<LeavesType, LeafPileBlock> LEAF_PILES = new LinkedHashMap<>();


    public static final Supplier<LeafPileBlock> AZALEA_FLOWER_PILE = regBlock("azalea_flower_pile", () ->
            new LeafPileBlock(LEAF_PILE_PROPERTIES.sound(SoundType.AZALEA), LeavesTypeRegistry.OAK_TYPE));

    //layer stuff

    public static final Supplier<Block> SAND_LAYER_BLOCK = regWithItem("sand_layer_block", () ->
            new SandLayerBlock(14406560, Properties.of(Material.TOP_SNOW, MaterialColor.SAND).strength(0.5f)
                    .sound(SoundType.SAND).isSuffocating(NEVER)
                    .isViewBlocking((blockState, blockView, blockPos) -> blockState.getValue(LayerBlock.LAYERS_8) >= 8)
                    .noOcclusion().requiresCorrectToolForDrops()));
    public static final Supplier<Block> RED_SAND_LAYER_BLOCK = regWithItem("red_sand_layer_block", () ->
            new SandLayerBlock(11098145, Properties.of(Material.TOP_SNOW, MaterialColor.COLOR_ORANGE).strength(0.5f)
                    .sound(SoundType.SAND).isSuffocating(NEVER)
                    .isViewBlocking((blockState, blockView, blockPos) -> blockState.getValue(LayerBlock.LAYERS_8) >= 8)
                    .noOcclusion().requiresCorrectToolForDrops()));
    public static final Supplier<Block> ASH_LAYER_BLOCK = regWithItem("ash_layer_block", () ->
            new LayerBlock(Properties.of(Material.TOP_SNOW, MaterialColor.COLOR_BLACK).instabreak()
                    .sound(SoundType.SNOW).isSuffocating(NEVER).noOcclusion().requiresCorrectToolForDrops()));
    public static final Supplier<Block> ASH_BLOCK = regWithItem("ash_block", () ->
            new AshBlock(Properties.of(Material.SNOW, MaterialColor.COLOR_BLACK).instabreak().sound(SoundType.SNOW)));
    public static final Supplier<Block> SOOT = regWithItem("soot", () ->
            new SootBlock(Properties.of(Material.TOP_SNOW, MaterialColor.COLOR_BLACK).noCollission().instabreak().sound(SoundType.SNOW).randomTicks()));
    public static final Supplier<Block> MOSS = regBlock("moss", () ->
            new MossMultifaceBlock(Properties.of(Material.MOSS).randomTicks().instabreak().sound(SoundType.MOSS_CARPET).noOcclusion().noCollission()));


    //vegetation

    public static final Supplier<Block> WEEDS = regWithItem("weeds", () ->
                    new WeedsBlock(Properties.of(Material.PLANT).noCollission()
                            .instabreak().sound(SoundType.GRASS)),
            getTab(CreativeModeTab.TAB_DECORATIONS));

    public static final Supplier<Block> HANGING_ROOTS_WALL = regBlock("hanging_roots_wall", () ->
            new WallRootsBlock(Properties.copy(Blocks.HANGING_ROOTS)));

    public static final Supplier<IvyBlock> IVY = regWithItem("ivy", () ->
            new IvyBlock(Properties.of(Material.PLANT).noCollission().strength(0.2f)
                    .sound(SoundType.AZALEA_LEAVES)));

    //mossy bricks stuff

    public static final Supplier<Block> MOSSY_BRICKS = regWithItem("mossy_bricks", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, Properties.of(Material.STONE, MaterialColor.COLOR_RED)
                    .requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final Supplier<Block> MOSSY_BRICK_STAIRS = regWithItem("mossy_brick_stairs", () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS, Properties.copy(MOSSY_BRICKS.get())));
    public static final Supplier<Block> MOSSY_BRICK_SLAB = regWithItem("mossy_brick_slab", () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_BRICKS.get())));
    public static final Supplier<Block> MOSSY_BRICK_WALL = regWithItem("mossy_brick_wall", () ->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_BRICKS.get())));
    public static final Supplier<Block> MOSSY_BRICK_VERTICAL_SLAB = regWithItem("mossy_brick_vertical_slab", () ->
            new MossyVerticalSlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_BRICKS.get())), "quark");

    //mossy stone

    public static final Supplier<Block> MOSSY_STONE = regWithItem("mossy_stone", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f)));
    public static final Supplier<Block> MOSSY_CHISELED_STONE_BRICKS = regWithItem("mossy_chiseled_stone_bricks", () ->
            new MossyBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_STAIRS = regWithItem("mossy_stone_stairs", () ->
            new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_SLAB = regWithItem("mossy_stone_slab", () ->
            new MossySlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_STONE.get())));
    public static final Supplier<Block> MOSSY_STONE_VERTICAL_SLAB = regWithItem("mossy_stone_vertical_slab", () ->
            new MossyVerticalSlabBlock(Mossable.MossLevel.MOSSY, Properties.copy(MOSSY_STONE_STAIRS.get())), "quark");
    public static final Supplier<Block> MOSSY_STONE_WALL = regWithItem("mossy_stone_wall", () ->
            new MossyWallBlock(Mossable.MossLevel.MOSSY, Properties.copy(Blocks.COBBLESTONE_WALL)));
    public static final Supplier<Block> STONE_WALL = regWithItem("stone_wall", () ->
            new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, Properties.copy(Blocks.COBBLESTONE_WALL)));

    //snow bricks

    public static final Supplier<Block> SNOW_BRICKS = regWithItem("snow_bricks", () ->
            new Block(Properties.copy(Blocks.SNOW_BLOCK)));
    public static final Supplier<Block> SNOW_BRICK_STAIRS = regWithItem("snow_brick_stairs", () ->
            new ModStairBlock(SNOW_BRICKS, Properties.copy(Blocks.SNOW_BLOCK)));
    public static final Supplier<Block> SNOW_BRICK_SLAB = regWithItem("snow_brick_slab", () ->
            new SlabBlock(Properties.copy(Blocks.SNOW_BLOCK)));
    public static final Supplier<Block> SNOW_BRICK_VERTICAL_SLAB = regWithItem("snow_brick_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(Blocks.SNOW_BLOCK)), "quark");
    public static final Supplier<Block> SNOW_BRICK_WALL = regWithItem("snow_brick_wall", () ->
            new WallBlock(Properties.copy(Blocks.SNOW_BLOCK)));

    //snowy stone

    public static final Supplier<Block> SNOWY_STONE = regWithItem("snowy_stone", () ->
            new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_STAIRS = regWithItem("snowy_stone_stairs", () ->
            new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_SLAB = regWithItem("snowy_stone_slab", () ->
            new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_VERTICAL_SLAB = regWithItem("snowy_stone_vertical_slab", () ->
            new SnowyVerticalSlabBlock(Properties.copy(SNOWY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SNOWY_STONE_WALL = regWithItem("snowy_stone_wall", () ->
            new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //snowy cobblestone

    public static final Supplier<Block> SNOWY_COBBLESTONE = regWithItem("snowy_cobblestone", () ->
            new SnowyBlock(Properties.copy(Blocks.COBBLESTONE).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_STAIRS = regWithItem("snowy_cobblestone_stairs", () ->
            new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_SLAB = regWithItem("snowy_cobblestone_slab", () ->
            new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_COBBLESTONE_VERTICAL_SLAB = regWithItem("snowy_cobblestone_vertical_slab", () ->
            new SnowyVerticalSlabBlock(Properties.copy(SNOWY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SNOWY_COBBLESTONE_WALL = regWithItem("snowy_cobblestone_wall", () ->
            new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //snowy stone bricks

    public static final Supplier<Block> SNOWY_STONE_BRICKS = regWithItem("snowy_stone_bricks", () ->
            new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_CHISELED_STONE_BRICKS = regWithItem("snowy_chiseled_stone_bricks", () ->
            new SnowyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_STAIRS = regWithItem("snowy_stone_brick_stairs", () ->
            new SnowyStairsBlock(SNOWY_STONE, Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_SLAB = regWithItem("snowy_stone_brick_slab", () ->
            new SnowySlabBlock(Properties.copy(SNOWY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SNOWY_STONE_BRICK_VERTICAL_SLAB = regWithItem("snowy_stone_brick_vertical_slab", () ->
            new SnowyVerticalSlabBlock(Properties.copy(SNOWY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SNOWY_STONE_BRICK_WALL = regWithItem("snowy_stone_brick_wall", () ->
            new SnowyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //sandy stone

    public static final Supplier<Block> SANDY_STONE = regWithItem("sandy_stone", () ->
            new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_STAIRS = regWithItem("sandy_stone_stairs", () ->
            new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_SLAB = regWithItem("sandy_stone_slab", () ->
            new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_VERTICAL_SLAB = regWithItem("sandy_stone_vertical_slab", () ->
            new SandyVerticalSlabBlock(Properties.copy(SANDY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SANDY_STONE_WALL = regWithItem("sandy_stone_wall", () ->
            new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //sandy cobblestone

    public static final Supplier<Block> SANDY_COBBLESTONE = regWithItem("sandy_cobblestone", () ->
            new SandyBlock(Properties.copy(Blocks.COBBLESTONE).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_STAIRS = regWithItem("sandy_cobblestone_stairs", () ->
            new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_SLAB = regWithItem("sandy_cobblestone_slab", () ->
            new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_COBBLESTONE_VERTICAL_SLAB = regWithItem("sandy_cobblestone_vertical_slab", () ->
            new SandyVerticalSlabBlock(Properties.copy(SANDY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SANDY_COBBLESTONE_WALL = regWithItem("sandy_cobblestone_wall", () ->
            new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //sandy stone bricks

    public static final Supplier<Block> SANDY_STONE_BRICKS = regWithItem("sandy_stone_bricks", () ->
            new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_CHISELED_STONE_BRICKS = regWithItem("sandy_chiseled_stone_bricks", () ->
            new SandyBlock(Properties.copy(Blocks.STONE).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_STAIRS = regWithItem("sandy_stone_brick_stairs", () ->
            new SandyStairsBlock(SANDY_STONE, Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_SLAB = regWithItem("sandy_stone_brick_slab", () ->
            new SandySlabBlock(Properties.copy(SANDY_STONE.get()).randomTicks()));
    public static final Supplier<Block> SANDY_STONE_BRICK_VERTICAL_SLAB = regWithItem("sandy_stone_brick_vertical_slab", () ->
            new SandyVerticalSlabBlock(Properties.copy(SANDY_STONE_STAIRS.get()).randomTicks()), "quark");
    public static final Supplier<Block> SANDY_STONE_BRICK_WALL = regWithItem("sandy_stone_brick_wall", () ->
            new SandyWallBlock(Properties.copy(Blocks.COBBLESTONE_WALL).randomTicks()));

    //cracked bricks

    public static final Supplier<Block> CRACKED_BRICKS = regWithItem("cracked_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2f, 6f)));
    public static final Supplier<Block> CRACKED_BRICK_STAIRS = regWithItem("cracked_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_BRICKS, () -> Items.BRICK,
                    Properties.copy(CRACKED_BRICKS.get())));
    public static final Supplier<Block> CRACKED_BRICK_SLAB = regWithItem("cracked_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    Properties.copy(CRACKED_BRICKS.get())));
    public static final Supplier<Block> CRACKED_BRICK_WALL = regWithItem("cracked_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    Properties.copy(CRACKED_BRICKS.get())));
    public static final Supplier<Block> CRACKED_STONE_VERTICAL_SLAB = regWithItem("cracked_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.BRICK,
                    Properties.copy(CRACKED_BRICK_SLAB.get())), "quark");

    //cracked stone bricks

    public static final Supplier<Block> CRACKED_STONE_BRICK_STAIRS = regWithItem("cracked_stone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_STONE_BRICKS, ModItems.STONE_BRICK,
                    Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final Supplier<Block> CRACKED_STONE_BRICK_SLAB = regWithItem("cracked_stone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final Supplier<Block> CRACKED_STONE_BRICK_WALL = regWithItem("cracked_stone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final Supplier<Block> CRACKED_STONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_stone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.STONE_BRICK,
                    Properties.copy(Blocks.CRACKED_STONE_BRICKS)), "quark");

    //cracked blackstone

    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS = regWithItem("cracked_polished_blackstone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK,
                    Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB = regWithItem("cracked_polished_blackstone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_WALL = regWithItem("cracked_polished_blackstone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)));
    public static final Supplier<Block> CRACKED_POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_polished_blackstone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.BLACKSTONE_BRICK,
                    Properties.copy(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)), "quark");

    //cracked nether bricks

    public static final Supplier<Block> CRACKED_NETHER_BRICK_STAIRS = regWithItem("cracked_nether_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_NETHER_BRICKS, () -> Items.NETHER_BRICK,
                    Properties.copy(Blocks.NETHER_BRICKS)));
    public static final Supplier<Block> CRACKED_NETHER_BRICK_SLAB = regWithItem("cracked_nether_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    Properties.copy(Blocks.NETHER_BRICKS)));
    public static final Supplier<Block> CRACKED_NETHER_BRICK_WALL = regWithItem("cracked_nether_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    Properties.copy(Blocks.NETHER_BRICKS)));
    public static final Supplier<Block> CRACKED_NETHER_BRICK_VERTICAL_SLAB = regWithItem("cracked_nether_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK,
                    Properties.copy(Blocks.NETHER_BRICKS)), "quark");

    //cracked deepslate

    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_STAIRS = regWithItem("cracked_deepslate_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_SLAB = regWithItem("cracked_deepslate_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_WALL = regWithItem("cracked_deepslate_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_BRICK_VERTICAL_SLAB = regWithItem("cracked_deepslate_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_BRICK,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_BRICKS)), "quark");

    //cracked deepslate tile

    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_STAIRS = regWithItem("cracked_deepslate_tile_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, () -> Blocks.CRACKED_DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_SLAB = regWithItem("cracked_deepslate_tile_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_WALL = regWithItem("cracked_deepslate_tile_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)));
    public static final Supplier<Block> CRACKED_DEEPSLATE_TILE_VERTICAL_SLAB = regWithItem("cracked_deepslate_tile_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.DEEPSLATE_TILE,
                    Properties.copy(Blocks.CRACKED_DEEPSLATE_TILES)), "quark");

    //mulch

    public static final Supplier<Block> MULCH_BLOCK = regWithItem("mulch_block", () ->
            new MulchBlock(Properties.of(Material.DIRT).strength(1f, 1f)
                    .sound(SoundType.ROOTED_DIRT).randomTicks()));
    public static final Supplier<Block> NULCH_BLOCK = regWithItem("nulch_block", () ->
            new NulchBlock(Properties.of(Material.DIRT).strength(1f, 1f)
                    .sound(SoundType.NETHER_WART).lightLevel(moltenLightLevel(10)).randomTicks()));

    //cut iron

    public static final Supplier<Block> CUT_IRON = regWithItem("cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(Blocks.IRON_BLOCK)));
    public static final Supplier<Block> EXPOSED_CUT_IRON = regWithItem("exposed_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_CUT_IRON = regWithItem("weathered_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_CUT_IRON = regWithItem("rusted_cut_iron", () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())));

    //cut iron stairs

    public static final Supplier<Block> CUT_IRON_STAIRS = regWithItem("cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> EXPOSED_CUT_IRON_STAIRS = regWithItem("exposed_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_CUT_IRON_STAIRS = regWithItem("weathered_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_CUT_IRON_STAIRS = regWithItem("rusted_cut_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, CUT_IRON, Properties.copy(CUT_IRON.get())));

    //cur iron slabs

    public static final Supplier<Block> CUT_IRON_SLAB = regWithItem("cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> EXPOSED_CUT_IRON_SLAB = regWithItem("exposed_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_CUT_IRON_SLAB = regWithItem("weathered_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_CUT_IRON_SLAB = regWithItem("rusted_cut_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())));

    //waxed cut iron

    public static final Supplier<Block> WAXED_CUT_IRON = regWithItem("waxed_cut_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_CUT_IRON = regWithItem("waxed_exposed_cut_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_CUT_IRON = regWithItem("waxed_weathered_cut_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_CUT_IRON = regWithItem("waxed_rusted_cut_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));

    //waxed cut iron stairs

    public static final Supplier<Block> WAXED_CUT_IRON_STAIRS = regWithItem("waxed_cut_iron_stairs", () ->
            new ModStairBlock(WAXED_CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_CUT_IRON_STAIRS = regWithItem("waxed_exposed_cut_iron_stairs", () ->
            new ModStairBlock(WAXED_EXPOSED_CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_CUT_IRON_STAIRS = regWithItem("waxed_weathered_cut_iron_stairs", () ->
            new ModStairBlock(WAXED_WEATHERED_CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_CUT_IRON_STAIRS = regWithItem("waxed_rusted_cut_iron_stairs", () ->
            new ModStairBlock(WAXED_RUSTED_CUT_IRON, Properties.copy(CUT_IRON.get())));

    //waxed cut iron slabs

    public static final Supplier<Block> WAXED_CUT_IRON_SLAB = regWithItem("waxed_cut_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_CUT_IRON_SLAB = regWithItem("waxed_exposed_cut_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_CUT_IRON_SLAB = regWithItem("waxed_weathered_cut_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_CUT_IRON_SLAB = regWithItem("waxed_rusted_cut_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));


    //plate iron
    public static final Supplier<Block> PLATE_IRON = regWithItem("plate_iron", () ->
            new RustableBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> EXPOSED_PLATE_IRON = regWithItem("exposed_plate_iron", () ->
            new RustableBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_PLATE_IRON = regWithItem("weathered_plate_iron", () ->
            new RustableBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_PLATE_IRON = regWithItem("rusted_plate_iron", () ->
            new RustableBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())));

    //plate iron stairs

    public static final Supplier<Block> PLATE_IRON_STAIRS = regWithItem("plate_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.UNAFFECTED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> EXPOSED_PLATE_IRON_STAIRS = regWithItem("exposed_plate_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.EXPOSED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_PLATE_IRON_STAIRS = regWithItem("weathered_plate_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.WEATHERED, CUT_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_PLATE_IRON_STAIRS = regWithItem("rusted_plate_iron_stairs", () ->
            new RustableStairsBlock(Rustable.RustLevel.RUSTED, CUT_IRON, Properties.copy(CUT_IRON.get())));

    //plate iron slab

    public static final Supplier<Block> PLATE_IRON_SLAB = regWithItem("plate_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> EXPOSED_PLATE_IRON_SLAB = regWithItem("exposed_plate_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WEATHERED_PLATE_IRON_SLAB = regWithItem("weathered_plate_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> RUSTED_PLATE_IRON_SLAB = regWithItem("rusted_plate_iron_slab", () ->
            new RustableSlabBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())));

    //waxed plate iron

    public static final Supplier<Block> WAXED_PLATE_IRON = regWithItem("waxed_plate_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_PLATE_IRON = regWithItem("waxed_exposed_plate_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_PLATE_IRON = regWithItem("waxed_weathered_plate_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_PLATE_IRON = regWithItem("waxed_rusted_plate_iron", () ->
            new Block(Properties.copy(CUT_IRON.get())));

    //waxed plate iron stairs

    public static final Supplier<Block> WAXED_PLATE_IRON_STAIRS = regWithItem("waxed_plate_iron_stairs", () ->
            new ModStairBlock(WAXED_PLATE_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_PLATE_IRON_STAIRS = regWithItem("waxed_exposed_plate_iron_stairs", () ->
            new ModStairBlock(WAXED_EXPOSED_PLATE_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_PLATE_IRON_STAIRS = regWithItem("waxed_weathered_plate_iron_stairs", () ->
            new ModStairBlock(WAXED_WEATHERED_PLATE_IRON, Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_PLATE_IRON_STAIRS = regWithItem("waxed_rusted_plate_iron_stairs", () ->
            new ModStairBlock(WAXED_RUSTED_PLATE_IRON, Properties.copy(CUT_IRON.get())));

    //waxed plate iron slab

    public static final Supplier<Block> WAXED_PLATE_IRON_SLAB = regWithItem("waxed_plate_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_EXPOSED_PLATE_IRON_SLAB = regWithItem("waxed_exposed_plate_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_WEATHERED_PLATE_IRON_SLAB = regWithItem("waxed_weathered_plate_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));
    public static final Supplier<Block> WAXED_RUSTED_PLATE_IRON_SLAB = regWithItem("waxed_rusted_plate_iron_slab", () ->
            new SlabBlock(Properties.copy(CUT_IRON.get())));

    //iron door

    public static final Supplier<Block> EXPOSED_IRON_DOOR = regWithItem("exposed_iron_door", () ->
            new RustableDoorBlock(Rustable.RustLevel.EXPOSED, Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> WEATHERED_IRON_DOOR = regWithItem("weathered_iron_door", () ->
            new RustableDoorBlock(Rustable.RustLevel.WEATHERED, Properties.copy(EXPOSED_IRON_DOOR.get())));
    public static final Supplier<Block> RUSTED_IRON_DOOR = regWithItem("rusted_iron_door", () ->
            new RustableDoorBlock(Rustable.RustLevel.RUSTED, Properties.copy(EXPOSED_IRON_DOOR.get())));

    //iron trapdoor

    public static final Supplier<Block> EXPOSED_IRON_TRAPDOOR = regWithItem("exposed_iron_trapdoor", () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.EXPOSED, Properties.copy(Blocks.IRON_TRAPDOOR)));
    public static final Supplier<Block> WEATHERED_IRON_TRAPDOOR = regWithItem("weathered_iron_trapdoor", () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.WEATHERED, Properties.copy(EXPOSED_IRON_TRAPDOOR.get())));
    public static final Supplier<Block> RUSTED_IRON_TRAPDOOR = regWithItem("rusted_iron_trapdoor", () ->
            new RustableTrapdoorBlock(Rustable.RustLevel.RUSTED, Properties.copy(EXPOSED_IRON_TRAPDOOR.get())));

    //iron bars

    public static final Supplier<Block> EXPOSED_IRON_BARS = regWithItem("exposed_iron_bars", () ->
            new RustableBarsBlock(Rustable.RustLevel.EXPOSED, Properties.copy(Blocks.IRON_BARS)));
    public static final Supplier<Block> WEATHERED_IRON_BARS = regWithItem("weathered_iron_bars", () ->
            new RustableBarsBlock(Rustable.RustLevel.WEATHERED, Properties.copy(EXPOSED_IRON_BARS.get())));
    public static final Supplier<Block> RUSTED_IRON_BARS = regWithItem("rusted_iron_bars", () ->
            new RustableBarsBlock(Rustable.RustLevel.RUSTED, Properties.copy(EXPOSED_IRON_BARS.get())));

    //waxed iron door

    public static final Supplier<Block> WAXED_IRON_DOOR = regWithItem("waxed_iron_door", () ->
            new RustAffectedDoorBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(Blocks.IRON_DOOR)) {
            });
    public static final Supplier<Block> WAXED_EXPOSED_IRON_DOOR = regWithItem("waxed_exposed_iron_door", () ->
            new RustAffectedDoorBlock(Rustable.RustLevel.EXPOSED, Properties.copy(WAXED_IRON_DOOR.get())) {
            });
    public static final Supplier<Block> WAXED_WEATHERED_IRON_DOOR = regWithItem("waxed_weathered_iron_door", () ->
            new RustAffectedDoorBlock(Rustable.RustLevel.WEATHERED, Properties.copy(WAXED_IRON_DOOR.get())) {
            });
    public static final Supplier<Block> WAXED_RUSTED_IRON_DOOR = regWithItem("waxed_rusted_iron_door", () ->
            new RustAffectedDoorBlock(Rustable.RustLevel.RUSTED, Properties.copy(WAXED_IRON_DOOR.get())) {
            });

    //waxed trapdoor
    public static final Supplier<Block> WAXED_IRON_TRAPDOOR = regWithItem("waxed_iron_trapdoor", () ->
            new TrapDoorBlock(Properties.copy(Blocks.IRON_TRAPDOOR)) {
            });
    public static final Supplier<Block> WAXED_EXPOSED_IRON_TRAPDOOR = regWithItem("waxed_exposed_iron_trapdoor", () ->
            new RustAffectedTrapdoorBlock(Rustable.RustLevel.EXPOSED, Properties.copy(WAXED_IRON_TRAPDOOR.get())) {
            });
    public static final Supplier<Block> WAXED_WEATHERED_IRON_TRAPDOOR = regWithItem("waxed_weathered_iron_trapdoor", () ->
            new RustAffectedTrapdoorBlock(Rustable.RustLevel.WEATHERED, Properties.copy(WAXED_IRON_TRAPDOOR.get())) {
            });
    public static final Supplier<Block> WAXED_RUSTED_IRON_TRAPDOOR = regWithItem("waxed_rusted_iron_trapdoor", () ->
            new RustAffectedTrapdoorBlock(Rustable.RustLevel.RUSTED, Properties.copy(WAXED_IRON_TRAPDOOR.get())) {
            });

    //waxed iron bars

    //TODO: rethink sound since its diff that iron bars
    public static final Supplier<Block> WAXED_IRON_BARS = regWithItem("waxed_iron_bars", () ->
            new IronBarsBlock(Properties.copy(EXPOSED_IRON_BARS.get())) {
            });
    public static final Supplier<Block> WAXED_EXPOSED_IRON_BARS = regWithItem("waxed_exposed_iron_bars", () ->
            new IronBarsBlock(Properties.copy(EXPOSED_IRON_BARS.get())) {
            });
    public static final Supplier<Block> WAXED_WEATHERED_IRON_BARS = regWithItem("waxed_weathered_iron_bars", () ->
            new IronBarsBlock(Properties.copy(EXPOSED_IRON_BARS.get())) {
            });
    public static final Supplier<Block> WAXED_RUSTED_IRON_BARS = regWithItem("waxed_rusted_iron_bars", () ->
            new IronBarsBlock(Properties.copy(EXPOSED_IRON_BARS.get())) {
            });

    //cracked end stone

    public static final Supplier<Block> CRACKED_END_STONE_BRICKS = regWithItem("cracked_end_stone_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_STAIRS = regWithItem("cracked_end_stone_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_SLAB = regWithItem("cracked_end_stone_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_WALL = regWithItem("cracked_end_stone_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK, Properties.copy(Blocks.END_STONE)));
    public static final Supplier<Block> CRACKED_END_STONE_BRICK_VERTICAL_SLAB = regWithItem("cracked_end_stone_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.END_STONE_BRICK,
                    Properties.copy(CRACKED_END_STONE_BRICK_SLAB.get())), "quark");

    //cracked prismarine

    public static final Supplier<Block> CRACKED_PRISMARINE_BRICKS = regWithItem("cracked_prismarine_bricks", () ->
            new CrackedBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_STAIRS = regWithItem("cracked_prismarine_brick_stairs", () ->
            new CrackedStairsBlock(Crackable.CrackLevel.CRACKED, CRACKED_END_STONE_BRICKS, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_SLAB = regWithItem("cracked_prismarine_brick_slab", () ->
            new CrackedSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_WALL = regWithItem("cracked_prismarine_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));
    public static final Supplier<Block> CRACKED_PRISMARINE_BRICK_VERTICAL_SLAB = regWithItem("cracked_prismarine_brick_vertical_slab", () ->
            new CrackedVerticalSlabBlock(Crackable.CrackLevel.CRACKED, ModItems.PRISMARINE_BRICK,
                    Properties.copy(CRACKED_PRISMARINE_BRICK_SLAB.get())), "quark");

    //chiseled prismarine bricks

    public static final Supplier<Block> CHISELED_PRISMARINE_BRICKS = regWithItem("chiseled_prismarine_bricks", () ->
            new Block(Properties.copy(Blocks.PRISMARINE)));

    //prismarine brick wall

    public static final Supplier<Block> PRISMARINE_BRICK_WALL = regWithItem("prismarine_brick_wall", () ->
            new CrackedWallBlock(Crackable.CrackLevel.UNCRACKED, ModItems.PRISMARINE_BRICK, Properties.copy(Blocks.PRISMARINE)));

    //dark prismarine wall

    public static final Supplier<Block> DARK_PRISMARINE_WALL = regWithItem("dark_prismarine_wall", () ->
            new WallBlock(Properties.copy(Blocks.DARK_PRISMARINE)));

    //vitrified sand

    public static final Supplier<Block> FULGURITE = regWithItem("fulgurite", () ->
            new FulguriteBlock(7, 3, Properties.copy(Blocks.GLASS)
                    .instabreak().lightLevel((s) -> s.getValue(FulguriteBlock.POWERED) ? 5 : 0)
                    .dynamicShape().requiresCorrectToolForDrops()));

    public static final Supplier<Block> VITRIFIED_SAND = regWithItem("vitrified_sand", () ->
            new GlassBlock(Properties.of(Material.GLASS, MaterialColor.TERRACOTTA_YELLOW)
                    .strength(2f, 6f).sound(SoundType.TUFF)
                    .requiresCorrectToolForDrops().noOcclusion().isViewBlocking((s, l, p) -> false)));

    //soil blocks

    public static final Supplier<Block> HUMUS = regWithItem("humus", () ->
            new SoilBlock(Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_GREEN)
                    .strength(0.5f).sound(SoundType.GRAVEL)));
    public static final Supplier<Block> FLUVISOL = regWithItem("fluvisol", () ->
            new FluvisolBlock(Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN)
                    .strength(0.5F).sound(SoundType.WART_BLOCK).randomTicks()));
    public static final Supplier<Block> SILT = regWithItem("silt", () ->
            new FluvisolBlock(Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN)
                    .strength(0.5F).sound(SoundType.WART_BLOCK).randomTicks()));

    public static final Supplier<Block> VERTISOL = regWithItem("vertisol", () ->
            new CrackedMudBlock(Properties.of(Material.DIRT, MaterialColor.DIRT)
                    .strength(0.5F).sound(SoundType.BASALT).randomTicks()));

    public static final Supplier<Block> CRACKED_MUD = regWithItem("cracked_mud", () ->
            new CrackedMudBlock(Properties.of(Material.DIRT, MaterialColor.DIRT)
                    .strength(2.5F).sound(SoundType.BASALT).randomTicks()));
    public static final Supplier<Block> CRYOSOL = regWithItem("cryosol", () ->
            new SoilBlock(Properties.of(Material.DIRT, MaterialColor.SNOW)
                    .strength(0.5F).sound(SoundType.TUFF).randomTicks()));
    public static final Supplier<Block> PERMAFROST = regWithItem("permafrost", () ->
            new PermafrostBlock(Properties.of(Material.ICE_SOLID, MaterialColor.CLAY)
                    .strength(3F).friction(1F).sound(SoundType.TUFF).randomTicks()));

    public static final Supplier<Block> ROOTED_GRASS_BLOCK = regWithItem("rooted_grass_block", () ->
            new RootedGrassBlock(Properties.copy(Blocks.GRASS_BLOCK).randomTicks().strength(0.5F)
                    .sound(SoundType.ROOTED_DIRT)));


    //vertical slab

    public static final Supplier<Block> CUT_IRON_VERTICAL_SLAB = regWithItem("cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> EXPOSED_CUT_IRON_VERTICAL_SLAB = regWithItem("exposed_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WEATHERED_CUT_IRON_VERTICAL_SLAB = regWithItem("weathered_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> RUSTED_CUT_IRON_VERTICAL_SLAB = regWithItem("rusted_cut_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())), "quark");

    public static final Supplier<Block> WAXED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_EXPOSED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_exposed_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_WEATHERED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_weathered_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_RUSTED_CUT_IRON_VERTICAL_SLAB = regWithItem("waxed_rusted_cut_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");

    public static final Supplier<Block> PLATE_IRON_VERTICAL_SLAB = regWithItem("plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.UNAFFECTED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> EXPOSED_PLATE_IRON_VERTICAL_SLAB = regWithItem("exposed_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.EXPOSED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WEATHERED_PLATE_IRON_VERTICAL_SLAB = regWithItem("weathered_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.WEATHERED, Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> RUSTED_PLATE_IRON_VERTICAL_SLAB = regWithItem("rusted_plate_iron_vertical_slab", () ->
            new RustableVerticalSlabBlock(Rustable.RustLevel.RUSTED, Properties.copy(CUT_IRON.get())), "quark");

    public static final Supplier<Block> WAXED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_EXPOSED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_exposed_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_WEATHERED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_weathered_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");
    public static final Supplier<Block> WAXED_RUSTED_PLATE_IRON_VERTICAL_SLAB = regWithItem("waxed_rusted_plate_iron_vertical_slab", () ->
            new VerticalSlabBlock(Properties.copy(CUT_IRON.get())), "quark");

    //frost

    public static final Supplier<Block> ICICLE = regBlock("icicle", () ->
            new IcicleBlock(Properties.of(Material.ICE).randomTicks().instabreak()
                    .sound(SoundType.GLASS).noOcclusion().dynamicShape()));

    public static final Supplier<Block> FROST = regBlock("frost", () ->
            new FrostBlock(Properties.of(Material.TOP_SNOW)
                    .randomTicks().instabreak().sound(SoundType.POWDER_SNOW).noOcclusion().noCollission()));

    public static final Supplier<Block> FROSTY_GRASS = regWithItem("frosty_grass", () ->
            new FrostyGrassBlock(Properties.copy(Blocks.GRASS).color(MaterialColor.SNOW)
                    .randomTicks().sound(SoundType.POWDER_SNOW)));

    public static final Supplier<Block> FROSTY_FERN = regWithItem("frosty_fern", () ->
            new FrostyGrassBlock(Properties.copy(FROSTY_GRASS.get())));

    public static final Supplier<Block> FROSTY_GLASS = regWithItem("frosty_glass", () ->
            new FrostyGlassBlock(Properties.copy(Blocks.GLASS).randomTicks()));

    public static final Supplier<Block> FROSTY_GLASS_PANE = regWithItem("frosty_glass_pane", () ->
            new FrostyGlassPaneBlock(Properties.copy(Blocks.GLASS_PANE).randomTicks()));

    public static final Supplier<Block> THIN_ICE = regBlock("thin_ice", () ->
            new ThinIceBlock(Properties.copy(Blocks.ICE)
                    .isViewBlocking(NEVER).isSuffocating(NEVER).isViewBlocking(NEVER)));


    //charred blocks

    public static final Supplier<Block> CHARRED_LOG = regWithItem("charred_log", () ->
            new CharredPillarBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(1.5f, 0.5f).sound(SoundType.BASALT)
                    .lightLevel(litLightLevel(5)).randomTicks()));

    public static final Supplier<Block> CHARRED_PLANKS = regWithItem("charred_planks", () ->
            new CharredBlock(Properties.copy(CHARRED_LOG.get())));

    public static final Supplier<Block> CHARRED_SLAB = regWithItem("charred_slab", () ->
            new CharredSlabBlock(Properties.copy(CHARRED_LOG.get())));

    public static final Supplier<Block> CHARRED_VERTICAL_SLAB = regWithItem("charred_vertical_slab", () ->
            new CharredVerticalSlabBlock(Properties.copy(CHARRED_LOG.get())));

    public static final Supplier<Block> CHARRED_STAIRS = regWithItem("charred_stairs", () ->
            new CharredStairsBlock(CHARRED_PLANKS, Properties.copy(CHARRED_LOG.get())));

    public static final Supplier<Block> CHARRED_FENCE = regWithItem("charred_fence", () ->
            new CharredFenceBlock(Properties.copy(CHARRED_LOG.get())));

    public static final Supplier<Block> CHARRED_FENCE_GATE = regWithItem("charred_fence_gate", () ->
            new CharredFenceGateBlock(Properties.copy(CHARRED_LOG.get())));



    private static void registerLeafPiles(Registrator<Block> event, Collection<LeavesType> leavesTypes) {
        for (LeavesType type : leavesTypes) {
            String name = type.getVariantId("leaf_pile", false);

            LeafPileBlock block = new LeafPileBlock(LEAF_PILE_PROPERTIES, type);
            event.register(ImmersiveWeathering.res(name), block);

            LEAF_PILES.put(type, block);
            type.addChild("immersive_weathering:leaf_pile", block);
        }
    }
}
