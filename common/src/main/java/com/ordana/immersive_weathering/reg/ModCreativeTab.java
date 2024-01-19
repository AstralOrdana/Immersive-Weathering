package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ModCreativeTab {

    public static final RegSupplier<CreativeModeTab> MOD_TAB = !CommonConfigs.CREATIVE_TAB.get() ? null :
        RegHelper.registerCreativeModeTab(ImmersiveWeathering.res("immersive_weathering"),
            (c) -> c.title(Component.translatable("itemGroup.immersive_weathering.immersive_weathering"))
                .icon(() -> ModBlocks.IVY.get().asItem().getDefaultInstance()));

    public static void init(){
        RegHelper.addItemsToTabsRegistration(ModCreativeTab::addItems);
    }

    public static void addItems(RegHelper.ItemToTabEvent e) {

        //BUILDING BLOCKS
        after(e, Items.BAMBOO_BUTTON, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CHARRED_LOG, ModBlocks.CHARRED_PLANKS, ModBlocks.CHARRED_STAIRS,
            ModBlocks.CHARRED_SLAB, ModBlocks.CHARRED_FENCE, ModBlocks.CHARRED_FENCE_GATE
        );

        after(e, Items.STONE_SLAB, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.STONE_WALL
        );

        after(e, Items.STONE_BUTTON, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.MOSSY_STONE, ModBlocks.MOSSY_STONE_STAIRS, ModBlocks.MOSSY_STONE_SLAB, ModBlocks.MOSSY_STONE_WALL,
            ModBlocks.SNOWY_STONE, ModBlocks.SNOWY_STONE_STAIRS, ModBlocks.SNOWY_STONE_SLAB, ModBlocks.SNOWY_STONE_WALL,
            ModBlocks.SANDY_STONE, ModBlocks.SANDY_STONE_STAIRS, ModBlocks.SANDY_STONE_SLAB, ModBlocks.SANDY_STONE_WALL
        );

        after(e, Items.MOSSY_COBBLESTONE_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.SNOWY_COBBLESTONE, ModBlocks.SNOWY_COBBLESTONE_STAIRS, ModBlocks.SNOWY_COBBLESTONE_SLAB, ModBlocks.SNOWY_COBBLESTONE_WALL,
            ModBlocks.SANDY_COBBLESTONE, ModBlocks.SANDY_COBBLESTONE_STAIRS, ModBlocks.SANDY_COBBLESTONE_SLAB, ModBlocks.SANDY_COBBLESTONE_WALL
        );

        after(e, Items.CHISELED_STONE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_WALL
        );

        after(e, Items.MOSSY_STONE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.MOSSY_CHISELED_STONE_BRICKS,
            ModBlocks.SNOWY_STONE_BRICKS, ModBlocks.SNOWY_STONE_BRICK_STAIRS, ModBlocks.SNOWY_STONE_BRICK_SLAB, ModBlocks.SNOWY_STONE_BRICK_WALL, ModBlocks.SNOWY_CHISELED_STONE_BRICKS,
            ModBlocks.SANDY_STONE_BRICKS, ModBlocks.SANDY_STONE_BRICK_STAIRS, ModBlocks.SANDY_STONE_BRICK_SLAB, ModBlocks.SANDY_STONE_BRICK_WALL, ModBlocks.SANDY_CHISELED_STONE_BRICKS
        );

        after(e, Items.DEEPSLATE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL
        );

        after(e, Items.DEEPSLATE_TILE_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL
        );

        after(e, Items.BRICKS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_BRICKS
        );

        after(e, Items.BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_BRICK_SLAB, ModBlocks.CRACKED_BRICK_STAIRS, ModBlocks.CRACKED_BRICK_WALL,
            ModBlocks.MOSSY_BRICKS, ModBlocks.MOSSY_BRICK_SLAB, ModBlocks.MOSSY_BRICK_STAIRS, ModBlocks.MOSSY_BRICK_WALL
        );

        after(e, Items.PRISMARINE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_PRISMARINE_BRICKS
        );

        after(e, Items.PRISMARINE_BRICK_SLAB, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.PRISMARINE_BRICK_WALL, ModBlocks.CHISELED_PRISMARINE_BRICKS,
            ModBlocks.CRACKED_PRISMARINE_BRICK_STAIRS, ModBlocks.CRACKED_PRISMARINE_BRICK_SLAB, ModBlocks.CRACKED_PRISMARINE_BRICK_WALL
        );

        after(e, Items.DARK_PRISMARINE_SLAB, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.DARK_PRISMARINE_WALL
        );

        after(e, Items.CHISELED_NETHER_BRICKS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_WALL
        );

        after(e, Items.POLISHED_BLACKSTONE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL
        );

        after(e, Items.END_STONE_BRICKS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_END_STONE_BRICKS
        );

        after(e, Items.END_STONE_BRICK_WALL, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.CRACKED_END_STONE_BRICK_STAIRS, ModBlocks.CRACKED_END_STONE_BRICK_SLAB, ModBlocks.CRACKED_END_STONE_BRICK_WALL
        );

        after(e, Items.IRON_BLOCK, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.PLATE_IRON, ModBlocks.PLATE_IRON_STAIRS, ModBlocks.PLATE_IRON_SLAB,
            ModBlocks.CUT_IRON, ModBlocks.CUT_IRON_STAIRS, ModBlocks.CUT_IRON_SLAB,
            ModBlocks.EXPOSED_PLATE_IRON, ModBlocks.EXPOSED_PLATE_IRON_STAIRS, ModBlocks.EXPOSED_PLATE_IRON_SLAB,
            ModBlocks.EXPOSED_CUT_IRON, ModBlocks.EXPOSED_CUT_IRON_STAIRS, ModBlocks.EXPOSED_CUT_IRON_SLAB,
            ModBlocks.WEATHERED_PLATE_IRON, ModBlocks.WEATHERED_PLATE_IRON_STAIRS, ModBlocks.WEATHERED_PLATE_IRON_SLAB,
            ModBlocks.WEATHERED_CUT_IRON, ModBlocks.WEATHERED_CUT_IRON_STAIRS, ModBlocks.WEATHERED_CUT_IRON_SLAB,
            ModBlocks.RUSTED_PLATE_IRON, ModBlocks.RUSTED_PLATE_IRON_STAIRS, ModBlocks.RUSTED_PLATE_IRON_SLAB,
            ModBlocks.RUSTED_CUT_IRON, ModBlocks.RUSTED_CUT_IRON_STAIRS, ModBlocks.RUSTED_CUT_IRON_SLAB,
            ModBlocks.WAXED_PLATE_IRON, ModBlocks.WAXED_PLATE_IRON_STAIRS, ModBlocks.WAXED_PLATE_IRON_SLAB,
            ModBlocks.WAXED_CUT_IRON, ModBlocks.WAXED_CUT_IRON_STAIRS, ModBlocks.WAXED_CUT_IRON_SLAB,
            ModBlocks.WAXED_EXPOSED_PLATE_IRON, ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS, ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB,
            ModBlocks.WAXED_EXPOSED_CUT_IRON, ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS, ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB,
            ModBlocks.WAXED_WEATHERED_PLATE_IRON, ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS, ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB,
            ModBlocks.WAXED_WEATHERED_CUT_IRON, ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS, ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB,
            ModBlocks.WAXED_RUSTED_PLATE_IRON, ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS, ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB,
            ModBlocks.WAXED_RUSTED_CUT_IRON, ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS, ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB
        );

        after(e, Items.IRON_BARS, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.EXPOSED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS, ModBlocks.RUSTED_IRON_BARS,
            ModBlocks.WAXED_IRON_BARS, ModBlocks.WAXED_EXPOSED_IRON_BARS, ModBlocks.WAXED_WEATHERED_IRON_BARS, ModBlocks.WAXED_RUSTED_IRON_BARS
        );

        after(e, Items.IRON_DOOR, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.RUSTED_IRON_DOOR,
            ModBlocks.WAXED_IRON_DOOR, ModBlocks.WAXED_EXPOSED_IRON_DOOR, ModBlocks.WAXED_WEATHERED_IRON_DOOR, ModBlocks.WAXED_RUSTED_IRON_DOOR
        );

        after(e, Items.IRON_TRAPDOOR, CreativeModeTabs.BUILDING_BLOCKS,
            ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.RUSTED_IRON_TRAPDOOR,
            ModBlocks.WAXED_IRON_TRAPDOOR, ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR, ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR, ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR
        );

        //COLORED BLOCKS
        after(e, Items.GLASS, CreativeModeTabs.COLORED_BLOCKS,
            ModBlocks.FROSTY_GLASS
        );

        after(e, Items.GLASS_PANE, CreativeModeTabs.COLORED_BLOCKS,
            ModBlocks.FROSTY_GLASS_PANE
        );


        //NATURAL BLOCKS
        after(e, Items.PODZOL, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.LOAM
        );

        after(e, Items.ROOTED_DIRT, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.ROOTED_GRASS_BLOCK,
            ModBlocks.EARTHEN_CLAY, ModBlocks.SANDY_DIRT, ModBlocks.SILT, ModBlocks.PERMAFROST,
            ModBlocks.GRASSY_EARTHEN_CLAY, ModBlocks.GRASSY_SANDY_DIRT, ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_PERMAFROST
            );

        after(e, Items.FARMLAND, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.LOAMY_FARMLAND,
            ModBlocks.EARTHEN_CLAY_FARMLAND, ModBlocks.SANDY_FARMLAND, ModBlocks.SILTY_FARMLAND,
            ModBlocks.MULCH_BLOCK, ModBlocks.NULCH_BLOCK
        );

        before(e, Items.ICE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.SOOT, ModBlocks.FROST
        );

        after(e, Items.SANDSTONE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.SAND_LAYER_BLOCK
        );

        after(e, Items.RED_SANDSTONE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.RED_SAND_LAYER_BLOCK
        );

        after(e, Items.ICE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.ICICLE, ModBlocks.THIN_ICE
        );

        after(e, Items.MOSS_CARPET, CreativeModeTabs.NATURAL_BLOCKS,
            ModItems.MOSS_CLUMP
        );

        after(e, Items.POINTED_DRIPSTONE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.VITRIFIED_SAND, ModBlocks.FULGURITE
        );

        after(e, Items.FERN, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.FROSTY_GRASS, ModBlocks.FROSTY_FERN
        );

        after(e, Items.FLOWERING_AZALEA_LEAVES, CreativeModeTabs.NATURAL_BLOCKS,
            ModItems.AZALEA_FLOWER_PILE
        );

        after(e, Items.FLOWERING_AZALEA_LEAVES, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.LEAF_PILES.values().stream().map(s-> (Supplier<Object>) () -> s).toArray(Supplier[]::new)
        );

        after(e, Items.VINE, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.IVY
        );

        after(e, Items.BEETROOT_SEEDS, CreativeModeTabs.NATURAL_BLOCKS,
            ModBlocks.WEEDS
        );


        //FOOD
        after(e, Items.ENCHANTED_GOLDEN_APPLE, CreativeModeTabs.FOOD_AND_DRINKS,
            ModItems.MOSS_CLUMP, ModItems.GOLDEN_MOSS_CLUMP, ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP
        );


        //COMBAT
        after(e, Items.TRIDENT, CreativeModeTabs.COMBAT,
            ModItems.ICE_SICKLE
        );

        before(e, Items.LEATHER_HELMET, CreativeModeTabs.COMBAT,
            ModItems.FLOWER_CROWN
        );


        //INGREDIENTS
        before(e, Items.BRICK, CreativeModeTabs.INGREDIENTS,
            ModItems.STONE_BRICK
        );

        after(e, Items.BRICK, CreativeModeTabs.INGREDIENTS,
            ModItems.DEEPSLATE_BRICK, ModItems.DEEPSLATE_TILE, ModItems.PRISMARINE_BRICK
        );

        after(e, Items.NETHER_BRICK, CreativeModeTabs.INGREDIENTS,
            ModItems.BLACKSTONE_BRICK, ModItems.END_STONE_BRICK
        );

        after(e, Items.HONEYCOMB, CreativeModeTabs.INGREDIENTS,
            ModItems.TALLOW
        );

        before(e, Items.INK_SAC, CreativeModeTabs.INGREDIENTS,
            ModItems.AZALEA_FLOWERS
        );

        before(e, Items.INK_SAC, CreativeModeTabs.INGREDIENTS,
            ModItems.BARK.values().stream().map(s-> (Supplier<Object>) () -> s).toArray(Supplier[]::new)
        );

        after(e, Items.BARRIER, CreativeModeTabs.OP_BLOCKS,
            ModItems.FIRE
        );

    }

    private static void after(RegHelper.ItemToTabEvent event, Item target,
                              ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        after(event, i -> i.is(target), tab, items);
    }

    private static void after(RegHelper.ItemToTabEvent event, Predicate<ItemStack> targetPred,
                              ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        //if (CommonConfigs.isEnabled(key)) {
        ItemLike[] entries = Arrays.stream(items).map((s -> (ItemLike) (s.get()))).toArray(ItemLike[]::new);
        if(MOD_TAB != null){
            tab = MOD_TAB.getKey();
        }
        event.addAfter(tab, targetPred, entries);
    }

    private static void before(RegHelper.ItemToTabEvent event, Item target,
                               ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        before(event, i -> i.is(target), tab, items);
    }

    private static void before(RegHelper.ItemToTabEvent event, Predicate<ItemStack> targetPred,
                               ResourceKey<CreativeModeTab> tab, Supplier<?>... items) {
        //if (CommonConfigs.isEnabled(key)) {
        ItemLike[] entries = Arrays.stream(items).map(s -> (ItemLike) s.get()).toArray(ItemLike[]::new);
        if(MOD_TAB != null){
            tab = MOD_TAB.getKey();
        }
        event.addBefore(tab, targetPred, entries);
        //}
    }
}
