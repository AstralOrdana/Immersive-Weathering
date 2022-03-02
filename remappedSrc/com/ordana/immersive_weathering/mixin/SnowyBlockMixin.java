package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin extends Block {

    public SnowyBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var targetPos = pos.up();
        if (world.getBlockState(pos).isOf(Blocks.PODZOL)) {
            if (BlockPos.streamOutwards(pos, 4, 4, 4)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.FERN::equals)
                    .toList().size() <= 8) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                        world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                    }
                }
            }
        }
    }
}
