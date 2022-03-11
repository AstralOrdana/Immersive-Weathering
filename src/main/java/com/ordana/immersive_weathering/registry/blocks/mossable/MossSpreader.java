package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
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
public class MossSpreader implements SpreadingPatchBlock<Mossable.MossLevel> {

    public static MossSpreader INSTANCE = new MossSpreader();

    @Override
    public Class<Mossable.MossLevel> getType() {
        return Mossable.MossLevel.class;
    }

    //basically how big those patches will be
    @Override
    public float getInterestForDirection(World level, BlockPos pos) {
        return 0.3f;
    }

    @Override
    public float getDisjointGrowthChance(World level, BlockPos pos) {
        return 0.5f;
    }

    //chance to have blocks that wont weather but still be able to make others weather if getDisjointGrowthChance is high enough
    @Override
    public float getUnWeatherableChance(World level, BlockPos pos) {
        return 0.4f;
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, World level, BlockPos pos) {
        var fluidState = state.getFluidState();
        if (fluidState.isIn(FluidTags.WATER) || state.isIn(ModTags.MOSSY)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }


    @Override
    public WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, World level, BlockPos pos) {
        var fluid = state.getFluidState();
        if (fluid.isIn(FluidTags.LAVA)) return WeatheringAgent.PREVENT_WEATHERING;
        if (state.isIn(ModTags.MOSS_SOURCE) || fluid.isIn(FluidTags.WATER)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }

    @Override
    public boolean needsAirToSpread(World level, BlockPos pos) {
        return true;
    }

    //utility to grow stuff
    static void growNeighbors(ServerWorld world, Random random, BlockPos pos) {
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
