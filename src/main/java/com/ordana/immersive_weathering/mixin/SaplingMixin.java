package com.ordana.immersive_weathering.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SaplingBlock.class)
public abstract class SaplingMixin extends Block {
    public SaplingMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        //it could have already turned into a tree
        if (world.getBlockState(pos).getBlock() instanceof SaplingBlock) {
            var biome = world.getBiome(pos);
            if (biome.value().shouldSnowGolemBurn(pos)) {
                if (world.random.nextFloat() < 0.08f) {
                    world.setBlockAndUpdate(pos, Blocks.DEAD_BUSH.defaultBlockState());
                }
            } else if (world.dimensionType().ultraWarm()) {
                if (world.random.nextFloat() < 0.4f) {
                    world.setBlockAndUpdate(pos, Blocks.DEAD_BUSH.defaultBlockState());
                }
            }
        }
    }
}
