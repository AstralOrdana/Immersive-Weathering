package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrackSpreader implements SpreadingPatchBlock<Crackable.CrackLevel> {

    public static final CrackSpreader INSTANCE = new CrackSpreader();

    @Override
    public Class<Crackable.CrackLevel> getType() {
        return Crackable.CrackLevel.class;
    }

    @Override
    public float getInterestForDirection(World level, BlockPos pos) {
        return 0.35f;
    }

    @Override
    public float getDisjointGrowthChance(World level, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public float getUnWeatherableChance(World level, BlockPos pos) {
        return 0.18f;
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, World level, BlockPos pos) {
        return state.isIn(ModTags.CRACKED) ? WeatheringAgent.WEATHER : WeatheringAgent.NONE;
    }

    @Override
    public WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, World level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (fluid.isIn(FluidTags.LAVA) || state.getBlock() instanceof FireBlock) return WeatheringAgent.WEATHER;
        if (state.isIn(ModTags.CRACK_SOURCE)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }
}
