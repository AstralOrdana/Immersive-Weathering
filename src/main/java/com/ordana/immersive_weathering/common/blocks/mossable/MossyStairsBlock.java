package com.ordana.immersive_weathering.common.blocks.mossable;

import java.util.Random;
import java.util.function.Supplier;

import com.ordana.immersive_weathering.common.blocks.ModStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MossyStairsBlock extends ModStairBlock implements Mossable, BonemealableBlock {

    private final MossLevel mossLevel;

    public MossyStairsBlock(MossLevel mossLevel, Supplier<Block> state, Properties properties) {
        super(state,properties);
        this.mossLevel = mossLevel;
    }

    @Override
    public MossSpreader getMossSpreader() {
        return MossSpreader.INSTANCE;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return this.mossLevel == MossLevel.MOSSY;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        MossSpreader.growNeighbors(world, random, pos);
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