package com.ordana.immersive_weathering.registry.blocks.rustable;
/*
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import java.util.Random;

public class WaxedRustableDoorBlock extends DoorBlock {
    protected WaxedRustableDoorBlock(Properties settings) {
        super(settings);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (direction == Direction.UP && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            if (neighborState.is(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.withPropertiesOf(state);
            }
        }
        if (direction == Direction.DOWN && doubleBlockHalf == DoubleBlockHalf.UPPER) {
            if (neighborState.is(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.withPropertiesOf(state);
            }
            if (neighborState.is(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.withPropertiesOf(state);
            }
        }
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf ? state.setValue(FACING, neighborState.getValue(FACING)).setValue(OPEN, neighborState.getValue(OPEN)).setValue(HINGE, neighborState.getValue(HINGE)).setValue(POWERED, neighborState.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void playSound(Level world, BlockPos pos, boolean open) {
        world.levelEvent(null, open ? this.getOpenSound() : this.getCloseSound(), pos, 0);
    }

    private int getOpenSound() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getCloseSound() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (hasPower != state.getValue(POWERED)) { // checks if redstone input has changed
            if (world.getBlockState(pos).is(ModBlocks.WAXED_IRON_DOOR)) {
                if (!this.defaultBlockState().is(block) && hasPower != state.getValue(POWERED)) {
                    if (hasPower != state.getValue(OPEN)) {
                        this.playSound(world, pos, hasPower);
                        world.gameEvent(hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, hasPower), 2);
                }
            }
            if (world.getBlockState(pos).is(ModBlocks.WAXED_EXPOSED_IRON_DOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.scheduleTick(pos, this, 1); // 1-tick
                } else {
                    world.scheduleTick(pos, this, 10); // 1 second
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
            }
            if (world.getBlockState(pos).is(ModBlocks.WAXED_WEATHERED_IRON_DOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.scheduleTick(pos, this, 1); // 1-tick
                } else {
                    world.scheduleTick(pos, this, 20); // 1 second
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
            }
            if (world.getBlockState(pos).is(ModBlocks.WAXED_RUSTED_IRON_DOOR)) {
                if (hasPower && !state.getValue(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                    state = state.cycle(OPEN);
                    this.playSound(world, pos, state.getValue(OPEN));
                    world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
                world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).is(ModBlocks.WAXED_EXPOSED_IRON_DOOR)) {
            state = state.cycle(OPEN);
            this.playSound(world, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).is(ModBlocks.WAXED_WEATHERED_IRON_DOOR)) {
            state = state.cycle(OPEN);
            this.playSound(world, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            world.gameEvent(state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
    }


}
    */