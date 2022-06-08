package com.ordana.immersive_weathering.registry.blocks.rustable;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class RustableDoorBlock extends DoorBlock implements Rustable{
    private final RustLevel rustLevel;

    public RustableDoorBlock(RustLevel rustLevel, Settings settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction == Direction.UP && doubleBlockHalf == DoubleBlockHalf.LOWER) {
            if (neighborState.isOf(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_IRON_DOOR)) {
                return ModBlocks.WAXED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_EXPOSED_IRON_DOOR)) {
                return ModBlocks.WAXED_EXPOSED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_WEATHERED_IRON_DOOR)) {
                return ModBlocks.WAXED_WEATHERED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_RUSTED_IRON_DOOR)) {
                return ModBlocks.WAXED_RUSTED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
        }
        if (direction == Direction.DOWN && doubleBlockHalf == DoubleBlockHalf.UPPER) {
            if (neighborState.isOf(Blocks.IRON_DOOR)) {
                return Blocks.IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.EXPOSED_IRON_DOOR)) {
                return ModBlocks.EXPOSED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WEATHERED_IRON_DOOR)) {
                return ModBlocks.WEATHERED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.RUSTED_IRON_DOOR)) {
                return ModBlocks.RUSTED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_IRON_DOOR)) {
                return ModBlocks.WAXED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_EXPOSED_IRON_DOOR)) {
                return ModBlocks.WAXED_EXPOSED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_WEATHERED_IRON_DOOR)) {
                return ModBlocks.WAXED_WEATHERED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
            if (neighborState.isOf(ModBlocks.WAXED_RUSTED_IRON_DOOR)) {
                return ModBlocks.WAXED_RUSTED_IRON_DOOR.getStateWithProperties(state).with(OPEN, neighborState.get(OPEN));
            }
        }
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf ? (BlockState)((BlockState)((BlockState)((BlockState)state.with(FACING, (Direction)neighborState.get(FACING))).with(OPEN, (Boolean)neighborState.get(OPEN))).with(HINGE, (DoorHinge)neighborState.get(HINGE))).with(POWERED, (Boolean)neighborState.get(POWERED)) : Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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
        boolean hasPower = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if (hasPower != state.get(POWERED)) { // checks if redstone input has changed
            if (world.getBlockState(pos).isOf(Blocks.IRON_DOOR)) {
                if (!this.getDefaultState().isOf(block) && hasPower != state.get(POWERED)) {
                    if (hasPower != state.get(OPEN)) {
                        this.playOpenCloseSound(world, pos, hasPower);
                        world.emitGameEvent((Entity) null,hasPower ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                    }
                    world.setBlockState(pos, state.with(POWERED, hasPower).with(OPEN, hasPower), 2);
                }
            }
            if (world.getBlockState(pos).isOf(ModBlocks.EXPOSED_IRON_DOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 10); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isOf(ModBlocks.WEATHERED_IRON_DOOR)) {
                if (hasPower) { // if the door is now being powered, open right away
                    world.createAndScheduleBlockTick(pos, this, 1); // 1-tick
                } else {
                    world.createAndScheduleBlockTick(pos, this, 20); // 1 second
                }
                world.setBlockState(pos, state.with(POWERED, hasPower), Block.NOTIFY_LISTENERS);
            }
            if (world.getBlockState(pos).isOf(ModBlocks.RUSTED_IRON_DOOR)) {
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
        if (world.getBlockState(pos).isIn(ModTags.EXPOSED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent((Entity) null, state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
        if (world.getBlockState(pos).isIn(ModTags.WEATHERED_IRON)) {
            state = state.cycle(OPEN);
            this.playOpenCloseSound(world, pos, state.get(OPEN)); // if it is powered, play open sound, else play close sound
            world.emitGameEvent((Entity) null, state.get(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos); // same principle here
            world.setBlockState(pos, state.with(OPEN, state.get(OPEN)), Block.NOTIFY_LISTENERS); // set open to match the powered state (powered true, open true)
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random){
        if(ImmersiveWeathering.getConfig().blockGrowthConfig.blockRusting) {
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
                                .filter(b -> b.isIn(ModTags.WEATHERED_IRON))
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
                        if (random.nextFloat() > 0.07f) {
                            this.tryDegrade(state, world, pos, random);
                        }
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
