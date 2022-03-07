package com.ordana.immersive_weathering.registry.blocks;

import io.netty.util.internal.MathUtil;
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

    BooleanProperty WEATHERABLE = BooleanProperty.of("weatherable");

    //how much the given face is influenced by other blocks
    enum Susceptibility {
        LOW, //ideally this should make so the block has no influence on this but can be used
        MEDIUM, //can only be influenced by already weathered blocks
        HIGH //can also be influenced by WEATHERABLE blocks
    }

    enum WeatheringAgent {
        NONE, //do nothing
        WEATHER, //allow the block to weather
        PREVENT_WEATHERING //stops it from weathering
    }

    /**
     * @return determines if a certain block should gain the WEATHERABLE state
     * Should be called on block placement or to refresh the state
     */
    default boolean shouldStartWeathering(BlockState state, BlockPos pos, World world) {
        var directions = getInfluenceForDirections2(pos);
        //list of weathering effects of surrounding blocks
        List<WeatheringAgent> weatheringAgents = new ArrayList<>();
        for (var e : directions.entrySet()) {
            BlockPos facingPos = pos.offset(e.getKey());
            BlockState facingState = world.getBlockState(facingPos);
            weatheringAgents.add(this.getBlockWeatheringEffect(e.getValue(), facingState, world, facingPos));
        }
        boolean oneSuccess = false;
        for (var w : weatheringAgents) {
            if (w == WeatheringAgent.PREVENT_WEATHERING) return false;
            else if (w == WeatheringAgent.WEATHER) oneSuccess = true;
        }
        return oneSuccess;
    }


    default Map<Direction, Susceptibility> getInfluenceForDirections(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChange = this.getInterestForDirection();
        float highInterestChange = this.getHighInterestChance();
        for (Direction d : Direction.values()) {
            if (posRandom.nextFloat() < directionChange) {
                Susceptibility in = posRandom.nextFloat() < highInterestChange ? Susceptibility.HIGH : Susceptibility.MEDIUM;
                directions.put(d, in);
            }
        }
        return directions;
    }

    default Map<Direction, Susceptibility> getInfluenceForDirections2(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        Map<Direction, Susceptibility> directions = new HashMap<>();
        float directionChance = this.getInterestForDirection();
        float highInterestChance = this.getHighInterestChance();
        int wantedDirs = getDirectionCount(posRandom, directionChance);
        List<Direction> dirs = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(dirs, posRandom);
        var selected = dirs.subList(0, wantedDirs);
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
    float getHighInterestChance();

    /**
     * gets the weathering effect that this block has on the current block. Override for more control
     *
     * @param sus previously defined "influence". Just used as a param to allow more block spreading and variety
     * @param state     target blockState
     * @param world     world
     * @param pos       target position
     * @return weathering effect of the target block
     */
    default WeatheringAgent getBlockWeatheringEffect(Susceptibility sus, BlockState state, World world, BlockPos pos) {
        //if high influence it can be affected by weathering blocks
        WeatheringAgent effect = WeatheringAgent.NONE;
        if(sus == Susceptibility.HIGH){
            effect = getLowInfluenceWeatheringEffect(state, world, pos);
        }
        if(effect == WeatheringAgent.NONE && sus == Susceptibility.MEDIUM){
            effect = getWeatheringEffect(state, world, pos);
        }
        if(effect == WeatheringAgent.NONE && sus == Susceptibility.MEDIUM){
            effect = getHighInfluenceWeatheringEffect(state, world, pos);
        }

        return effect;
    }

    /**
     * This gets called for each side since each side has at least a Low Susceptibility level
     * be very careful when using because overusing it will in the worst case convert every single block
     * Intended for High influence bocks like water sources for mossy blocks that will convert them 100% of the times
     * @return effect that this block has
     */
    default WeatheringAgent getHighInfluenceWeatheringEffect(BlockState state, World world, BlockPos pos){
        return WeatheringAgent.NONE;
    }

    /**
     * gets called more rarely since it requires the given direction has at least a HIGHSusceptibility level
     * Dy default checks if the given block belongs to the same weathering family & it itself is weathering
     * @return effect that this block has
     */
    default WeatheringAgent getLowInfluenceWeatheringEffect(BlockState state, World world, BlockPos pos) {
        Block b = state.getBlock();
        return (b instanceof WeatherableBlock wt &&
                this instanceof Degradable t &&
                b instanceof Degradable c &&
                c.getDegradationLevel().getClass() == t.getDegradationLevel().getClass()
                && wt.isWeatherable(state)) ? WeatheringAgent.WEATHER : WeatheringAgent.NONE;
    }


    //simply check for WEATHERABLE blockstate
    boolean isWeatherable(BlockState state);

    /**
     * if this block can influence this on Low and high influence levels. Usually just checks if it is a fully weathered block
     *
     * @param state neighbouring state
     * @param world world
     * @param pos   neighbour position
     * @return weathering effect that this block has on the current block. Dictates if the block should be allowed to weather or not
     */
    WeatheringAgent getWeatheringEffect(BlockState state, World world, BlockPos pos);
}




