package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LeafPileBlock extends Block implements Fertilizable {

    public static final IntProperty LAYERS = IntProperty.of("layers", 0, 8);
    ;
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final float[] COLLISIONS = new float[]{0, 1.7f, 1.6f, 1.5f, 1.3f, 1.1f, 0.8f, 0.5f};

    private final boolean hasFlowers; //if it can be boneMealed
    private final boolean hasThorns; //if it can hurt
    private final List<DefaultParticleType> particles;

    protected LeafPileBlock(Settings settings, boolean hasFlowers, boolean hasThorns,
                            List<DefaultParticleType> particles) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1));
        this.hasFlowers = hasFlowers;
        this.hasThorns = hasThorns;
        this.particles = particles;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    protected int getLayers(BlockState state) {
        return state.get(LAYERS);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        int layers = this.getLayers(state);
        if (layers > 1 && this.hasThorns) {
            return true;
        }
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int layers = this.getLayers(state);
        if (layers > 1 && this.hasThorns) {
            if (world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) || world.getBlockState(pos.down()).isOf(Blocks.DIRT) || world.getBlockState(pos.down()).isOf(Blocks.COARSE_DIRT) || world.getBlockState(pos.down()).isOf(Blocks.ROOTED_DIRT)) {
                world.setBlockState(pos.down(), Blocks.PODZOL.getDefaultState(), 2);
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int layers = this.getLayers(state);

        if (layers > 2) {
            if (entity instanceof LivingEntity && !(entity instanceof FoxEntity || entity instanceof BeeEntity)) {
                float stuck = COLLISIONS[Math.max(0, layers - 1)];
                entity.slowMovement(state, new Vec3d(stuck, stuck, stuck));

                if (layers >= 6 && this.hasThorns) {
                    if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                        if(!(entity instanceof PlayerEntity player) || player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                            double d = Math.abs(entity.getX() - entity.lastRenderX);
                            double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                            if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                                entity.damage(DamageSource.SWEET_BERRY_BUSH, 0.5F * (layers - 5));
                            }
                        }
                    }
                }
            }
        }

        //particles
        if (layers > 0 && world.isClient && (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this))) {

            Random random = world.getRandom();
            boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
            if (bl && random.nextBoolean()) {
                //double yOff = (layers < 5) ? 0.5 : 1;
                double y = pos.getY() + LAYERS_TO_SHAPE[layers].getMax(Direction.Axis.Y) + 0.0625;
                int color = world.getBiome(pos).value().getFoliageColor();
                for (var p : particles) {
                    world.addParticle(p,
                            entity.getX() +MathHelper.nextBetween(random,-0.2f,0.2f),
                            y,
                            entity.getZ() +MathHelper.nextBetween(random,-0.2f,0.2f),
                            MathHelper.nextBetween(random,-0.75f,-1),
                            color,
                            0);
                    //Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f)
                }
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return switch (type) {
            case LAND -> getLayers(state) < 5;
            case WATER -> getLayers(state) == 0;
            case AIR -> false;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.down());
        if (!below.isOf(Blocks.BARRIER)) {
            if (!below.isOf(Blocks.HONEY_BLOCK) && !below.isOf(Blocks.SOUL_SAND) &&
                    !(below.getFluidState().isOf(Fluids.WATER) && state.get(LAYERS) == 0)) {
                return below.isSideSolidFullSquare(world, pos.down(), Direction.UP) || below.isOf(this) && below.get(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 8 && i > 0) {

            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i < 3;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            int i = blockState.get(LAYERS);
            return blockState.with(LAYERS, Math.min(8, i + 1));
        } else {
            if (blockState.getFluidState().isOf(Fluids.WATER)) return null;
            BlockState below = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
            if (below.getFluidState().isOf(Fluids.WATER)) {
                if (!blockState.isAir()) return null;
                return this.getDefaultState().with(LAYERS, 0);
            }
            return super.getPlacementState(ctx);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return this.hasFlowers;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return this.hasFlowers;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockState(targetPos, s)
                );
            }
        }
    }
}
