package com.ordana.immersive_weathering.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class IceSickleItem extends SwordItem {

    public IceSickleItem(Tier tier, int i, float v, Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, i, v)));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + 80));
        return super.finishUsingItem(stack, level, entity);
    }
}
