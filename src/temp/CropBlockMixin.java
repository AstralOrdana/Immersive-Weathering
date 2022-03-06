package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.MulchBlock;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CropBlock.class)
public class CropBlockMixin extends Block {
    public CropBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.is(ModTags.FERTILE_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    public int getMaxAge() {
        return 7;
    }

    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public IntegerProperty getAgeProperty() {
        return CropBlock.AGE;
    }

    public BlockState withAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var soilPos = pos.below();
        BlockState soilState = world.getBlockState(soilPos);
        int i;
        if (world.getRawBrightness(pos.above(), 0) >= 9 && (i = this.getAge(state)) < this.getMaxAge()) {
            if (soilState.is(ModBlocks.MULCH)) {
                if (soilState.getValue(MulchBlock.SOAKED)) {
                    world.setBlock(pos, this.withAge(i + 1), Block.UPDATE_CLIENTS);
                }
                else if (!soilState.getValue(MulchBlock.SOAKED)) {
                    ci.cancel();
                }
            }
        }
    }
}
