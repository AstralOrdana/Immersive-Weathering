package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatBlockGoal.class)
public abstract class EatBlockGoalMixin extends Goal {

    @Shadow
    @Final
    private Mob mob;

    @Shadow
    @Final
    private Level level;

    @Inject(method = "tick", at = @At(value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;",
            shift = At.Shift.AFTER))
    private void eatRootedGrass(CallbackInfo ci) {
        BlockPos blockPos2 = this.mob.blockPosition().below();
        if (this.level.getBlockState(blockPos2).is(ModBlocks.ROOTED_GRASS_BLOCK.get())) {
            if (PlatHelper.isMobGriefingOn(level, this.mob)) {
                this.level.levelEvent(2001, blockPos2, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                this.level.setBlock(blockPos2, Blocks.DIRT.defaultBlockState(), 2);
            }
            this.mob.ate();
        }
    }

    @Inject(method = "canUse", at = @At(value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;",
            shift = At.Shift.BEFORE), cancellable = true)
    private void canEatRootedGrass(CallbackInfoReturnable<Boolean> cir) {
        if (this.level.getBlockState(this.mob.blockPosition().below()).is(ModBlocks.ROOTED_GRASS_BLOCK.get())) {
            cir.setReturnValue(true);
        }
    }

}
