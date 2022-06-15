package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.MapColor;
import net.minecraft.block.Material;

public class ModMaterials {

    public static final Material QUICKSAND;

    static {
        QUICKSAND = (new Material.Builder(MapColor.PALE_YELLOW)).allowsMovement().build();
    }
}
