package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.items.FrostItem;
import com.ordana.immersive_weathering.items.IceSickleItem;
import com.ordana.immersive_weathering.items.IcicleItem;
import com.ordana.immersive_weathering.items.ThinIceItem;
import com.ordana.immersive_weathering.items.materials.IcicleToolMaterial;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class ModItems {

    public static void init() {
    }

    public static <T extends Item> Supplier<T> regItem(String name, Supplier<T> itemSup) {
        return RegHelper.registerItem(ImmersiveWeathering.res(name), itemSup);
    }

    //icicle

    public static final Supplier<BlockItem> ICICLE = regItem("icicle", () -> new IcicleItem(
            ModBlocks.ICICLE.get(), new Item.Properties().food(ModFoods.ICICLE)));

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

    public static final Supplier<Item> TALLOW = regItem("tallow",
            () -> new HoneycombItem(new Item.Properties()));

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

}
