package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Random;

public class SootBlock extends MultifaceGrowthBlock {

    public SootBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState());
    }

    @Override
    public LichenGrower getGrower() {
        return null;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                net.minecraft.util.math.random.Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ModParticles.SOOT,
                            entity.getX() +MathHelper.nextBetween(random,-0.2f,0.2f),
                            entity.getY() + 0.125,
                            entity.getZ() +MathHelper.nextBetween(random,-0.2f,0.2f),
                            MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f,
                            0.05D,
                            MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);

                }
            }
        }
    }
}
