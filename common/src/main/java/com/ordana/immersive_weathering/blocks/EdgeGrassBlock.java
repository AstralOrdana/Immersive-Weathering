package com.ordana.immersive_weathering.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EdgeGrassBlock extends Block {
  public static final IntegerProperty NORTH = ModBlockProperties.NORTH;
  public static final IntegerProperty SOUTH = ModBlockProperties.SOUTH;
  public static final IntegerProperty EAST = ModBlockProperties.EAST;
  public static final IntegerProperty WEST = ModBlockProperties.WEST;

  public EdgeGrassBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, 0).setValue(SOUTH, 0).setValue(EAST, 0).setValue(WEST, 0));
  }

  public BlockState getStateForPlacement(BlockPlaceContext context) {
    var level = context.getLevel();
    var pos = context.getClickedPos();
    var state = level.getBlockState(pos);
    var direction = context.getHorizontalDirection();

    BlockState blockState = super.getStateForPlacement(context);
    if (state.is(this)) blockState = state;
    var dir = getStateFromDirection(direction);
    var grassLevel = context.isSecondaryUseActive() ? 2 : 1;
    var subState = blockState.setValue(dir, grassLevel);

    //for (Direction direction : Direction.Plane.HORIZONTAL) {
    var relativePos = pos.relative(direction);
    var relativeState = level.getBlockState(relativePos);

    if (relativeState.isFaceSturdy(level, relativePos, direction.getOpposite())) {
      if (subState.getValue(getStateFromDirection(direction)) > 0) grassLevel = context.isSecondaryUseActive() ? 1 : 2;
    }

    if (relativeState.is(this)) {
      if (relativeState.getValue(getStateFromDirection(direction.getOpposite())) > 0) grassLevel = context.isSecondaryUseActive() ? 1 : 2;
    }

    return blockState.setValue(dir, grassLevel);
  }

  public IntegerProperty getStateFromDirection(Direction dir) {
    switch (dir) {
      case EAST -> {
        return EAST;
      }
      case SOUTH -> {
        return SOUTH;
      }
      case WEST -> {
        return WEST;
      }
      default -> {
        return NORTH;
      }
    }
  }

  public Direction getDirectionFromState(IntegerProperty dir) {
    if (EAST.equals(dir)) {
      return Direction.EAST;
    } else if (SOUTH.equals(dir)) {
      return Direction.SOUTH;
    } else if (WEST.equals(dir)) {
      return Direction.WEST;
    }
    return Direction.NORTH;
  }

  public int getAdjacentSolidBlock(Level level, BlockPos pos, BlockState state) {
    //level.getBlockState(pos.relative(getDirectionFromState(state.getValue)))
    return 0;
  }

  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
  }

  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(NORTH, SOUTH, EAST, WEST);
  }

  public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
    return Block.isFaceFull(level.getBlockState(pos.below()).getCollisionShape(level, pos.below()), Direction.UP);
  }

  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    return direction == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }

  public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
    return useContext.getItemInHand().getItem() == this.asItem() && !isFull(state) || super.canBeReplaced(state, useContext);
  }

  public boolean isFull(BlockState state) {
    return state.getValue(NORTH) > 0 && state.getValue(SOUTH) > 0 && state.getValue(EAST) > 0 && state.getValue(WEST) > 0;
  }

}

