package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SaplingBlock.class)
public abstract class SaplingMixin extends Block {
    public SaplingMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        //it could have already turned into a tree
        if (world.getBlockState(pos).getBlock() instanceof SaplingBlock) {
            var biome = world.getBiome(pos);
            if (biome.value().isHot(pos)) {
                if (world.random.nextFloat() < 0.08f) {
                    world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
                }
            } else if (world.getDimension().isUltrawarm()) {
                if (world.random.nextFloat() < 0.4f) {
                    world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
                }
            }
        }
    }
}
