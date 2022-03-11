package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public class ComposterMixin {
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        int i = state.get(ComposterBlock.LEVEL);
        if (i == 8) {
            if (world.random.nextFloat() < 0.5f) {
                Block.dropStack(world, pos.up(), new ItemStack(Items.DIRT));
            }
        }
    }
}
