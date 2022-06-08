package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.PatchSpreader;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

//this class determines how moss patches spread
public class MossSpreader implements PatchSpreader<Mossable.MossLevel> {

    public static MossSpreader INSTANCE = new MossSpreader();

    @Override
    public Class<Mossable.MossLevel> getType() {
        return Mossable.MossLevel.class;
    }

    //basically how big those patches will be
    @Override
    public double getInterestForDirection(World level, BlockPos pos) {
        return 0.65f;
    }

    @Override
    public double getDisjointGrowthChance(World level, BlockPos pos) {
        return 0.65f;
    }

    //chance to have blocks that wont weather but still be able to make others weather if getDisjointGrowthChance is high enough
    @Override
    public double getUnWeatherableChance(World level, BlockPos pos) {
        return 0.3f;
    }

    @Override
    public boolean needsAirToSpread(World level, BlockPos pos) {
        return true;
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, World level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (state.isIn(ModTags.MOSSY) || fluid.isIn(FluidTags.WATER)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }


    @Override
    public WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, World level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (fluid.isIn(FluidTags.LAVA)) return WeatheringAgent.PREVENT_WEATHERING;
        if (state.isIn(ModTags.MOSS_SOURCE)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }

    //utility to grow stuff
    static void growNeighbors(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                if (targetBlock.getBlock() instanceof Mossable mossable) {
                    var newState = mossable.getNextMossy(targetBlock);
                    newState.ifPresent(s -> world.setBlockState(targetPos, s));
                }
            }
        }
    }
}
