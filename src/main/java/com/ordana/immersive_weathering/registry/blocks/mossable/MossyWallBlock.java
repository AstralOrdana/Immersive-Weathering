package com.ordana.immersive_weathering.registry.blocks.mossable;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.WallBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MossyWallBlock extends WallBlock implements Mossable, Fertilizable {

    private final MossLevel mossLevel;

    public MossyWallBlock(MossLevel mossLevel, Settings settings) {
        super(settings);
        this.mossLevel = mossLevel;
    }

    @Override
    public MossSpreader getMossSpreader() {
        return MossSpreader.INSTANCE;
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
        MossSpreader.growNeighbors(world, random, pos);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return isWeathering(state);
    }

    @Override
    public boolean isWeathering(BlockState state) {
        return false;
    }

    @Override
    public MossLevel getMossLevel() {
        return mossLevel;
    }

}