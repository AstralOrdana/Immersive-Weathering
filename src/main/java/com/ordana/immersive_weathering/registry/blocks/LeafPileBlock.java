package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LeafPileBlock extends Block {
    public static final int MAX_LAYERS = 8;
    public static final IntProperty LAYERS;
    protected static final VoxelShape[] LAYERS_TO_SHAPE;
    public static final int field_31248 = 5;

    protected LeafPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LAYERS, 1));
    }

    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d(0.8D, 0.8D, 0.8D));
            if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                    double d = Math.abs(entity.getX() - entity.lastRenderX);
                    double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                    if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                        entity.damage(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                }
            }
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch(type) {
            case LAND:
                return (Integer)state.get(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[(Integer)state.get(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[(Integer)state.get(LAYERS) - (Integer)state.get(LAYERS)];
    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[(Integer)state.get(LAYERS)];
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[(Integer)state.get(LAYERS)];
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (!blockState.isOf(Blocks.BARRIER)) {
            if (!blockState.isOf(Blocks.HONEY_BLOCK) && !blockState.isOf(Blocks.SOUL_SAND)) {
                return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.isOf(this) && (Integer)blockState.get(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = (Integer)state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            int i = (Integer)blockState.get(LAYERS);
            return (BlockState)blockState.with(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getPlacementState(ctx);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LAYERS});
    }

    static {
        LAYERS = Properties.LAYERS;
        LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    }
}
