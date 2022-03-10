package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatherableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Degradable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public interface Mossable extends Degradable<Mossable.MossLevel>, WeatherableBlock {

    Supplier<BiMap<Block, Block>> MOSS_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(Blocks.STONE, ModBlocks.MOSSY_STONE)
            .put(Blocks.STONE_STAIRS, ModBlocks.MOSSY_STONE_STAIRS)
            .put(Blocks.STONE_SLAB, ModBlocks.MOSSY_STONE_SLAB)
            .put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE)
            .put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS)
            .put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB)
            .put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL)
            .put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS)
            .put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS)
            .put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB)
            .put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL)
            .put(Blocks.BRICKS, ModBlocks.MOSSY_BRICKS)
            .put(Blocks.BRICK_STAIRS, ModBlocks.MOSSY_BRICK_STAIRS)
            .put(Blocks.BRICK_SLAB, ModBlocks.MOSSY_BRICK_SLAB)
            .put(Blocks.BRICK_WALL, ModBlocks.MOSSY_BRICK_WALL)
            .build());

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> MOSS_LEVEL_DECREASES = Suppliers.memoize(() -> Objects.requireNonNull(MOSS_LEVEL_INCREASES.get()).inverse());

    static Optional<Block> getIncreasedMossBlock(Block block) {
        return Optional.ofNullable(MOSS_LEVEL_INCREASES.get().get(block));
    }

    @Override
    default Optional<BlockState> getDegradationResult(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    default float getChance() {
        return 1.0f;
    }

    enum MossLevel {
        UNAFFECTED,
        MOSSY;
    }

    @Override
    default WeatheringAgent getWeatheringEffect(BlockState state, World world, BlockPos pos) {
        var fluidState = state.getFluidState();
        if (fluidState.isIn(FluidTags.LAVA)) return WeatheringAgent.PREVENT_WEATHERING;
        if (fluidState.isIn(FluidTags.WATER) || state.isIn(ModTags.MOSS_SOURCE)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }

    @Override
    default float getInterestForDirection() {
        return 0.3f;
    }

    @Override
    default float getHighInterestChance() {
        return 0.5f;
    }

    @Override
    default float getUnWeatherableChance() {return 0.1f; }

    //utility to grow stuff
    static void growNeighbors(ServerWorld world, Random random, BlockPos pos) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                if (targetBlock.getBlock() instanceof Mossable mossable) {
                    var newState = mossable.getDegradationResult(targetBlock);
                    newState.ifPresent(s -> world.setBlockState(targetPos, s, 3));
                }
            }
        }
    }
}