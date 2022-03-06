package com.ordana.immersive_weathering.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(NyliumBlock.class)
public class NyliumBlockMixin {
    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.above();
        if (world.getBlockState(pos).is(Blocks.WARPED_NYLIUM)) {
            if (BlockPos.withinManhattanStream(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.WARPED_ROOTS::equals)
                    .toList().size() <= 5) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockAndUpdate(targetPos, Blocks.WARPED_ROOTS.defaultBlockState());
                        }
                        if (random.nextFloat() < 0.2f) {
                            world.setBlockAndUpdate(targetPos, Blocks.WARPED_FUNGUS.defaultBlockState());
                        }
                        if (random.nextFloat() < 0.15f) {
                            world.setBlockAndUpdate(targetPos, Blocks.NETHER_SPROUTS.defaultBlockState());
                        }
                    }
                }
            }
        }
        if (world.getBlockState(pos).is(Blocks.CRIMSON_NYLIUM)) {
            if (BlockPos.withinManhattanStream(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.CRIMSON_ROOTS::equals)
                    .toList().size() <= 5) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                        if (random.nextFloat() > 0.2f) {
                            world.setBlockAndUpdate(targetPos, Blocks.CRIMSON_ROOTS.defaultBlockState());
                        }
                        if (random.nextFloat() < 0.2f) {
                            world.setBlockAndUpdate(targetPos, Blocks.CRIMSON_ROOTS.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
