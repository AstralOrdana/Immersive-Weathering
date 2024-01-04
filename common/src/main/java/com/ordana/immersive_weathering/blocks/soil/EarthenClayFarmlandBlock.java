package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Iterator;
import java.util.Objects;

public class EarthenClayFarmlandBlock extends ModFarmlandBlock {

    public EarthenClayFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        int i = 0;
        if (bl) i = 7;
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.EARTHEN_CLAY.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, bl) : Objects.requireNonNull(super.getStateForPlacement(context)).setValue(MOISTURE, i);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.getRawBrightness(blockPos.above(), 0) >= 9 && blockState.getValue(BlockStateProperties.MOISTURE) == 7) {
            BlockState cropState = serverLevel.getBlockState(blockPos.above());
            if(cropState.is(ModTags.CLAY_SOIL_CROP)) {
                for(int j = 0; j < 10; j++){
                    cropState.randomTick(serverLevel, blockPos.above(), randomSource);
                }
            }
        }
        int i = blockState.getValue(MOISTURE);
        if (serverLevel.isRainingAt(blockPos.above()) || isNearWater(serverLevel, blockPos)) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        } else if (serverLevel.isDay() && !serverLevel.isRaining() && serverLevel.canSeeSky(blockPos.above())) {
            if (!isUnderCrops(serverLevel, blockPos)) turnToDirt(blockState, serverLevel, blockPos);
        }
    }

    public static boolean isNearWater(LevelReader level, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-3, 0, -3), pos.offset(3, 1, 3)).iterator();

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
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            turnToDirt(blockState, serverLevel, blockPos);
        }
    }

    public static void turnToDirt(BlockState state, Level level, BlockPos pos) {
        if (state.getValue(MOISTURE) == 7) level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.EARTHEN_CLAY.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), level, pos));
        else level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.EARTHEN_CLAY.get().defaultBlockState(), level, pos));
    }
}
