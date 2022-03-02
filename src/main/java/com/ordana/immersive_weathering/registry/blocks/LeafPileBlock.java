package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class LeafPileBlock extends Block implements Fertilizable {
    public static final int MAX_LAYERS = 8;
    public static final IntProperty LAYERS;
    protected static final VoxelShape[] LAYERS_TO_SHAPE;
    public static final int field_31248 = 5;

    protected LeafPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1));
    }

    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    protected int getLayers(BlockState state) {
        return state.get(this.getLayersProperty());
    }

    public IntProperty getLayersProperty() {
        return LAYERS;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        int a = this.getLayers(state);
        if (a == 2) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(1.7D, 1.7D, 1.7D));
            }
        }
        if (a == 3) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(1.6D, 1.6D, 1.6D));
            }
        }
        if (a == 4) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(1.5D, 1.5D, 1.5D));
            }
        }
        if (a == 5) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(1.3D, 1.3D, 1.3D));
            }
        }
        if (a == 6) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(1.1D, 1.1D, 1.1D));
                if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                    if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                        double d = Math.abs(entity.getX() - entity.lastRenderX);
                        double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                        if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                            entity.damage(DamageSource.SWEET_BERRY_BUSH, 0.5F);
                        }
                    }
                }
            }
        }
        if (a == 7) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(0.8D, 0.8D, 0.8D));
                if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                    if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                        double d = Math.abs(entity.getX() - entity.lastRenderX);
                        double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                        if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                            entity.damage(DamageSource.SWEET_BERRY_BUSH, 1F);
                        }
                    }
                }
            }
        }
        if (a == 8) {
            if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
                entity.slowMovement(state, new Vec3d(0.5D, 0.5D, 0.5D));
                if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                    if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                        double d = Math.abs(entity.getX() - entity.lastRenderX);
                        double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                        if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                            entity.damage(DamageSource.SWEET_BERRY_BUSH, 1.5F);
                        }
                    }
                }
            }
        }
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    if (a < 5) {
                        if (world.getBlockState(pos).isOf(ModBlocks.OAK_LEAF_PILE)) {
                            world.addParticle(ModParticles.OAK_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.BIRCH_LEAF_PILE)) {
                            world.addParticle(ModParticles.BIRCH_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                            world.addParticle(ModParticles.SPRUCE_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.JUNGLE_LEAF_PILE)) {
                            world.addParticle(ModParticles.JUNGLE_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.ACACIA_LEAF_PILE)) {
                            world.addParticle(ModParticles.ACACIA_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.DARK_OAK_LEAF_PILE)) {
                            world.addParticle(ModParticles.DARK_OAK_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.AZALEA_FLOWER_PILE)) {
                            world.addParticle(ModParticles.AZALEA_FLOWER, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.AZALEA_LEAF_PILE)) {
                            world.addParticle(ModParticles.AZALEA_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.FLOWERING_AZALEA_LEAF_PILE)) {
                            world.addParticle(ModParticles.AZALEA_LEAF, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                            world.addParticle(ModParticles.AZALEA_FLOWER, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                    }
                    if (a > 4) {
                        if (world.getBlockState(pos).isOf(ModBlocks.OAK_LEAF_PILE)) {
                            world.addParticle(ModParticles.OAK_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.BIRCH_LEAF_PILE)) {
                            world.addParticle(ModParticles.BIRCH_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.SPRUCE_LEAF_PILE)) {
                            world.addParticle(ModParticles.SPRUCE_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.JUNGLE_LEAF_PILE)) {
                            world.addParticle(ModParticles.JUNGLE_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.ACACIA_LEAF_PILE)) {
                            world.addParticle(ModParticles.ACACIA_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.DARK_OAK_LEAF_PILE)) {
                            world.addParticle(ModParticles.DARK_OAK_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.AZALEA_FLOWER_PILE)) {
                            world.addParticle(ModParticles.AZALEA_FLOWER, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.AZALEA_LEAF_PILE)) {
                            world.addParticle(ModParticles.AZALEA_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                        if (world.getBlockState(pos).isOf(ModBlocks.FLOWERING_AZALEA_LEAF_PILE)) {
                            world.addParticle(ModParticles.AZALEA_LEAF, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                            world.addParticle(ModParticles.AZALEA_FLOWER, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.0001F, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                        }
                    }
                }
            }
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return switch (type) {
            case LAND -> (Integer) state.get(LAYERS) < 5;
            case WATER -> false;
            case AIR -> false;
            default -> false;
        };
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
        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        }
        else {
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
            return super.getPlacementState(ctx);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    static {
        LAYERS = Properties.LAYERS;
        LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        if(state.isOf(ModBlocks.FLOWERING_AZALEA_LEAF_PILE) || state.isOf(ModBlocks.AZALEA_FLOWER_PILE)) {
            return true;
        }
        else return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if(state.isOf(ModBlocks.FLOWERING_AZALEA_LEAF_PILE) || state.isOf(ModBlocks.AZALEA_FLOWER_PILE)) {
            return true;
        }
        else return false;
    }

    private static final HashMap<Block, Block> FLOWERY_BLOCKS = new HashMap<>();

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {

        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA);
        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES);
        FLOWERY_BLOCKS.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, ModBlocks.AZALEA_LEAF_PILE);

        for (var direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            float f = 0.5f;
            if (random.nextFloat() > 0.5f) {
                if (world.getBlockState(targetPos).isIn(ModTags.FLOWERABLE)) {
                    FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                        if (targetBlock.isOf(shorn)) {
                            world.setBlockState(targetPos, flowery.getStateWithProperties(targetBlock));
                        }
                    });
                }
            }
        }
    }
}
