package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.entities.FallingLayerEntity;
import com.ordana.immersive_weathering.reg.LeafPilesRegistry;
import com.ordana.immersive_weathering.reg.ModTags;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class LeafPileBlock extends LayerBlock implements BonemealableBlock {

    private static final int FIRE_SPREAD = 30;
    private static final int FLAMMABILITY = 60;

    public static final IntegerProperty LAYERS = ModBlockProperties.LEAF_LAYERS;


    private static final VoxelShape[] SHAPE_BY_LAYER_L = new VoxelShape[8 + 1];

    static {
        Arrays.setAll(SHAPE_BY_LAYER_L, l -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, l * 2, 16.0D));
        SHAPE_BY_LAYER_L[0] = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1f, 16.0D);
    }

    private static final float[] COLLISIONS = new float[]{1, 0.999f, 0.998f, 0.997f, 0.996f, 0.994f, 0.993f, 0.992f};

    private final boolean hasFlowers; //if it can be boneMealed
    private final boolean hasThorns; //if it can hurt & make podzol
    private final boolean isLeafy; //if it can make humus
    private final List<Supplier<SimpleParticleType>> particles;

    public LeafPileBlock(Properties settings, boolean hasFlowers, boolean hasThorns, boolean isLeafy,
                         List<Supplier<SimpleParticleType>> particles) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1));
        this.hasFlowers = hasFlowers;
        this.hasThorns = hasThorns;
        this.particles = particles;
        this.isLeafy = isLeafy;
        RegHelper.registerBlockFlammability(this, FIRE_SPREAD, FLAMMABILITY);
    }


    @PlatformOnly(PlatformOnly.FORGE)
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return FIRE_SPREAD;
    }

    @PlatformOnly(PlatformOnly.FABRIC)
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return FLAMMABILITY;
    }

    @Override
    public int getLayers(BlockState state) {
        return state.getValue(LAYERS);
    }

    @Override
    public IntegerProperty layerProperty() {
        return LAYERS;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return 1;
    }

    @Override
    public VoxelShape getDefaultShape(BlockState state) {
        return SHAPE_BY_LAYER_L[state.getValue(LAYERS)];
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        int layers = this.getLayers(state);

        if (layers > 3) {
            if (CommonConfigs.LEAF_PILES_SLOW.get() && entity instanceof LivingEntity && !(entity instanceof Fox || entity instanceof Bee || EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, (LivingEntity) entity) > 0)) {
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
        if (layers > 0 && level.isClientSide && (entity instanceof LivingEntity && entity.getFeetBlockState().is(this))) {

            Random random = level.getRandom();
            boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
            if (bl && random.nextBoolean()) {
                //double yOff = (layers < 5) ? 0.5 : 1;
                double y = pos.getY() + SHAPE_BY_LAYER_L[layers].max(Direction.Axis.Y) + 0.0625;
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);
                for (var p : particles) {
                    level.addParticle(p.get(),
                            entity.getX() + Mth.randomBetween(random, -0.2f, 0.2f),
                            y,
                            entity.getZ() + Mth.randomBetween(random, -0.2f, 0.2f),
                            Mth.randomBetween(random, -0.75f, -1),
                            color,
                            0);
                    //Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f)
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext c) {
            var e = c.getEntity();
            if (e instanceof FallingLayerEntity) {
                return SHAPE_BY_LAYER_L[state.getValue(LAYERS)];
            }
        }
        return Shapes.empty();
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER_L[getLayers(state)];
    }


    //just used for placement by blockItem
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState bottomState = world.getBlockState(pos.below());
        if (bottomState.getBlock() instanceof LeavesBlock || state.getValue(LAYERS) != 0 || world.getFluidState(pos.below()).is(Fluids.WATER) || bottomState.isFaceSturdy(world, pos.below(), Direction.UP)) return true;
        return !shouldFall(state, world.getBlockState(pos.below()));
    }


    @Override
    public boolean shouldFall(BlockState state, BlockState belowState) {
        if ((state.getValue(LAYERS) == 0 && belowState.is(Blocks.WATER)) || belowState.is(ModTags.LEAF_PILES))
            return false;
        return super.shouldFall(state, belowState);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && state.getValue(LAYERS) <= 1) {
            state = state.setValue(LAYERS, neighborState.is(Blocks.WATER) ? 0 : 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().is(this.asItem()) && i < 8 && i > 0) {
            return true;
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
    public boolean isRandomlyTicking(BlockState state) {
        int layers = this.getLayers(state);
        return layers > 1 && (this.isLeafy || this.hasThorns);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockPos = pos.below();
            if (isFree(level.getBlockState(blockPos))) {
                var leafParticle = LeafPilesRegistry.getFallenLeafParticle(state).orElse(null);
                if (leafParticle == null) return;
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);
                for (var p : particles) {
                    if (random.nextFloat() < 0.2) {
                        double d = (double) pos.getX() + random.nextDouble();
                        double e = (double) pos.getY() - 0.05;
                        double f = (double) pos.getZ() + random.nextDouble();
                        level.addParticle(leafParticle, d, e, f, 0.0, color, 0.0);
                    }
                }
            }
        }

    }

}
