package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public interface SpreadingPatchBlock<T extends Enum<?>> {

    Class<T> getType();

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
    default boolean getWanderWeatheringState(boolean hasTicked, BlockPos pos, Level level, int maxRecursion) {
        Random posRandom = new Random(Mth.getSeed(pos));
        var directions = getInfluenceForDirections(posRandom, pos, level);
        if (hasTicked && posRandom.nextFloat() < this.getUnWeatherableChance(level, pos)) return false;
        //list of weathering effects of surrounding blocks
        List<WeatheringAgent> weatheringAgents = new ArrayList<>();
        boolean needsAir = this.needsAirToSpread(level, pos);
        boolean hasAir = false;
        for (var e : directions.entrySet()) {
            BlockPos facingPos = pos.relative(e.getKey());
            BlockState facingState = level.getBlockState(facingPos);
            if (!hasAir && needsAir) {
                hasAir = !facingState.isRedstoneConductor(level, pos);
            }
            weatheringAgents.add(this.getBlockWeatheringEffect(e.getValue(), facingState, level, facingPos, maxRecursion));
        }
        if (needsAir && !hasAir) return false;
        boolean oneSuccess = false;
        for (var w : weatheringAgents) {
            if (w == WeatheringAgent.PREVENT_WEATHERING) return false;
            else if (w == WeatheringAgent.WEATHER) oneSuccess = true;
        }
        return oneSuccess;
    }

    default boolean getWanderWeatheringState(boolean hasTicked, BlockPos pos, Level level) {
        return getWanderWeatheringState(hasTicked, pos, level, 2);
    }

    @Deprecated
    default Map<Direction, Susceptibility> getInfluenceForDirectionsOld(BlockPos pos, Level level) {
        Random posRandom = new Random(Mth.getSeed(pos));
        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChange = this.getInterestForDirection(level, pos);
        float highInterestChange = this.getDisjointGrowthChance(level, pos);
        for (Direction d : Direction.values()) {
            if (posRandom.nextFloat() < directionChange) {
                Susceptibility in = posRandom.nextFloat() < highInterestChange ? Susceptibility.HIGH : Susceptibility.MEDIUM;
                directions.put(d, in);
            }
        }
        return directions;
    }

    default Map<Direction, Susceptibility> getInfluenceForDirections(Random posRandom, BlockPos pos, Level level) {

        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChance = this.getInterestForDirection(level, pos);
        float highInterestChance = this.getDisjointGrowthChance(level, pos);
        int wantedDirs = //(posRandom.nextFloat() < this.getUnWeatherableChance(level, pos)) ? 0 :
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
        for (int i = 0; i < values.length; i++) {
            if (r < values[i]) return i;
        }
        return 0;
    }

    /**
     * @return The change that a certain direction will influence this block
     */
    float getInterestForDirection(Level level, BlockPos pos);

    /**
     * @return The chance that this block will accept WEATHERING blocks instead of only fully weathered ones
     * By default this causes the spreading to appear in different places that dont touch at once
     */
    float getDisjointGrowthChance(Level level, BlockPos pos);

    /**
     * gets the weathering effect that this block has on the current block. Override for more control
     *
     * @param sus   previously defined "influence". Just used as a param to allow more block spreading and variety
     * @param state target blockState
     * @param level world
     * @param pos   target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Susceptibility sus, BlockState state, Level level, BlockPos pos, int maxRecursion) {
        //if high influence it can be affected by weathering blocks
        WeatheringAgent effect = WeatheringAgent.NONE;
        if (sus.value >= Susceptibility.HIGH.value) {
            effect = getLowInfluenceWeatheringEffect(state, level, pos, maxRecursion);
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
    //this is horrible
    default WeatheringAgent getLowInfluenceWeatheringEffect(BlockState state, Level level, BlockPos pos, int maxRecursion) {
        Block b = state.getBlock();
        if (maxRecursion > 0 && b instanceof Weatherable w && w.isWeathering(state)) {
            var p = w.getPatchSpreader(this.getType()).orElse(null);
            if (p != null) {
                //could incur in infinite recursion here
                //if I find another neighboring weathered state I ask it what kind it is so because I dont know
                //if it's a crackable or mossable
                if (p.getWanderWeatheringState(false, pos, level, maxRecursion - 1)) {
                    return WeatheringAgent.WEATHER;
                }
            }
        }
        return WeatheringAgent.NONE;
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

    /**
     * Chance that this block will outright not be able to weather through LOW and MEDIUM influence blocks
     */
    float getUnWeatherableChance(Level level, BlockPos pos);

    /**
     * @return true if this block can not age when surrounded by full blocks
     */
    default boolean needsAirToSpread(Level level, BlockPos pos) {
        return false;
    }


}
