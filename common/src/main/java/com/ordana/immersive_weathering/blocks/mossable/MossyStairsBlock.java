package com.ordana.immersive_weathering.blocks.mossable;

import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class MossyStairsBlock extends ModStairBlock implements Mossable, BonemealableBlock {

    private final MossLevel mossLevel;

    public MossyStairsBlock(MossLevel mossLevel, Supplier<Block> state, Properties properties) {
        super(state, properties);
        this.mossLevel = mossLevel;
    }

    @Override
    public MossSpreader getMossSpreader() {
        return MossSpreader.INSTANCE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return this.mossLevel == MossLevel.MOSSY;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        MossSpreader.growNeighbors(level, random, pos);
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
    public MossLevel getMossLevel() {
        return mossLevel;
    }

}