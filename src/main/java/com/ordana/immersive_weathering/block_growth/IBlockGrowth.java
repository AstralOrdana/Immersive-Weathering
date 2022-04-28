package com.ordana.immersive_weathering.block_growth;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockGrowth {

    Iterable<Block> getOwners();

    void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome);
}
