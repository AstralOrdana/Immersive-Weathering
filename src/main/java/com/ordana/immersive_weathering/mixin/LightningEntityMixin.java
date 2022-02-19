package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightningEntity.class)
public class LightningEntityMixin extends Entity {

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "powerLightningRod", at = @At("HEAD"), cancellable = true)
    private void powerLightningRod(CallbackInfo ci) {
        BlockPos blockPos = this.getAffectedBlockPos();
        BlockPos downPos = blockPos.down();
        BlockPos northPos = blockPos.north();
        BlockPos southPos = blockPos.south();
        BlockPos eastPos = blockPos.east();
        BlockPos westPos = blockPos.west();
        BlockState downState = world.getBlockState(downPos);
        BlockState northState = world.getBlockState(northPos);
        BlockState southState = world.getBlockState(southPos);
        BlockState eastState = world.getBlockState(eastPos);
        BlockState westState = world.getBlockState(westPos);
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.SAND)) {
            world.setBlockState(blockPos, Blocks.GLASS.getDefaultState());
            if (downState.isIn(BlockTags.SAND)) {
                world.setBlockState(downPos, Blocks.GLASS.getDefaultState());
            }
            if (northState.isIn(BlockTags.SAND)) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(northPos, Blocks.GLASS.getDefaultState());
                }
            }
            if (southState.isIn(BlockTags.SAND)) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(southPos, Blocks.GLASS.getDefaultState());
                }
            }
            if (eastState.isIn(BlockTags.SAND)) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(eastPos, Blocks.GLASS.getDefaultState());
                }
            }
            if (westState.isIn(BlockTags.SAND)) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(westPos, Blocks.GLASS.getDefaultState());
                }
            }
        }
    }

    public BlockPos getAffectedBlockPos() {
        Vec3d vec3d = this.getPos();
        return new BlockPos(vec3d.x, vec3d.y - 1.0E-6D, vec3d.z);
    }
    @Override
    public void initDataTracker() {
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
    }
    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
