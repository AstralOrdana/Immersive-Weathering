package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Optional;
import java.util.function.Supplier;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface Crackable extends ChangeOverTimeBlock<Crackable.CrackLevel>, SpreadingPatchBlock {

    Supplier<BiMap<Block, Block>> CRACK_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()

            .put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS)
            .put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS.get())
            .put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            .put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS)
            .put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS)
            .put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES)

            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB.get())
            .put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB.get())
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB.get())
            .put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB.get())
            .put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB.get())
            .put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB.get())

            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS.get())
            .put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS.get())
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS.get())
            .put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS.get())
            .put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS.get())
            .put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS.get())

            .put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL.get())
            .put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL.get())
            .put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL.get())
            .put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL.get())
            .put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL.get())
            .put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL.get())
            .build());

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> CRACK_LEVEL_DECREASES = Suppliers.memoize(() -> CRACK_LEVEL_INCREASES.get().inverse());

    Supplier<BiMap<Item, Block>> BRICK_TO_BASE_BLOCK = Suppliers.memoize(() -> ImmutableBiMap.<Item, Block>builder()

            .put(ModItems.BLACKSTONE_BRICK.get(), Blocks.POLISHED_BLACKSTONE_BRICKS)
            .put(ModItems.DEEPSLATE_BRICK.get(), Blocks.DEEPSLATE_BRICKS)
            .put(ModItems.STONE_BRICK.get(), Blocks.STONE_BRICKS)

            .build());

    //these can be removed if you want

    static Optional<Block> getDecreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_DECREASES.get().get(block));
    }

    static Block getUncrackedCrackBlock(Block block) {
        Block block2 = block;
        Block block3 = CRACK_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = CRACK_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    static Optional<BlockState> getDecreasedCrackState(BlockState state) {
        return Crackable.getDecreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static BlockState getUncrackedCrackState(BlockState state) {
        return Crackable.getUncrackedCrackBlock(state.getBlock()).withPropertiesOf(state);
    }

    static Optional<Block> getIncreasedCrackBlock(Block block) {
        return Optional.ofNullable(CRACK_LEVEL_INCREASES.get().get(block));
    }

    Item getRepairItem(BlockState state);

    @Override
    default Optional<BlockState> getNext(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default float getChanceModifier() {
        return 1.0f;
    }

    enum CrackLevel {
        UNCRACKED,
        CRACKED;
    }

    @Override
    default float getInterestForDirection(){
        return 0.7f;
    }

    @Override
    default float getDisjointGrowthChance(){
        return 0.5f;
    }

    @Override
    default float getUnWeatherableChance(){
        return 0.2f;
    }

    @Override
    default WeatheringAgent getWeatheringEffect(BlockState state, Level level, BlockPos pos){
        return state.is(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS) ||state.is(ModTags.CRACKED) ? WeatheringAgent.WEATHER : WeatheringAgent.NONE;
    };
}
