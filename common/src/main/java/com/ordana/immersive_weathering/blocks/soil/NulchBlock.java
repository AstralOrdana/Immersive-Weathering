package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.util.RandomSource;

public class NulchBlock extends Block {
    //TODO: merge with Mulch
    public static final BooleanProperty MOLTEN = ModBlockProperties.MOLTEN;

    public NulchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(MOLTEN, false));
    }


    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        var biome = world.getBiome(pos);
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                if (world.isRainingAt(pos.above())) {
                    return;
                }
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
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
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
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


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(Items.LAVA_BUCKET) && !state.getValue(MOLTEN)) {
                level.playSound(player, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                    level.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            // fill bucket from mulch
            else if (stack.is(Items.BUCKET) && state.getValue(MOLTEN)) {
                level.playSound(player, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.LAVA_BUCKET.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                    level.setBlockAndUpdate(pos, state.setValue(MOLTEN, false));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }


}
