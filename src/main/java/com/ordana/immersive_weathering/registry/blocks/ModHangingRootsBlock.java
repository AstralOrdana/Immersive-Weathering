package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModHangingRootsBlock extends HangingRootsBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty HANGING;
    public static final DirectionProperty FACING;
    private static final Map<Direction, VoxelShape> FACING_TO_SHAPE;
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public ModHangingRootsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(HANGING, true));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HANGING);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HANGING)) {
            return SHAPE;
        }
        else return FACING_TO_SHAPE.get(state.get(FACING));
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canPlaceAt(state, world, pos)) {
            return Blocks.AIR.getDefaultState();
        } else {
            if (state.get(WATERLOGGED)) {
                world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        if(state.get(HANGING)) direction = Direction.DOWN;
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();

        Direction dir = ctx.getSide();
        if(dir==Direction.UP) blockState = blockState.with(HANGING, true);
        else if(dir!=Direction.DOWN) blockState = blockState.with(FACING, dir).with(HANGING, false);
        if (blockState.canPlaceAt(worldView, blockPos)) {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        return null;
    }

    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        HANGING = Properties.HANGING;
        FACING = HorizontalFacingBlock.FACING;
        FACING_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(2.0D, 2.0D, 13.0D, 14.0D, 14.0D, 16.0D), Direction.SOUTH, Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 3.0D), Direction.WEST, Block.createCuboidShape(13.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D), Direction.EAST, Block.createCuboidShape(0.0D, 2.0D, 2.0D, 3.0D, 14.0D, 14.0D)));
    }
}
