package com.ordana.immersive_weathering.blocks.mossy;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.PatchSpreader;
import com.ordana.immersive_weathering.util.Weatherable;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface Mossable extends Weatherable {

    Supplier<BiMap<Block, Block>> MOSS_LEVEL_INCREASES = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.STONE, ModBlocks.MOSSY_STONE.get())
                .put(Blocks.STONE_STAIRS, ModBlocks.MOSSY_STONE_STAIRS.get())
                .put(Blocks.STONE_SLAB, ModBlocks.MOSSY_STONE_SLAB.get())
                .put(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE)
                .put(ModBlocks.STONE_WALL.get(), ModBlocks.MOSSY_STONE_WALL.get())
                .put(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS)
                .put(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB)
                .put(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL)
                .put(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS)
                .put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.MOSSY_CHISELED_STONE_BRICKS.get())
                .put(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS)
                .put(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB)
                .put(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL)
                .put(Blocks.BRICKS, ModBlocks.MOSSY_BRICKS.get())
                .put(Blocks.BRICK_STAIRS, ModBlocks.MOSSY_BRICK_STAIRS.get())
                .put(Blocks.BRICK_SLAB, ModBlocks.MOSSY_BRICK_SLAB.get())
                .put(Blocks.BRICK_WALL, ModBlocks.MOSSY_BRICK_WALL.get());
        WeatheringHelper.addOptional(builder, "quark:vertical_brick_slab", "immersive_weathering:vertical_mossy_brick_slab");
        WeatheringHelper.addOptional(builder, "quark:vertical_cobblestone_slab", "quark:vertical_mossy_cobblestone_slab");
        WeatheringHelper.addOptional(builder, "quark:vertical_stone_brick_slab", "quark:vertical_mossy_stone_brick_slab");
        WeatheringHelper.addOptional(builder, "immersive_weathering:vertical_stone_slab", "immersive_weathering:vertical_mossy_stone_slab");

        IWPlatformStuff.addExtraMossyBlocks(builder);
        return builder.build();
    });


    Supplier<BiMap<Block, Block>> MOSS_LEVEL_DECREASES = Suppliers.memoize(() -> MOSS_LEVEL_INCREASES.get().inverse());

    static BlockState getUnaffectedMossBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = MOSS_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = MOSS_LEVEL_DECREASES.get().get(block2);
        }
        return block2.withPropertiesOf(state);
    }

    static BlockState getMossyBlock(BlockState state) {
        Block block2 = state.getBlock();
        Block block3 = MOSS_LEVEL_INCREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = MOSS_LEVEL_INCREASES.get().get(block2);
        }
        return block2.withPropertiesOf(state);
    }


    static Optional<Block> getDecreasedMossBlock(Block block) {
        return Optional.ofNullable(MOSS_LEVEL_DECREASES.get().get(block));
    }

    static Optional<Block> getIncreasedMossBlock(Block block) {
        return Optional.ofNullable(MOSS_LEVEL_INCREASES.get().get(block));
    }

    default Optional<BlockState> getNextMossy(BlockState state) {
        return getIncreasedMossBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default Optional<BlockState> getPreviousMossy(BlockState state) {
        return getDecreasedMossBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }


    MossSpreader getMossSpreader();

    @Override
    default <T extends Enum<?>> Optional<PatchSpreader<T>> getPatchSpreader(Class<T> weatheringClass) {
        if (weatheringClass == MossLevel.class) {
            return Optional.of((PatchSpreader<T>) getMossSpreader());
        }
        return Optional.empty();
    }

    default boolean shouldWeather(BlockState state, BlockPos pos, Level level) {
        if (!CommonConfigs.MOSS_SPREADING_ENABLED.get()) return false;
        return this.getMossSpreader().getWantedWeatheringState(false, pos, level);
    }

    MossLevel getMossLevel();

    boolean isWeathering(BlockState state);

    enum MossLevel {
        UNAFFECTED,
        MOSSY;
    }

    @Override
    default void tryWeather(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < this.getWeatherChanceSpeed()) {
            Optional<BlockState> opt = Optional.empty();
            if (this.getMossSpreader().getWantedWeatheringState(true, pos, serverLevel)) {
                opt = this.getNextMossy(state);
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
