package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.FulguriteBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin extends Entity {

    public LightningEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract BlockPos getAffectedBlockPos();

    @Inject(method = "powerLightningRod", at = @At("HEAD"))
    private void powerLightningRod(CallbackInfo ci) {
        BlockPos blockPos = this.getAffectedBlockPos();
        BlockState blockState = this.world.getBlockState(blockPos);
        for (Direction direction : Direction.values()) {
            var targetPos = blockPos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.isIn(BlockTags.BASE_STONE_OVERWORLD) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateMagma) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(targetPos, Blocks.MAGMA_BLOCK.getDefaultState());
                }
            }
            else if (neighborState.isOf(Blocks.MAGMA_BLOCK) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateLava) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(targetPos, Blocks.LAVA.getDefaultState());
                }
            }
            else if (neighborState.isIn(BlockTags.SAND) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateVitrifiedSand) {
                if (world.random.nextFloat() < 0.5f) {
                    world.setBlockState(targetPos, ModBlocks.VITRIFIED_SAND.getDefaultState());
                }
            }
        }
        if (blockState.isIn(BlockTags.SAND) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateVitrifiedSand) {
            WeatheringHelper.onLightningHit(blockPos, world, 0);
        }
        else if (blockState.isIn(BlockTags.BASE_STONE_OVERWORLD) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateMagma) {
            world.setBlockState(blockPos, Blocks.MAGMA_BLOCK.getDefaultState());
        }
        else if (blockState.isOf(Blocks.MAGMA_BLOCK) && ImmersiveWeathering.getConfig().fireAndIceConfig.lightningCreateLava) {
            world.setBlockState(blockPos, Blocks.LAVA.getDefaultState());
        }
        else if (blockState.isOf(ModBlocks.FULGURITE)) {
            ((FulguriteBlock)blockState.getBlock()).setPowered(blockState, this.world, blockPos);
        }
    }
}
