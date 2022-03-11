package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandMixin extends Block {
    public FarmlandMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.up();
        if (world.getBlockState(targetPos).isAir()) {
            //checks if it doesn't have at least 9 weeds around
            if (!world.isChunkLoaded(pos)) return;
            List<BlockPos> neighbors = WeatheringHelper.grabBlocksAroundRandomly(pos, 3,3,3);
            boolean hasFullyGrownWeedNearby = false;
            int weedsNearby = 0;
            for(BlockPos p : neighbors){
                if (weedsNearby > 9) return;
                BlockState s = world.getBlockState(p);
                if (s.isOf(ModBlocks.WEEDS)) {
                    weedsNearby = weedsNearby + 1;
                    if (!hasFullyGrownWeedNearby && s.get(CropBlock.AGE) == Properties.AGE_7_MAX) {
                        hasFullyGrownWeedNearby = true;
                    }
                }
            }
            //almost identical to the old one
            if (random.nextFloat() < 0.035f) {
                if (hasFullyGrownWeedNearby) {
                    world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState());
                }
            } else if (random.nextFloat() < 0.0002f) {
                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState());
            }
        }
    }
}
