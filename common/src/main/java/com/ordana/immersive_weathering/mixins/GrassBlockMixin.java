package com.ordana.immersive_weathering.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(SpreadingSnowyDirtBlock.class)
public class GrassBlockMixin {


    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
    ordinal = 0))
    protected boolean mayPlaceOn(BlockState instance, Block block, Operation<Boolean> operation,
                                 @Share("newSoil")LocalRef<BlockState> newSoil) {
        boolean or = operation.call(instance, block);
        if(!or){
            Optional<BlockState> soil = WeatheringHelper.getGrassySoil(instance);
            if (soil.isPresent()) {
                newSoil.set(soil.get());
                or = true;
            }
        }else newSoil.set(null);
        return or;
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z",
    ordinal = 1))
    protected boolean mayPlaceOn(ServerLevel instance, BlockPos pos, BlockState state, Operation<Boolean> operation,
                                 @Share("newSoil")LocalRef<BlockState> newSoil) {
        BlockState soil = newSoil.get();
        if(soil != null){
            if(state.hasProperty(SnowyDirtBlock.SNOWY) && state.getValue(SnowyDirtBlock.SNOWY)){
                soil = soil.setValue(SnowyDirtBlock.SNOWY, true);
            }
            return operation.call(instance, pos, soil);
        }
        return operation.call(instance, pos, state);
    }
}
