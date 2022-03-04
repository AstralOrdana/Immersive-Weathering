package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MulchCarpetBlock extends CarpetBlock {

    public MulchCarpetBlock(Properties settings) {
        super(settings);
    }

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (world.isClientSide) {
                Random random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    if (world.getBlockState(pos).is(ModBlocks.MULCH)) {
                        world.addParticle(ModParticles.MULCH, entity.getX(), entity.getY() + 0.5, entity.getZ(), Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                    if (world.getBlockState(pos).is(ModBlocks.NULCH)) {
                        world.addParticle(ModParticles.NULCH, entity.getX(), entity.getY() + 0.5, entity.getZ(), Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                }
            }
        }
    }
}
