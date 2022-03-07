package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.MulchBlock;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends Block {
    public CropBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    public void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (floor.isIn(ModTags.FERTILE_BLOCKS)) {
            cir.setReturnValue(true);
        }
    }

    @Shadow
    public abstract int getMaxAge();

    @Shadow
    protected abstract int getAge(BlockState state);

    @Shadow
    public abstract IntProperty getAgeProperty();

    @Shadow
    public abstract BlockState withAge(int age);

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var soilPos = pos.down();
        BlockState soilState = world.getBlockState(soilPos);
        int i;
        if (world.getBaseLightLevel(pos.up(), 0) >= 9 && (i = this.getAge(state)) < this.getMaxAge()) {
            if (soilState.isOf(ModBlocks.MULCH)) {
                if (soilState.get(MulchBlock.SOAKED)) {
                    world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                }
                else if (!soilState.get(MulchBlock.SOAKED)) {
                    ci.cancel();
                }
            }
        }
    }
}
