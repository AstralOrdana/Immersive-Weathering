package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class LightningEntityMixin extends Entity {

    @Shadow
    protected abstract BlockPos getStrikePosition();

    public LightningEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }


    @Inject(method = "powerLightningRod", at = @At("HEAD"))
    private void powerLightningRod(CallbackInfo ci) {
        BlockPos blockPos = this.getStrikePosition();

        BlockState blockState = this.level.getBlockState(blockPos);
        BlockGrowthHandler.tickBlock(TickSource.LIGHTNING, blockState, (ServerLevel) level, blockPos);
    }
}
