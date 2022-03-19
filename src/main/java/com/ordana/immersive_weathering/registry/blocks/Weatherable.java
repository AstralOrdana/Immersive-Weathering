package com.ordana.immersive_weathering.registry.blocks;

import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;

//a block that can weather
public interface Weatherable {

    BooleanProperty WEATHERABLE = BooleanProperty.of("weatherable");
    BooleanProperty STABLE = BooleanProperty.of("stable");

    boolean isWeatherable(BlockState state);

    <T extends Enum<?>> Optional<SpreadingPatchBlock<T>> getPatchSpreader(Class<T> weatheringClass);
}
