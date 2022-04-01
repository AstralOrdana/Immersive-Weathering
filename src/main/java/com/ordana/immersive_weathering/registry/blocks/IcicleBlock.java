package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModDamageSource;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.entity.FallingIcicleEntity;
import com.ordana.immersive_weathering.registry.entity.IcicleBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class IcicleBlock extends PointedDripstoneBlock implements BlockEntityProvider {

    public IcicleBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(VERTICAL_DIRECTION) == Direction.DOWN && state.get(THICKNESS) == Thickness.TIP) {
            return new IcicleBlockEntity(pos, state);
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T tile) {
        return tile instanceof IcicleBlockEntity t ? t : null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return isValidIciclePlacement(world, pos, state.get(VERTICAL_DIRECTION));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction dir, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (dir != Direction.UP && dir != Direction.DOWN) {
            return state;
        } else {
            Direction direction = state.get(VERTICAL_DIRECTION);
            if (direction == Direction.DOWN && world.getBlockTickScheduler().isQueued(pos, this)) {
                return state;
            } else if (dir == direction.getOpposite() && !this.canPlaceAt(state, world, pos)) {
                if (direction == Direction.DOWN) {
                    world.createAndScheduleBlockTick(pos, this, 2);
                }
                else {
                    world.createAndScheduleBlockTick(pos, this, 1);
                }

                return state;
            } else {
                boolean bl = state.get(THICKNESS) == Thickness.TIP_MERGE;
                Thickness thickness = calculateIcicleThickness(world, pos, direction, bl);
                return state.with(THICKNESS, thickness);
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP && state.get(THICKNESS) == Thickness.TIP) {
            entity.handleFallDamage(fallDistance + 2.0F, 3.5F, ModDamageSource.ICICLE);
            entity.setFrozenTicks(300);
        } else {
            entity.handleFallDamage(fallDistance, 1.0F, DamageSource.FALL);
        }
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!world.isClient && projectile.canModifyAt(world, blockPos) && projectile.getVelocity().length() > 0.6D) {
            world.breakBlock(blockPos, false);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                var optional = getFluid(world, pos, state);
                if(optional.filter(fluid-> (isValidDripFluid(fluid) ||
                        (!world.getBiome(pos).value().isCold(pos) && f < 0.02f))).isPresent()){
                    //TODO: make lava above melt icicle and remove particle
                    createParticle(world, pos, state, optional.get());
                }
            }
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isPointingUp(state) && !this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        dripTick(state, world, pos, random.nextFloat());
        if (random.nextFloat() < 0.2F && isIcicleTip(state, world, pos)) {
            tryGrow(state, world, pos, random);
        }
        var biome = world.getBiome(pos);
        if ((world.getLightLevel(LightType.BLOCK, pos) > 13 - state.getOpacity(world, pos)) || (world.getRegistryKey() == World.NETHER) || (biome.isIn(ModTags.HOT) && world.isDay())) {
            world.removeBlock(pos, false);
        }
    }

    public static void dripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
        if (!(dripChance > 0.2F)) {
            if (isIcicleTip(state, world, pos) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                Fluid fluid = getDripFluid(world, pos);
                float f;
                if (fluid == Fluids.WATER) {
                    f = 0.17578125F;
                } else {
                    f = 0.05859375F;
                }

                if (!(dripChance >= f)) {
                    BlockPos blockPos = getTipPos(state, world, pos, 11, false);
                    if (blockPos != null) {
                        BlockPos blockPos2 = getCauldronPos(world, blockPos);
                        if (blockPos2 != null) {
                            world.syncWorldEvent(1504, blockPos, 0);
                            int i = blockPos.getY() - blockPos2.getY();
                            int j = 50 + i;
                            BlockState blockState = world.getBlockState(blockPos2);
                            world.createAndScheduleBlockTick(blockPos2, blockState.getBlock(), j);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getVerticalPlayerLookDirection().getOpposite();
        Direction direction2 = getDirectionToPlaceAt(worldAccess, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !ctx.shouldCancelInteraction();
            Thickness thickness = calculateIcicleThickness(worldAccess, blockPos, direction2, bl);
            return thickness == null ? null : this.getDefaultState().with(VERTICAL_DIRECTION, direction2).with(THICKNESS, thickness).with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
        }
    }

    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            //world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, world.random.nextFloat() * 0.1F + 0.9F);
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN,pos, Block.getRawIdFromState(this.getDefaultState()));
        }
    }

    @Override
    public DamageSource getDamageSource() {
        return ModDamageSource.FALLING_ICICLE;
    }

    private void scheduleFall(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockPos = getTipPos(state, world, pos, 2147483647, true);
        if (blockPos != null) {
            BlockPos.Mutable mutable = blockPos.mutableCopy();
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.getCollisionShape(world, mutable, ShapeContext.absent()).getMax(Direction.Axis.Y) >= 1.0D || blockState.isOf(Blocks.POWDER_SNOW)) {
                world.breakBlock(blockPos, true);
                mutable.move(Direction.UP);
            }

            mutable.move(Direction.UP);

            while (isPointingDown(world.getBlockState(mutable))) {
                world.createAndScheduleBlockTick(mutable, this, 2);
                mutable.move(Direction.UP);
            }

        }
    }

    private void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        //check if it can fall first
        for (BlockState blockstate = state; isPointingDown(blockstate); blockstate = world.getBlockState(mutable)) {
            if(!this.canPlaceAt(blockstate, world, mutable))break;
            if (isTip(blockstate, true)) {
                if (!FallingBlock.canFallThrough(world.getBlockState(mutable.down()))) return;
                break;
            }
            mutable.move(Direction.DOWN);
        }

        mutable = pos.mutableCopy();

        for (BlockState blockstate = state; isPointingDown(blockstate); blockstate = world.getBlockState(mutable)) {
            FallingBlockEntity fallingblockentity = FallingIcicleEntity.spawnFromBlock(world, mutable, blockstate);
            if (isTip(blockstate, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float f = (float) i;
                fallingblockentity.setHurtEntities(f, 40);
                break;
            }
            mutable.move(Direction.DOWN);
        }
    }

    public static void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.up(1));
        if (blockState.isIn(ModTags.ICE) && world.isDay() && !world.isRaining() && !world.isThundering()) {
            BlockPos blockPos = getTipPos(state, world, pos, 7, false);
            if (blockPos != null) {
                BlockState blockState3 = world.getBlockState(blockPos);
                if (canDrip(blockState3) && canGrow(blockState3, world, blockPos)) {
                    if (random.nextBoolean()) {
                        tryGrow(world, blockPos, Direction.DOWN);
                    } else {
                        tryGrowStalagmite(world, blockPos);
                    }
                }
            }
        }
    }

    private static void tryGrowStalagmite(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < 10; ++i) {
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) {
                return;
            }

            if (isTip(blockState, Direction.UP) && canGrow(blockState, world, mutable)) {
                tryGrow(world, mutable, Direction.UP);
                return;
            }

            if (isValidIciclePlacement(world, mutable, Direction.UP) && !world.isWater(mutable.down())) {
                tryGrow(world, mutable.down(), Direction.UP);
                return;
            }
        }

    }

    private static void tryGrow(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.isOf(Blocks.WATER)) {
            createIcicle(world, blockPos, direction, Thickness.TIP);
        }

    }

    private static void createIcicle(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState blockState = ModBlocks.ICICLE.getDefaultState().with(VERTICAL_DIRECTION, direction).with(THICKNESS, thickness).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, blockState, 3);
    }

    private static void growMerged(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockPos2;
        BlockPos blockPos;
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            blockPos = pos;
            blockPos2 = pos.up();
        } else {
            blockPos2 = pos;
            blockPos = pos.down();
        }

        createIcicle(world, blockPos2, Direction.DOWN, Thickness.TIP_MERGE);
        createIcicle(world, blockPos, Direction.UP, Thickness.TIP_MERGE);
    }

    private static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        double e = pos.getX() + 0.5D + vec3d.x;
        double f = pos.getY();
        double g = pos.getZ() + 0.5D + vec3d.z;
        Fluid fluid2 = getDripFluid(world, fluid);
        ParticleEffect particleEffect = fluid2.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        world.addParticle(particleEffect, e, f, g, 0.0D, 0.0D, 0.0D);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.get(VERTICAL_DIRECTION);
            Predicate<BlockState> predicate = (statex) -> statex.isOf(ModBlocks.ICICLE) && statex.get(VERTICAL_DIRECTION) == direction;
            return searchInDirection(world, pos, direction.getDirection(), predicate, (statex) -> isTip(statex, allowMerged), range).orElse(null);
        }
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
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

    private static Thickness calculateIcicleThickness(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isIcicleFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.get(THICKNESS) != Thickness.TIP_MERGE ? Thickness.TIP : Thickness.TIP_MERGE;
        } else if (!isIcicleFacingDirection(blockState, direction)) {
            return Thickness.TIP;
        } else {
            Thickness thickness = blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isIcicleFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.get(THICKNESS) == Thickness.TIP && !(Boolean) state.get(WATERLOGGED);
    }

    private static boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() || isTip(blockState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> getSupportingPos(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        Predicate<BlockState> predicate = (statex) -> statex.isOf(ModBlocks.ICICLE) && statex.get(VERTICAL_DIRECTION) == direction;
        return searchInDirection(world, pos, direction.getOpposite().getDirection(), predicate, (statex) -> !statex.isOf(ModBlocks.ICICLE), 11);
    }

    private static boolean isValidIciclePlacement(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return (blockState.isOf(Blocks.CHAIN) || blockState.isOf(Blocks.LIGHTNING_ROD) || blockState.isOf(Blocks.END_ROD) ||blockState.isOf(Blocks.HOPPER) || blockState.isIn(BlockTags.WALLS) || blockState.isIn(ModTags.BARS) || blockState.isIn(BlockTags.FENCES) || blockState.isIn(BlockTags.LEAVES) || blockState.isSideSolidFullSquare(world, blockPos, direction)) || isIcicleFacingDirection(blockState, direction);
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(ModBlocks.ICICLE)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isPointingDown(BlockState state) {
        return isIcicleFacingDirection(state, Direction.DOWN);
    }

    private static boolean isPointingUp(BlockState state) {
        return isIcicleFacingDirection(state, Direction.UP);
    }

    private static boolean isIcicleTip(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(ModBlocks.ICICLE);
    }

    private static boolean isIcicleFacingDirection(BlockState state, Direction direction) {
        return state.isOf(ModBlocks.ICICLE) && state.get(VERTICAL_DIRECTION) == direction;
    }

    @Nullable
    private static BlockPos getCauldronPos(World world, BlockPos pos) {
        Predicate<BlockState> predicate = (state) -> state.getBlock() instanceof AbstractCauldronBlock;
        return searchInDirection(world, pos, Direction.DOWN.getDirection(), AbstractBlockState::isAir, predicate, 11).orElse((BlockPos) null);
    }

    public static Fluid getDripFluid(World world, BlockPos pos) {
        return getFluid(world, pos, world.getBlockState(pos)).filter(IcicleBlock::isValidDripFluid).orElse(Fluids.EMPTY);
    }

    private static Optional<Fluid> getFluid(World world, BlockPos pos, BlockState state) {
        return !isPointingDown(state) ? Optional.empty() : getSupportingPos(world, pos, state).map((posx) -> world.getFluidState(posx.up()).getFluid());
    }

    private static boolean isValidDripFluid(Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState iceBlockState, BlockState waterState) {
        return ((iceBlockState.isIn(ModTags.ICE)) && (waterState.isOf(Blocks.WATER)));
    }

    private static Fluid getDripFluid(World world, Fluid fluid) {
        if (fluid.matchesType(Fluids.EMPTY)) {
            return world.getDimension().isUltrawarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, Predicate<BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.toImmutable());
            }

            if (world.isOutOfHeightLimit(mutable.getY()) || !continuePredicate.test(blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}



