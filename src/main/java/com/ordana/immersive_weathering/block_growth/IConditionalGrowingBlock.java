package com.ordana.immersive_weathering.block_growth;

import net.minecraft.world.level.block.state.BlockState;

public interface IConditionalGrowingBlock {

    boolean canGrow(BlockState state);
}
