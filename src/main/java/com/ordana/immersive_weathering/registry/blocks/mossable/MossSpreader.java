package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

//this class determines how moss patches spread
public class MossSpreader implements SpreadingPatchBlock {

    public static MossSpreader INSTANCE = new MossSpreader();


    @Override
    public float getInterestForDirection(Level level, BlockPos pos) {
        return 0.95f;
    }

    @Override
    public float getDisjointGrowthChance(Level level, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public float getUnWeatherableChance(Level level, BlockPos pos) {
        return 0.15f;
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        var fluidState = state.getFluidState();
        if (fluidState.is(FluidTags.WATER) || state.is(ModTags.MOSSY)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }


    @Override
    public WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (fluid.is(FluidTags.LAVA)) return WeatheringAgent.PREVENT_WEATHERING;
        if (state.is(ModTags.MOSS_SOURCE) || fluid.is(FluidTags.WATER)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }

    @Override
    public boolean needsAirToSpread(Level level, BlockPos pos) {
        return true;
    }

    //utility to grow stuff
    static void growNeighbors(ServerLevel world, Random random, BlockPos pos) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                if (targetBlock.getBlock() instanceof Mossable mossable) {
                    var newState = mossable.getNextMossy(targetBlock);
                    newState.ifPresent(s -> world.setBlockAndUpdate(targetPos, s));
                }
            }
        }
    }
}
