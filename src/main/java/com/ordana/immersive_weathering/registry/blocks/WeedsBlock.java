package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class WeedsBlock extends CropBlock {

    public WeedsBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !(entity instanceof FoxEntity || entity instanceof BeeEntity || entity instanceof SheepEntity || entity instanceof CatEntity || entity instanceof VillagerEntity)) {
            if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                if (entity instanceof PlayerEntity player && !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty() && ImmersiveWeathering.getConfig().leavesConfig.leggingsPreventThornDamage) {
                    return;
                } else if (entity instanceof PlayerEntity player) {
                    double d = Math.abs(entity.getX() - entity.lastRenderX);
                    double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                    if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                        entity.damage(DamageSource.SWEET_BERRY_BUSH, 0.5F);
                    }
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getAvailableMoisture(this, world, pos);
            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                world.setBlockState(pos, this.withAge(i + 1), 2);
            }
        }
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.WEEDS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (this.getAge(state) == this.getMaxAge() && random.nextInt(10)==0) {
            double r = 0.3;
            double x = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * r;
            double y = (double) pos.getY() + 0.8 + (random.nextDouble() - 0.5) * r;
            double z = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * r;
            world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, 0.1D, 0.5D, 0.1D);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos below = pos.down();
        return this.canPlantOnTop(world.getBlockState(below), world, below);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.FERTILE_BLOCKS) || floor.isIn(BlockTags.DIRT) || floor.isIn(ModTags.CRACKED) || floor.isIn(ModTags.MOSSY);
    }
}
