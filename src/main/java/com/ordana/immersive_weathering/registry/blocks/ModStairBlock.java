package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {
    protected ModStairBlock(Supplier<Block> baseBlockState, Properties settings) {
        super(()->baseBlockState.get().defaultBlockState(), settings);
    }
}
