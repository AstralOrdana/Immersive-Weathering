package com.ordana.immersive_weathering.mixins.forge;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin({ItemColors.class})
public interface AccessorItemColor {
    @Accessor("f_92674_")
    Map<Holder.Reference<Item>, ItemColor> getItemColors();
}

