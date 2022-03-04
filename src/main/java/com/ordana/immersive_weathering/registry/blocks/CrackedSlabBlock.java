package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.world.level.block.state.BlockState;

public class CrackedSlabBlock extends CrackableSlabBlock {
    public CrackedSlabBlock(CrackLevel crackLevel, Properties settings) {
        super(crackLevel, settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }
}
