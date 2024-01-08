package com.ordana.immersive_weathering.blocks.soil_types;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Iterator;

public class SandyFarmlandBlock extends ModFarmlandBlock implements Fallable {

    public SandyFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.SANDY_DIRT.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.getRawBrightness(blockPos.above(), 0) >= 9 && blockState.getValue(BlockStateProperties.MOISTURE) == 7) {
            BlockState cropState = serverLevel.getBlockState(blockPos.above());
            if(cropState.is(ModTags.SAND_SOIL_CROP)) {
                for(int j = 0; j < 10; j++){
                    cropState.randomTick(serverLevel, blockPos.above(), randomSource);
                }
            }
        }
        int i = blockState.getValue(MOISTURE);
        if (!isNearWater(serverLevel, blockPos) && !serverLevel.isRainingAt(blockPos.above())) {
            if (i > 0) {
                serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, i - 1), 2);
            } else if (!isUnderCrops(serverLevel, blockPos)) {
                turnToDirt(blockState, serverLevel, blockPos);
            }
        } else if (i < MAX_MOISTURE) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

    }

    public static boolean isNearWater(LevelReader level, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-2, 0, -2), pos.offset(2, 1, 2)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!level.getFluidState(blockPos).is(FluidTags.WATER));

        return true;
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (!level.isClientSide && level.random.nextFloat() < f - 0.5F && entity instanceof LivingEntity && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
            turnToDirt(state, level, pos);
        }

        super.fallOn(level, state, pos, entity, f);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, pos, state);
            this.falling(fallingBlockEntity);
        }
        if (!state.canSurvive(level, pos)) {
            turnToDirt(state, level, pos);
        }
    }

    public static void turnToDirt(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.SANDY_DIRT.get().withPropertiesOf(state), level, pos));
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    protected void falling(FallingBlockEntity entity) {
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isFree(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockPos = pos.below();
            if (isFree(level.getBlockState(blockPos))) {
                ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
            }
        }

    }

    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return -16777216;
    }
}