package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.crackable.CrackSpreader;
import java.util.Optional;
import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CrackableMossableStairsBlock extends MossableStairsBlock implements CrackableMossable {

    private final CrackLevel crackLevel;

    public CrackableMossableStairsBlock(MossLevel mossLevel, CrackLevel crackLevel, BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(mossLevel, baseBlockState, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
