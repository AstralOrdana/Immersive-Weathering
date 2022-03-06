package com.ordana.immersive_weathering.registry.blocks.rustable;

import com.ordana.immersive_weathering.registry.ModTags;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class RustableBarsBlock extends IronBarsBlock implements Rustable{
    private final RustLevel rustLevel;

    public RustableBarsBlock(RustLevel rustLevel, Properties settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random){
        if (world.getBlockState(pos).is(ModTags.CLEAN_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.relative(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (world.getBlockState(pos.relative(direction)).is(Blocks.AIR) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                    this.onRandomTick(state, world, pos, random);
                }
                if (world.getBlockState(pos.relative(direction)).is(Blocks.BUBBLE_COLUMN)) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
        }
        if (world.getBlockState(pos).is(ModTags.EXPOSED_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.relative(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (world.isRainingAt(pos.above()) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                    this.onRandomTick(state, world, pos, random);
                }
                if (world.getBlockState(pos.relative(direction)).is(Blocks.BUBBLE_COLUMN)) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
                if (world.isRainingAt(pos.relative(direction)) && world.getBlockState(pos.above()).is(ModTags.WEATHERED_IRON)) {
                    if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .filter(b->b.is(ModTags.WEATHERED_IRON))
                            .toList().size() <= 9) {
                        float f = 0.06f;
                        if (random.nextFloat() > 0.06f) {
                            this.applyChangeOverTime(state, world, pos, random);
                        }
                    }
                }
            }
        }
        if (world.getBlockState(pos).is(ModTags.WEATHERED_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.relative(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (neighborState.getFluidState().getType() == Fluids.WATER || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER) {
                    this.onRandomTick(state, world, pos, random);
                }
                if (world.getBlockState(pos.relative(direction)).is(Blocks.BUBBLE_COLUMN)) {
                    if (random.nextFloat() > 0.07f) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).isPresent();
    }

    @Override
    public RustLevel getAge() {
        return this.rustLevel;
    }
}
