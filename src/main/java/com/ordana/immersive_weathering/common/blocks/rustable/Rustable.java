package com.ordana.immersive_weathering.common.blocks.rustable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.common.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public interface Rustable extends ChangeOverTimeBlock<Rustable.RustLevel> {
    Supplier<BiMap<Block, Block>> RUST_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
            .put(ModBlocks.CUT_IRON.get(), ModBlocks.EXPOSED_CUT_IRON.get())
            .put(ModBlocks.EXPOSED_CUT_IRON.get(), ModBlocks.WEATHERED_CUT_IRON.get())
            .put(ModBlocks.WEATHERED_CUT_IRON.get(), ModBlocks.RUSTED_CUT_IRON.get())
            .put(ModBlocks.CUT_IRON_SLAB.get(), ModBlocks.EXPOSED_CUT_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_SLAB.get(), ModBlocks.WEATHERED_CUT_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_SLAB.get(), ModBlocks.RUSTED_CUT_IRON_SLAB.get())
            .put(ModBlocks.CUT_IRON_STAIRS.get(), ModBlocks.EXPOSED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_STAIRS.get(), ModBlocks.WEATHERED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_STAIRS.get(), ModBlocks.RUSTED_CUT_IRON_STAIRS.get())
            .put(ModBlocks.PLATE_IRON.get(), ModBlocks.EXPOSED_PLATE_IRON.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON.get(), ModBlocks.WEATHERED_PLATE_IRON.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON.get(), ModBlocks.RUSTED_PLATE_IRON.get())
            .put(ModBlocks.PLATE_IRON_SLAB.get(), ModBlocks.EXPOSED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_SLAB.get(), ModBlocks.WEATHERED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_SLAB.get(), ModBlocks.RUSTED_PLATE_IRON_SLAB.get())
            .put(ModBlocks.PLATE_IRON_STAIRS.get(), ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get(), ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get(), ModBlocks.RUSTED_PLATE_IRON_STAIRS.get())

            .put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR.get())
            .put(ModBlocks.EXPOSED_IRON_DOOR.get(), ModBlocks.WEATHERED_IRON_DOOR.get())
            .put(ModBlocks.WEATHERED_IRON_DOOR.get(), ModBlocks.RUSTED_IRON_DOOR.get())
            .put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR.get())
            .put(ModBlocks.EXPOSED_IRON_TRAPDOOR.get(), ModBlocks.WEATHERED_IRON_TRAPDOOR.get())
            .put(ModBlocks.WEATHERED_IRON_TRAPDOOR.get(), ModBlocks.RUSTED_IRON_TRAPDOOR.get())
            .put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS.get())
            .put(ModBlocks.EXPOSED_IRON_BARS.get(), ModBlocks.WEATHERED_IRON_BARS.get())
            .put(ModBlocks.WEATHERED_IRON_BARS.get(), ModBlocks.RUSTED_IRON_BARS.get())

            .put(ModBlocks.CUT_IRON_VERTICAL_SLAB.get(), ModBlocks.EXPOSED_CUT_IRON_VERTICAL_SLAB.get())
            .put(ModBlocks.EXPOSED_CUT_IRON_VERTICAL_SLAB.get(), ModBlocks.WEATHERED_CUT_IRON_VERTICAL_SLAB.get())
            .put(ModBlocks.WEATHERED_CUT_IRON_VERTICAL_SLAB.get(), ModBlocks.RUSTED_CUT_IRON_VERTICAL_SLAB.get())

            .put(ModBlocks.PLATE_IRON_VERTICAL_SLAB.get(), ModBlocks.EXPOSED_PLATE_IRON_VERTICAL_SLAB.get())
            .put(ModBlocks.EXPOSED_PLATE_IRON_VERTICAL_SLAB.get(), ModBlocks.WEATHERED_PLATE_IRON_VERTICAL_SLAB.get())
            .put(ModBlocks.WEATHERED_PLATE_IRON_VERTICAL_SLAB.get(), ModBlocks.RUSTED_PLATE_IRON_VERTICAL_SLAB.get())

            .build());

    Supplier<BiMap<Block, Block>> RUST_LEVEL_DECREASES = Suppliers.memoize(() -> RUST_LEVEL_INCREASES.get().inverse());

    static Optional<Block> getDecreasedRustBlock(Block block) {
        return Optional.ofNullable(RUST_LEVEL_DECREASES.get().get(block));
    }

    static Block getUnaffectedRustBlock(Block block) {
        Block block2 = block;
        Block block3 = RUST_LEVEL_DECREASES.get().get(block2);
        while (block3 != null) {
            block2 = block3;
            block3 = RUST_LEVEL_DECREASES.get().get(block2);
        }
        return block2;
    }

    default Optional<BlockState> getPrevious(BlockState state) {
        return Rustable.getDecreasedRustBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getIncreasedRustBlock(Block block) {
        return Optional.ofNullable(RUST_LEVEL_INCREASES.get().get(block));
    }

    static BlockState getUnaffectedRustState(BlockState state) {
        return Rustable.getUnaffectedRustBlock(state.getBlock()).withPropertiesOf(state);
    }

    @Override
    default Optional<BlockState> getNext(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default public float getChanceModifier() {
        if (this.getAge() == Rustable.RustLevel.UNAFFECTED) {
            return 0.75f;
        }
        return 1.0f;
    }

    enum RustLevel {
        UNAFFECTED,
        EXPOSED,
        WEATHERED,
        RUSTED;

    }

    default int getInfluenceRadius(){
        return 4;
    }

    //same as the base one but has configurable radius
    @Override
    default void applyChangeOverTime(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        int age = this.getAge().ordinal();
        int j = 0;
        int k = 0;
        int affectingDistance = this.getInfluenceRadius();
        for(BlockPos blockpos : BlockPos.withinManhattan(pos, affectingDistance, affectingDistance, affectingDistance)) {
            int distance = blockpos.distManhattan(pos);
            if (distance > affectingDistance) {
                break;
            }

            if (!blockpos.equals(pos)) {
                BlockState blockstate = serverLevel.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (block instanceof ChangeOverTimeBlock changeOverTimeBlock) {
                    Enum<?> ageEnum = changeOverTimeBlock.getAge();
                    //checks if they are of same age class
                    if (this.getAge().getClass() == ageEnum.getClass()) {
                        int neighbourAge = ageEnum.ordinal();
                        if (neighbourAge < age) {
                            return;
                        }

                        if (neighbourAge > age) {
                            ++k;
                        } else {
                            ++j;
                        }
                    }
                }
            }
        }

        float f = (float)(k + 1) / (float)(k + j + 1);
        float f1 = f * f * this.getChanceModifier();
        if (random.nextFloat() < f1) {
            this.getNext(state).ifPresent((p_153039_) -> {
                serverLevel.setBlockAndUpdate(pos, p_153039_);
            });
        }

    }



}
