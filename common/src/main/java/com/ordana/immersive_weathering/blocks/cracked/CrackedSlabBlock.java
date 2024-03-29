package com.ordana.immersive_weathering.blocks.cracked;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackedSlabBlock extends SlabBlock implements Crackable {
    private final CrackLevel crackLevel;
    private final Supplier<Item> brickItem;

    public CrackedSlabBlock(CrackLevel crackLevel, Supplier<Item> brickItem, Properties settings) {
        super(settings);
        this.crackLevel = crackLevel;
        this.brickItem = brickItem;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
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

    @Override
    public Item getRepairItem(BlockState state) {
        return brickItem.get();
    }

}
