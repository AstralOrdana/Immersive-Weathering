package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.items.*;
import com.ordana.immersive_weathering.items.materials.FlowerCrownMaterial;
import com.ordana.immersive_weathering.items.materials.IcicleToolMaterial;
import net.mehvahdjukaar.moonlight.api.item.WoodBasedItem;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {

    public static void init() {
        BlockSetAPI.addDynamicItemRegistration(ModItems::registerLeafPilesItems, LeavesType.class);
        BlockSetAPI.addDynamicItemRegistration(ModItems::registerBark, WoodType.class);

    }

    public static <T extends Item> Supplier<T> regItem(String name, Supplier<T> itemSup) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), itemSup);
    }

    //helpers

    private static Supplier<BlockItem> regLeafPile(String name, Supplier<LeafPileBlock> oakLeafPile) {
        return regItem(name, () -> new LeafPileBlockItem(oakLeafPile.get(), new Item.Properties()));
    }

    //icicle

    public static final Supplier<BlockItem> ICICLE = regItem("icicle", () -> new IcicleItem(
            ModBlocks.ICICLE.get(), new Item.Properties().food(ModFoods.ICICLE)));

    //leaf pile
    public static final Map<LeavesType, BlockItem> LEAF_PILES = new LinkedHashMap<>();
    public static final Supplier<BlockItem> AZALEA_FLOWER_PILE = regLeafPile("azalea_flower_pile", ModBlocks.AZALEA_FLOWER_PILE);

    //bricks

    public static final Supplier<Item> STONE_BRICK = regItem("stone_brick", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> PRISMARINE_BRICK = regItem("prismarine_brick", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> END_STONE_BRICK = regItem("end_stone_brick", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> BLACKSTONE_BRICK = regItem("blackstone_brick", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> DEEPSLATE_BRICK = regItem("deepslate_brick", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> DEEPSLATE_TILE = regItem("deepslate_tile", () ->
            new Item(new Item.Properties()));
    public static final Supplier<Item> MORTAR = regItem("mortar", () ->
        new Item(new Item.Properties()));

    //flowers

    public static final Supplier<Item> AZALEA_FLOWERS = regItem("azalea_flowers", () ->
            new AzaleaFlowersItem(new Item.Properties().food(ModFoods.AZALEA_FLOWER)));

    public static final Supplier<Item> FLOWER_CROWN = regItem("flower_crown", () ->
            new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final Supplier<Item> MOSS_CLUMP = regItem("moss_clump", () ->
            new MossClumpItem(ModBlocks.MOSS.get(), new Item.Properties().food(ModFoods.MOSS_CLUMP)));

    public static final Supplier<Item> GOLDEN_MOSS_CLUMP = regItem("golden_moss_clump", () ->
            new Item(new Item.Properties().food(ModFoods.GOLDEN_MOSS_CLUMP)));

    public static final Supplier<Item> ENCHANTED_GOLDEN_MOSS_CLUMP = regItem("enchanted_golden_moss_clump", () ->
            new EnchantedGoldenMossClumpItem(new Item.Properties()
                    .rarity(Rarity.EPIC).food(ModFoods.ENCHANTED_GOLDEN_MOSS_CLUMP)));

    //bark

    public static final Map<WoodType, Item> BARK = new LinkedHashMap<>();

    public static final Supplier<Item> TALLOW = regItem("tallow",
            () -> new HoneycombItem(new Item.Properties()));

    public static final Supplier<Item> STEEL_WOOL = regItem("steel_wool", () ->
            new Item(new Item.Properties().defaultDurability(128)));

    public static final Supplier<Item> ICE_SICKLE = regItem("ice_sickle", () ->
            new IceSickleItem(IcicleToolMaterial.INSTANCE, 5, -1f,
                    new Item.Properties().food(ModFoods.ICICLE)));

    public static final Supplier<Item> THIN_ICE_ITEM = regItem("thin_ice", () ->
            new ThinIceItem(ModBlocks.THIN_ICE.get(), new Item.Properties()));

    public static final Supplier<Item> FROST_ITEM = regItem("frost", () ->
            new FrostItem(ModBlocks.FROST.get(), new Item.Properties()));

    public static final Supplier<Item> FIRE = regItem("fire", () ->
        new BlockItem(Blocks.FIRE, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SOUL_FIRE = regItem("soul_fire", () ->
        new BlockItem(Blocks.SOUL_FIRE, new Item.Properties().stacksTo(1)));


    private static void registerBark(Registrator<Item> event, Collection<WoodType> woodTypes) {
        for (WoodType type : woodTypes) {
            String name = !type.canBurn() ? type.getVariantId("scales", false) : type.getVariantId("bark", false);

            Item item = new WoodBasedItem(new Item.Properties(), type, 200);
            event.register(ImmersiveWeathering.res(name), item);
            BARK.put(type, item);
            type.addChild("immersive_weathering:bark", item);
        }
    }

    private static void registerLeafPilesItems(Registrator<Item> event, Collection<LeavesType> leavesTypes) {
        for (LeavesType type : leavesTypes) {
            var b = ModBlocks.LEAF_PILES.get(type);
            BlockItem i = new LeafPileBlockItem(b, new Item.Properties());
            event.register(Utils.getID(b), i);
            LEAF_PILES.put(type, i);
        }
    }
}
