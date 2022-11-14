package com.ordana.immersive_weathering.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;
import java.util.Map;

public class WallRootsBlock extends Block implements SimpleWaterloggedBlock {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(2.0D, 2.0D, 13.0D, 14.0D, 14.0D, 16.0D), Direction.SOUTH, Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 3.0D), Direction.WEST, Block.box(13.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D), Direction.EAST, Block.box(0.0D, 2.0D, 2.0D, 3.0D, 14.0D, 14.0D)));
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WallRootsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_49220_, BlockPos p_49221_, CollisionContext p_49222_) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos pos, BlockPos pos1) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(levelAccessor, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = levelReader.getBlockState(blockpos);
        return blockstate.isFaceSturdy(levelReader, blockpos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = super.getStateForPlacement(context);
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                blockstate = blockstate.setValue(FACING, direction.getOpposite());
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate.setValue(WATERLOGGED,levelreader.getFluidState(blockpos).getType() == Fluids.WATER);
                }
            }
        }
        return null;
    }


    @PlatformOnly(PlatformOnly.FORGE)
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0;
    }

    @Override
    public float getMaxVerticalOffset() {
        return 0.2F;
    }
}
