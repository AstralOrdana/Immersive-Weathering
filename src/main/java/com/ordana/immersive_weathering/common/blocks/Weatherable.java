package com.ordana.immersive_weathering.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;

//a block that can weather
public interface Weatherable {

    EnumProperty<WeatheringState> WEATHERABLE = EnumProperty.create("weathering", WeatheringState.class);



    enum WeatheringState implements StringRepresentable {
        FALSE,
        TRUE,
        STABLE;

        public boolean isWeathering() {
            return this == TRUE;
        }

        public boolean isStable() {
            return this == STABLE;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    boolean shouldWeather(BlockState state, BlockPos pos, Level level);

    boolean isWeathering(BlockState state);

    <T extends Enum<?>> Optional<PatchSpreader<T>> getPatchSpreader(Class<T> weatheringClass);

    default float getWeatherChanceSpeed(){
        return 0.1f;
    }

    //call this with random tick
    void tryWeather(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random);

    //call on
    default BlockState getWeatheredStateForPlacement(BlockState state, BlockPos pos, Level level){
        if (state != null) {
            WeatheringState weathering = this.shouldWeather(state, pos, level) ? WeatheringState.TRUE : WeatheringState.FALSE;
            state = state.setValue(WEATHERABLE, weathering);
        }
        return state;
    }

    //call on neighbor changed
    default void updateWeatheredStateOnNeighborChanged(BlockState state, Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            WeatheringState current = state.getValue(WEATHERABLE);
            if(!current.isStable()) {
                var wantedState = this.shouldWeather(state, pos, serverLevel)
                        ? WeatheringState.TRUE : WeatheringState.FALSE;
                if (state.getValue(WEATHERABLE) != wantedState) {
                    //update weathering state
                    serverLevel.setBlock(pos, state.setValue(WEATHERABLE, wantedState),2);
                    //schedule block event in 1 tick
                    if(wantedState==WeatheringState.TRUE) {
                        level.scheduleTick(pos, state.getBlock(), 1);
                    }
                }
            }
        }
    }

    static BlockState setStable(BlockState state){
        if(state.hasProperty(Weatherable.WEATHERABLE)){
            state = state.setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE);
        }
        return state;
    }
}
