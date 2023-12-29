package com.ordana.immersive_weathering.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ordana.immersive_weathering.reg.ModTags;
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

    @Shadow
    @Final
    public static BooleanProperty DRAG_DOWN;

    protected BubbleColumnMixin(Properties properties) {
        super(properties);
    }

    @WrapOperation(method = "canSurvive", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
            ordinal = 1))
    protected boolean canSurvive(BlockState instance, Block block, Operation<Boolean> original) {
        if (instance.is(ModTags.BUBBLES_ASCENDING) || instance.is(ModTags.BUBBLES_DESCENDING)) {
            return true;
        }
        return original.call(instance, block);
    }

    @Inject(method = "getColumnState", at = @At("HEAD"), cancellable = true)
    private static void getColumnState(BlockState blockState, CallbackInfoReturnable<BlockState> cir) {
        if (blockState.is(ModTags.BUBBLES_DESCENDING)) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, true));
        } else if (blockState.is(ModTags.BUBBLES_ASCENDING)) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, false));
        }
    }
}