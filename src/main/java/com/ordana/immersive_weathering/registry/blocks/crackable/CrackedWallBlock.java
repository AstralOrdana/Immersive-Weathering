package com.ordana.immersive_weathering.registry.blocks.crackable;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedWallBlock extends WallBlock implements Crackable {

    private final Crackable.CrackLevel crackLevel;

    public CrackedWallBlock(Crackable.CrackLevel crackLevel, BlockBehaviour.Properties settings) {
        super(settings);
        this.crackLevel = crackLevel;
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
}
