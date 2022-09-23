package com.ordana.immersive_weathering.blocks.charred;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.mehvahdjukaar.moonlight.api.block.ILightable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;

import java.util.Random;

public interface Charred extends ILightable {

    BooleanProperty SMOLDERING = ModBlockProperties.SMOLDERING;

    default void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);
            if (world.isRainingAt(pos.relative(direction)) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (world.isRainingAt(pos.relative(direction)) || biome.is(ModTags.WET) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || neighborState.is(BlockTags.FIRE)) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (isLit(state)) {
                //TODO: extinguish
                world.setBlockAndUpdate(pos, state.setValue(SMOLDERING, false));
            }
        } else if (temperature > 0 && !state.getValue(SMOLDERING)) {
            world.setBlockAndUpdate(pos, state.setValue(SMOLDERING, true));
        }
    }

    default void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0 && FallingBlock.isFree(world.getBlockState(pos.below()))) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() - 0.05;
            double f = (double) pos.getZ() + random.nextDouble();
            world.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
        }

        if (isLit(state)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            world.addParticle(ModParticles.EMBERSPARK.get(), d, e, f, 0.1D, 3D, 0.1D);
        }
    }

    default void onEntityStepOn(BlockState state, Entity entity) {
        if (isLit(state)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
    }

    @Override
    default boolean isLit(BlockState state) {
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
}
