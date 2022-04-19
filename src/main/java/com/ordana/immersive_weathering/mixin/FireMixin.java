package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.AshBlock;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.blocks.SootBlock;
import com.ordana.immersive_weathering.data.BlockGrowthHandler;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {
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
    @Redirect(method = "tick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z",
            ordinal = 3))
    private boolean removeBlock(ServerLevel level, BlockPos pos, boolean b) {
        BlockState s = level.getBlockState(pos);
        boolean result = level.removeBlock(pos,b);
        SootBlock.convertToSoot(level, pos, s);
        return result;
    }


    //replace fire with soot/ash
    @Redirect(method = "tryCatchFire",
            require = 0,
            at = @At(value = "INVOKE",
                    target = "net/minecraft/world/level/Level.removeBlock (Lnet/minecraft/core/BlockPos;Z)Z"))
    private boolean removeBlock(Level level, BlockPos pPos, boolean isMoving) {
        if (!AshBlock.convertToAsh(level, pPos)) level.removeBlock(pPos, isMoving);
        return true;
    }

    //fire can replace soot
    @Redirect(method = "getFireOdds",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelReader;isEmptyBlock(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean canFireReplace(LevelReader levelReader, BlockPos pos) {
        if (levelReader.getBlockState(pos).is(ModBlocks.SOOT.get())) return true;
        return levelReader.isEmptyBlock(pos);
    }
}