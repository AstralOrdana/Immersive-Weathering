package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin extends Block {

    public BubbleColumnBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "getColumnState", at = @At(value = "HEAD"), cancellable = true)
    private static BlockState getColumnState(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (blockState.is(Blocks.BUBBLE_COLUMN)) {
            return blockState;
        } else if (blockState.is(ModTags.BUBBLES_ASCENDING)) {
            return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, false);
        } else {
            return blockState.is(ModTags.BUBBLES_DESCENDING) ? Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, true) : Blocks.WATER.defaultBlockState();
        }
    }

}
