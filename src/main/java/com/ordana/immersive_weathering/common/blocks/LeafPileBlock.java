package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LeafPileBlock extends Block implements BonemealableBlock {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 0, 8);
    ;
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final float[] COLLISIONS = new float[]{1, 0.999f, 0.998f, 0.997f, 0.996f, 0.994f, 0.993f, 0.992f};

    private final boolean hasFlowers; //if it can be boneMealed
    private final boolean hasThorns; //if it can hurt & make podzol
    private final boolean isLeafy; //if it can make humus
    private final List<Lazy<SimpleParticleType>> particles;

    public LeafPileBlock(Properties settings, boolean hasFlowers, boolean hasThorns, boolean isLeafy,
                            List<Supplier<SimpleParticleType>> particles) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
        this.hasFlowers = hasFlowers;
        this.hasThorns = hasThorns;
        this.particles = particles.stream().map(Lazy::of).collect(Collectors.toList());
        this.isLeafy = isLeafy;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 1;
    }

    protected int getLayers(BlockState state) {
        return state.getValue(LAYERS);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        int layers = this.getLayers(state);

        if (layers > 3) {
            if (entity instanceof LivingEntity && !(entity instanceof Fox || entity instanceof Bee)) {
                float stuck = COLLISIONS[Math.max(0, layers - 1)];
                entity.makeStuckInBlock(state, new Vec3(stuck, 1, stuck));

                if (layers >= 6 && this.hasThorns) {
                    if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                        if (!(entity instanceof Player player) || player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {

                            double d = Math.abs(entity.getX() - entity.xOld);
                            double e = Math.abs(entity.getZ() - entity.zOld);
                            if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                                entity.hurt(DamageSource.SWEET_BERRY_BUSH, 0.5F * (layers - 5));
                            }
                        }
                    }
                }
            }
        }

        //particles
        if (layers > 0 && level.isClientSide && (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this))) {

            Random random = level.getRandom();
            boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
            if (bl && random.nextBoolean()) {
                //double yOff = (layers < 5) ? 0.5 : 1;
                double y = pos.getY() + LAYERS_TO_SHAPE[layers].max(Direction.Axis.Y) + 0.0625;
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos,0);
                for (var p : particles) {
                    level.addParticle(p.get(),
                            entity.getX() +Mth.randomBetween(random,-0.2f,0.2f),
                            y,
                            entity.getZ() +Mth.randomBetween(random,-0.2f,0.2f),
                            Mth.randomBetween(random,-0.75f,-1),
                            color,
                            0);
                    //Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f)
                }
            }
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return switch (type) {
            case LAND -> getLayers(state) < 5;
            case WATER -> getLayers(state) == 0;
            case AIR -> false;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return LAYERS_TO_SHAPE[getLayers(state)];
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.below());
        if (!below.is(Blocks.BARRIER)) {
            if (!below.is(Blocks.HONEY_BLOCK) && !below.is(Blocks.SOUL_SAND) &&
                    !(below.getFluidState().is(Fluids.WATER) && state.getValue(LAYERS) == 0)) {
                return below.isFaceSturdy(world, pos.below(), Direction.UP) || below.is(this) && below.getValue(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().is(this.asItem()) && i < 8 && i > 0) {

            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i < 3;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockState.is(this)) {
            int i = blockState.getValue(LAYERS);
            return blockState.setValue(LAYERS, Math.min(8, i + 1));
        } else {
            if (blockState.getFluidState().is(Fluids.WATER)) return null;
            BlockState below = ctx.getLevel().getBlockState(ctx.getClickedPos().below());
            if (below.getFluidState().is(Fluids.WATER)) {
                if (!blockState.isAir()) return null;
                return this.defaultBlockState().setValue(LAYERS, 0);
            }
            return super.getStateForPlacement(ctx);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return this.hasFlowers;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return this.hasFlowers;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockAndUpdate(targetPos, s)
                );
            }
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        int layers = this.getLayers(state);
        return layers > 1 && (this.isLeafy || this.hasThorns);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int layers = this.getLayers(state);
        if (layers > 1 && random.nextFloat() < ServerConfigs.HUMUS_SPAWN_BELOW_LEAVES.get()) {
            //TODO: maybe move this to data
            if(this.isLeafy || this.hasThorns) {
                BlockState below = world.getBlockState(pos.below());
                if (below.is(Blocks.GRASS_BLOCK) || below.is(Blocks.DIRT) || below.is(Blocks.COARSE_DIRT) || below.is(Blocks.ROOTED_DIRT)) {
                    world.setBlock(pos.below(), (isLeafy ? ModBlocks.HUMUS.get() : Blocks.PODZOL).defaultBlockState(), 2);
                }
            }
        }
    }

    public static void spawnFromLeaves(BlockState state, BlockPos pos, Level level, Random random){
        //Drastically reduced this chance to help lag
        if (!state.getValue(LeavesBlock.PERSISTENT) && random.nextFloat() < ServerConfigs.FALLING_LEAVES.get()) {

            var leafPile = LeafPilesRegistry.getFallenLeafPile(state).orElse(null);
            if (leafPile != null && level.getBlockState(pos.below()).isAir()) {


                if (WeatheringHelper.isIciclePos(pos) && level.getBiome(pos).value().coldEnoughToSnow(pos)) {
                    level.setBlock(pos.below(), ModBlocks.ICICLE.get().defaultBlockState()
                            .setValue(PointedDripstoneBlock.TIP_DIRECTION, Direction.DOWN), 2);
                }

                if (!level.isAreaLoaded(pos, 2)) return;
                BlockPos targetPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
                int maxFallenLeavesReach = ServerConfigs.LEAF_PILE_REACH.get();
                int maxPileHeight = ServerConfigs.MAX_LEAF_PILE_HEIGHT.get();
                int dist = pos.getY() - targetPos.getY();
                //calculating normally if heightmap fails
                if (dist < 0) {
                    targetPos = pos;
                    do {
                        targetPos = targetPos.below();
                        dist = pos.getY() - targetPos.getY();
                    } while (level.getBlockState(targetPos).getMaterial().isReplaceable() &&
                            dist < maxFallenLeavesReach);
                    targetPos = targetPos.above();

                }
                if (dist < maxFallenLeavesReach) {

                    BlockState replaceState = level.getBlockState(targetPos);

                    boolean isOnLeaf = replaceState.getBlock() instanceof LeafPileBlock;
                    int pileHeight = 1;
                    if (isOnLeaf) {
                        pileHeight = replaceState.getValue(LeafPileBlock.LAYERS);
                        if (pileHeight == 0 || pileHeight >= maxPileHeight) return;
                    }

                    BlockState baseLeaf = leafPile.defaultBlockState().setValue(LeafPileBlock.LAYERS, 0);
                    //if we find a non-air block we check if its upper face is sturdy. Given previous iteration if we are not on the first cycle blocks above must be air
                    if (isOnLeaf ||
                            (replaceState.getMaterial().isReplaceable() && baseLeaf.canSurvive(level, targetPos)
                                    && !WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, 2, 2,
                                    level, b -> b.getBlock() instanceof LeafPileBlock, 6))) {


                        if (level.getBlockState(targetPos.below()).is(Blocks.WATER)) {
                            level.setBlock(targetPos, baseLeaf.setValue(LeafPileBlock.LAYERS, 0), 2);
                        } else {
                            if (isOnLeaf) {
                                int original = pileHeight;
                                boolean hasLog = false;
                                BlockState[] neighbors = new BlockState[4];
                                for (Direction direction : Direction.Plane.HORIZONTAL) {
                                    neighbors[direction.get2DDataValue()] = level.getBlockState(targetPos.relative(direction));
                                }
                                for (var neighbor : neighbors) {
                                    if (WeatheringHelper.isLog(neighbor)) {
                                        hasLog = true;
                                        break;
                                    }
                                }
                                for (var neighbor : neighbors) {
                                    if (neighbor.getBlock() instanceof LeafPileBlock) {
                                        int i = hasLog ? maxPileHeight :
                                                Math.min(neighbor.getValue(LeafPileBlock.LAYERS) - 1, maxPileHeight);
                                        if (i > pileHeight) {
                                            pileHeight = Math.min(pileHeight + 1, i);
                                            break;
                                        }
                                    }
                                }
                                if (pileHeight == original) return;
                            }
                            level.setBlock(targetPos, baseLeaf.setValue(LeafPileBlock.LAYERS, pileHeight), 2);
                        }
                    }

                }
            }
        }
    }


}
