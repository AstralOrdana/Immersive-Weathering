package com.ordana.immersive_weathering.blocks.mossy;

import com.ordana.immersive_weathering.blocks.cracked.CrackSpreader;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackableMossableStairsBlock extends MossableStairsBlock implements CrackableMossable {

    private final Supplier<Item> brickItem;
    private final CrackLevel crackLevel;

    public CrackableMossableStairsBlock(MossLevel mossLevel, CrackLevel crackLevel, Supplier<Item> brickItem, Supplier<Block> baseBlockState, Properties settings) {
        super(mossLevel, baseBlockState, settings);
        this.crackLevel = crackLevel;
        this.brickItem = brickItem;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public Item getRepairItem(BlockState state) {
        return brickItem.get();
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
