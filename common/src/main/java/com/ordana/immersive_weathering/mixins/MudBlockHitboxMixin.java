package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.entities.FallingLayerEntity;
import com.ordana.immersive_weathering.entities.FallingPropaguleEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.text.html.BlockView;

@Mixin(MudBlock.class)
public abstract class MudBlockHitboxMixin {

    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context instanceof EntityCollisionContext c) {
            var e = c.getEntity();
            if (e instanceof FallingPropaguleEntity || e instanceof FallingLayerEntity) {
                cir.setReturnValue(Shapes.block());
            }
        }
    }
}
