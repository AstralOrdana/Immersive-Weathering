package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
public abstract class FarmlandMixin extends Block {
    public FarmlandMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.above();
        if (world.getBlockState(targetPos).isAir()) {
            //checks if it doesn't have at least 9 weeds around
            if (!world.isAreaLoaded(pos, 3)) return;
            var neighbors = BlockPos.spiralAround(pos, 3, Direction.SOUTH, Direction.EAST).iterator();
            boolean hasFullyGrownWeedNearby = false;
            int weedsNearby = 0;
            while (neighbors.hasNext()) {
                if (weedsNearby > 9) return;
                BlockPos p = neighbors.next();
                BlockState s = world.getBlockState(p);
                if (s.is(ModBlocks.WEEDS.get())) {
                    weedsNearby = weedsNearby + 1;
                    if (!hasFullyGrownWeedNearby && s.getValue(CropBlock.AGE) == BlockStateProperties.MAX_AGE_7) {
                        hasFullyGrownWeedNearby = true;
                    }
                }
            }
            //almost identical to the old one
            if (random.nextFloat() < 0.035f) {
                if (hasFullyGrownWeedNearby) {
                    world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState());
                }
            } else if (random.nextFloat() < 0.0002f) {
                world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState());
            }
        }
    }
}
