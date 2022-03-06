package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Degradable;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.*;

public interface WeatherableBlock {

    //we could have this as an integer to have more control of how far we are from the weathering source
    BooleanProperty WEATHERABLE = BooleanProperty.of("weatherable");

    enum Influence {
        LOW, //can only be influenced by already weathered blocks
        HIGH //can also be influenced by WEATHERABLE blocks
    }

    enum WeatheringAgent {
        NONE, //do nothing
        WEATHER, //allow the block to weather
        PREVENT_WEATHERING //stops it from weathering
    }

    /**
     * @return determines if a certain block should gain the WEATHERING state
     * Should be called on block placement or to refresh the state
     */
    default boolean shouldStartWeathering(BlockState state, BlockPos pos, World world) {
        var directions = getInfluenceForDirections(pos);
        //list of weathering effects of surrounding blocks
        List<WeatherableBlock.WeatheringAgent> weatheringAgents = new ArrayList<>();
        for (var e : directions.entrySet()) {
            BlockPos facingPos = pos.offset(e.getKey());
            BlockState facingState = world.getBlockState(facingPos);
            weatheringAgents.add(this.getBlockWeatheringEffect(e.getValue(), facingState, world, facingPos));
        }
        boolean oneSuccess = false;
        for (var w : weatheringAgents) {
            if (w == WeatherableBlock.WeatheringAgent.PREVENT_WEATHERING) return false;
            else if (w == WeatherableBlock.WeatheringAgent.WEATHER) oneSuccess = true;
        }
        return oneSuccess;
    }


    default Map<Direction, WeatherableBlock.Influence> getInfluenceForDirections(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        Map<Direction, WeatherableBlock.Influence> directions = new HashMap<>();
        float directionChange = this.getInterestForDirection();
        float highInterestChange = this.getHighInterestChance();
        for (Direction d : Direction.values()) {
            if (posRandom.nextFloat() < directionChange) {
                WeatherableBlock.Influence in = posRandom.nextFloat() < highInterestChange ? WeatherableBlock.Influence.HIGH : WeatherableBlock.Influence.LOW;
                directions.put(d, in);
            }
        }
        return directions;
    }


    /**
     * @return The change that a certain direction will influence this block
     */
    float getInterestForDirection();

    /**
     * @return The chance that this block will accept WEATHERING blocks instead of only fully weathered ones
     */
    float getHighInterestChance();

    /**
     * gets the weathering effect that this block has on the current block. Override for more control
     * @param influence previously defined "influence". Just used as a param to allow more block spreading and variety
     * @param state target blockState
     * @param world world
     * @param pos target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Influence influence, BlockState state, World world, BlockPos pos) {
        //if high influence it can be affected by weathering blocks
        if (influence == Influence.HIGH && isHighInfluenceOnlyBlock(state, world, pos)) return WeatheringAgent.WEATHER;
        return getWeatheringEffect(state, world, pos);
    }

    //if this block can influence this only when the current side is on High influence
    default boolean isHighInfluenceOnlyBlock(BlockState state, World world, BlockPos pos) {
        Block b = state.getBlock();
        return (b instanceof WeatherableBlock wt &&
                this instanceof Degradable t &&
                b instanceof Degradable c &&
                    c.getDegradationLevel().getClass() == t.getDegradationLevel().getClass()
                && wt.isWeatherable(state));
    }


    //simply check for WEATHERABLE blockstate
    boolean isWeatherable(BlockState state);

    /**
     * if this block can influence this on Low and high influence levels. Usually just checks if it is a fully weathered block
     * @param state neighbouring state
     * @param world world
     * @param pos   neighbour position
     * @return weathering effect that this block has on the current block. Dictates if the block should be allowed to weather or not
     */
    WeatheringAgent getWeatheringEffect(BlockState state, World world, BlockPos pos);
}

