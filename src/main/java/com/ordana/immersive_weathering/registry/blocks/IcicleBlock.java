package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.annotations.VisibleForTesting;
import com.ordana.immersive_weathering.registry.ModDamageSource;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.state.property.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class IcicleBlock extends PointedDripstoneBlock implements Fallable, SimpleWaterloggedBlock {

    public IcicleBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return isValidIciclePlacement(world, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction2 = state.getValue(TIP_DIRECTION);
            if (direction2 == Direction.DOWN && world.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (direction == direction2.getOpposite() && !this.canSurvive(state, world, pos)) {
                if (direction2 == Direction.DOWN) {
                    this.scheduleFall(state, world, pos);
                } else {
                    world.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean bl = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness thickness = calculateIcicleThickness(world, pos, direction2, bl);
                return state.setValue(THICKNESS, thickness);
            }
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 3.5F, ModDamageSource.ICICLE);
            entity.setTicksFrozen(300);
        } else {
            entity.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluidAboveStalactite(world, pos, state).filter((fluid) -> f < 0.02F || canFillCauldron(fluid)).ifPresent((fluid) ->
                        //TODO: make lava above melt icicle and remove particle
                        spawnDripParticle(world, pos, state, fluid));
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (isStalagmite(state) && !this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        } else {
            spawnFallingStalactite(state, world, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        maybeFillCauldron(state, world, pos, random.nextFloat());
        if (random.nextFloat() < 0.2F && isHeldByIcicle(state, world, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, world, pos, random);
        }
        var biome = world.getBiome(pos);
        if ((world.getBrightness(LightLayer.BLOCK, pos) > 13 - state.getLightBlock(world, pos)) || (world.dimension() == Level.NETHER) || (biome.is(ModTags.HOT) && world.isDay())) {
            world.removeBlock(pos, false);
        }
    }

    public static void maybeFillCauldron(BlockState state, ServerLevel world, BlockPos pos, float dripChance) {
        if (!(dripChance > 0.2F)) {
            if (isHeldByIcicle(state, world, pos) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                Fluid fluid = getCauldronFillFluidType(world, pos);
                float f;
                if (fluid == Fluids.WATER) {
                    f = 0.17578125F;
                }
                else {
                    f = 0.05859375F;
                }

                if (!(dripChance >= f)) {
                    BlockPos blockPos = findTip(state, world, pos, 11, false);
                    if (blockPos != null) {
                        BlockPos blockPos2 = getCauldronPos(world, blockPos);
                        if (blockPos2 != null) {
                            world.levelEvent(1504, blockPos, 0);
                            int i = blockPos.getY() - blockPos2.getY();
                            int j = 50 + i;
                            BlockState blockState = world.getBlockState(blockPos2);
                            world.scheduleTick(blockPos2, blockState.getBlock(), j);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor worldAccess = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction direction = ctx.getNearestLookingVerticalDirection().getOpposite();
        Direction direction2 = calculateTipDirection(worldAccess, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !ctx.isSecondaryUseActive();
            DripstoneThickness thickness = calculateIcicleThickness(worldAccess, blockPos, direction2, bl);
            return thickness == null ? null : this.defaultBlockState().setValue(TIP_DIRECTION, direction2).setValue(THICKNESS, thickness).setValue(WATERLOGGED, worldAccess.getFluidState(blockPos).getType() == Fluids.WATER);
        }
    }

    @Override
    public void onBrokenAfterFall(Level world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            //TODO: add glass shatter sound here
            world.levelEvent(1045, pos, 0);
        }

    }

    @Override
    public DamageSource getFallDamageSource() {
        return ModDamageSource.FALLING_ICICLE;
    }

    private void scheduleFall(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockPos blockPos = findTip(state, world, pos, 2147483647, true);
        if (blockPos != null) {
            BlockPos.MutableBlockPos mutable = blockPos.mutable();
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.getCollisionShape(world, mutable, CollisionContext.empty()).max(Direction.Axis.Y) >= 1.0D || blockState.is(Blocks.POWDER_SNOW)) {
                world.destroyBlock(blockPos, true);
                mutable.move(Direction.UP);
            }

            mutable.move(Direction.UP);

            while(isStalactite(world.getBlockState(mutable))) {
                world.scheduleTick(mutable, this, 2);
                mutable.move(Direction.UP);
            }

        }
    }

    private static void spawnFallingStalactite(BlockState state, ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        BlockState blockState = state;
        while (isStalactite(blockState)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, mutable, blockState);
            if (isTip(blockState, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float f = (float) i;
                fallingBlockEntity.setHurtsEntities(f, 40);
                break;
            }
            mutable.move(Direction.DOWN);
            blockState = world.getBlockState(mutable);
        }
    }

    public static void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.above(1));
        BlockState blockState2 = world.getBlockState(pos.above(2));
        if (canGrow(blockState, blockState2) && world.isDay() && !world.isRaining() && !world.isThundering()) {
            BlockPos blockPos = findTip(state, world, pos, 7, false);
            if (blockPos != null) {
                BlockState blockState3 = world.getBlockState(blockPos);
                if (canDrip(blockState3) && canTipGrow(blockState3, world, blockPos)) {
                    if (random.nextBoolean()) {
                        grow(world, blockPos, Direction.DOWN);
                    } else {
                        growStalagmiteBelow(world, blockPos);
                    }
                }
            }
        }
    }

    private static void growStalagmiteBelow(ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(int i = 0; i < 10; ++i) {
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) {
                return;
            }

            if (isUnmergedTipWithDirection(blockState, Direction.UP) && canTipGrow(blockState, world, mutable)) {
                grow(world, mutable, Direction.UP);
                return;
            }

            if (isValidIciclePlacement(world, mutable, Direction.UP) && !world.isWaterAt(mutable.below())) {
                grow(world, mutable.below(), Direction.UP);
                return;
            }
        }

    }

    private static void grow(ServerLevel world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isUnmergedTipWithDirection(blockState, direction.getOpposite())) {
            createMergedTips(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.is(Blocks.WATER)) {
            createIcicle(world, blockPos, direction, DripstoneThickness.TIP);
        }

    }

    private static void createIcicle(LevelAccessor world, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState blockState = ModBlocks.ICICLE.get().defaultBlockState().setValue(TIP_DIRECTION, direction).setValue(THICKNESS, thickness).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
        world.setBlock(pos, blockState, 3);
    }

    private static void createMergedTips(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockPos blockPos2;
        BlockPos blockPos;
        if (state.getValue(TIP_DIRECTION) == Direction.UP) {
            blockPos = pos;
            blockPos2 = pos.above();
        } else {
            blockPos2 = pos;
            blockPos = pos.below();
        }

        createIcicle(world, blockPos2, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createIcicle(world, blockPos, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    private static void spawnDripParticle(Level world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3 vec3d = state.getOffset(world, pos);
        double e = (double)pos.getX() + 0.5D + vec3d.x;
        double f = (double)((float)(pos.getY() + 1) - 0.6875F) - 0.0625D;
        double g = (double)pos.getZ() + 0.5D + vec3d.z;
        Fluid fluid2 = getDripFluid(world, fluid);
        ParticleOptions particleEffect = fluid2.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        world.addParticle(particleEffect, e, f, g, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    private static BlockPos findTip(BlockState state, LevelAccessor world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.getValue(TIP_DIRECTION);
            Predicate<BlockState> predicate = (statex) -> statex.is(ModBlocks.ICICLE.get()) && statex.getValue(TIP_DIRECTION) == direction;
            return searchInDirection(world, pos, direction.getAxisDirection(), predicate, (statex) -> isTip(statex, allowMerged), range).orElse(null);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader world, BlockPos pos, Direction direction) {
        Direction direction2;
        if (isValidIciclePlacement(world, pos, direction)) {
            direction2 = direction;
        } else {
            if (!isValidIciclePlacement(world, pos, direction.getOpposite())) {
                return null;
            }

            direction2 = direction.getOpposite();
        }

        return direction2;
    }

    private static DripstoneThickness calculateIcicleThickness(LevelReader world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.relative(direction));
        if (isIcicleFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isIcicleFacingDirection(blockState, direction)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness thickness = blockState.getValue(THICKNESS);
            if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.relative(direction2));
                return !isIcicleFacingDirection(blockState2, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !(Boolean)state.getValue(WATERLOGGED);
    }

    private static boolean canTipGrow(BlockState state, ServerLevel world, BlockPos pos) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() || isUnmergedTipWithDirection(blockState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> getSupportingPos(Level world, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(TIP_DIRECTION);
        Predicate<BlockState> predicate = (statex) -> statex.is(ModBlocks.ICICLE.get()) && statex.getValue(TIP_DIRECTION) == direction;
        return searchInDirection(world, pos, direction.getOpposite().getAxisDirection(), predicate, (statex) -> !statex.is(ModBlocks.ICICLE.get()), 11);
    }

    private static boolean isValidIciclePlacement(LevelReader world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, direction) || isIcicleFacingDirection(blockState, direction);
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.is(ModBlocks.ICICLE.get())) {
            return false;
        } else {
            DripstoneThickness thickness = state.getValue(THICKNESS);
            return thickness == DripstoneThickness.TIP || allowMerged && thickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState state, Direction direction) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == direction;
    }

    private static boolean isStalactite(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isHeldByIcicle(BlockState state, LevelReader world, BlockPos pos) {
        return isStalactite(state) && !world.getBlockState(pos.above()).is(ModBlocks.ICICLE.get());
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.is(ModBlocks.ICICLE.get()) && state.getValue(TIP_DIRECTION) == direction;
    }

    @Nullable
    private static BlockPos getCauldronPos(Level world, BlockPos pos) {
        Predicate<BlockState> predicate = (state) -> state.getBlock() instanceof AbstractCauldronBlock;
        return searchInDirection(world, pos, Direction.DOWN.getAxisDirection(), BlockStateBase::isAir, predicate, 11).orElse((BlockPos) null);
    }

    public static Fluid getCauldronFillFluidType(Level world, BlockPos pos) {
        return getFluidAboveStalactite(world, pos, world.getBlockState(pos)).filter(IcicleBlock::canFillCauldron).orElse(Fluids.EMPTY);
    }

    private static Optional<Fluid> getFluidAboveStalactite(Level world, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : getSupportingPos(world, pos, state).map((posx) -> world.getFluidState(posx.above()).getType());
    }

    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState iceBlockState, BlockState waterState) {
        return ((iceBlockState.is(ModTags.ICE)) && (waterState.is(Blocks.WATER)));
    }

    private static Fluid getDripFluid(Level world, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return world.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> searchInDirection(LevelAccessor world, BlockPos pos, Direction.AxisDirection direction, Predicate<BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.immutable());
            }

            if (world.isOutsideBuildHeight(mutable.getY()) || !continuePredicate.test(blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    static {
        TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        UP_TIP_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
        DOWN_TIP_SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        BASE_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        FRUSTUM_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
        MIDDLE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    }
}

