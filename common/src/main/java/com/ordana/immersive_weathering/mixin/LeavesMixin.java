package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.block_growth.growths.builtin.LeavesGrowth;
import com.ordana.immersive_weathering.client.ParticleHelper;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
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

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements BonemealableBlock {

    public LeavesMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At(value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"))
    public void randomDisplayTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random, CallbackInfo ci) {
        LeavesGrowth.decayLeavesPile(blockState, serverLevel, blockPos);
    }


    @Inject(method = "animateTick", at = @At("HEAD"))
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, Random random, CallbackInfo ci) {
        ParticleHelper.spawnLeavesParticles(state, world, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
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

