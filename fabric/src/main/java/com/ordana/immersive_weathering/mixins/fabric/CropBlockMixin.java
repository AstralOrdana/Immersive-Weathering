package com.ordana.immersive_weathering.mixins.fabric;

import com.ordana.immersive_weathering.blocks.soil.MulchBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends Block {
    public CropBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.is(ModTags.FERTILE_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        BlockState soilState = world.getBlockState(pos.below());
        if (soilState.is(ModBlocks.MULCH_BLOCK.get()) && soilState.getValue(MulchBlock.MOISTURE) == 0) ci.cancel();
    }
}