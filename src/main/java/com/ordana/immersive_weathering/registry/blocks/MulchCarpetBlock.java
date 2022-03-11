package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MulchCarpetBlock extends CarpetBlock {

    private final List<DefaultParticleType> particle;

    public MulchCarpetBlock(Settings settings, List<DefaultParticleType> particle) {
        super(settings);
        this.particle = particle;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    for (var p : particle) {
                        world.addParticle(p, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                }
            }
        }
    }
}

