package com.ordana.immersive_weathering.common.items;

import com.ordana.immersive_weathering.common.ModFoods;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class IcicleItem extends BlockItem {

    public IcicleItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + 80));
        return super.finishUsingItem(stack, level, entity);
    }

    @Nullable
    @Override
    public FoodProperties getFoodProperties() {
        return ServerConfigs.ICICLE_FOOD.get() ? ModFoods.ICICLE : null;
    }

    @Override
    public boolean isEdible() {
        return ServerConfigs.ICICLE_FOOD.get();
    }
}
