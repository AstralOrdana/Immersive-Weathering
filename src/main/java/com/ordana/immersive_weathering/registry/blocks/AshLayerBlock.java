package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.entities.FallingAshEntity;
import io.netty.handler.codec.mqtt.MqttProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;
import java.util.Random;


/**
 * Author: MehVahdJukaar
 */

public class AshLayerBlock extends FallingBlock {
    public static final int MAX_LAYERS = 8;
    public static final IntProperty LAYERS = Properties.LAYERS;
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    public AshLayerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1));
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult pHit, ProjectileEntity projectile) {
        BlockPos pos = pHit.getBlockPos();
        if (projectile instanceof PotionEntity potion && PotionUtil.getPotion(potion.getStack()) == Potions.WATER) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity;
            if (flag) {
                this.removeOneLayer(state, pos, world);
            }
        }
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return 0x9a9090;
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getBlock() != oldState.getBlock())
            worldIn.createAndScheduleBlockTick(pos, this, this.getFallDelay());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS) / 2];
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    protected int getLayers(BlockState state) {
        return state.get(LAYERS);
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
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    //ugly but works
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState facingState, WorldAccess world, BlockPos currentPos, BlockPos otherPos) {
        if (world instanceof ServerWorld serverWorld) {
            BlockPos pos = currentPos.up();
            BlockState state1 = world.getBlockState(pos);
            ;
            while (state1.isOf(this)) {
                serverWorld.createAndScheduleBlockTick(pos, this, this.getFallDelay());
                pos = pos.up();
                state1 = serverWorld.getBlockState(pos);
            }
        }
        return super.getStateForNeighborUpdate(state, direction, facingState, world, currentPos, otherPos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, Random pRand) {
        BlockState below = level.getBlockState(pos.down());
        if ((FallingAshEntity.isFree(below) || hasIncompleteAshPileBelow(below)) && pos.getY() >= level.getBottomY()) {

            while (state.isOf(this)) {
                FallingBlockEntity fallingblockentity = FallingAshEntity.spawnFromBlock(level, pos, state);
                this.configureFallingBlockEntity(fallingblockentity);

                pos = pos.up();
                state = level.getBlockState(pos);
            }
        }
    }

    private boolean hasIncompleteAshPileBelow(BlockState state) {
        return state.isOf(this) && state.get(LAYERS) != MAX_LAYERS;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getBlockPos());
        if (blockstate.isOf(this)) {
            int i = blockstate.get(LAYERS);
            return blockstate.with(LAYERS, Math.min(MAX_LAYERS, i + 1));
        } else {
            return super.getPlacementState(context);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    private void removeOneLayer(BlockState state, BlockPos pos, World level) {
        int levels = state.get(LAYERS);
        if (levels > 1) level.setBlockState(pos, state.with(LAYERS, levels - 1), 3);
        else level.removeBlock(pos, false);
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

    private void addParticle(Entity entity, BlockPos pos, World level, int layers, float upSpeed) {
        level.addParticle(ModParticles.SOOT, entity.getX(), pos.getY() + layers * (1 / 8f), entity.getZ(),
                MathHelper.nextBetween(level.random, -1.0F, 1.0F) * 0.083333336F,
                upSpeed,
                MathHelper.nextBetween(level.random, -1.0F, 1.0F) * 0.083333336F);
    }

    @Override
    public void onSteppedOn(World level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClient && level.random.nextInt(8) == 0 && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
            addParticle(entity, pos, level, state.get(LAYERS), 0.05f);
        }
        super.onSteppedOn(level, pos, state, entity);
    }

    @Override
    public void onLandedUpon(World level, BlockState state, BlockPos pos, Entity entity, float height) {
        int layers = state.get(LAYERS);
        entity.handleFallDamage(height, layers > 2 ? 0.3f : 1, DamageSource.FALL);
        if (level.isClient) {
            for (int i = 0; i < Math.min(12, height * 1.4); i++) {

                addParticle(entity, pos, level, layers, 0.12f);
            }
        }
    }
}