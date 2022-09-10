package com.ordana.immersive_weathering.data.block_growths;

import net.minecraft.world.level.block.state.BlockState;

public interface IConditionalGrowingBlock {

    boolean canGrow(BlockState state);
}
