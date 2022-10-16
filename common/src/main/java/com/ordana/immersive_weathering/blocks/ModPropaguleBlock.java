package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.entities.FallingPropaguleEntity;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

import javax.swing.text.html.BlockView;
import java.util.Random;

public class ModPropaguleBlock extends MangrovePropaguleBlock implements Fallable {
    private static final BooleanProperty WATERLOGGED;

    public ModPropaguleBlock(Properties properties) {
        super(properties);
    }

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify) {
        level.scheduleTick(pos, this, this.getFallDelay());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        else if ((!state.getValue(HANGING) || (state.getValue(HANGING) && state.getValue(AGE) < 4)) && !state.canSurvive(level, pos)) {
            level.removeBlock(pos, true);
        }
        level.scheduleTick(pos, this, this.getFallDelay());
        return state;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (canFallThrough(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight() && (level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above()).is(ModTags.LEAF_PILES)) && state.getValue(HANGING) && state.getValue(AGE) == 4) {
            FallingBlockEntity fallingblockentity = FallingPropaguleEntity.fall(level, pos, state);
            this.configureFallingBlockEntity(fallingblockentity);
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
    }

    protected int getFallDelay() {
        return 2;
    }

    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockPos = pos.below();
            if (canFallThrough(level.getBlockState(blockPos))) {
                double d = (double)pos.getX() + random.nextDouble();
                double e = (double)pos.getY() - 0.05D;
                double f = (double)pos.getZ() + random.nextDouble();
                var leafParticle = WeatheringHelper.getFallenLeafParticle(Blocks.MANGROVE_LEAVES.defaultBlockState()).orElse(null);
                assert leafParticle != null;
                level.addParticle(leafParticle, d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public int color(BlockState state, BlockView level, BlockPos pos) {
        return -16777216;
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
    }
}