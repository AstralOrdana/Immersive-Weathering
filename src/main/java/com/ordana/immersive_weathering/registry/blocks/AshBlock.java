package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class AshBlock extends FallingBlock {
    public AshBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
    }

    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, false), 2);
            world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            world.addParticle(ParticleTypes.FLAME, d, e+1, f, 0.0D, 0.0D, 0.0D);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int l = 0; l < 14; ++l) {
                mutable.set(i + MathHelper.nextInt(random, -7, 7), j + MathHelper.nextInt(random, -7, 7), k + MathHelper.nextInt(random, -7, 7));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    world.addParticle(ParticleTypes.SMOKE, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(LIT)) {
            if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.damage(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.isOf(Blocks.MAGMA_BLOCK)) {
                if (world.hasRain(pos.up())) {
                    return;
                }
                world.setBlockState(pos, state.with(LIT, true), 2);
            }
            if (world.hasRain(pos.up()) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                if (neighborState.isOf(Blocks.MAGMA_BLOCK)) {
                    return;
                }
                world.setBlockState(pos, state.with(LIT, false), 2);
            }
        }
    }

    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return -1842206;
    }
}
