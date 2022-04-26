package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class WeedsBlock extends CropBlock {

    public WeedsBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(this, world, pos);
            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                world.setBlock(pos, this.getStateForAge(i + 1), 2);
            }
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.WEEDS.get();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (this.getAge(state) == this.getMaxAge() && random.nextInt(10) == 0) {
            double r = 0.3;
            double x = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * r;
            double y = (double) pos.getY() + 0.8 + (random.nextDouble() - 0.5) * r;
            double z = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * r;
            world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, 0.1D, 0.5D, 0.1D);
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 100;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (level.random.nextFloat() < 0.1 && entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            if (!level.isClientSide && state.getValue(AGE) > 0 && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                if (!(entity instanceof Player player) || player.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                    double d0 = Math.abs(entity.getX() - entity.xOld);
                    double d1 = Math.abs(entity.getZ() - entity.zOld);
                    if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
                        entity.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                }
            }
        }
    }

}
