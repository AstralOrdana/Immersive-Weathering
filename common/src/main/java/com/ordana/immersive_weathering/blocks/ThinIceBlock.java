package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModSoundEvents;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    //block growth always calls can survive before placing so this gets called
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (!isDimEnoughToForm(state, level, pos)) return false;
        BlockState upState = level.getBlockState(pos.above());
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //TODO: move to block growth
        /*
        if (state.getValue(HAS_ICE)) {
            //TODO: use common logic here for cold and hot
            if (level.getBlockState(pos.above()).is(Blocks.AIR) && (level.isRaining() || level.isNight())
                    && level.getBiome(pos).is(ModTags.ICY) && isDimEnoughToForm(state, level, pos)) {
                for (Direction direction : Direction.values()) {
                    if (level.getBlockState(pos.relative(direction)).is(Blocks.WATER)) {
                        level.setBlockAndUpdate(pos.relative(direction), this.getPlacement(level, pos));
                    }
                }
            }
        }*/

        //TODO: group in a single method
        if (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos)) {
            this.melt(state, level, pos);
        }

        //TODO: also move to growths
        if (CommonConfigs.THIN_ICE_MELTING.get()) {
            if (level.dimensionType().ultraWarm() || (!level.isRaining() && level.isDay()) || !isDimEnoughToForm(state, level, pos)) {
                level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }

    private boolean isDimEnoughToForm(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBrightness(LightLayer.BLOCK, pos) < 7 - state.getLightBlock(level, pos);
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        int i = state.getValue(CRACKED);
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
            return;
        }
        if (!level.isClientSide && level.random.nextFloat() < fallDistance - 0.5f && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {
            if (level.random.nextBoolean()) {
                level.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), level, pos));
                level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (i < 3) {
                level.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
                level.playSound(null, pos, ModSoundEvents.ICICLE_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (i == 3) {
                level.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), level, pos));
                level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
        for (Direction direction : Direction.values()) {
            BlockState targetState = level.getBlockState(pos.relative(direction));
            if (level.getBlockState(pos.relative(direction)).is(this)) {
                int j = targetState.getValue(CRACKED);
                if (level.random.nextBoolean() && j < 3) {
                    level.setBlockAndUpdate(pos.relative(direction), this.withPropertiesOf(targetState).setValue(CRACKED, j + 1));
                }
            }
        }
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
            return;
        }
        if (!level.isClientSide && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f) {
            //TODO: merge check and optimize
            if (level.random.nextInt(15) == 0) {
                int i = state.getValue(CRACKED);
                if (i < 3) {
                    level.setBlockAndUpdate(pos, state.setValue(CRACKED, i + 1));
                    level.playSound(null, pos, ModSoundEvents.ICICLE_CRACK.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else if (i == 3) {
                    level.setBlockAndUpdate(pos, ThinIceBlock.pushEntitiesUp(state, Blocks.WATER.defaultBlockState(), level, pos));
                    level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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
