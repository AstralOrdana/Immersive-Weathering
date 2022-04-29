package com.ordana.immersive_weathering.common.items;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.ModFoods;
import com.ordana.immersive_weathering.common.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.common.ModBlocks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {

    //new BlockItem\((.*?),(.*?);
    //regBlockItem($1, $2);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<Item> ITEMS_OVERRIDE = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static RegistryObject<Item> regOverride(Item original, Supplier<Item> supplier) {
        return ITEMS_OVERRIDE.register(original.getRegistryName().getPath(), supplier);
    }

    public static RegistryObject<BlockItem> regBlockItem(RegistryObject<Block> blockSup, Item.Properties properties, int burnTIme) {
        return ITEMS.register(blockSup.getId().getPath(), () -> new BurnableBlockItem(blockSup.get(), properties, burnTIme));
    }

    public static RegistryObject<BlockItem> regBlockItem(RegistryObject<? extends Block> blockSup, Item.Properties properties) {
        return ITEMS.register(blockSup.getId().getPath(), () -> new BlockItem(blockSup.get(), properties));
    }

    private static RegistryObject<BlockItem> regLeafPile(RegistryObject<LeafPileBlock> blockSup, Item.Properties properties) {
        return ITEMS.register(blockSup.getId().getPath(), () -> new LeafPileBlockItem(blockSup.get(), properties));
    }


    public static final RegistryObject<BlockItem> ICICLE = ITEMS.register("icicle",()->new IcicleItem(
            ModBlocks.ICICLE.get(), new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<BlockItem> OAK_LEAF_PILE = regLeafPile(ModBlocks.OAK_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> SPRUCE_LEAF_PILE = regLeafPile(ModBlocks.SPRUCE_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> BIRCH_LEAF_PILE = regLeafPile(ModBlocks.BIRCH_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> JUNGLE_LEAF_PILE = regLeafPile(ModBlocks.JUNGLE_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> ACACIA_LEAF_PILE = regLeafPile(ModBlocks.ACACIA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> DARK_OAK_LEAF_PILE = regLeafPile(ModBlocks.DARK_OAK_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> AZALEA_LEAF_PILE = regLeafPile(ModBlocks.AZALEA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> FLOWERING_AZALEA_LEAF_PILE = regLeafPile(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    public static final RegistryObject<BlockItem> AZALEA_FLOWER_PILE = regLeafPile(ModBlocks.AZALEA_FLOWER_PILE, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));


    public static final RegistryObject<Item> STONE_BRICK = ITEMS.register("stone_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> PRISMARINE_BRICK = ITEMS.register("prismarine_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> END_STONE_BRICK = ITEMS.register("end_stone_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> BLACKSTONE_BRICK = ITEMS.register("blackstone_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DEEPSLATE_BRICK = ITEMS.register("deepslate_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> DEEPSLATE_TILE = ITEMS.register("deepslate_tile", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> AZALEA_FLOWERS = ITEMS.register("azalea_flowers", () -> new AzaleaFlowersItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> FLOWER_CROWN = ITEMS.register("flower_crown", () -> new FlowerCrownItem(FlowerCrownMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> MOSS_CLUMP = ITEMS.register("moss_clump", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.MOSS_CLUMP)));
    public static final RegistryObject<Item> GOLDEN_MOSS_CLUMP = ITEMS.register("golden_moss_clump", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS).food(ModFoods.GOLDEN_MOSS_CLUMP)));

    public static final RegistryObject<Item> OAK_BARK = ITEMS.register("oak_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> BIRCH_BARK = ITEMS.register("birch_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> SPRUCE_BARK = ITEMS.register("spruce_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> JUNGLE_BARK = ITEMS.register("jungle_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> DARK_OAK_BARK = ITEMS.register("dark_oak_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> ACACIA_BARK = ITEMS.register("acacia_bark", () -> new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> CRIMSON_SCALES = ITEMS.register("crimson_scales", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> WARPED_SCALES = ITEMS.register("warped_scales", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));


    public static final RegistryObject<Item> HANGING_ROOTS_ITEM = regOverride(Items.HANGING_ROOTS, () ->
            new CeilingAndWallBlockItem(Blocks.HANGING_ROOTS, ModBlocks.HANGING_ROOTS_WALL.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> ICE_SICKLE = ITEMS.register("ice_sickle", ()->
            new IceSickleItem(IcicleToolMaterial.INSTANCE, 5, -1f, new Item.Properties().food(ModFoods.ICICLE).tab(CreativeModeTab.TAB_COMBAT)));


}
