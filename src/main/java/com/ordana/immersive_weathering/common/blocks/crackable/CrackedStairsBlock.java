package com.ordana.immersive_weathering.common.blocks.crackable;

import java.util.function.Supplier;

import com.ordana.immersive_weathering.common.blocks.ModStairBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedStairsBlock extends ModStairBlock implements Crackable {

    private final CrackLevel crackLevel;
    private final Supplier<Item> brickItem;

    public CrackedStairsBlock(CrackLevel crackLevel, Supplier<Block> baseBlock, Supplier<Item> brickItem, BlockBehaviour.Properties settings) {
        super(baseBlock, settings);
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