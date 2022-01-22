package com.ordana.mossier_moss.registry.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class MossyStairsBlock extends CleanStairsBlock {
    public MossyStairsBlock(MossLevel mossLevel, BlockState baseBlockState, Settings settings) {
        super(mossLevel, baseBlockState, settings);
    }
}
