package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.crackable.CrackSpreader;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CrackableMossableWallBlock extends MossableWallBlock implements CrackableMossable {

    private final CrackLevel crackLevel;

    public CrackableMossableWallBlock(MossLevel mossLevel, CrackLevel crackLevel, Settings settings) {
        super(mossLevel, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
