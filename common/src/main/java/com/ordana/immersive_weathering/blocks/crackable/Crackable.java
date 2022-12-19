package com.ordana.immersive_weathering.blocks.crackable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.blocks.PatchSpreader;
import com.ordana.immersive_weathering.blocks.Weatherable;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import net.minecraft.util.RandomSource;
import java.util.function.Supplier;

public interface Crackable extends Weatherable {

    Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()

                .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS)
                .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS.get())
                .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
                .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
                .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS)
                .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES)
                .put(Blocks.PRISMARINE_BRICKS, ModBlocks.CRACKED_PRISMARINE_BRICKS.get())
                .put(Blocks.END_STONE_BRICKS, ModBlocks.CRACKED_END_STONE_BRICKS.get())

                .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB.get())
                .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB.get())
                .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB.get())
                .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB.get())
                .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB.get())
                .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB.get())
                .put(Blocks.PRISMARINE_BRICK_SLAB, ModBlocks.CRACKED_PRISMARINE_BRICK_SLAB.get())
                .put(Blocks.END_STONE_BRICK_SLAB, ModBlocks.CRACKED_END_STONE_BRICK_SLAB.get())

                .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS.get())
                .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS.get())
                .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS.get())
                .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS.get())
                .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS.get())
                .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS.get())
                .put(Blocks.PRISMARINE_BRICK_STAIRS, ModBlocks.CRACKED_PRISMARINE_BRICK_STAIRS.get())
                .put(Blocks.END_STONE_BRICK_STAIRS, ModBlocks.CRACKED_END_STONE_BRICK_STAIRS.get())

                .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL.get())
                .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL.get())
                .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL.get())
                .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL.get())
                .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL.get())
                .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL.get())
                .put(ModBlocks.PRISMARINE_BRICK_WALL.get(), ModBlocks.CRACKED_PRISMARINE_BRICK_WALL.get())
                .put(Blocks.END_STONE_BRICK_WALL, ModBlocks.CRACKED_END_STONE_BRICK_WALL.get());
        WeatheringHelper.addOptional(builder,"quark:vertical_brick_slab", "immersive_weathering:vertical_cracked_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_stone_brick_slab", "immersive_weathering:vertical_cracked_stone_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_polished_blackstone_brick_slab", "immersive_weathering:vertical_cracked_polished_blackstone_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_nether_brick_slab", "immersive_weathering:vertical_cracked_nether_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_deepslate_brick_slab", "immersive_weathering:vertical_cracked_deepslate_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_deepslate_tile_slab", "immersive_weathering:vertical_cracked_deepslate_tile_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_end_stone_brick_slab", "immersive_weathering:vertical_cracked_end_stone_brick_slab");
        WeatheringHelper.addOptional(builder,"quark:vertical_prismarine_brick_slab", "immersive_weathering:vertical_cracked_prismarine_brick_slab");

        IWPlatformStuff.addExtraCrackedBlocks(builder);

        return builder.build();
    });

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> CRACK_LEVEL_DECREASES = Suppliers.memoize(() -> CRACK_LEVEL_INCREASES.get().inverse());


    //these can be removed if you want


    static BlockState getUncrackedCrackBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = CRACK_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = CRACK_LEVEL_DECREASES.get().get(block2);
        }
        return block2.withPropertiesOf(state);
    }

    static BlockState getCrackedBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = CRACK_LEVEL_INCREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = CRACK_LEVEL_INCREASES.get().get(block2);
        }
        return block2.withPropertiesOf(state);
    }


    static Optional<Block> getDecreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_DECREASES.get().get(block));
    }

    static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_INCREASES.get().get(block));
    }


    default Optional<BlockState> getNextCracked(BlockState state) {
        return getIncreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default Optional<BlockState> getPreviousCracked(BlockState state) {
        return getDecreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
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

    default boolean shouldWeather(BlockState state, BlockPos pos, Level level) {
        if(!CommonConfigs.CRACK_SPREADING_ENABLED.get())return false;
        return this.getCrackSpreader().getWantedWeatheringState(false, pos, level);
    }

    Item getRepairItem(BlockState state);

    enum CrackLevel {
        UNCRACKED,
        CRACKED;
    }

    @Override
    default void tryWeather(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < this.getWeatherChanceSpeed()) {
            Optional<BlockState> opt = Optional.empty();
            if (this.getCrackSpreader().getWantedWeatheringState(true, pos, serverLevel)) {
                opt = this.getNextCracked(state);
            }
            BlockState newState = opt.orElse(state.setValue(Weatherable.WEATHERABLE, WeatheringState.FALSE));
            if (newState != state) {
                serverLevel.setBlock(pos, newState, 2);
                //schedule block event in 1 tick
                if (!newState.hasProperty(Weatherable.WEATHERABLE)) {
                    serverLevel.scheduleTick(pos, state.getBlock(), 1);
                }
            }
        }
    }


}
