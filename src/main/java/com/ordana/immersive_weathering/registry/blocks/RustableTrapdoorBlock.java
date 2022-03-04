package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import java.util.Random;

public class RustableTrapdoorBlock extends TrapDoorBlock implements Rustable{
    private final RustLevel rustLevel;

    public RustableTrapdoorBlock(RustLevel rustLevel, Properties settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    public void playOpenCloseSound(Level world, BlockPos pos, boolean open) {
        world.levelEvent(null, open ? this.getOpenSoundEventId() : this.getCloseSoundEventId(), pos, 0);
    }

    private int getOpenSoundEventId() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getCloseSoundEventId() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = world.hasNeighborSignal(pos);
        if (hasPower != state.getValue(POWERED)) { // checks if redstone input has changed
            if (world.getBlockState(pos).is(ModTags.CLEAN_IRON)) {
                if (!this.defaultBlockState().is(block) && hasPower != state.getValue(POWERED)) {
                    if (hasPower != state.getValue(OPEN)) {
                        this.playOpenCloseSound(world, pos, hasPower);
                        world.gameEvent(hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, hasPower), 2);
                }
            }
            if (world.getBlockState(pos).is(ModTags.EXPOSED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.scheduleTick(pos, this, 1); // 1-tick
                } else {
                    world.scheduleTick(pos, this, 10); // 1 second
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
            }
            if (world.getBlockState(pos).is(ModTags.WEATHERED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.scheduleTick(pos, this, 1); // 1-tick
                } else {
                    world.scheduleTick(pos, this, 20); // 1 second
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
            }
            if (world.getBlockState(pos).is(ModTags.RUSTED_IRON)) {
                if (hasPower && !state.getValue(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                    state = state.cycle(OPEN);
                    this.playOpenCloseSound(world, pos, state.getValue(OPEN));
                    world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).is(ModTags.EXPOSED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).is(ModTags.WEATHERED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
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
}
