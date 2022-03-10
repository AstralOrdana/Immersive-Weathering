package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NyliumBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.up();
        if (world.getBlockState(pos).isOf(Blocks.WARPED_NYLIUM)) {
            if (BlockPos.streamOutwards(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.WARPED_ROOTS::equals)
                    .toList().size() <= 5) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockState(targetPos, Blocks.WARPED_ROOTS.getDefaultState());
                        }
                        if (random.nextFloat() < 0.2f) {
                            world.setBlockState(targetPos, Blocks.WARPED_FUNGUS.getDefaultState());
                        }
                        if (random.nextFloat() < 0.15f) {
                            world.setBlockState(targetPos, Blocks.NETHER_SPROUTS.getDefaultState());
                        }
                    }
                }
            }
        }
        if (world.getBlockState(pos).isOf(Blocks.CRIMSON_NYLIUM)) {
            if (BlockPos.streamOutwards(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.CRIMSON_ROOTS::equals)
                    .toList().size() <= 5) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockState(targetPos, Blocks.CRIMSON_ROOTS.getDefaultState());
                        }
                        if (random.nextFloat() < 0.2f) {
                            world.setBlockState(targetPos, Blocks.CRIMSON_FUNGUS.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
