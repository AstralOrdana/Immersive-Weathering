package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SnowyDirtBlock.class)
public class SnowyBlockMixin extends Block {

    public SnowyBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        var targetPos = pos.above();
        if (world.getBlockState(pos).is(Blocks.PODZOL)) {
            if (BlockPos.withinManhattanStream(pos, 4, 4, 4)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(Blocks.FERN::equals)
                    .toList().size() <= 8) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                        world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                    }
                }
            }
        }
    }
}
