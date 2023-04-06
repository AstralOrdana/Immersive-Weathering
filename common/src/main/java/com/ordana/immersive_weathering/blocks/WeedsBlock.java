package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModTags;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class WeedsBlock extends CropBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED;
    private static final int FIRE_SPREAD = 60;
    private static final int FLAMMABILITY = 100;

    public WeedsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(WATERLOGGED, false));
        RegHelper.registerBlockFlammability(this, FIRE_SPREAD, FLAMMABILITY);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelAccessor = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AGE);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return FIRE_SPREAD;
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return FLAMMABILITY;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(this, world, pos);
            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                world.setBlock(pos, this.getStateForAge(i + 1), 2);
            }
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (this.getAge(state) == this.getMaxAge() && random.nextInt(10) == 0) {
            double r = 0.3;
            double x = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * r;
            double y = (double) pos.getY() + 0.8 + (random.nextDouble() - 0.5) * r;
            double z = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * r;
            world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, 0.1D, 0.5D, 0.1D);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.random.nextFloat() < 0.1 && entity instanceof Player player) { //no more dead villagers
            if (!level.isClientSide && state.getValue(AGE) > 0 && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                if (player.getItemBySlot(EquipmentSlot.FEET).isEmpty() || !CommonConfigs.LEGGINGS_PREVENTS_THORN_DAMAGE.get()) {
                    double d0 = Math.abs(entity.getX() - entity.xOld);
                    double d1 = Math.abs(entity.getZ() - entity.zOld);
                    if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
                        entity.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                }
            }
        }
    }

    //not needed on forge
    //commented out the PlatformOnlys because it wasnt working on forge?? ~o

    //@PlatformOnly(PlatformOnly.FABRIC)
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    //@PlatformOnly(PlatformOnly.FABRIC)
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return true;
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
    }
}
