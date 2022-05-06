package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {

    protected CampfireMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "doesBlockCauseSignalFire", at = @At("HEAD"), cancellable = true)
    private void isSmokeSource(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(ModTags.SMOKEY_BLOCKS)) cir.setReturnValue(true);
    }
}