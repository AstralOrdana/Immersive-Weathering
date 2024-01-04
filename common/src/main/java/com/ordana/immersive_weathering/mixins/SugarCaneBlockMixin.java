package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.blocks.soil.EarthenClayFarmlandBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    public void canPlaceAt(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = level.getBlockState(pos.below());
        if ((blockState.is(ModBlocks.EARTHEN_CLAY_FARMLAND.get()) && blockState.getValue(EarthenClayFarmlandBlock.MOISTURE) > 0) ||
            (blockState.is(ModBlocks.EARTHEN_CLAY.get()) && blockState.getValue(BlockStateProperties.WATERLOGGED))) {
            cir.setReturnValue(true);
        }
    }
}
