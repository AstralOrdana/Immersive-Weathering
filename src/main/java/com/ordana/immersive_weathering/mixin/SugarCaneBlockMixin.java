package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.CrackedMudBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.MulchBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.down());
        if ((blockState.isOf(ModBlocks.CRACKED_MUD) && blockState.get(CrackedMudBlock.SOAKED)) || (blockState.isOf(ModBlocks.MULCH_BLOCK) && blockState.get(MulchBlock.SOAKED))) {
            cir.setReturnValue(true);
        }
    }
}
