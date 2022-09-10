package com.ordana.immersive_weathering.data.block_growths.growths;

import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface IBlockGrowth {

    @Nullable
    Iterable<? extends Block> getOwners();

    void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome);

    Collection<TickSource> getTickSources();
}
