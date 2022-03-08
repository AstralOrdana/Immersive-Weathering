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

public interface SpreadingPatchBlock {

    BooleanProperty WEATHERABLE = BooleanProperty.create("weathering");

    //how much the given face is influenced by other blocks
    enum Susceptibility {
        LOW(1), //ideally this should make so the block has no influence on this but can be used
        MEDIUM(2), //can only be influenced by already weathered blocks
        HIGH(3); //can also be influenced by WEATHERING blocks

        private final int value;

        Susceptibility(int effect) {
            this.value = effect;
        }
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


    default Map<Direction, Susceptibility> getInfluenceForDirections(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChange = this.getInterestForDirection();
        float highInterestChange = this.getDisjointGrowthChance();
        for (Direction d : Direction.values()) {
            if (posRandom.nextFloat() < directionChange) {
                Susceptibility in = posRandom.nextFloat() < highInterestChange ? Susceptibility.HIGH : Susceptibility.MEDIUM;
                directions.put(d, in);
            }
        }
        return directions;
    }

    default Map<Direction, Susceptibility> getInfluenceForDirections2(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChance = this.getInterestForDirection();
        float highInterestChance = this.getDisjointGrowthChance();
        int wantedDirs = (posRandom.nextFloat() < this.getUnWeatherableChance()) ? 0 :
                getDirectionCount(posRandom, directionChance);
        List<Direction> dirs = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(dirs, posRandom);

        for (int i = 0; i < dirs.size(); i++) {
            Susceptibility sus;
            if (i < wantedDirs) {
                //med and high Susceptibility for these directions
                sus = posRandom.nextFloat() < highInterestChance ? Susceptibility.HIGH : Susceptibility.MEDIUM;
            } else {
                //low Susceptibility
                sus = Susceptibility.LOW;
            }
            directions.put(dirs.get(i), sus);
        }
        return directions;
    }


    default int getDirectionCount(Random random, float a) {
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
    float getDisjointGrowthChance();

    /**
     * gets the weathering effect that this block has on the current block. Override for more control
     *
     * @param sus   previously defined "influence". Just used as a param to allow more block spreading and variety
     * @param state target blockState
     * @param level world
     * @param pos   target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Susceptibility sus, BlockState state, Level level, BlockPos pos) {
        //if high influence it can be affected by weathering blocks
        WeatheringAgent effect = WeatheringAgent.NONE;
        if (sus.value >= Susceptibility.HIGH.value) {
            effect = getLowInfluenceWeatheringEffect(state, level, pos);
        }
        if (effect == WeatheringAgent.NONE && sus.value >= Susceptibility.MEDIUM.value) {
            effect = getWeatheringEffect(state, level, pos);
        }
        if (effect == WeatheringAgent.NONE && sus.value >= Susceptibility.LOW.value) {
            effect = getHighInfluenceWeatheringEffect(state, level, pos);
        }

        return effect;
    }

    /**
     * This gets called for each side since each side has at least a Low Susceptibility level
     * be very careful when using because overusing it will in the worst case convert every single block
     * Intended for High influence bocks like water sources for mossy blocks that will convert them 100% of the times
     *
     * @return effect that this block has
     */
    default WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        return WeatheringAgent.NONE;
    }

    /**
     * gets called more rarely since it requires the given direction has at least a HIGHSusceptibility level
     * Dy default checks if the given block belongs to the same weathering family & it itself is weathering
     *
     * @return effect that this block has
     */
    default WeatheringAgent getLowInfluenceWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        Block b = state.getBlock();
        return (b instanceof SpreadingPatchBlock wt &&
                this instanceof ChangeOverTimeBlock t &&
                b instanceof ChangeOverTimeBlock c &&
                c.getAge().getClass() == t.getAge().getClass()
                && wt.isWeathering(state)) ? WeatheringAgent.WEATHER : WeatheringAgent.NONE;
    }

    /**
     * if this block can influence this on Low and high influence levels. Usually just checks if it is a fully weathered block
     *
     * @param state neighbouring state
     * @param level world
     * @param pos   neighbour position
     * @return weathering effect that this block has on the current block. Dictates if the block should be allowed to weather or not
     */
    WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos);

    //simply check for WEATHERING blockstate
    boolean isWeathering(BlockState state);

    /**
     * Chance that this block will outright not be able to weather through LOW and MEDIUM influence blocks
     */
    float getUnWeatherableChance();

}
