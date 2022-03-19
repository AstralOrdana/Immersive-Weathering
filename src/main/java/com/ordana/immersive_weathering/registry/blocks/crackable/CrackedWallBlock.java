package com.ordana.immersive_weathering.registry.blocks.crackable;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;

public class CrackedWallBlock extends WallBlock implements Crackable {

    private final CrackLevel crackLevel;

    public CrackedWallBlock(CrackLevel crackLevel, Settings settings) {
        super(settings);
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
