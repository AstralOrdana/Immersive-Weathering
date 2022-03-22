package com.ordana.immersive_weathering.common.blocks.mossable;

import com.ordana.immersive_weathering.common.blocks.crackable.CrackSpreader;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackableMossableBlock extends MossableBlock implements CrackableMossable {

    private final Supplier<Item> brickItem;
    private final CrackLevel crackLevel;

    public CrackableMossableBlock(MossLevel mossLevel, CrackLevel crackLevel, Supplier<Item> brickItem, BlockBehaviour.Properties settings) {
        super(mossLevel, settings);
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
