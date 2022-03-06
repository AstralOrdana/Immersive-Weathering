package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.*;

public interface WeathereableBlock {

    BooleanProperty WEATHERABLE = BooleanProperty.create("weathering");

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
        var directions = getInfluenceForDirections2(pos);
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

    default Map<Direction, Influence> getInfluenceForDirections2(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        Map<Direction, Influence> directions = new HashMap<>();
        float directionChance = this.getInterestForDirection();
        float highInterestChance = this.getHighInterestChance();
        int wantedDirs = getDirectionNumber(posRandom, directionChance);
        List<Direction> dirs = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(dirs, posRandom);
        var selected = dirs.subList(0, wantedDirs);
        for (Direction d : selected) {
            Influence in = posRandom.nextFloat() < highInterestChance ? Influence.HIGH : Influence.LOW;
            directions.put(d, in);
        }
        return directions;
    }


    default int getDirectionNumber(Random random, float a) {
        final int n = 6;
        float[] values = new float[n + 1];
        for (int x = 0; x <= n; x++) {
            values[x] = (-1f / (n * (n + 1f))) * (x * (2 * n * a - 2 * a - 2 * n + 1) + x * x * (1 - 2 * a) + (2 * a * n - 2 * n));
        }
        float r = random.nextFloat();
        for (int i = 0; i < values.length - 1; i++) {
            if (r < values[i]) return i;
        }
        return 0;
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
     *
     * @param influence previously defined "influence". Just used as a param to allow more block spreading and variety
     * @param state     target blockState
     * @param level     world
     * @param pos       target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Influence influence, BlockState state, Level level, BlockPos pos) {
        //if high influence it can be affected by weathering blocks
        if (influence == Influence.HIGH && isHighInfluenceOnlyBlock(state, level, pos)) return WeatheringAgent.WEATHER;
        return getWeatheringEffect(state, level, pos);
    }

    //if this block can influence this only when the current side is on High influence
    default boolean isHighInfluenceOnlyBlock(BlockState state, Level level, BlockPos pos) {
        Block b = state.getBlock();
        return (b instanceof WeathereableBlock wt &&
                this instanceof ChangeOverTimeBlock t &&
                b instanceof ChangeOverTimeBlock c &&
                c.getAge().getClass() == t.getAge().getClass()
                && wt.isWeathering(state));
    }


    //simply check for WEATHERING blockstate
    boolean isWeathering(BlockState state);

    /**
     * if this block can influence this on Low and high influence levels. Usually just checks if it is a fully weathered block
     *
     * @param state neighbouring state
     * @param level world
     * @param pos   neighbour position
     * @return weathering effect that this block has on the current block. Dictates if the block should be allowed to weather or not
     */
    WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos);


}
