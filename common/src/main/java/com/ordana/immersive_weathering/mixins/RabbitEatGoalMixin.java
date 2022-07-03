package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.blocks.WeedsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/entity/animal/Rabbit$RaidGardenGoal")
public abstract class RabbitEatGoalMixin extends MoveToBlockGoal {

    @Shadow
    private boolean canRaid;

    public RabbitEatGoalMixin(PathfinderMob pathfinderMob, double v, int i) {
        super(pathfinderMob, v, i);
    }

    @Inject(method = "isValidTarget", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
            ordinal = 1),
            cancellable = true)
    private void isTempting(LevelReader levelReader, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = levelReader.getBlockState(pos.above());
        if (blockstate.getBlock() instanceof WeedsBlock weedsBlock && weedsBlock.isMaxAge(blockstate)) {
            this.canRaid = true;
            cir.setReturnValue(true);
        }
    }
}