package com.ordana.immersive_weathering.common.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class IcicleItem extends BlockItem {

    public IcicleItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + 80));
        return super.finishUsingItem(stack, level, entity);
    }
}
