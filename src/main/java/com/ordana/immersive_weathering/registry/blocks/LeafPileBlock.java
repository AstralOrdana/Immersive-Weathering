package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.entities.FallingLeafLayerEntity;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LeafPileBlock extends FallingBlock implements Fertilizable {

    public static final IntProperty LAYERS = IntProperty.of("layers", 0, 8);
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final float[] COLLISIONS = new float[]{0, 1.7f, 1.6f, 1.5f, 1.3f, 1.1f, 0.8f, 0.5f};
    private static final int MAX_LAYERS = 8;

    private final boolean hasFlowers; //if it can be boneMealed
    private final boolean hasThorns; //if it can hurt & make podzol
    private final boolean isLeafy; //if it can make humus
    private final List<DefaultParticleType> particles;

    protected LeafPileBlock(Settings settings, boolean hasFlowers, boolean hasThorns, boolean isLeafy,
                            List<DefaultParticleType> particles) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1));
        this.hasFlowers = hasFlowers;
        this.hasThorns = hasThorns;
        this.isLeafy = isLeafy;
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
        return layers > 1;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        BlockPos blockPos;
        BlockState downState = world.getBlockState(pos.down());
        if (random.nextInt(18) == 0 && FallingBlock.canFallThrough(downState) && !downState.isOf(Blocks.WATER)) {
            if (!(world.getBlockState(pos.down()).getBlock() instanceof LeafPileBlock)) {
                double d = (double) pos.getX() + random.nextDouble();
                double e = (double) pos.getY() - 0.05;
                double f = (double) pos.getZ() + random.nextDouble();
                for (var p : particles) {
                    ParticleUtil.spawnParticle(world, pos.down(), p, UniformIntProvider.create(0, 2));
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        int layers = this.getLayers(state);
        if(ImmersiveWeathering.getConfig().leavesConfig.leafPilesConvertBlockBelow) {
            if (layers > 1) {
                if (this.hasThorns) {
                    if (world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) || world.getBlockState(pos.down()).isOf(Blocks.DIRT) || world.getBlockState(pos.down()).isOf(Blocks.COARSE_DIRT) || world.getBlockState(pos.down()).isOf(Blocks.ROOTED_DIRT)) {
                        world.setBlockState(pos.down(), Blocks.PODZOL.getDefaultState(), 3);
                    }
                } else if (this.isLeafy) {
                    if (world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) || world.getBlockState(pos.down()).isOf(Blocks.DIRT) || world.getBlockState(pos.down()).isOf(Blocks.COARSE_DIRT) || world.getBlockState(pos.down()).isOf(Blocks.ROOTED_DIRT)) {
                        world.setBlockState(pos.down(), ModBlocks.HUMUS.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int layers = this.getLayers(state);

        if (layers > 0) {
            if (entity instanceof LivingEntity && !(entity instanceof FoxEntity || entity instanceof BeeEntity)) {
                float stuck = COLLISIONS[Math.max(0, layers - 1)];
                entity.slowMovement(state, new Vec3d(stuck, stuck, stuck));

                if (layers >= 6 && this.hasThorns) {
                    if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                        if(entity instanceof PlayerEntity player && !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty() && ImmersiveWeathering.getConfig().leavesConfig.leggingsPreventThornDamage){
                            return;
                        }
                        else if (entity instanceof PlayerEntity player) {
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

            net.minecraft.util.math.random.Random random = world.getRandom();
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
    public VoxelShape getCollisionShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext c) {
            var e = c.getEntity();
            if (e instanceof FallingLeafLayerEntity) {
                return LAYERS_TO_SHAPE[state.get(LAYERS)];
            }
        }
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState facingState, WorldAccess world, BlockPos currentPos, BlockPos otherPos) {
        if (world instanceof ServerWorld serverLevel) {
            BlockPos pos = currentPos.up();
            BlockState state1 = world.getBlockState(pos);

            while (state1.isOf(this)) {
                serverLevel.createAndScheduleBlockTick(pos, this, this.getFallDelay());
                pos = pos.up();
                state1 = serverLevel.getBlockState(pos);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, facingState, world, currentPos, otherPos);
    }

    @Override
    public boolean canReplace(BlockState pState, ItemPlacementContext pUseContext) {
        int i = pState.get(LAYERS);
        if (pUseContext.getStack().isOf(this.asItem()) && i < MAX_LAYERS) {
            return true;
        } else {
            return i == 1;
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
    public boolean canGrow(World world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
        return this.hasFlowers;
    }

    @Override
    public void grow(ServerWorld world, net.minecraft.util.math.random.Random random, BlockPos pos, BlockState state) {
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

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        int layers = this.getLayers(state);
        if (state.getBlock() != oldState.getBlock() && layers > 0)
            worldIn.createAndScheduleBlockTick(pos, this, this.getFallDelay());
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, net.minecraft.util.math.random.Random pRand) {
        BlockState below = level.getBlockState(pos.down());
        if ((FallingLeafLayerEntity.isFree(below) || hasIncompleteLeafPileBelow(below)) && pos.getY() >= level.getBottomY()) {
            int layers = this.getLayers(state);
            while (state.isOf(this) && layers > 0) {
                FallingBlockEntity fallingblockentity = FallingLeafLayerEntity.fall(level, pos, state);
                this.configureFallingBlockEntity(fallingblockentity);

                pos = pos.up();
                state = level.getBlockState(pos);
            }
        }
    }

    private boolean hasIncompleteLeafPileBelow(BlockState state) {
        return state.isOf(this) && state.get(LAYERS) != MAX_LAYERS;
    }
}
