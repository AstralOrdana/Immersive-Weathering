package com.ordana.immersive_weathering.blocks.rustable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;

public class RustAffectedDoorBlock extends DoorBlock {

    private final Rustable.RustLevel rustLevel;

    public RustAffectedDoorBlock(Rustable.RustLevel rustLevel, Properties properties) {
        super(properties, BlockSetType.IRON);
        this.rustLevel = rustLevel;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if ((direction == Direction.UP && doubleBlockHalf == DoubleBlockHalf.LOWER) ||
                (direction == Direction.DOWN && doubleBlockHalf == DoubleBlockHalf.UPPER)) {
            if (neighborState.getBlock() instanceof RustAffectedDoorBlock) {
                state = neighborState.getBlock().withPropertiesOf(state);
            }
        }
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(state.getBlock()) && neighborState.getValue(HALF) != doubleBlockHalf ? state.setValue(FACING, neighborState.getValue(FACING)).setValue(OPEN, neighborState.getValue(OPEN)).setValue(HINGE, neighborState.getValue(HINGE)).setValue(POWERED, neighborState.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
        }
    }

    public void playSound(Level level, BlockPos pos, boolean open) {
        level.levelEvent(null, open ? 1011 : 1005, pos, 0);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (hasPower != state.getValue(POWERED) && this != block) { // checks if redstone input has changed. only reacts to non door blocks

            switch (this.getAge()) {
                case UNAFFECTED -> {
                    if (hasPower != state.getValue(OPEN)) {
                        this.playSound(level, pos, hasPower);
                        level.gameEvent(null,hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    level.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, hasPower), 2);
                }
                case EXPOSED -> {
                    if (hasPower) { // if the door is now being powered, open right away
                        level.scheduleTick(pos, this, 1); // 1-tick
                    } else {
                        level.scheduleTick(pos, this, 10); // 0.5 second
                    }
                    level.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
                }
                case WEATHERED -> {
                    if (hasPower) { // if the door is now being powered, open right away
                        level.scheduleTick(pos, this, 1); // 1-tick
                    } else {
                        level.scheduleTick(pos, this, 20); // 1 second
                    }
                    level.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
                }
                case RUSTED -> {
                    if (hasPower && !state.getValue(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                        state = state.cycle(OPEN);
                        this.playSound(level, pos, state.getValue(OPEN));
                        level.gameEvent(null,state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    level.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS);

                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.getAge() == Rustable.RustLevel.EXPOSED || this.getAge() == Rustable.RustLevel.WEATHERED) {
            state = state.cycle(OPEN);
            this.playSound(level, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            level.gameEvent(null,state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            level.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    public Rustable.RustLevel getAge() {
        return rustLevel;
    }

}