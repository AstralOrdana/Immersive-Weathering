package com.ordana.immersive_weathering.block_growth;

import net.minecraft.block.BlockState;

public interface IConditionalGrowingBlock {

    boolean canGrow(BlockState state);
}