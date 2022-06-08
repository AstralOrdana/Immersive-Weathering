package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ThinIceBlock extends IceBlock implements Waterloggable {
    public static final IntProperty CRACKED = IntProperty.of("cracked", 0,3);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);

    public ThinIceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CRACKED, 0).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(CRACKED);
        stateManager.add(WATERLOGGED);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.up());
        return state.getFluidState().isOf(Fluids.WATER) && !upState.getFluidState().isOf(Fluids.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        for (Direction direction : Direction.values()) {
            if (world.getBlockState(pos.up()).isOf(Blocks.AIR) && (world.isRaining() || world.isNight()) && world.getBiome(pos).isIn(ModTags.ICY) && (world.getLightLevel(LightType.BLOCK, pos) < 7 - state.getOpacity(world, pos))) {
                if (world.getBlockState(pos.offset(direction.getOpposite())).isOf(Blocks.ICE)) {
                    if (world.getBlockState(pos.offset(direction)).isOf(Blocks.WATER)) {
                        world.setBlockState(pos.offset(direction), this.getDefaultState().with(WATERLOGGED, true));
                    }
                }
            }
        }

        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
            this.melt(state, world, pos);
        }

        if(ImmersiveWeathering.getConfig().fireAndIceConfig.thinIceMelting) {
            if (world.getDimension().ultrawarm() || (!world.isRaining() && world.isDay()) || (world.getLightLevel(LightType.BLOCK, pos) > 7 - state.getOpacity(world, pos))) {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        int i = state.get(CRACKED);
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, (LivingEntity) entity) > 0) {
            return;
        }
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5f && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512f) {
            if (world.random.nextBoolean()) {
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if (state.get(WATERLOGGED)) {
                    world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
                }
                else world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.AIR.getDefaultState(), world, pos));world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
            } else if (i < 3) {
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, state.with(CRACKED, i + 1));
            } else if (i == 3) {
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if (state.get(WATERLOGGED)) {
                    world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
                }
                else world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.AIR.getDefaultState(), world, pos));
            }
        }
        for (Direction direction : Direction.values()) {
            BlockState targetState = world.getBlockState(pos.offset(direction));
            if (world.getBlockState(pos.offset(direction)).isOf(ModBlocks.THIN_ICE)) {
                if (world.random.nextBoolean() && i < 3) {
                    world.setBlockState(pos.offset(direction), this.getStateWithProperties(targetState).with(CRACKED, i + 1));
                }
            }
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEquipmentLevel(Enchantments.FEATHER_FALLING, (LivingEntity) entity) > 0) {
            return;
        }
        if (!world.isClient && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512f) {

            if (world.random.nextInt(15) == 0) {
                int i = state.get(CRACKED);
                if (world.random.nextInt(3) == 0) {
                    world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (state.get(WATERLOGGED)) {
                        world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
                    }
                    else world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.AIR.getDefaultState(), world, pos));world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
                } else if (i < 3) {
                    world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.setBlockState(pos, state.with(CRACKED, i + 1));
                } else if (i == 3) {
                    world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (state.get(WATERLOGGED)) {
                        world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.WATER.getDefaultState(), world, pos));
                    }
                    else world.setBlockState(pos, ThinIceBlock.pushEntitiesUpBeforeBlockChange(state, Blocks.AIR.getDefaultState(), world, pos));
                }
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }
}
