package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SnowyBlock.class)
public abstract class SnowyBlockMixin extends Block {

    public SnowyBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.isOf(Blocks.PODZOL)) {
            var targetPos = pos.up();
            if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir()) {
                if (!world.isChunkLoaded(pos)) return;
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                        p -> p.getBlock() == Blocks.FERN, 8)) {
                    world.setBlockState(targetPos, Blocks.FERN.getDefaultState(), 2);
                }
            }
        }
    }
}
