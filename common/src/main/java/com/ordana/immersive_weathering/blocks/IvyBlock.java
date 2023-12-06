package com.ordana.immersive_weathering.blocks;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

//not touching this

//TODO: This stuff should now use the new multiface spreader mechanic when doing whatever it is that it does
public class IvyBlock extends MultifaceBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = ModBlockProperties.AGE;
	public static final int MAX_AGE = 10;
	private final MultifaceSpreader spreader = new MultifaceSpreader(this);

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
		Level level = ctx.getLevel();
		BlockPos blockPos = ctx.getClickedPos();
		BlockState blockState = level.getBlockState(blockPos);
		return Arrays.stream(ctx.getNearestLookingDirections()).map(direction -> this.getStateForPlacement(blockState, level, blockPos, direction)).filter(Objects::nonNull).findFirst().orElse(null);
	}

	@Override
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < MAX_AGE;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return state.getValue(AGE) < 0 || Stream.of(DIRECTIONS).anyMatch(direction -> this.isValidStateForPlacement(level, state, pos, direction.getOpposite()));
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;//this.canGrowPseudoAdjacent(level, pos, state) || this.canGrowAdjacent(level, pos, state) || this.canGrowExternal(level, pos, state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource r) {
		int method = r.nextInt(3);
		Random random = new Random(r.nextLong());

		if (method == 0) {
			if (growPseudoAdjacent(level, random, pos, state)) {
				return;
			}
		} else if (method == 1) {
			if (growAdjacent(level, random, pos, state)) {
				return;
			}
		} else {
			if (growExternal(level, random, pos, state)) {
				return;
			}
		}

		int methodTwo = random.nextInt(3);

		if (methodTwo == method) {
			boolean side = random.nextBoolean();
			methodTwo = method == 0 ? side ? 1 : 2 : method == 1 ? side ? 0 : 2 : side ? 0 : 1;
		}

		if (methodTwo == 0) {
			if (growPseudoAdjacent(level, random, pos, state)) {
				return;
			}
		} else if (methodTwo == 1) {
			if (growAdjacent(level, random, pos, state)) {
				return;
			}
		} else {
			if (growExternal(level, random, pos, state)) {
				return;
			}
		}

		int methodThree = method == 0 ? methodTwo == 1 ? 2 : 1 : method == 1 ? methodTwo == 0 ? 2 : 0 : methodTwo == 2 ? 0 : 2;

		if (methodThree == 0) {
			growPseudoAdjacent(level, random, pos, state);
		} else if (methodThree == 1) {
			growAdjacent(level, random, pos, state);
		} else {
			growExternal(level, random, pos, state);
		}

	}

	public static boolean isIvyPos(BlockPos pos) {
		Random posRandom = new Random(Mth.getSeed(pos));
		return posRandom.nextInt(2) == 0;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource r, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, state.getBlock().withPropertiesOf(state).setValue(AGE, 0));
		Random random = new Random(r.nextLong());
		int method = r.nextInt(3);

			if (method == 0) {
				if (growPseudoAdjacent(level, random, pos, state)) {
					return;
				}
			} else if (method == 1) {
				if (growAdjacent(level, random, pos, state)) {
					return;
				}
			} else {
				if (growExternal(level, random, pos, state)) {
					return;
				}
			}

			int methodTwo = random.nextInt(3);

			if (methodTwo == method) {
				boolean side = random.nextBoolean();
				methodTwo = method == 0 ? side ? 1 : 2 : method == 1 ? side ? 0 : 2 : side ? 0 : 1;
			}

			if (methodTwo == 0) {
				if (growPseudoAdjacent(level, random, pos, state)) {
					return;
				}
			} else if (methodTwo == 1) {
				if (growAdjacent(level, random, pos, state)) {
					return;
				}
			} else {
				if (growExternal(level, random, pos, state)) {
					return;
				}
			}

			int methodThree = method == 0 ? methodTwo == 1 ? 2 : 1 : method == 1 ? methodTwo == 0 ? 2 : 0 : methodTwo == 2 ? 0 : 2;

			if (methodThree == 0) {
				growPseudoAdjacent(level, random, pos, state);
			} else if (methodThree == 1) {
				growAdjacent(level, random, pos, state);
			} else {
				growExternal(level, random, pos, state);
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

	public boolean growPseudoAdjacent(Level level, Random random, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction dir : shuffledDirections) {
			BlockState attemptedStateHere = newStateHere.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);
			if (this.canSurvive(attemptedStateHere, level, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				level.setBlockAndUpdate(pos, newStateHere);
				return true;
			}
		}

		return false;
	}

	public boolean canGrowPseudoAdjacent(Level level, BlockPos pos, BlockState state) {
		BlockState newStateHere = state;
		for (Direction dir : Direction.values()) {
			BlockState attemptedStateHere = newStateHere.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);
			if (this.canSurvive(attemptedStateHere, level, pos)) {
				newStateHere = attemptedStateHere;
			}
			if (!newStateHere.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public boolean growAdjacent(Level level, Random random, BlockPos pos, BlockState state) {
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
					BlockState adjacentState = level.getBlockState(adjacentPos);
					BlockState newState = this.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.is(this)) && this.canSurvive(newState, level, adjacentPos) && isIvyPos(adjacentPos)) {
						BlockState finalNewState = adjacentState.is(this) ? adjacentState.setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true) : (state.getValue(AGE) < MAX_AGE ? newState.setValue(AGE, state.getValue(AGE) + 1) : newState);
						level.setBlockAndUpdate(adjacentPos, finalNewState);
						if (!finalNewState.equals(adjacentState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowAdjacent(Level level, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos adjacentPos = pos.relative(dir);
					BlockState adjacentState = level.getBlockState(adjacentPos);
					BlockState newState = this.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(idealFacingDir), true);
					if ((adjacentState.isAir() || adjacentState.is(this)) && this.canSurvive(newState, level, adjacentPos)) {
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

	public boolean growExternal(Level level, Random random, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);
		Collections.shuffle(facing, random);

		List<Direction> shuffledDirections = Lists.newArrayList(Direction.values());
		Collections.shuffle(shuffledDirections, random);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : shuffledDirections) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.relative(idealFacingDir).relative(dir);
					BlockState externalState = level.getBlockState(externalPos);
					BlockState newStateOpposed = this.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);

					if (level.getBlockState(pos.relative(dir)).isAir() && (externalState.isAir() || externalState.is(this)) && this.canSurvive(newStateOpposed, level, externalPos) && isIvyPos(externalPos)) {
						BlockState finalNewState = externalState.is(this) ? externalState.setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true) : (state.getValue(AGE) < MAX_AGE ? newStateOpposed.setValue(AGE, state.getValue(AGE) + 1) : newStateOpposed);
						level.setBlockAndUpdate(externalPos, finalNewState);
						if (!finalNewState.equals(externalState)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean canGrowExternal(Level level, BlockPos pos, BlockState state) {
		List<Direction> facing = getFacingDirections(state);

		for (Direction idealFacingDir : facing) {
			for (Direction dir : Direction.values()) {
				if (dir.getAxis() != idealFacingDir.getAxis()) {
					BlockPos externalPos = pos.relative(idealFacingDir).relative(dir);
					BlockState externalState = level.getBlockState(externalPos);
					BlockState newStateOpposed = this.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(dir.getOpposite()), true);

					if (level.getBlockState(pos.relative(dir)).isAir() && (externalState.isAir() || externalState.is(this)) && this.canSurvive(newStateOpposed, level, externalPos)) {
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

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof ShearsItem && state.getValue(AGE) < MAX_AGE) {
			level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
			ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));
			if (player instanceof ServerPlayer) {
				if (!player.getAbilities().instabuild) stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
				player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
				level.gameEvent(player, GameEvent.SHEAR, pos);
				level.setBlockAndUpdate(pos, state.setValue(AGE, MAX_AGE));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.use(state, level, pos, player, hand, hitResult);
	}
}
