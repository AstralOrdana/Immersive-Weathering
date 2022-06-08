package com.ordana.immersive_weathering.registry.blocks;

import java.util.*;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IvyBlock extends MultifaceGrowthBlock implements Fertilizable {
	public static final IntProperty AGE = IntProperty.of("age", 0, 10);
	public static final int MAX_AGE = 10;

	public IvyBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(AGE, 7));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AGE);
	}

	protected BlockState age(BlockState state) {
		return state.contains(AGE) ? state.cycle(AGE) : state;
	}

	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		World world = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		return Arrays.stream(ctx.getPlacementDirections()).map(direction -> this.withDirection(blockState, world, blockPos, direction)).filter(Objects::nonNull).findFirst().orElse(null);
	}

	@Override
	public LichenGrower getGrower() {
		return null;
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(AGE) < MAX_AGE;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return Stream.of(DIRECTIONS).anyMatch(direction -> this.canGrowWithDirection(world, state, pos, direction.getOpposite()));
	}

	@Override
	public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
		return this.canGrowPseudoAdjacent(world, pos, state) || this.canGrowAdjacent(world, pos, state) || this.canGrowExternal(world, pos, state);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
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
		Random posRandom = new Random(MathHelper.hashCode(pos));
		return posRandom.nextInt(2) == 0;
	}

	@Override
	public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.getBlock().getStateWithProperties(state).with(AGE, 0));
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
			if (state.get(MultifaceGrowthBlock.getProperty(dir))) {
				facing.add(dir);
			}
		}

		return facing;
	}

	public boolean growPseudoAdjacent(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, (Random) random);

		for (Direction dir : shuffledDirections) {
			BlockState attemptedStateHere = newStateHere.with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true);
			if (this.canPlaceAt(attemptedStateHere, world, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				world.setBlockState(pos, newStateHere);
				return true;
			}
		}

		return false;
	}

	public boolean canGrowPseudoAdjacent(World world, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;
		for (Direction dir : Direction.values()) {
			BlockState attemptedStateHere = newStateHere.with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true);
			if (this.canPlaceAt(attemptedStateHere, world, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public boolean growAdjacent(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, (Random) random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, (Random) random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {

				if (idealFacingDir.getAxis().getType() == Direction.Type.HORIZONTAL) {
					if (!dir.equals(Direction.UP)) {
						if (dir.equals(Direction.DOWN) && random.nextDouble() < 0.9) {
							return false;
						} else if (dir.getAxis().getType() == Direction.Type.HORIZONTAL && random.nextDouble() < 0.25) {
							return false;
						}
					}
				}

				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos adjacentPos = pos.offset(dir);
					BlockState adjacentState = world.getBlockState(adjacentPos);
					BlockState newState = ModBlocks.IVY.getDefaultState().with(MultifaceGrowthBlock.getProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.isOf(this)) && this.canPlaceAt(newState, world, adjacentPos) && isIvyPos(adjacentPos)) {
						BlockState finalNewState = adjacentState.isOf(this) ? adjacentState.with(MultifaceGrowthBlock.getProperty(idealFacingDir), true) : (state.get(AGE) < MAX_AGE ? newState.with(AGE, state.get(AGE) + 1) : newState);
						world.setBlockState(adjacentPos, finalNewState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowAdjacent(World world, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos adjacentPos = pos.offset(dir);
					BlockState adjacentState = world.getBlockState(adjacentPos);
					BlockState newState = ModBlocks.IVY.getDefaultState().with(MultifaceGrowthBlock.getProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.isOf(this)) && this.canPlaceAt(newState, world, adjacentPos)) {
						BlockState finalNewState = adjacentState.isOf(this) ? adjacentState.with(MultifaceGrowthBlock.getProperty(idealFacingDir), true) : (state.get(AGE) < MAX_AGE ? newState.with(AGE, state.get(AGE) + 1) : newState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean growExternal(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, (Random) random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, (Random) random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.offset(idealFacingDir).offset(dir);
					BlockState externalState = world.getBlockState(externalPos);
					BlockState newStateOpposed = ModBlocks.IVY.getDefaultState().with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.offset(dir)).isAir() && (externalState.isAir() || externalState.isOf(this)) && this.canPlaceAt(newStateOpposed, world, externalPos) && isIvyPos(externalPos)) {
						BlockState finalNewState = externalState.isOf(this) ? externalState.with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true) : (state.get(AGE) < MAX_AGE ? newStateOpposed.with(AGE, state.get(AGE) + 1) : newStateOpposed);
						world.setBlockState(externalPos, finalNewState);
						if (!finalNewState.equals(externalState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowExternal(World world, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.offset(idealFacingDir).offset(dir);
					BlockState externalState = world.getBlockState(externalPos);
					BlockState newStateOpposed = ModBlocks.IVY.getDefaultState().with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.offset(dir)).isAir() && (externalState.isAir() || externalState.isOf(this)) && this.canPlaceAt(newStateOpposed, world, externalPos)) {
						BlockState finalNewState = externalState.isOf(this) ? externalState.with(MultifaceGrowthBlock.getProperty(dir.getOpposite()), true) : (state.get(AGE) < MAX_AGE ? newStateOpposed.with(AGE, state.get(AGE) + 1) : newStateOpposed);
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
