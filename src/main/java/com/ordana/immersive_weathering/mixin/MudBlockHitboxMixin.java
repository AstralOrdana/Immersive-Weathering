package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.entities.FallingLeafLayerEntity;
import com.ordana.immersive_weathering.registry.entities.FallingPropaguleEntity;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MudBlock.class)
public abstract class MudBlockHitboxMixin {

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityShapeContext c) {
            var e = c.getEntity();
            if (e instanceof FallingPropaguleEntity || e instanceof FallingLeafLayerEntity) {
                cir.setReturnValue(VoxelShapes.fullCube());
            }
        }
    }
}

