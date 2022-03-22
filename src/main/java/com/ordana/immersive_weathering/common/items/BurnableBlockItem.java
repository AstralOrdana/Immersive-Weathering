package com.ordana.immersive_weathering.common.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BurnableBlockItem extends BlockItem {
    private final int burnTime;

    public BurnableBlockItem(Block p_40565_, Properties p_40566_, int burnTime) {
        super(p_40565_, p_40566_);
        this.burnTime = burnTime;
    }


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }

}
