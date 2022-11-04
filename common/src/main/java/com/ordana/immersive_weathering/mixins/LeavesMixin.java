package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.data.block_growths.growths.builtin.LeavesGrowth;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.RandomSource;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements BonemealableBlock {

    public LeavesMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At(value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    public void onRemoved(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
        LeavesGrowth.decayLeavesPile(blockState, serverLevel, blockPos, random);
    }


    @Inject(method = "animateTick", at = @At("HEAD"))
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        LeavesGrowth.spawnFallingLeavesParticle(state, world, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockAndUpdate(targetPos, s)
                );
            }
        }
    }
}

