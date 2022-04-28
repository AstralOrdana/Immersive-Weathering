package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {

    public BlockMixin(Settings p_60452_) {
        super(p_60452_);
    }

    @Inject(method = "hasRandomTicks", at = @At("TAIL"), cancellable = true)
    public void randomTick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && BlockGrowthHandler.canRandomTick(state)) cir.setReturnValue(true);
    }
}
