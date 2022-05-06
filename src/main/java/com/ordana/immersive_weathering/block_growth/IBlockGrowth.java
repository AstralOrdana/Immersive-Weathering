package com.ordana.immersive_weathering.block_growth;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

public interface IBlockGrowth {

    Iterable<Block> getOwners();

    void tryGrowing(BlockPos pos, BlockState state, ServerWorld level, RegistryEntry<Biome> biome);
}