package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeathereableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public interface Mossable extends ChangeOverTimeBlock<Mossable.MossLevel>, WeathereableBlock {
    Supplier<BiMap<Block, Block>> MOSS_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(Blocks.STONE, ModBlocks.MOSSY_STONE.get())
            .put(Blocks.STONE_STAIRS, ModBlocks.MOSSY_STONE_STAIRS.get())
            .put(Blocks.STONE_SLAB, ModBlocks.MOSSY_STONE_SLAB.get())
            .put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE)
            .put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS)
            .put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB)
            .put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL)
            .put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS)
            .put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS)
            .put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB)
            .put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL)
            .put(Blocks.BRICKS, ModBlocks.MOSSY_BRICKS.get())
            .put(Blocks.BRICK_STAIRS, ModBlocks.MOSSY_BRICK_STAIRS.get())
            .put(Blocks.BRICK_SLAB, ModBlocks.MOSSY_BRICK_SLAB.get())
            .put(Blocks.BRICK_WALL, ModBlocks.MOSSY_BRICK_WALL.get())
            .build());

    Supplier<BiMap<Block, Block>> MOSS_LEVEL_DECREASES = Suppliers.memoize(() -> MOSS_LEVEL_INCREASES.get().inverse());

    static Optional<Block> getDecreasedMossBlock(Block block) {
        return Optional.ofNullable(MOSS_LEVEL_DECREASES.get().get(block));
    }

    static Block getUnaffectedMossBlock(Block block) {
        Block block2 = block;
        Block block3 = MOSS_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = MOSS_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    static Optional<BlockState> getDecreasedMossState(BlockState state) {
        return Mossable.getDecreasedMossBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getIncreasedMossBlock(Block block) {
        return Optional.ofNullable(MOSS_LEVEL_INCREASES.get().get(block));
    }

    static BlockState getUnaffectedMossState(BlockState state) {
        return Mossable.getUnaffectedMossBlock(state.getBlock()).withPropertiesOf(state);
    }

    @Override
    default Optional<BlockState> getNext(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default float getChanceModifier() {
        return 1.0f;
    }

    enum MossLevel {
        UNAFFECTED,
        MOSSY;

    }

    @Override
    default float getInterestForDirection() {
        return 0.4f;
    }

    @Override
    default float getHighInterestChance() {
        return 0.5f;
    }

    @Override
    default WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos) {
        var fluidState = state.getFluidState();
        if (fluidState.is(FluidTags.LAVA)) return WeatheringAgent.PREVENT_WEATHERING;
        if (fluidState.is(FluidTags.WATER) || state.is(ModTags.MOSS_SOURCE)) return WeatheringAgent.WEATHER;
        return WeatheringAgent.NONE;
    }

    //utility to grow stuff
    static void growNeighbors(ServerLevel world, Random random, BlockPos pos) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                if (targetBlock.getBlock() instanceof Mossable mossable) {
                    var newState = mossable.getNext(targetBlock);
                    newState.ifPresent(s -> world.setBlockAndUpdate(targetPos, s.getBlock().withPropertiesOf(targetBlock)));
                }
            }
        }
    }
}
