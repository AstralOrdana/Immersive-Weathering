package com.ordana.immersive_weathering.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FuelBlockItem extends BlockItem {

    private final Supplier<Integer> burnTime;

    public FuelBlockItem(Block block, Item.Properties properties, Supplier<Integer> burnTime) {
        super(block, properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
        return burnTime.get();
    }
}