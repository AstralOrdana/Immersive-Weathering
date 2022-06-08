package com.ordana.immersive_weathering.registry.blocks.rustable;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class WaxedRustableTrapdoorBlock extends TrapdoorBlock {
    public WaxedRustableTrapdoorBlock(Settings settings) {
        super(settings);
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
            if (world.getBlockState(pos).isOf(ModBlocks.WAXED_IRON_TRAPDOOR)) {
                if (!this.getDefaultState().isOf(block) && hasPower != state.get(POWERED)) {
                    if (hasPower != state.get(OPEN)) {
                        this.playOpenCloseSound(world, pos, hasPower);
                        world.emitGameEvent((Entity) null, hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlockState(pos, state.with(POWERED, hasPower).with(OPEN, hasPower), 2);
                }
            }
            if (world.getBlockState(pos).isOf(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 10); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isOf(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 20); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isOf(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR)) {
                if (hasPower && !state.get(POWERED)) { // if its recieving power but the blockstate says unpowered, that means it has just been powered on this tick
                    state = state.cycle(OPEN);
                    this.playOpenCloseSound(world, pos, state.get(OPEN));
                    world.emitGameEvent((Entity) null, state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
                world.setBlockState(pos, state.with(POWERED, hasPower).with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (world.getBlockState(pos).isOf(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent((Entity) null, state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).isOf(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent((Entity) null, state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
    }
}
