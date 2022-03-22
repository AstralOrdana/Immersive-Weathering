package com.ordana.immersive_weathering.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.function.Supplier;

public class MulchCarpetBlock extends CarpetBlock {

    private final Supplier<SimpleParticleType> particle;

    public MulchCarpetBlock(Properties settings, Supplier<SimpleParticleType> particle) {
        super(settings);
        this.particle = particle;
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (world.isClientSide) {
                Random random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {

                    world.addParticle(particle.get(),
                            entity.getX() +Mth.randomBetween(random,-0.2f,0.2f),
                            pos.getY() + 0.0626,
                            entity.getZ() +Mth.randomBetween(random,-0.2f,0.2f),
                            Mth.randomBetween(random,-0.9f,-1),
                            -1,
                            0);
                }
            }
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 10;
    }
}
