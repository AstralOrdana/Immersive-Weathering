package com.ordana.immersive_weathering.registry.blocks;

import java.util.Locale;
import java.util.Optional;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;

//a block that can weather
public interface Weatherable {

    BooleanProperty WEATHERABLE = BooleanProperty.of("weatherable");
    BooleanProperty STABLE = BooleanProperty.of("stable");

    enum WeatheringState implements StringIdentifiable {
        WEATHERING,
        STABLE,
        NONE;

        public boolean isWeatherable() {
            return this == WEATHERING;
        }

        public boolean isStable() {
            return this == STABLE;
        }

        @Override
        public String asString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    boolean isWeatherable(BlockState state);

    <T extends Enum<?>> Optional<SpreadingPatchBlock<T>> getPatchSpreader(Class<T> weatheringClass);
}
