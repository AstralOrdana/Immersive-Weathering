package com.ordana.immersive_weathering.registry.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.AbstractLichenBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class IvyBlock extends AbstractLichenBlock implements Fertilizable {
	public static final IntProperty AGE = Properties.AGE_25;
	public static final int MAX_AGE = 25;

	public IvyBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(AGE, 0));
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
	public boolean hasRandomTicks(BlockState state) {
		return state.get(AGE) < 25;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return state.get(AGE) < 25 && List.of(DIRECTIONS).stream().anyMatch(direction -> this.canSpread(state, world, pos, direction.getOpposite()));
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return state.get(AGE) < 25 && canGrowAround(world, pos, state);
	}

	public boolean canGrowAround(World world, BlockPos pos, BlockState state) {
		return this.canGrowPseudoAdjacent(world, pos, state) || this.canGrowAdjacent(world, pos, state) || this.canGrowExternal(world, pos, state);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.grow(world, random, pos, state);
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
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
			if (state.get(AbstractLichenBlock.getProperty(dir))) {
				facing.add(dir);
			}
		}

		return facing;
	}

	public boolean growPseudoAdjacent(World world, Random random, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction dir : shuffledDirections) {
			BlockState attemptedStateHere = newStateHere.with(AbstractLichenBlock.getProperty(dir.getOpposite()), true);
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
			BlockState attemptedStateHere = newStateHere.with(AbstractLichenBlock.getProperty(dir.getOpposite()), true);
			if (this.canPlaceAt(attemptedStateHere, world, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public boolean growAdjacent(World world, Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

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
					BlockState newState = ModBlocks.IVY.getDefaultState().with(AbstractLichenBlock.getProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.isOf(this)) && this.canPlaceAt(newState, world, adjacentPos)) {
						BlockState finalNewState = adjacentState.isOf(this) ? adjacentState.with(AbstractLichenBlock.getProperty(idealFacingDir), true) : (state.get(AGE) < 25 ? newState.with(AGE, state.get(AGE) + 1) : newState);
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
					BlockState newState = ModBlocks.IVY.getDefaultState().with(AbstractLichenBlock.getProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.isOf(this)) && this.canPlaceAt(newState, world, adjacentPos)) {
						BlockState finalNewState = adjacentState.isOf(this) ? adjacentState.with(AbstractLichenBlock.getProperty(idealFacingDir), true) : (state.get(AGE) < 25 ? newState.with(AGE, state.get(AGE) + 1) : newState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean growExternal(World world, Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.offset(idealFacingDir).offset(dir);
					BlockState externalState = world.getBlockState(externalPos);
					BlockState newStateOpposed = ModBlocks.IVY.getDefaultState().with(AbstractLichenBlock.getProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.offset(dir)).isAir() && (externalState.isAir() || externalState.isOf(this)) && this.canPlaceAt(newStateOpposed, world, externalPos)) {
						BlockState finalNewState = externalState.isOf(this) ? externalState.with(AbstractLichenBlock.getProperty(dir.getOpposite()), true) : (state.get(AGE) < 25 ? newStateOpposed.with(AGE, state.get(AGE) + 1) : newStateOpposed);
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
					BlockState newStateOpposed = ModBlocks.IVY.getDefaultState().with(AbstractLichenBlock.getProperty(dir.getOpposite()), true);

					if (world.getBlockState(pos.offset(dir)).isAir() && (externalState.isAir() || externalState.isOf(this)) && this.canPlaceAt(newStateOpposed, world, externalPos)) {
						BlockState finalNewState = externalState.isOf(this) ? externalState.with(AbstractLichenBlock.getProperty(dir.getOpposite()), true) : (state.get(AGE) < 25 ? newStateOpposed.with(AGE, state.get(AGE) + 1) : newStateOpposed);
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
