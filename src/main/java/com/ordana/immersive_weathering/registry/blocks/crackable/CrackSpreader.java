package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.C;

import java.util.Optional;
import java.util.function.Supplier;

public class CrackSpreader implements SpreadingPatchBlock<Crackable.CrackLevel> {

    public static final CrackSpreader INSTANCE = new CrackSpreader();

    @Override
    public Class<Crackable.CrackLevel> getType() {
        return Crackable.CrackLevel.class;
    }

    @Override
    public float getInterestForDirection(Level level, BlockPos pos) {
        return 0.35f;
    }

    @Override
    public float getDisjointGrowthChance(Level level, BlockPos pos) {
        return 0.5f;
    }

    @Override
    public float getUnWeatherableChance(Level level, BlockPos pos) {
        return 0.18f;
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
