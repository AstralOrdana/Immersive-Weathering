package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class ExposedRustableTrapdoorBlock extends CleanRustableTrapdoorBlock {
    public ExposedRustableTrapdoorBlock(RustLevel rustLevel, Settings settings) {
        super(rustLevel, settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (world.hasRain(pos.up()) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER || state.get(WATERLOGGED)) {
                this.tickDegradation(state, world, pos, random);
            }
            if (world.getBlockState(pos.offset(direction)).isOf(Blocks.BUBBLE_COLUMN)) {
                float f = 0.06f;
                if (random.nextFloat() > 0.06f) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
            if (world.hasRain(pos.offset(direction)) && world.getBlockState(pos.up()).isIn(ImmersiveWeathering.WEATHERED_IRON)) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.WEATHERED_IRON::contains)
                        .toList().size() <= 9) {
                    float f = 0.06f;
                    if (random.nextFloat() > 0.06f) {
                        this.tryDegrade(state, world, pos, random);
                    }
                }
            }
        }
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
            if (hasPower) { // if the door is now being powered, open right away
                world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
            }
            else {
                world.createAndScheduleBlockTick(pos, this, 10); // 1 second
            }
            world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        state = state.cycle(OPEN);
        this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
        world.emitGameEvent(state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
        world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
    }
}
