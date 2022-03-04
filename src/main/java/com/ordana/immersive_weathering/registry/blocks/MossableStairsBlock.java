package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

public class MossableStairsBlock extends ModStairBlock implements Mossable {
    private final Mossable.MossLevel mossLevel;

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    //TODO: Use cracked interface instead
    static {
        CLEANED_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS.get());
        CLEANED_BLOCKS.put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS.get());
    }

    public MossableStairsBlock(Mossable.MossLevel mossLevel, Supplier<Block> baseBlockState, Properties settings) {
        super(baseBlockState, settings);
        this.mossLevel = mossLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random){

        if (world.getBlockState(pos).is(ModTags.CRACKABLE)) {
            for (Direction direction : Direction.values()) {
                BlockPos targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .anyMatch(Blocks.FIRE::equals)) {
                    float f = 0.5F;
                    if (random.nextFloat() < 0.5F) {
                        CLEANED_BLOCKS.forEach((solid, cracked) -> {
                            if (targetBlock.is(solid)) {
                                world.setBlockAndUpdate(targetPos, cracked.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                }
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.CRACKABLE))
                        .toList().size() >= 20) {
                    if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .filter(b->b.is(ModTags.CRACKED))
                            .toList().size() <= 8) {
                        float f = 0.0000625F;
                        if (random.nextFloat() < 0.0000625F) {
                            CLEANED_BLOCKS.forEach((solid, cracked) -> {
                                if (targetBlock.is(solid)) {
                                    world.setBlockAndUpdate(targetPos, cracked.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        if (world.getBlockState(pos.relative(direction)).is(ModTags.CRACKED)) {
                            float g = 0.02F;
                            if (random.nextFloat() < 0.02F) {
                                CLEANED_BLOCKS.forEach((solid, cracked) -> {
                                    if (targetBlock.is(solid)) {
                                        world.setBlockAndUpdate(targetPos, cracked.withPropertiesOf(targetBlock));
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getType() == Fluids.LAVA || neighborState.getFluidState().getType() == Fluids.FLOWING_LAVA) {
                return;
            }
        }
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if ((world.getBlockState(pos.relative(direction)).is(ModTags.MOSS_SOURCE) || (neighborState.hasProperty(BlockStateProperties.WATERLOGGED)) && neighborState.getValue(BlockStateProperties.WATERLOGGED))) {
                float f = 0.5f;
                if (random.nextFloat() > 0.5f) {
                    this.applyChangeOverTime(state, world, pos, random);
                }
            }
            if (BlockPos.withinManhattanStream(pos, 1, 1, 1)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.hasProperty(BlockStateProperties.WATERLOGGED) && e.getValue(BlockStateProperties.WATERLOGGED)) || e.is(ModTags.MOSS_SOURCE))) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.MOSSY))
                        .toList().size() <= 20) {
                    float f = 0.4f;
                    if (random.nextFloat() > 0.4f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.hasProperty(BlockStateProperties.WATERLOGGED) && e.getValue(BlockStateProperties.WATERLOGGED)) || e.is(ModTags.MOSS_SOURCE))) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.MOSSY))
                        .toList().size() <= 15) {
                    float f = 0.3f;
                    if (random.nextFloat() > 0.3f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.withinManhattanStream(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.hasProperty(BlockStateProperties.WATERLOGGED) && e.getValue(BlockStateProperties.WATERLOGGED)) || e.is(ModTags.MOSS_SOURCE))) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.MOSSY))
                        .toList().size() <= 8) {
                    float f = 0.2f;
                    if (random.nextFloat() > 0.2f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.withinManhattanStream(pos, 4, 4, 4)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.hasProperty(BlockStateProperties.WATERLOGGED) && e.getValue(BlockStateProperties.WATERLOGGED)) || e.is(ModTags.MOSS_SOURCE))) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.MOSSY))
                        .toList().size() <= 6) {
                    float f = 0.1f;
                    if (random.nextFloat() > 0.1f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
            if (BlockPos.withinManhattanStream(pos, 5, 5, 5)
                    .map(world::getBlockState)
                    .anyMatch(e -> (e.hasProperty(BlockStateProperties.WATERLOGGED) && e.getValue(BlockStateProperties.WATERLOGGED)) || e.is(ModTags.MOSS_SOURCE))) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.MOSSY))
                        .toList().size() <= 3) {
                    float f = 0.09f;
                    if (random.nextFloat() > 0.09f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).isPresent();
    }

    @Override
    public Mossable.MossLevel getAge() {
        return this.mossLevel;
    }
}
