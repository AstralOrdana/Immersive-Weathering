package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour {


    public BlockMixin(Properties p_60452_) {
        super(p_60452_);
    }

    @Inject(method = "isRandomlyTicking", at = @At("TAIL"), cancellable = true)
    public void randomTick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && BlockGrowthHandler.canRandomTick(state)) cir.setReturnValue(true);
    }


}
