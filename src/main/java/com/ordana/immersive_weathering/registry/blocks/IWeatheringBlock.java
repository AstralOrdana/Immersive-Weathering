package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface IWeatheringBlock {

    //we could have this as an integer to have more control of how far we are from the weathering source
    BooleanProperty WEATHERING = BooleanProperty.create("weathering");

    enum Influence {
        LOW, //can only be influenced by already weathered blocks
        HIGH //can also be influenced by WEATHERING blocks
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
    default boolean shouldStartWeathering(BlockState state, BlockPos pos, Level level) {
        var directions = getInfluenceForDirections(pos);
        //list of weathering effects of surrounding blocks
        List<WeatheringAgent> weatheringAgents = new ArrayList<>();
        for (var e : directions.entrySet()) {
            BlockPos facingPos = pos.relative(e.getKey());
            BlockState facingState = level.getBlockState(facingPos);
            weatheringAgents.add(this.getBlockWeatheringEffect(e.getValue(), facingState, level, facingPos));
        }
        boolean oneSuccess = false;
        for (var w : weatheringAgents) {
            if (w == WeatheringAgent.PREVENT_WEATHERING) return false;
            else if (w == WeatheringAgent.WEATHER) oneSuccess = true;
        }
        return oneSuccess;
    }


    default Map<Direction, Influence> getInfluenceForDirections(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        Map<Direction, Influence> directions = new HashMap<>();
        float directionChange = this.getInterestForDirection();
        float highInterestChange = this.getHighInterestChance();
        for (Direction d : Direction.values()) {
            if (posRandom.nextFloat() < directionChange) {
                Influence in = posRandom.nextFloat() < highInterestChange ? Influence.HIGH : Influence.LOW;
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
     * @param level world
     * @param pos target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Influence influence, BlockState state, Level level, BlockPos pos) {
        //if high influence it can be affected by weathering blocks
        if (influence == Influence.HIGH && isHighInfluenceOnlyBlock(state, level, pos)) return WeatheringAgent.WEATHER;
        return getWeatheringEffect(state, level, pos);
    }

    //if this block can influence this only when the current side is on High influence
    default boolean isHighInfluenceOnlyBlock(BlockState state, Level level, BlockPos pos) {
        return (state.getBlock() instanceof IWeatheringBlock && state.getValue(WEATHERING));
    }

    /**
     * if this block can influence this on Low and high influence levels. Usually just checks if it is a fully weathered block
     * @param state neighbouring state
     * @param level world
     * @param pos   neighbour position
     * @return weathering effect that this block has on the current block. Dictates if the block should be allowed to weather or not
     */
    WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos);


}
