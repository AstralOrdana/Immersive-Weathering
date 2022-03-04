package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedStairsBlock extends CrackableStairsBlock  {

    public CrackedStairsBlock(CrackLevel crackLevel, BlockState baseBlockState, Properties settings) {
        super(crackLevel, baseBlockState, settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }
}