package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;

import java.util.Collection;
import java.util.List;

public class HardcodedGrowths {
    public static Collection<? extends IBlockGrowth> getHardcoded() {
        return List.of(new CampfireGrowth(), new IceGrowth(), new LeavesGrowth());
    }
}
