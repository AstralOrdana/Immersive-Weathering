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
public class SaplingMixin extends Block {
    public SaplingMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var biome = world.getBiome(pos);
        if (biome.isIn(ModTags.HOT)) {
            if (world.random.nextFloat() < 0.08f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (world.getRegistryKey() == World.NETHER) {
            if (world.random.nextFloat() < 0.4f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
    }
}
