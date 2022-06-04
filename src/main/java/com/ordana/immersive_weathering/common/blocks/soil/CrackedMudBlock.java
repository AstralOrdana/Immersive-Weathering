package com.ordana.immersive_weathering.common.blocks.soil;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.registry.ModTags;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;

public class CrackedMudBlock extends Block implements IConditionalGrowingBlock {

    public CrackedMudBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SOAKED, false).setValue(FERTILE, true));
    }

    public static final BooleanProperty SOAKED = BooleanProperty.create("soaked");
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (state.getValue(SOAKED)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (world.isRainingAt(pos.relative(direction)) || biome.is(ModTags.WET) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || biome.is(ModTags.HOT) || world.dimension() == Level.NETHER) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (!state.getValue(SOAKED)) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, true));
            }
        }
        else if (temperature > 0 && state.getValue(SOAKED)) {
            world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
        stateManager.add(FERTILE);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE) && state.getValue(SOAKED);
    }
}