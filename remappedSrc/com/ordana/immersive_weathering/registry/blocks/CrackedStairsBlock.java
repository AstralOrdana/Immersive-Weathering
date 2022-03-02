package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;

public class CrackedStairsBlock extends CrackableStairsBlock  {

    public CrackedStairsBlock(CrackLevel crackLevel, BlockState baseBlockState, Settings settings) {
        super(crackLevel, baseBlockState, settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false;
    }
}