package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public class LightningEntityMixin extends Entity {

    public LightningEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "powerLightningRod", at = @At("HEAD"))
    private void powerLightningRod(CallbackInfo ci) {
        BlockPos blockPos = this.getAffectedBlockPos();
        BlockPos downPos = blockPos.below();
        BlockPos northPos = blockPos.north();
        BlockPos southPos = blockPos.south();
        BlockPos eastPos = blockPos.east();
        BlockPos westPos = blockPos.west();
        BlockState downState = level.getBlockState(downPos);
        BlockState northState = level.getBlockState(northPos);
        BlockState southState = level.getBlockState(southPos);
        BlockState eastState = level.getBlockState(eastPos);
        BlockState westState = level.getBlockState(westPos);
        BlockState blockState = this.level.getBlockState(blockPos);
        if (blockState.is(BlockTags.SAND)) {
            level.setBlockAndUpdate(blockPos, Blocks.GLASS.defaultBlockState());
            if (downState.is(BlockTags.SAND)) {
                level.setBlockAndUpdate(downPos, Blocks.GLASS.defaultBlockState());
            }
            if (northState.is(BlockTags.SAND)) {
                if (level.random.nextFloat() < 0.5f) {
                    level.setBlockAndUpdate(northPos, Blocks.GLASS.defaultBlockState());
                }
            }
            if (southState.is(BlockTags.SAND)) {
                if (level.random.nextFloat() < 0.5f) {
                    level.setBlockAndUpdate(southPos, Blocks.GLASS.defaultBlockState());
                }
            }
            if (eastState.is(BlockTags.SAND)) {
                if (level.random.nextFloat() < 0.5f) {
                    level.setBlockAndUpdate(eastPos, Blocks.GLASS.defaultBlockState());
                }
            }
            if (westState.is(BlockTags.SAND)) {
                if (level.random.nextFloat() < 0.5f) {
                    level.setBlockAndUpdate(westPos, Blocks.GLASS.defaultBlockState());
                }
            }
        }
    }

    public BlockPos getAffectedBlockPos() {
        Vec3 vec3d = this.position();
        return new BlockPos(vec3d.x, vec3d.y - 1.0E-6D, vec3d.z);
    }

    @Override
    public void defineSynchedData() {
    }
    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
    }
    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
    }
    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
