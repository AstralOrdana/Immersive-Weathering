package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;

//a block that can weather
public interface Weatherable {

    BooleanProperty WEATHERABLE = BooleanProperty.create("weathering");



    //EnumProperty<WeatheringState> WEATHERABLE = EnumProperty.create("weathering", WeatheringState.class);

    enum WeatheringState implements StringRepresentable {
        WEATHERING,
        STABLE,
        NONE;

        public boolean isWeathering() {
            return this == WEATHERING;
        }

        public boolean isStable() {
            return this == STABLE;
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }


    boolean isWeathering(BlockState state);

    <T extends Enum<?>> Optional<SpreadingPatchBlock<T>> getPatchSpreader(Class<T> weatheringClass);

    //call this with random tick
    void tryWeather(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random);

    default float getWeatherChanceSpeed(){
        return 0.1f;
    };
}
