package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FarmlandBlock.class)
public class FarmlandMixin extends Block {
    public FarmlandMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.up();
        if (!world.getBlockState(targetPos).isOf(ModBlocks.WEEDS)) {
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ModBlocks.WEEDS::equals)
                    .toList().size() <= 9) {
                if (random.nextFloat() < 0.0002f) {
                    world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState());
                }
                else if (BlockPos.streamOutwards(pos, 3, 3, 3)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .anyMatch(ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX)::equals)) {
                    if (random.nextFloat() < 0.8f) {
                        world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState());
                    }
                }
            }
        }
    }
}
