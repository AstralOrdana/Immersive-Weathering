package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.items.*;
import com.ordana.immersive_weathering.items.materials.FlowerCrownMaterial;
import com.ordana.immersive_weathering.items.materials.IcicleToolMaterial;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

public class ModItems {

    public static void init() {
    }

    public static <T extends Item> Supplier<T> regItem(String name, Supplier<T> itemSup) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), itemSup);
    }

    //helpers

    private static Supplier<BlockItem> regLeafPile(String name, Supplier<LeafPileBlock> oakLeafPile) {
        return regItem(name, () -> new LeafPileBlockItem(oakLeafPile.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    }

    //icicle

    public static final Supplier<BlockItem> ICICLE = regItem("icicle", () -> new IcicleItem(
            ModBlocks.ICICLE.get(), new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_DECORATIONS)));

    //leaf pile

    public static final Supplier<BlockItem> OAK_LEAF_PILE = regLeafPile("oak_leaf_pile", ModBlocks.OAK_LEAF_PILE);
    public static final Supplier<BlockItem> SPRUCE_LEAF_PILE = regLeafPile("spruce_leaf_pile", ModBlocks.SPRUCE_LEAF_PILE);
    public static final Supplier<BlockItem> BIRCH_LEAF_PILE = regLeafPile("birch_leaf_pile", ModBlocks.BIRCH_LEAF_PILE);
    public static final Supplier<BlockItem> JUNGLE_LEAF_PILE = regLeafPile("jungle_leaf_pile", ModBlocks.JUNGLE_LEAF_PILE);
    public static final Supplier<BlockItem> ACACIA_LEAF_PILE = regLeafPile("acacia_leaf_pile", ModBlocks.ACACIA_LEAF_PILE);
    public static final Supplier<BlockItem> DARK_OAK_LEAF_PILE = regLeafPile("dark_oak_leaf_pile", ModBlocks.DARK_OAK_LEAF_PILE);
    public static final Supplier<BlockItem> AZALEA_LEAF_PILE = regLeafPile("azalea_leaf_pile", ModBlocks.AZALEA_LEAF_PILE);
    public static final Supplier<BlockItem> FLOWERING_AZALEA_LEAF_PILE = regLeafPile("flowering_azalea_leaf_pile", ModBlocks.FLOWERING_AZALEA_LEAF_PILE);
    public static final Supplier<BlockItem> AZALEA_FLOWER_PILE = regLeafPile("azalea_flower_pile", ModBlocks.AZALEA_FLOWER_PILE);

    //bricks

    public static final Supplier<Item> STONE_BRICK = regItem("stone_brick", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> PRISMARINE_BRICK = regItem("prismarine_brick", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> END_STONE_BRICK = regItem("end_stone_brick", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> BLACKSTONE_BRICK = regItem("blackstone_brick", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> DEEPSLATE_BRICK = regItem("deepslate_brick", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> DEEPSLATE_TILE = regItem("deepslate_tile", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    //flowers

    public static final Supplier<Item> AZALEA_FLOWERS = regItem("azalea_flowers", () ->
            new AzaleaFlowersItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final Supplier<Item> FLOWER_CROWN = regItem("flower_crown", () ->
            new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final Supplier<Item> MOSS_CLUMP = regItem("moss_clump", () ->
            new MossClumpItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.MOSS_CLUMP)));

    public static final Supplier<Item> GOLDEN_MOSS_CLUMP = regItem("golden_moss_clump", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.GOLDEN_MOSS_CLUMP)));

    public static final Supplier<Item> ENCHANTED_GOLDEN_MOSS_CLUMP = regItem("enchanted_golden_moss_clump", () ->
            new EnchantedGoldenMossClumpItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)
                    .rarity(Rarity.EPIC).food(ModFoods.ENCHANTED_GOLDEN_MOSS_CLUMP)));

    //I hate pond water ;_;
    public static final Supplier<Item> POND_WATER = regItem("pond_water", () ->
            new PondWaterItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.POND_WATER).stacksTo(16)));


    //bark

    public static final Supplier<Item> OAK_BARK = regItem("oak_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> BIRCH_BARK = regItem("birch_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> SPRUCE_BARK = regItem("spruce_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> JUNGLE_BARK = regItem("jungle_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> DARK_OAK_BARK = regItem("dark_oak_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> ACACIA_BARK = regItem("acacia_bark", () ->
            new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final Supplier<Item> CRIMSON_SCALES = regItem("crimson_scales", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final Supplier<Item> WARPED_SCALES = regItem("warped_scales", () ->
            new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final Supplier<Item> TALLOW = regItem("tallow",
            () -> new HoneycombItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final Supplier<Item> ICE_SICKLE = regItem("ice_sickle", () ->
            new IceSickleItem(IcicleToolMaterial.INSTANCE, 5, -1f,
                    new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_COMBAT)));

    public static final Supplier<Item> THIN_ICE_ITEM = regItem("thin_ice", () ->
            new ThinIceItem(ModBlocks.THIN_ICE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final Supplier<Item> FROST_ITEM = regItem("frost", () ->
            new FrostItem(ModBlocks.FROST.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

}
