package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class RustableTrapdoorBlock extends TrapdoorBlock implements Rustable{
    private final RustLevel rustLevel;

    public RustableTrapdoorBlock(RustLevel rustLevel, Settings settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    public void playOpenCloseSound(World world, BlockPos pos, boolean open) {
        world.syncWorldEvent(null, open ? this.getOpenSoundEventId() : this.getCloseSoundEventId(), pos, 0);
    }

    private int getOpenSoundEventId() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getCloseSoundEventId() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = world.isReceivingRedstonePower(pos);
        if (hasPower != state.get(POWERED)) { // checks if redstone input has changed
            if (world.getBlockState(pos).isIn(ModTags.CLEAN_IRON)) {
                if (!this.getDefaultState().isOf(block) && hasPower != state.get(POWERED)) {
                    if (hasPower != state.get(OPEN)) {
                        this.playOpenCloseSound(world, pos, hasPower);
                        world.emitGameEvent(hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlockState(pos, state.with(POWERED, hasPower).with(OPEN, hasPower), 2);
                }
            }
            if (world.getBlockState(pos).isIn(ModTags.EXPOSED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 10); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isIn(ModTags.WEATHERED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 20); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isIn(ModTags.RUSTED_IRON)) {
                if (hasPower && !state.get(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                    state = state.cycle(OPEN);
                    this.playOpenCloseSound(world, pos, state.get(OPEN));
                    world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
                world.setBlockState(pos, state.with(POWERED, hasPower).with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).isIn(ModTags.EXPOSED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).isIn(ModTags.WEATHERED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (world.getBlockState(pos).isIn(ModTags.CLEAN_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.offset(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (world.getBlockState(pos.offset(direction)).isOf(Blocks.AIR) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                    this.tickDegradation(state, world, pos, random);
                }
                if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
        if (world.getBlockState(pos).isIn(ModTags.EXPOSED_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.offset(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (world.hasRain(pos.up()) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                    this.tickDegradation(state, world, pos, random);
                }
                if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
                if (world.hasRain(pos.offset(direction)) && world.getBlockState(pos.up()).isIn(ModTags.WEATHERED_IRON)) {
                    if (BlockPos.streamOutwards(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .filter(b->b.isIn(ModTags.WEATHERED_IRON))
                            .toList().size() <= 9) {
                        float f = 0.06f;
                        if (random.nextFloat() > 0.06f) {
                            this.tryDegrade(state, world, pos, random);
                        }
                    }
                }
            }
        }
        if (world.getBlockState(pos).isIn(ModTags.WEATHERED_IRON)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.offset(direction);
                BlockState neighborState = world.getBlockState(targetPos);
                if (neighborState.getFluidState().getFluid() == Fluids.WATER || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER) {
                    this.tickDegradation(state, world, pos, random);
                }
                if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                    float f = 0.07f;
                    if (random.nextFloat() > 0.07f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).isPresent();
    }

    @Override
    public RustLevel getDegradationLevel() {
        return this.rustLevel;
    }
}
