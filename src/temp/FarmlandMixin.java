package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FarmBlock.class)
public class FarmlandMixin extends Block {
    public FarmlandMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.above();
        if (!world.getBlockState(targetPos).is(ModBlocks.WEEDS)) {
            if (BlockPos.withinManhattanStream(pos, 3, 3, 3)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ModBlocks.WEEDS::equals)
                    .toList().size() <= 9) {
                if (random.nextFloat() < 0.04f) {
                    if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .anyMatch(ModBlocks.WEEDS.defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7)::equals)) {
                        if (random.nextFloat() < 0.8f) {
                            world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState());
                        }
                    }
                }
                else if (random.nextFloat() < 0.0002f) {
                    world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState());
                }
            }
        }
    }
}
