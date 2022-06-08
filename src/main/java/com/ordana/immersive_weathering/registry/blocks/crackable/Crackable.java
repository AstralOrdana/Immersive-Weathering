package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.PatchSpreader;
import com.ordana.immersive_weathering.registry.blocks.Weatherable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public interface Crackable extends Weatherable {

    Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()

            .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS)
            .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS)
            .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
            .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS)
            .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES)
            .put(Blocks.PRISMARINE_BRICKS, ModBlocks.CRACKED_PRISMARINE_BRICKS)
            .put(Blocks.END_STONE_BRICKS, ModBlocks.CRACKED_END_STONE_BRICKS)

            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB)
            .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB)
            .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB)
            .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB)
            .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB)
            .put(Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.CRACKED_PRISMARINE_BRICK_SLAB)
            .put(Blocks.END_STONE_BRICK_SLAB, ModBlocks.CRACKED_END_STONE_BRICK_SLAB)

            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS)
            .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS)
            .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS)
            .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS)
            .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS)
            .put(Blocks.PRISMARINE_BRICK_STAIRS, ModBlocks.CRACKED_PRISMARINE_BRICK_STAIRS)
            .put(Blocks.END_STONE_BRICK_STAIRS, ModBlocks.CRACKED_END_STONE_BRICK_STAIRS)

            .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL)
            .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL)
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL)
            .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL)
            .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL)
            .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL)
            .put(ModBlocks.PRISMARINE_BRICK_WALL, ModBlocks.CRACKED_PRISMARINE_BRICK_WALL)
            .put(Blocks.END_STONE_BRICK_WALL, ModBlocks.CRACKED_END_STONE_BRICK_WALL)
            .build());

    private static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_INCREASES.get().get(block));
    }

    default Optional<BlockState> getNextCracked(BlockState state) {
        return getIncreasedCrackBlock(state.getBlock()).map(block -> block.getStateWithProperties(state));
    }

    CrackSpreader getCrackSpreader();

    @Override
    default <T extends Enum<?>> Optional<PatchSpreader<T>> getPatchSpreader(Class<T> weatheringClass) {
        if (weatheringClass == CrackLevel.class) {
            return Optional.of((PatchSpreader<T>) getCrackSpreader());
        }
        return Optional.empty();
    }

    CrackLevel getCrackLevel();

    default boolean shouldWeather(BlockState state, BlockPos pos, World level) {
        return this.getCrackSpreader().getWantedWeatheringState(false, pos, level);
    }

    enum CrackLevel {
        UNCRACKED,
        CRACKED;
    }

    @Override
    default void tryWeather(BlockState state, ServerWorld serverWorld, BlockPos pos, Random random) {
        if(ImmersiveWeathering.getConfig().blockGrowthConfig.blockCracking) {
            if (random.nextFloat() < this.getWeatherChanceSpeed()) {
                Optional<BlockState> opt = Optional.empty();
                if (this.getCrackSpreader().getWantedWeatheringState(true, pos, serverWorld)) {
                    opt = this.getNextCracked(state);
                }
                BlockState newState = opt.orElse(state.with(WEATHERABLE, WeatheringState.FALSE));
                if (newState != state) {
                    serverWorld.setBlockState(pos, newState, 2);
                    //schedule block event in 1 tick
                    if (!newState.contains(WEATHERABLE)) {
                        serverWorld.createAndScheduleBlockTick(pos, state.getBlock(), 1);
                    }
                }
            }
        }
    }
}