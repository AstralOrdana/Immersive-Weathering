package com.ordana.immersive_weathering.blocks.mossy;

import com.ordana.immersive_weathering.blocks.cracked.CrackSpreader;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackableMossableSlabBlock extends MossableSlabBlock implements CrackableMossable {

    private final Supplier<Item> brickItem;
    private final CrackLevel crackLevel;

    public CrackableMossableSlabBlock(MossLevel mossLevel, CrackLevel crackLevel, Supplier<Item> brickItem, Properties settings) {
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
