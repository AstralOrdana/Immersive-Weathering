package com.ordana.immersive_weathering.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SandLayerBlock extends LayerBlock{

    private final int particleColor;

    public SandLayerBlock( int particleColor, Properties properties) {
        super(properties);
        this.particleColor = particleColor;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level.isClientSide && level.random.nextInt(8) == 0 && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
            addParticle(state, entity, pos, level, state.getValue(LAYERS_8), 0.05f);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float height) {
        int layers = state.getValue(LAYERS_8);
        entity.causeFallDamage(height, layers > 2 ? 0.3f : 1, DamageSource.FALL);
        if (level.isClientSide) {
            for (int i = 0; i < Math.min(12, height * 1.4); i++) {

                addParticle(state, entity, pos, level, layers, 0.12f);
            }
        }
    }

    private void addParticle(BlockState state, Entity entity, BlockPos pos, Level level, int layers, float upSpeed) {
        level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), entity.getX(), pos.getY() + layers * (1 / 8f), entity.getZ(),
                Mth.randomBetween(level.random, -1.0F, 1.0F) * 0.083333336F,
                upSpeed,
                Mth.randomBetween(level.random, -1.0F, 1.0F) * 0.083333336F);
    }

    //TODO: check and merge properly
}
