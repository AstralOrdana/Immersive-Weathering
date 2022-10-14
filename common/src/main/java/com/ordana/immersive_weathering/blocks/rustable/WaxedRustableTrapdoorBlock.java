package com.ordana.immersive_weathering.blocks.rustable;

import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class WaxedRustableTrapdoorBlock extends TrapDoorBlock implements Rustable {
    private final RustLevel rustLevel;

    public WaxedRustableTrapdoorBlock(RustLevel rustLevel, Properties settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    public void playOpenCloseSound(Level world, BlockPos pos, boolean open) {
        world.levelEvent(null, open ? 1011 : 1006, pos, 0);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean hasPower = world.hasNeighborSignal(pos);
        if (hasPower != state.getValue(POWERED)) { // checks if redstone input has changed
            switch (this.getAge()) {
                case UNAFFECTED -> {
                    if (!this.defaultBlockState().is(block) && hasPower != state.getValue(POWERED)) {
                        if (hasPower != state.getValue(OPEN)) {
                            this.playOpenCloseSound(world, pos, hasPower);
                            world.gameEvent(null, hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                        }
                        world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, hasPower), 2);
                    }
                }
                case EXPOSED -> {
                    if (hasPower) { // if the door is now being powered, open right away
                        world.scheduleTick(pos, this, 1); // 1-tick
                    } else {
                        world.scheduleTick(pos, this, 10); // 1 second
                    }
                    world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
                }
                case WEATHERED -> {
                    if (hasPower) { // if the door is now being powered, open right away
                        world.scheduleTick(pos, this, 1); // 1-tick
                    } else {
                        world.scheduleTick(pos, this, 20); // 1 second
                    }
                    world.setBlock(pos, state.setValue(POWERED, hasPower), Block.UPDATE_CLIENTS);
                }
                case RUSTED -> {
                    if (hasPower && !state.getValue(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                        state = state.cycle(OPEN);
                        this.playOpenCloseSound(world, pos, state.getValue(OPEN));
                        world.gameEvent(null, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlock(pos, state.setValue(POWERED, hasPower).setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (this.getAge() == RustLevel.EXPOSED || this.getAge() == RustLevel.WEATHERED) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.getValue(OPEN)); // if it is powered, play open sound, else play close sound
            world.gameEvent(null, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlock(pos, state.setValue(OPEN, state.getValue(OPEN)), Block.UPDATE_CLIENTS); // set open to match the powered state (powered true, open true)
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    public RustLevel getAge() {
        return this.rustLevel;
    }

}
