package com.ordana.immersive_weathering.blocks.soil_types;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;

public class ModFarmlandBlock extends FarmBlock {

    public ModFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            turnToDirt(null, blockState, serverLevel, blockPos);
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? Blocks.DIAMOND_BLOCK.defaultBlockState() : super.getStateForPlacement(context);
    }

    public static boolean isUnderCrops(BlockGetter level, BlockPos pos) {
        Block block = level.getBlockState(pos.above()).getBlock();
        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock || block instanceof SweetBerryBushBlock || block instanceof BambooStalkBlock || block instanceof BambooSaplingBlock;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.above());
        Block block = blockState.getBlock();
        return !blockState.isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof MovingPistonBlock || block instanceof SweetBerryBushBlock || block instanceof BambooStalkBlock || block instanceof BambooSaplingBlock;
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-6, 0, -6), pos.offset(6, 1, 6)).iterator();

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
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        int i = blockState.getValue(MOISTURE);
        if (!isNearWater(serverLevel, blockPos) && !serverLevel.isRainingAt(blockPos.above())) {
            if (i > 0) {
                serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, i - 1), 2);
            } else if (!isUnderCrops(serverLevel, blockPos)) {
                turnToDirt(null, blockState, serverLevel, blockPos);
            }
        } else if (i < MAX_MOISTURE) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        }

    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        entity.causeFallDamage(f, 1.0F, level.damageSources().fall());
    }

}