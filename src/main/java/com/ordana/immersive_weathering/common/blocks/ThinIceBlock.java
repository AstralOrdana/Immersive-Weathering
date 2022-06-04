package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ThinIceBlock extends IceBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty CRACKED = IntegerProperty.create("cracked", 0,3);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);

    public ThinIceBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRACKED, 0).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(CRACKED);
        stateManager.add(WATERLOGGED);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.above());
        return state.getFluidState().is(Fluids.WATER) && !upState.getFluidState().is(Fluids.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            if (world.getBlockState(pos.above()).is(Blocks.AIR) && (world.isRaining() || world.isNight()) && world.getBiome(pos).is(ModTags.ICY) && (world.getBrightness(LightLayer.BLOCK, pos) < 7 - state.getLightBlock(world, pos))) {
                if (world.getBlockState(pos.relative(direction.getOpposite())).is(Blocks.ICE)) {
                    if (world.getBlockState(pos.relative(direction)).is(Blocks.WATER)) {
                        world.setBlockAndUpdate(pos.relative(direction), this.defaultBlockState().setValue(WATERLOGGED, true));
                    }
                }
            }
        }

        if (world.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(world, pos)) {
            this.melt(state, world, pos);
        }

        if(ImmersiveWeathering.getConfig().fireAndIceConfig.thinIceMelting) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        int i = state.getValue(CRACKED);
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
            return;
        }
        if (!world.isClientSide && world.random.nextFloat() < fallDistance - 0.5f && (entity instanceof Player || world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {
            if (world.random.nextBoolean()) {
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (state.getValue(WATERLOGGED)) {
                    world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                }
                else world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.AIR.defaultBlockState(), world, pos));world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
            } else if (i < 3) {
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
            } else if (i == 3) {
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (state.getValue(WATERLOGGED)) {
                    world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                }
                else world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.AIR.defaultBlockState(), world, pos));
            }
        }
        for (Direction direction : Direction.values()) {
            BlockState targetState = world.getBlockState(pos.relative(direction));
            if (world.getBlockState(pos.relative(direction)).is(ModBlocks.THIN_ICE)) {
                if (world.random.nextBoolean() && i < 3) {
                    world.setBlockAndUpdate(pos.relative(direction), this.withPropertiesOf(targetState).setValue(CRACKED, i + 1));
                }
            }
        }
        super.fallOn(world, state, pos, entity, fallDistance);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
            return;
        }
        if (!world.isClientSide && (entity instanceof Player || world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {

            if (world.random.nextInt(15) == 0) {
                int i = state.getValue(CRACKED);
                if (world.random.nextInt(3) == 0) {
                    world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (state.getValue(WATERLOGGED)) {
                        world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                    }
                    else world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.AIR.defaultBlockState(), world, pos));world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                } else if (i < 3) {
                    world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    world.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
                } else if (i == 3) {
                    world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (state.getValue(WATERLOGGED)) {
                        world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                    }
                    else world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.AIR.defaultBlockState(), world, pos));
                }
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
    }
}
