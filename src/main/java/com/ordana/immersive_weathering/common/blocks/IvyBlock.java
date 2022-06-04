package com.ordana.immersive_weathering.common.blocks;

import java.util.*;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import com.google.common.collect.Lists;
import org.jetbrains.annotations.Nullable;

public class IvyBlock extends MultifaceBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 10);
	public static final int MAX_AGE = 10;

	public IvyBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(AGE, 7));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	protected BlockState age(BlockState state) {
		return state.hasProperty(AGE) ? state.cycle(AGE) : state;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Level world = ctx.getLevel();
		BlockPos blockPos = ctx.getClickedPos();
		BlockState blockState = world.getBlockState(blockPos);
		return Arrays.stream(ctx.getNearestLookingDirections()).map(direction -> this.getStateForPlacement(blockState, world, blockPos, direction)).filter(Objects::nonNull).findFirst().orElse(null);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < MAX_AGE;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return Stream.of(DIRECTIONS).anyMatch(direction -> this.canSpread(state, world, pos, direction.getOpposite()));
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return this.canGrowPseudoAdjacent(world, pos, state) || this.canGrowAdjacent(world, pos, state) || this.canGrowExternal(world, pos, state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		int method = random.nextInt(3);

		if (method == 0) {
			if (growPseudoAdjacent(world, random, pos, state)) {
				return;
			}
		} else if (method == 1) {
			if (growAdjacent(world, random, pos, state)) {
				return;
			}
		} else {
			if (growExternal(world, random, pos, state)) {
				return;
			}
		}

		int methodTwo = random.nextInt(3);

		if (methodTwo == method) {
			boolean side = random.nextBoolean();
			methodTwo = method == 0 ? side ? 1 : 2 : method == 1 ? side ? 0 : 2 : side ? 0 : 1;
		}

		if (methodTwo == 0) {
			if (growPseudoAdjacent(world, random, pos, state)) {
				return;
			}
		} else if (methodTwo == 1) {
			if (growAdjacent(world, random, pos, state)) {
				return;
			}
		} else {
			if (growExternal(world, random, pos, state)) {
				return;
			}
		}

		int methodThree = method == 0 ? methodTwo == 1 ? 2 : 1 : method == 1 ? methodTwo == 0 ? 2 : 0 : methodTwo == 2 ? 0 : 2;

		if (methodThree == 0) {
			growPseudoAdjacent(world, random, pos, state);
		} else if (methodThree == 1) {
			growAdjacent(world, random, pos, state);
		} else {
			growExternal(world, random, pos, state);
		}

	}

	public static boolean isIvyPos(BlockPos pos) {
		Random posRandom = new Random(Mth.getSeed(pos));
		return posRandom.nextInt(2) == 0;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		world.setBlockAndUpdate(pos, state.getBlock().withPropertiesOf(state).setValue(AGE, 0));
		int method = random.nextInt(3);

			if (method == 0) {
				if (growPseudoAdjacent(world, random, pos, state)) {
					return;
				}
			} else if (method == 1) {
				if (growAdjacent(world, random, pos, state)) {
					return;
				}
			} else {
				if (growExternal(world, random, pos, state)) {
					return;
				}
			}

			int methodTwo = random.nextInt(3);

			if (methodTwo == method) {
				boolean side = random.nextBoolean();
				methodTwo = method == 0 ? side ? 1 : 2 : method == 1 ? side ? 0 : 2 : side ? 0 : 1;
			}

			if (methodTwo == 0) {
				if (growPseudoAdjacent(world, random, pos, state)) {
					return;
				}
			} else if (methodTwo == 1) {
				if (growAdjacent(world, random, pos, state)) {
					return;
				}
			} else {
				if (growExternal(world, random, pos, state)) {
					return;
				}
			}

			int methodThree = method == 0 ? methodTwo == 1 ? 2 : 1 : method == 1 ? methodTwo == 0 ? 2 : 0 : methodTwo == 2 ? 0 : 2;

			if (methodThree == 0) {
				growPseudoAdjacent(world, random, pos, state);
			} else if (methodThree == 1) {
				growAdjacent(world, random, pos, state);
			} else {
				growExternal(world, random, pos, state);
			}


	}

	public List<Direction> getFacingDirections(BlockState state) {
		List<Direction> facing = Lists.newArrayList();

		for (Direction dir : Direction.values()) {
			if (state.getValue(MultifaceBlock.getFaceProperty(dir))) {
				facing.add(dir);
			}
		}

		return facing;
	}

	public boolean growPseudoAdjacent(Level world, Random random, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction dir : shuffledDirections) {
			BlockState attemptedStateHere = newStateHere.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);
			if (this.canSurvive(attemptedStateHere, world, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				world.setBlockAndUpdate(pos, newStateHere);
				return true;
			}
		}

		return false;
	}

	public boolean canGrowPseudoAdjacent(Level world, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;
		for (Direction dir : Direction.values()) {
			BlockState attemptedStateHere = newStateHere.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);
			if (this.canSurvive(attemptedStateHere, world, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public boolean growAdjacent(Level world, Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {

				if (idealFacingDir.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
					if (!dir.equals(Direction.UP)) {
						if (dir.equals(Direction.DOWN) && random.nextDouble() < 0.9) {
							return false;
						} else if (dir.getAxis().getPlane() == Direction.Plane.HORIZONTAL && random.nextDouble() < 0.25) {
							return false;
						}
					}
				}

				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos adjacentPos = pos.relative(dir);
					BlockState adjacentState = world.getBlockState(adjacentPos);
					BlockState newState = ModBlocks.IVY.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.is(this)) && this.canSurvive(newState, world, adjacentPos) && isIvyPos(adjacentPos)) {
						BlockState finalNewState = adjacentState.is(this) ? adjacentState.setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true) : (state.getValue(AGE) < MAX_AGE ? newState.setValue(AGE, state.getValue(AGE) + 1) : newState);
						world.setBlockAndUpdate(adjacentPos, finalNewState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowAdjacent(Level world, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos adjacentPos = pos.relative(dir);
					BlockState adjacentState = world.getBlockState(adjacentPos);
					BlockState newState = ModBlocks.IVY.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.is(this)) && this.canSurvive(newState, world, adjacentPos)) {
						BlockState finalNewState = adjacentState.is(this) ? adjacentState.setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true) : (state.getValue(AGE) < MAX_AGE ? newState.setValue(AGE, state.getValue(AGE) + 1) : newState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean growExternal(Level world, Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.relative(idealFacingDir).relative(dir);
					BlockState externalState = world.getBlockState(externalPos);
					BlockState newStateOpposed = ModBlocks.IVY.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.relative(dir)).isAir() && (externalState.isAir() || externalState.is(this)) && this.canSurvive(newStateOpposed, world, externalPos) && isIvyPos(externalPos)) {
						BlockState finalNewState = externalState.is(this) ? externalState.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true) : (state.getValue(AGE) < MAX_AGE ? newStateOpposed.setValue(AGE, state.getValue(AGE) + 1) : newStateOpposed);
						world.setBlockAndUpdate(externalPos, finalNewState);
						if (!finalNewState.equals(externalState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowExternal(Level world, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.relative(idealFacingDir).relative(dir);
					BlockState externalState = world.getBlockState(externalPos);
					BlockState newStateOpposed = ModBlocks.IVY.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.relative(dir)).isAir() && (externalState.isAir() || externalState.is(this)) && this.canSurvive(newStateOpposed, world, externalPos)) {
						BlockState finalNewState = externalState.is(this) ? externalState.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true) : (state.getValue(AGE) < MAX_AGE ? newStateOpposed.setValue(AGE, state.getValue(AGE) + 1) : newStateOpposed);
						if (!finalNewState.equals(externalState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

}
