package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.ModBlocks;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(FarmBlock.class)
public abstract class FarmlandMixin extends Block {
    public FarmlandMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.above();
        float f = random.nextFloat();
        if (f < 0.035f) {
            BlockState target = world.getBlockState(targetPos);
            if (target.isAir() || (target.is(BlockTags.CROPS) && f < 0.0001)) {
                //checks if it doesn't have at least 9 weeds around
                if (!world.isAreaLoaded(pos, 3)) return;
                List<BlockPos> neighbors = WeatheringHelper.grabBlocksAroundRandomly(pos, 3, 3, 3);
                boolean hasFullyGrownWeedNearby = false;
                int weedsNearby = 0;
                for (BlockPos p : neighbors) {
                    if (weedsNearby > 9) return;
                    BlockState s = world.getBlockState(p);
                    if (s.is(ModBlocks.WEEDS.get())) {
                        weedsNearby = weedsNearby + 1;
                        if (!hasFullyGrownWeedNearby && s.getValue(CropBlock.AGE) == BlockStateProperties.MAX_AGE_7) {
                            hasFullyGrownWeedNearby = true;
                        }
                    }
                }
                if (hasFullyGrownWeedNearby || f < 0.0002) {
                    if (target.is(BlockTags.CROPS)) world.destroyBlock(targetPos, true);
                    world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState());
                }
            }
        }
    }
}
