package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class WaxedRustableDoorBlock extends DoorBlock {
    protected WaxedRustableDoorBlock(Settings settings) {
        super(settings);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.get(HALF);
        if (direction == Direction.UP && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            if (neighborState.isOf(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.getStateWithProperties(state);
            }
        }
        if (direction == Direction.DOWN && doubleBlockHalf == DoubleBlockHalf.UPPER) {
            if (neighborState.isOf(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.getStateWithProperties(state);
            }
            if (neighborState.isOf(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.getStateWithProperties(state);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
        boolean hasPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (hasPower != state.get(POWERED)) { // checks if redstone input has changed
            if (world.getBlockState(pos).isIn(ImmersiveWeathering.EXPOSED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 10); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isIn(ImmersiveWeathering.WEATHERED_IRON)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 20); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isIn(ImmersiveWeathering.RUSTED_IRON)) {
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
        if (world.getBlockState(pos).isIn(ImmersiveWeathering.EXPOSED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).isIn(ImmersiveWeathering.WEATHERED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
    }
}
