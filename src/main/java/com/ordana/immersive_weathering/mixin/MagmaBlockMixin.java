package com.ordana.immersive_weathering.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin {
    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        for (var direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            if (BlockPos.withinManhattanStream(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.LAVA::equals)) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(Blocks.MAGMA_BLOCK::equals)
                        .toList().size() <= 8) {
                    float f = 0.5f;
                    if (random.nextFloat() > 0.5f) {
                        if (world.getBlockState(targetPos).is(Blocks.NETHERRACK)) {
                            world.setBlockAndUpdate(targetPos, Blocks.MAGMA_BLOCK.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
