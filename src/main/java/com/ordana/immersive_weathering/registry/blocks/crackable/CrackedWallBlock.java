package com.ordana.immersive_weathering.registry.blocks.crackable;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CrackedWallBlock extends WallBlock implements Crackable {

    private final Crackable.CrackLevel crackLevel;
    private final Supplier<Item> brickItem;

    public CrackedWallBlock(Crackable.CrackLevel crackLevel, Supplier<Item> brickItem, BlockBehaviour.Properties settings) {
        super(settings);
        this.crackLevel = crackLevel;
        this.brickItem = brickItem;
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
    public CrackLevel getAge() {
        return crackLevel;
    }

    @Override
    public Item getRepairItem(BlockState state) {
        return brickItem.get();
    }
}
