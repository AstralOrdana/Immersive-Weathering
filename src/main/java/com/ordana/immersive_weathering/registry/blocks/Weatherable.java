package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

//a block that can weather
public interface Weatherable {

    BooleanProperty WEATHERABLE = BooleanProperty.create("weathering");

    boolean isWeathering(BlockState state);
}
