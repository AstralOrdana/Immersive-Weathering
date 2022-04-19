package com.ordana.immersive_weathering.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {

    public ModStairBlock(Supplier<Block> baseBlockState, Properties settings) {
        super(() -> baseBlockState.get().defaultBlockState(), settings);
    }
}
