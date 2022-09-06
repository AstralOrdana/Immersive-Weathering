package com.ordana.immersive_weathering.blocks.charred;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class CharredPillarBlock extends RotatedPillarBlock implements Charred {

    public static final IntegerProperty DISTANCE;
    public static final BooleanProperty SUPPORTED;
    private static final int TICK_DELAY = 1;

    public CharredPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(SMOLDERING, false).setValue(SUPPORTED, false).setValue(DISTANCE, 4));
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1);
        }
    }

    private int getDistanceAt(BlockState neighbor) {
        if (neighbor.hasProperty(DISTANCE)) {
            return neighbor.is(ModTags.CHARRED_BLOCKS) ? neighbor.getValue(DISTANCE) : 3;
        }
        return 0;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        int i = getDistanceAt(neighborState) + 1;
        if (i != 1 || state.getValue(DISTANCE) != i) {
            level.scheduleTick(currentPos, this, 1);
        }
        if (i == 0 && level.getBlockState(currentPos.below()).isFaceSturdy(level, currentPos.below(), Direction.UP)) {
            level.scheduleTick(currentPos, this, 1);
        }

        return state;
    }

    private boolean isSupported(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP) || level.getBlockState(pos.below()).is(BlockTags.LEAVES);
    }

    public static int getDistance(BlockGetter level, BlockPos pos) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.DOWN);
        BlockState blockState = level.getBlockState(mutableBlockPos);
        int i = 4;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (blockState.hasProperty(DISTANCE) && blockState.is(ModTags.CHARRED_BLOCKS)) {
                i = blockState.getValue(DISTANCE);

            } else if (blockState.isFaceSturdy(level, mutableBlockPos, Direction.UP)) {
                return 0;
            }
            BlockState blockState2 = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
            if (blockState2.hasProperty(DISTANCE) && blockState2.is(ModTags.CHARRED_BLOCKS)) {
                i = Math.min(i, blockState2.getValue(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }
        return i;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        int i = getDistance(level, blockPos);
        return (this.defaultBlockState()).setValue(AXIS, context.getClickedFace().getAxis()).setValue(DISTANCE, i).setValue(SUPPORTED, this.isSupported(level, blockPos));
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int i = getDistance(level, pos);
        BlockPos belowPos = pos.below();
        BlockState blockState = state.setValue(DISTANCE, i).setValue(SUPPORTED, this.isSupported(level, pos));
        if (level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP) || level.getBlockState(belowPos).is(BlockTags.LEAVES)) {
            level.setBlock(pos, blockState.getBlock().withPropertiesOf(blockState).setValue(SUPPORTED, true), 0);
        }
        else level.setBlock(pos, blockState.getBlock().withPropertiesOf(blockState).setValue(SUPPORTED, false), 0);

        if (level.getBlockState(pos).getValue(DISTANCE) != 1 && !state.getValue(SUPPORTED)) {
            level.setBlock(pos, blockState.getBlock().withPropertiesOf(blockState).setValue(DISTANCE, 0), 0);
            FallingBlockEntity.fall(level, pos, blockState);
        }
    }


    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }


    static {
        DISTANCE = BlockStateProperties.STABILITY_DISTANCE;
        SUPPORTED = ModBlockProperties.SUPPORTED;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(SMOLDERING, SUPPORTED, DISTANCE);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        onEntityStepOn(state, entity);
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return interactWithPlayer(state, worldIn, pos, player, handIn);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult pHit, Projectile projectile) {
        BlockPos pos = pHit.getBlockPos();
        interactWithProjectile(level, state, projectile, pos);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof Projectile projectile) {
            interactWithProjectile(worldIn, state, projectile, pos);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        Charred.super.animateTick(state, world, pos, random);
        //TODO: CHECK
        BlockPos blockPos;
        if (random.nextInt(16) == 0 && FallingBlock.isFree(world.getBlockState(pos.below()))) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() - 0.05;
            double f = (double) pos.getZ() + random.nextDouble();
            world.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
        }
    }

}
