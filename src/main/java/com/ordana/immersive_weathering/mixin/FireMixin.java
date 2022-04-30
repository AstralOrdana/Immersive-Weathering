package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.AshBlock;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.blocks.MulchBlock;
import com.ordana.immersive_weathering.common.blocks.SootLayerBlock;
import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {
        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {

            //if it's upwards only (normal fire) place soot next to it
            BlockGrowthHandler.tickBlock(state, level, pos);

            //place soot above
            //TODO: see if this can be optimized & use datapack
            int smokeHeight = 6;
            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.above();
                BlockState above = level.getBlockState(sootPos.above());
                if (Block.isFaceFull(above.getCollisionShape(level, sootPos.above()), Direction.DOWN)) {
                    if (level.getBlockState(sootPos).isAir()) {
                        level.setBlock(sootPos, ModBlocks.SOOT.get().defaultBlockState().setValue(BlockStateProperties.UP, true), Block.UPDATE_CLIENTS);
                    }
                    smokeHeight = i + 1;
                }
            }
            //TODO: merge with campfire
        }
    }

    //expired fire turns into soot
    @Inject(method = "tick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z",
            ordinal = 3,
            shift = At.Shift.AFTER))
    private void removeBlock(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random, CallbackInfo ci) {
        SootLayerBlock.convertToSoot(serverLevel, pos, state);
    }


    //can fail if another mod redirects it
    //replace a block burnt by fire with soot/ash
    @Redirect(method = "tryCatchFire",
            require = 0,
            at = @At(value = "INVOKE",
                    target = "net/minecraft/world/level/Level.removeBlock (Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean removeBlock(Level level, BlockPos pPos, boolean isMoving) {
        if (!AshBlock.convertToAsh(level, pPos)) level.removeBlock(pPos, isMoving);
        return true;
    }

    //fire can replace soot
    @Inject(method = "getFireOdds",
            at = @At(value = "HEAD"), cancellable = true)
    private void canFireReplace(LevelReader reader, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (reader.getBlockState(pos).is(ModBlocks.SOOT.get())) cir.setReturnValue(0);
    }
}