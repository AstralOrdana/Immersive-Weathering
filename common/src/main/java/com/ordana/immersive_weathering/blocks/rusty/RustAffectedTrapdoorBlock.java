package com.ordana.immersive_weathering.blocks.rusty;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;

//affected by rust but cannot rust itself (waxed)
public class RustAffectedTrapdoorBlock extends TrapDoorBlock {
    private final Rustable.RustLevel rustLevel;

    public RustAffectedTrapdoorBlock(Rustable.RustLevel rustLevel, Properties properties, BlockSetType type) {
        super(properties, type);
        this.rustLevel = rustLevel;
    }

    public void playOpenCloseSound(Level level, BlockPos pos, boolean open) {
        level.levelEvent(null, open ? 1011 : 1006, pos, 0);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = level.hasNeighborSignal(pos);
        if (hasPower != state.getValue(POWERED)) { // checks if redstone input has changed
            switch (this.getAge()) {
                case UNAFFECTED -> {
                    if (!this.defaultBlockState().is(block) && hasPower != state.getValue(POWERED)) {
                        if (hasPower != state.getValue(OPEN)) {
                            this.playOpenCloseSound(level, pos, hasPower);
                            level.gameEvent(null, hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                        }
                        level.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, hasPower), 2);
                    }
                }
                case EXPOSED -> {
                    if (hasPower) { // if the door is now being powered, open right away
                        level.scheduleTick(pos, this, 1); // 1-tick
                    } else {
                        level.scheduleTick(pos, this, 10); // 1 second
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
                        this.playOpenCloseSound(level, pos, state.getValue(OPEN));
                        level.gameEvent(null, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
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
            this.playOpenCloseSound(level, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            level.gameEvent(null, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            level.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    public Rustable.RustLevel getAge() {
        return this.rustLevel;
    }

}
