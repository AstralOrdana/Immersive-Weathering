package com.ordana.immersive_weathering.common.blocks.mossable;

import com.ordana.immersive_weathering.common.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.common.blocks.crackable.Crackable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;

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
    default boolean shouldWeather(BlockState state, BlockPos pos, Level level) {
        return Mossable.super.shouldWeather(state, pos, level) ||
                Crackable.super.shouldWeather(state, pos, level);
    }

    @Override
    default void tryWeather(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        if (random.nextFloat() < this.getWeatherChanceSpeed()) {
            boolean isMoss = this.getMossSpreader().getWanderWeatheringState(true, pos, serverLevel);
            Optional<BlockState> opt = Optional.empty();
            if (isMoss) {
                opt = this.getNextMossy(state);
            } else if (this.getCrackSpreader().getWanderWeatheringState(true, pos, serverLevel)) {
                opt = this.getNextCracked(state);
            }
            BlockState newState = opt.orElse(state.setValue(WEATHERABLE, WeatheringState.FALSE));
            if(newState != state) {
                serverLevel.setBlock(pos, newState, 2);
                //schedule block event in 1 tick
                if (!newState.hasProperty(WEATHERABLE)) {
                    serverLevel.scheduleTick(pos, state.getBlock(), 1);
                }
            }
        }
    }
}
