package com.ordana.immersive_weathering.blocks.mossable;

import com.ordana.immersive_weathering.blocks.crackable.CrackSpreader;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.blocks.crackable.Crackable.CrackLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackableMossableBlock extends MossableBlock implements CrackableMossable {

    private final Supplier<Item> brickItem;
    private final CrackLevel crackLevel;

    public CrackableMossableBlock(MossLevel mossLevel, CrackLevel crackLevel, Supplier<Item> brickItem, Properties settings) {
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
    public Crackable.CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
