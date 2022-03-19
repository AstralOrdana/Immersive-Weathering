package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.ordana.immersive_weathering.registry.blocks.ModStairs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;

public class CrackedStairsBlock extends ModStairs implements Crackable {

    private final CrackLevel crackLevel;

    public CrackedStairsBlock(CrackLevel crackLevel, BlockState baseBlock, AbstractBlock.Settings settings) {
        super(baseBlock, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }


    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeatherable(state);
    }

    @Override
    public boolean isWeatherable(BlockState state) {
        return false;
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }
}