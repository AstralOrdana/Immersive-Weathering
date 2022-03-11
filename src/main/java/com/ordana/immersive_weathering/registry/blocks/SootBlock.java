package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
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
import net.minecraft.world.World;

import java.util.Random;

public class SootBlock extends AbstractLichenBlock {

    public static final BooleanProperty LIT = Properties.LIT;

    public SootBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        super.appendProperties(stateManager);
        stateManager.add(LIT);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, false), 2);
            world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            world.addParticle(ModParticles.EMBER, d, e, f, 0.1D, 3D, 0.1D);
        }
    }

    //TODO: merge with ash
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.isIn(ModTags.MAGMA_SOURCE)) {
                if (world.hasRain(pos.up())) {
                    return;
                }
                world.setBlockState(pos, state.with(LIT, true), 2);
            }
            if (world.hasRain(pos.up()) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                if (neighborState.isIn(ModTags.MAGMA_SOURCE)) {
                    return;
                }
                world.setBlockState(pos, state.with(LIT, false), 2);
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    if (!state.get(LIT)) {
                        world.addParticle(ModParticles.SOOT, entity.getX(), entity.getY() + 0.5, entity.getZ(), MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                }
            }
            if (state.get(LIT)) {
                if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                    entity.damage(DamageSource.HOT_FLOOR, 1.0F);
                }
            }
        }
    }
}
