package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModSoundEvents;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import net.minecraft.util.RandomSource;

public class ThinIceBlock extends IceBlock implements LiquidBlockContainer {

    protected static final VoxelShape SHAPE = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);

    public static final IntegerProperty CRACKED = ModBlockProperties.CRACKED;
    public static final BooleanProperty CAN_EXPAND = ModBlockProperties.CAN_EXPAND;

    public ThinIceBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRACKED, 0).setValue(CAN_EXPAND, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(CRACKED, CAN_EXPAND);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    //block growth always calls can survive before placing so this gets called
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (!isDimEnoughToForm(state, world, pos)) return false;
        BlockState upState = world.getBlockState(pos.above());
        return state.getFluidState().is(Fluids.WATER) && !upState.getFluidState().is(Fluids.WATER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Level level = blockPlaceContext.getLevel();
        BlockPos pos = blockPlaceContext.getClickedPos();
        return getPlacement(level, pos);
    }

    private BlockState getPlacement(Level level, BlockPos pos) {

        boolean hasIce = false;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (canExpand(level, pos.relative(dir))) {
                hasIce = true;
                break;
            }
        }
        return this.defaultBlockState().setValue(CAN_EXPAND, hasIce);
    }

    public boolean canExpand(Level level, BlockPos pos){
        return level.getBlockState(pos).is(ModTags.ICE) && level.getBiome(pos).value().coldEnoughToSnow(pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        //TODO: move to block growth
        /*
        if (state.getValue(HAS_ICE)) {
            //TODO: use common logic here for cold and hot
            if (world.getBlockState(pos.above()).is(Blocks.AIR) && (world.isRaining() || world.isNight())
                    && world.getBiome(pos).is(ModTags.ICY) && isDimEnoughToForm(state, world, pos)) {
                for (Direction direction : Direction.values()) {
                    if (world.getBlockState(pos.relative(direction)).is(Blocks.WATER)) {
                        world.setBlockAndUpdate(pos.relative(direction), this.getPlacement(world, pos));
                    }
                }
            }
        }*/

        //TODO: group in a single method
        if (world.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(world, pos)) {
            this.melt(state, world, pos);
        }

        //TODO: also move to growths
        if (CommonConfigs.THIN_ICE_MELTING.get()) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || !isDimEnoughToForm(state, world, pos)) {
                world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }

    private boolean isDimEnoughToForm(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBrightness(LightLayer.BLOCK, pos) < 7 - state.getLightBlock(world, pos);
    }


    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        int i = state.getValue(CRACKED);
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
            return;
        }
        if (!world.isClientSide && world.random.nextFloat() < fallDistance - 0.5f && (entity instanceof Player || world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {
            if (world.random.nextBoolean()) {
                world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (i < 3) {
                world.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
                world.playSound(null, pos, ModSoundEvents.ICICLE_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (i == 3) {
                world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
        for (Direction direction : Direction.values()) {
            BlockState targetState = world.getBlockState(pos.relative(direction));
            if (world.getBlockState(pos.relative(direction)).is(this)) {
                int j = targetState.getValue(CRACKED);
                if (world.random.nextBoolean() && j < 3) {
                    world.setBlockAndUpdate(pos.relative(direction), this.withPropertiesOf(targetState).setValue(CRACKED, j + 1));
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
            //TODO: merge check and optimize
            if (world.random.nextInt(15) == 0) {
                int i = state.getValue(CRACKED);
                if (i < 3) {
                    world.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
                    world.playSound(null, pos, ModSoundEvents.ICICLE_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else if (i == 3) {
                    world.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), world, pos));
                    world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return false;
    }
}
