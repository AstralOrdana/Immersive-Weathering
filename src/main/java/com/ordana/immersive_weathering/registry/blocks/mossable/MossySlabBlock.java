package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.*;

public class MossySlabBlock extends SlabBlock implements Mossable, Fertilizable {
    protected final Mossable.MossLevel mossLevel;

    public MossySlabBlock(Mossable.MossLevel mossLevel, Settings settings) {
        super(settings);
        this.mossLevel = mossLevel;
    }

    @Override
    public float getInterestForDirection() {
        return 0;
    }

    @Override
    public float getHighInterestChance() {
        return 0;
    }

    @Override
    public boolean isWeatherable(BlockState state) {
        return false;
    }

    @Override
    public float getDegradationChanceMultiplier() {
        return 0;
    }

    @Override
    public MossLevel getDegradationLevel() {
        return mossLevel;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return this.mossLevel == MossLevel.MOSSY;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Mossable.growNeighbors(world, random, pos);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeatherable(state);
    }
}
