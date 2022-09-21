package com.ordana.immersive_weathering.blocks.charred;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public interface OverhangCharred extends Charred{

    IntegerProperty OVERHANG = ModBlockProperties.OVERHANG;
    default int getOverhang(Level level, BlockPos pos) {
        int overhang = 2;
        for (var dir : Direction.values()) {
            if (dir == Direction.DOWN) {
                var free = FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight();
                if (!free) {
                    overhang = 0;
                    break;
                }
            }
            else if (dir != Direction.UP) {
                BlockPos neighborPos = pos.relative(dir);
                var neighbor = level.getBlockState(neighborPos);
                if (neighbor.hasProperty(OVERHANG)) {
                    if(neighbor.getValue(OVERHANG) == 0){
                        overhang = 1;
                        break;
                    }
                }
                else if(neighbor.isFaceSturdy(level, neighborPos, dir.getOpposite())){
                    overhang = 1;
                    break;
                }
            }
        }
        return overhang;
    }

    default void updateOverhang(BlockState state, Level level, BlockPos pos) {
        int supported = getOverhang(level, pos);
        if (supported != state.getValue(OVERHANG)) {
            level.setBlockAndUpdate(pos, state.setValue(OVERHANG, supported));
        }
        if (supported==2) {
            level.scheduleTick(pos, state.getBlock(), 1);
        }
    }

    default void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        Charred.super.animateTick(state, level, pos, random);
        if (random.nextInt(16) == 0 && FallingBlock.isFree(level.getBlockState(pos.below()))) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() - 0.05;
            double f = (double) pos.getZ() + random.nextDouble();
            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
        }
    }
}
