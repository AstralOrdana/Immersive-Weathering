package com.ordana.immersive_weathering.registry.blocks;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//a block that can weather
public interface Weatherable {

    EnumProperty<WeatheringState> WEATHERABLE = EnumProperty.of("weatherable", WeatheringState.class);

    enum WeatheringState implements StringIdentifiable {
        FALSE,
        TRUE,
        STABLE;

        public boolean isWeatherable() {
            return this == TRUE;
        }

        public boolean isStable() {
            return this == STABLE;
        }

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    boolean shouldWeather(BlockState state, BlockPos pos, World world);

    boolean isWeatherable(BlockState state);

    <T extends Enum<?>> Optional<PatchSpreader<T>> getPatchSpreader(Class<T> weatheringClass);

    default float getWeatherChanceSpeed(){
        return 0.1f;
    }

    //call this with random tick
    void tryWeather(BlockState state, ServerWorld serverWorld, BlockPos pos, Random random);

    //call on
    default BlockState getWeatheredStateForPlacement(BlockState state, BlockPos pos, World world){
        if (state != null) {
            WeatheringState weathering = this.shouldWeather(state, pos, world) ? WeatheringState.TRUE : WeatheringState.FALSE;
            state = state.with(WEATHERABLE, weathering);
        }
        return state;
    }

    //call on neighbor changed
    default void updateWeatheredStateOnNeighborChanged(BlockState state, World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            WeatheringState current = state.get(WEATHERABLE);
            if(!current.isStable()) {
                var wantedState = this.shouldWeather(state, pos, serverWorld)
                        ? WeatheringState.TRUE : WeatheringState.FALSE;
                if (state.get(WEATHERABLE) != wantedState) {
                    //update weathering state
                    serverWorld.setBlockState(pos, state.with(WEATHERABLE, wantedState),2);
                    //schedule block event in 1 tick
                    if(wantedState==WeatheringState.TRUE) {
                        world.createAndScheduleBlockTick(pos, state.getBlock(), 1);
                    }
                }
            }
        }
    }
}
