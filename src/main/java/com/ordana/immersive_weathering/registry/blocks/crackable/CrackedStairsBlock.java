package com.ordana.immersive_weathering.registry.blocks.crackable;

import java.util.function.Supplier;

import com.ordana.immersive_weathering.registry.blocks.ModStairs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;

public class CrackedStairsBlock extends ModStairs implements Crackable {

    private final CrackLevel crackLevel;

    public CrackedStairsBlock(CrackLevel crackLevel, Supplier<Block> baseBlock, AbstractBlock.Settings settings) {
        super((BlockState) baseBlock, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }


    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeathering(state);
    }

    @Override
    public boolean isWeathering(BlockState state) {
        return false;
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }
}