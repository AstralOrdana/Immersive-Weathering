package com.ordana.immersive_weathering.blocks.rustable;

import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import java.util.Random;

public class RustableSlabBlock extends SlabBlock implements Rustable {
    private final RustLevel rustLevel;

    public RustableSlabBlock(RustLevel rustLevel, Properties settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
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
                            .filter(b -> b.is(ModTags.WEATHERED_IRON))
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
                    float f = 0.07f;
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

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int i, int i1) {
        if (i == 1) {
            if (level.isClientSide) {
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
            }
            return true;
        }
        return super.triggerEvent(state, level, pos, i, i1);
    }
}
