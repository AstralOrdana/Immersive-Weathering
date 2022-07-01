package com.ordana.immersive_weathering.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

public class CommonUtils {

    public static ResourceLocation getID(Block object) {
        return Registry.BLOCK.getKey(object);
    }

    public static ResourceLocation getID(Item object) {
        return Registry.ITEM.getKey(object);
    }

    public static ResourceLocation getID(Fluid object) {
        return Registry.FLUID.getKey(object);
    }

    public static ResourceLocation getID(BlockEntityType<?> object) {
        return Registry.BLOCK_ENTITY_TYPE.getKey(object);
    }

    public static ResourceLocation getID(Object object) {
        if (object instanceof Block b) return getID(b);
        if (object instanceof Item b) return getID(b);
        if (object instanceof Fluid b) return getID(b);
        throw new UnsupportedOperationException("Unknown class type " + object.getClass());
    }

}
