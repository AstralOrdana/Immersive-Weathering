package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import java.util.Optional;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface CrackableMossable extends Mossable, Crackable {

    @Override
    default <T extends Enum<?>> Optional<SpreadingPatchBlock<T>> getPatchSpreader(Class<T> weatheringClass) {
        if (weatheringClass == MossLevel.class) {
            return Optional.of((SpreadingPatchBlock<T>) getMossSpreader());
        } else if (weatheringClass == CrackLevel.class) {
            return Optional.of((SpreadingPatchBlock<T>) getCrackSpreader());
        }
        return Optional.empty();
    }

    @Override
    default boolean shouldWeather(BlockState state, BlockPos pos, World world) {
        return Mossable.super.shouldWeather(state, pos, world) ||
                Crackable.super.shouldWeather(state, pos, world);
    }

    @Override
    default void tryWeather(BlockState state, ServerWorld serverLevel, BlockPos pos, Random random) {
        if (random.nextFloat() < this.getWeatherChanceSpeed()) {
            boolean isMoss = this.getMossSpreader().getWanderWeatheringState(true, pos, serverLevel);
            Optional<BlockState> opt = Optional.empty();
            if (isMoss) {
                opt = this.getNextMossy(state);
            } else if (this.getCrackSpreader().getWanderWeatheringState(true, pos, serverLevel)) {
                opt = this.getNextCracked(state);
            }
            BlockState newState = opt.orElse(state.with(WEATHERABLE, WeatheringState.FALSE));
            if(newState != state) {
                serverLevel.setBlockState(pos, newState, 2);
                //schedule block event in 1 tick
                if (!newState.contains(WEATHERABLE)) {
                    serverLevel.createAndScheduleBlockTick(pos, state.getBlock(), 1);
                }
            }
        }
    }
}
