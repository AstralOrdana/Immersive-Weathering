package com.ordana.immersive_weathering.blocks.charred;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.mehvahdjukaar.moonlight.api.block.ILightable;
import net.mehvahdjukaar.moonlight.api.client.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public interface Charred extends ILightable, Fallable {

    BooleanProperty SMOLDERING = ModBlockProperties.SMOLDERING;

    default void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = level.getBiome(pos);
            BlockState neighborState = level.getBlockState(targetPos);
            if (level.isRainingAt(pos.relative(direction)) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (level.isRainingAt(pos.relative(direction)) || biome.is(ModTags.WET) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || neighborState.is(BlockTags.FIRE)) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (isLitUp(state)) {
                //TODO: extinguish
                level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, false));
            }
        } else if (temperature > 0 && !state.getValue(SMOLDERING)) {
            level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, true));
        }
    }

    default void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(16) == 0 && FallingBlock.isFree(level.getBlockState(pos.below()))) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() - 0.05;
            double f = (double) pos.getZ() + random.nextDouble();
            level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
        }
        if (isLitUp(state)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            level.addParticle(ModParticles.EMBERSPARK.get(), d, e, f, 0.1D, 3D, 0.1D);
        }
    }

    default void onEntityStepOn(BlockState state, Entity entity) {
        if (isLitUp(state)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(entity.damageSources().hotFloor(), 1.0F);
            }
        }
    }


    @Override
    default boolean isLitUp(BlockState state) {
        return state.getValue(SMOLDERING);
    }

    @Override
    default BlockState toggleLitState(BlockState state, boolean lit) {
        return state.setValue(SMOLDERING, lit);
    }



    IntegerProperty OVERHANG = ModBlockProperties.OVERHANG;

    default int getOverhang(Level level, BlockPos pos) {
        int overhang = 2;
        for (var dir : Direction.values()) {
            if (dir == Direction.DOWN) {
                var free = FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight();
                if (!free) {
                    overhang = 0;
                    break;
                }
            }
            else if (dir != Direction.UP) {
                BlockPos neighborPos = pos.relative(dir);
                var neighbor = level.getBlockState(neighborPos);
                if (neighbor.hasProperty(OVERHANG)) {
                    if(neighbor.getValue(OVERHANG) == 0){
                        overhang = 1;
                        break;
                    }
                }
                else if(neighbor.isFaceSturdy(level, neighborPos, dir.getOpposite())){
                    overhang = 1;
                    break;
                }
            }
        }
        return overhang;
    }

    default void updateOverhang(BlockState state, Level level, BlockPos pos) {
        int supported = getOverhang(level, pos);
        if (supported != state.getValue(OVERHANG)) {
            level.setBlockAndUpdate(pos, state.setValue(OVERHANG, supported));
        }
        if (supported==2) {
            level.scheduleTick(pos, state.getBlock(), 1);
        }
    }


    @Override
    default void onLand(Level level, BlockPos pos, BlockState blockState, BlockState blockState2, FallingBlockEntity fallingBlock) {
    }

    default InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        boolean flint = item instanceof FlintAndSteelItem;
        boolean charge = stack.is(Items.FIRE_CHARGE);
        if ((flint || charge) && !state.getValue(SMOLDERING)) {
            level.playSound(player, pos, flint ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.EMBERSPARK.get(), UniformInt.of(3, 5));
            if (!player.getAbilities().instabuild) {
                if (flint) stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                if (charge) stack.shrink(1);
            }
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, Boolean.TRUE));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.SUCCESS;
        }
        else if (state.getValue(SMOLDERING)) {
            level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (level.isClientSide()) {
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.EMBERSPARK.get(), UniformInt.of(3, 5));
                ParticleUtil.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SMOKE, UniformInt.of(3, 5), -0.05f, 0.05f, false);
            }
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, Boolean.FALSE));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
