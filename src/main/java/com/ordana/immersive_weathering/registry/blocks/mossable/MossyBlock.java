package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.ModTags;
import java.util.*;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MossyBlock extends Block implements Mossable, BonemealableBlock {
    protected final Mossable.MossLevel mossLevel;

    public MossyBlock(Mossable.MossLevel mossLevel, BlockBehaviour.Properties settings) {
        super(settings);
        this.mossLevel = mossLevel;
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
        Mossable.growNeighbors(world, random, pos);
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
    public MossLevel getAge() {
        return mossLevel;
    }
}

