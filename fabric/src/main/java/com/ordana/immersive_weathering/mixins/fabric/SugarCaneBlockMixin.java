package com.ordana.immersive_weathering.mixins.fabric;

import net.minecraft.world.level.block.SugarCaneBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

    /*
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlaceAt(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.below());
        if ((blockState.is(ModBlocks.CRACKED_MUD.get()) && blockState.getValue(CrackedMudBlock.SOAKED)) || (blockState.is(ModBlocks.MULCH_BLOCK.get()))) {
            cir.setReturnValue(true);
        }
    }

     */
}
