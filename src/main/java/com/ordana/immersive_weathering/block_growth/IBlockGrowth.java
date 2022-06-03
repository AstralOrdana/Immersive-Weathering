package com.ordana.immersive_weathering.block_growth;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface IBlockGrowth {

    @Nullable
    Iterable<Block> getOwners();

    void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome);

    Collection<TickSource> getTickSources();
}
