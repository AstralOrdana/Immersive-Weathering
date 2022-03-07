package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class WeedsBlock extends CropBlock {

    protected WeedsBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
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
        return ModItems.WEEDS.get();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (this.getAge(state) == this.getMaxAge()) {
            double r = 0.3;
            double x = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * r;
            double y = (double) pos.getY() + 0.8 + (random.nextDouble() - 0.5) * r;
            double z = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * r;
            world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, 0.1D, 0.5D, 0.1D);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(world.getBlockState(below), world, below);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(ModTags.FERTILE_BLOCKS) || floor.is(BlockTags.DIRT);
    }
}
