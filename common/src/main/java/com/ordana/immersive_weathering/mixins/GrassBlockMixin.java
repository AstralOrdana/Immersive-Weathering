package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpreadingSnowyDirtBlock.class)
public class GrassBlockMixin {


    @Inject(method = "randomTick", at = @At(value = "TAIL"))
    protected void mayPlaceOn(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {

        if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {

            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState s = level.getBlockState(blockPos);
                var soil = WeatheringHelper.getGrassySoil(s);
                if (soil.isPresent() && SpreadingSnowyDirtBlock.canPropagate(state, level, blockPos)) {
                    if (soil.get().hasProperty(BlockStateProperties.SNOWY)) level.setBlockAndUpdate(blockPos, soil.get().setValue(BlockStateProperties.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    else level.setBlockAndUpdate(blockPos, soil.get());
                }
            }
        }
    }
}
