package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class NulchBlock extends Block {
    //TODO: merge with Mulch
    public static final BooleanProperty MOLTEN = ModBlockProperties.MOLTEN;

    public NulchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(MOLTEN, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            if (player.getItemInHand(hand).is(Items.LAVA_BUCKET) && !state.getValue(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
                world.playSound(player, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getItemInHand(hand).is(Items.BUCKET) && state.getValue(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.LAVA_BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, false));
                world.playSound(player, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        var biome = world.getBiome(pos);
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                if (world.isRainingAt(pos.above())) {
                    return;
                }
                world.setBlock(pos, state.setValue(MOLTEN, true), 2);
            }
        }
        if (biome.is(ModTags.ICY)) {
            if (world.random.nextFloat() < 0.4f) {
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, false));
            }
        } else if (world.dimension() == Level.NETHER) {
            if (world.random.nextFloat() < 0.5f) {
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
            }
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(MOLTEN);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (state.getValue(MOLTEN)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.stepOn(level, pos, state, entity);
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (level.isClientSide) {
                Random random = level.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {

                    level.addParticle(ModParticles.NULCH.get(),
                            entity.getX() + Mth.randomBetween(random, -0.2f, 0.2f),
                            pos.getY() + 1.025,
                            entity.getZ() + Mth.randomBetween(random, -0.2f, 0.2f),
                            Mth.randomBetween(random, -0.9f, -1),
                            -1,
                            0);
                }
            }
        }
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (state.getValue(MOLTEN)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
