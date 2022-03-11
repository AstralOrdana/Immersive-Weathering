package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
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
public abstract class NyliumBlockMixin {

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (random.nextFloat() < 0.0005f) {
            var targetPos = pos.up();

            if (state.isOf(Blocks.WARPED_NYLIUM)) {
                if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world,
                            b -> b.isOf(Blocks.NETHER_SPROUTS)
                                    || b.isOf(Blocks.WARPED_ROOTS)
                                    || b.isOf(Blocks.WARPED_FUNGUS),
                            5)) {

                        var o = WeatheringHelper.getNyliumGrowth(state, random);
                        o.ifPresent(b -> world.setBlockState(targetPos, b.getDefaultState()));
                    }
                }
            }
            if (state.isOf(Blocks.CRIMSON_NYLIUM)) {
                if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world,
                            b -> b.isOf(Blocks.CRIMSON_ROOTS)
                                    || b.isOf(Blocks.CRIMSON_FUNGUS),
                            5)) {

                        var o = WeatheringHelper.getNyliumGrowth(state, random);
                        o.ifPresent(b -> world.setBlockState(targetPos, b.getDefaultState()));
                    }
                }
            }
        }
    }
}
