package com.ordana.immersive_weathering.registry.blocks.crackable;

import java.util.function.Supplier;

import com.ordana.immersive_weathering.registry.blocks.ModStairBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CrackedStairsBlock extends ModStairBlock implements Crackable   {

    private final Crackable.CrackLevel crackLevel;

    public CrackedStairsBlock(Crackable.CrackLevel crackLevel, Supplier<Block> baseBlock, BlockBehaviour.Properties settings) {
        super(baseBlock, settings);
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