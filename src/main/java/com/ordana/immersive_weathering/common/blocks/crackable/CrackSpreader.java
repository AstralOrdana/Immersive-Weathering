package com.ordana.immersive_weathering.common.blocks.crackable;

import com.ordana.immersive_weathering.common.ModTags;
import com.ordana.immersive_weathering.common.blocks.PatchSpreader;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CrackSpreader implements PatchSpreader<Crackable.CrackLevel> {

    public static final CrackSpreader INSTANCE = new CrackSpreader();

    @Override
    public Class<Crackable.CrackLevel> getType() {
        return Crackable.CrackLevel.class;
    }

    @Override
    public double getInterestForDirection(Level level, BlockPos pos) {
        return ServerConfigs.CRACK_INTEREST_FOR_FACE.get();
    }

    @Override
    public double getDisjointGrowthChance(Level level, BlockPos pos) {
        return ServerConfigs.CRACK_DISJOINT_GROWTH.get();
    }

    @Override
    public double getUnWeatherableChance(Level level, BlockPos pos) {
        return ServerConfigs.CRACK_UN_WEATHERABLE_CHANCE.get();
    }

    @Override
    public boolean needsAirToSpread(Level level, BlockPos pos) {
        return ServerConfigs.CRACK_NEEDS_AIR.get();
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        return state.is(ModTags.CRACKED) ? WeatheringAgent.WEATHER : WeatheringAgent.NONE;
    }

    @Override
    public WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (fluid.is(FluidTags.LAVA) || state.getBlock() instanceof FireBlock) return WeatheringAgent.WEATHER;
        if (state.is(ModTags.CRACK_SOURCE)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }
}
