package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NyliumBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(NyliumBlock.class)
public abstract class NyliumBlockMixin {

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        if (random.nextFloat() < 0.0005f) {
            var targetPos = pos.above();

            if (state.is(Blocks.WARPED_NYLIUM)) {
                if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world,
                            b -> b.is(Blocks.NETHER_SPROUTS)
                                    || b.is(Blocks.WARPED_ROOTS)
                                    || b.is(Blocks.WARPED_FUNGUS),
                            5)) {

                        var o = WeatheringHelper.getNyliumGrowth(state, random);
                        o.ifPresent(b -> world.setBlockAndUpdate(targetPos, b.defaultBlockState()));
                    }
                }
            }
            if (state.is(Blocks.CRIMSON_NYLIUM)) {
                if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world,
                            b -> b.is(Blocks.CRIMSON_ROOTS)
                                    || b.is(Blocks.CRIMSON_FUNGUS),
                            5)) {

                        var o = WeatheringHelper.getNyliumGrowth(state, random);
                        o.ifPresent(b -> world.setBlockAndUpdate(targetPos, b.defaultBlockState()));
                    }
                }
            }
        }
    }
}
