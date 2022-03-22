package com.ordana.immersive_weathering.common.blocks.mossable;

import com.ordana.immersive_weathering.common.ModTags;
import com.ordana.immersive_weathering.common.blocks.SpreadingPatchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

//this class determines how moss patches spread
public class MossSpreader implements SpreadingPatchBlock<Mossable.MossLevel> {

    public static MossSpreader INSTANCE = new MossSpreader();

    @Override
    public Class<Mossable.MossLevel> getType() {
        return Mossable.MossLevel.class;
    }

    //basically how big those patches will be
    @Override
    public float getInterestForDirection(Level level, BlockPos pos) {
        return 0.3f;
    }

    @Override
    public float getDisjointGrowthChance(Level level, BlockPos pos) {
        return 0.5f;
    }

    //chance to have blocks that wont weather but still be able to make others weather if getDisjointGrowthChance is high enough
    @Override
    public float getUnWeatherableChance(Level level, BlockPos pos) {
        return 0.4f;
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
