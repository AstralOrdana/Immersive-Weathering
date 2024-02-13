package com.ordana.immersive_weathering.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TintedGlassPane extends IronBarsBlock {
  public TintedGlassPane(Properties properties) {
    super(properties);
  }

  public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
    return false;
  }

  public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
    return level.getMaxLightLevel();
  }
}
