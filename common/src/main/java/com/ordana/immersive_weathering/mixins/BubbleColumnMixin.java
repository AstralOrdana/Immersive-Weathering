package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnMixin extends Block {

    @Shadow @Final public static BooleanProperty DRAG_DOWN;

    protected BubbleColumnMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canSurvive", at = @At("TAIL"), cancellable = true)
    protected void canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            BlockState blockState = level.getBlockState(pos.below());
            if (blockState.is(ModTags.BUBBLES_ASCENDING) || blockState.is(ModTags.BUBBLES_DESCENDING)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getColumnState", at = @At("HEAD"), cancellable = true)
    private static void getColumnState(BlockState blockState, CallbackInfoReturnable<BlockState> cir) {
        if(blockState.is(ModTags.BUBBLES_DESCENDING)){
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, true));
        }
        else if(blockState.is(ModTags.BUBBLES_ASCENDING)){
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, false));
        }
    }
}