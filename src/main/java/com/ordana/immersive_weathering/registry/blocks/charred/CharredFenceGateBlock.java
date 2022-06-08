package com.ordana.immersive_weathering.registry.blocks.charred;

import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Random;

public class CharredFenceGateBlock extends FenceGateBlock implements Charrable {

    public CharredFenceGateBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SMOLDERING, false).with(OPEN, false).with(POWERED, false).with(IN_WALL, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SMOLDERING);
        stateManager.add(FACING, OPEN, POWERED, IN_WALL);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult pHit, ProjectileEntity projectile) {
        BlockPos pos = pHit.getBlockPos();
        if (projectile instanceof PotionEntity potion && PotionUtil.getPotion(potion.getStack()) == Potions.WATER) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity;
            if (flag) {
                world.setBlockState(pos, state.with(SMOLDERING, false));
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(SMOLDERING)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            world.addParticle(ModParticles.EMBERSPARK, d, e, f, 0.1D, 3D, 0.1D);
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(SMOLDERING)) {
            if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.damage(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);
            if (world.hasRain(pos.offset(direction)) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (world.hasRain(pos.offset(direction)) || biome.isIn(ModTags.WET) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.isIn(ModTags.MAGMA_SOURCE) || neighborState.isIn(BlockTags.FIRE)) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (state.get(SMOLDERING)) {
                world.setBlockState(pos, state.with(SMOLDERING, false));
            }
        } else if (temperature > 0 && !state.get(SMOLDERING)) {
            world.setBlockState(pos, state.with(SMOLDERING, true));
        }
    }
}
