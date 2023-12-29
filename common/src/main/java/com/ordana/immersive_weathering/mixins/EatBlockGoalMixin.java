package com.ordana.immersive_weathering.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EatBlockGoal.class)
public abstract class EatBlockGoalMixin extends Goal {

    @WrapOperation(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean eatRootedGrass(BlockState instance, Block block, Operation<Boolean> original) {
        boolean or = original.call(instance, block);
        if (!or && instance.is(ModBlocks.ROOTED_GRASS_BLOCK.get())) {
            or = true;
        }
        return or;
    }

    @WrapOperation(method = "canUse", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean canEatRootedGrass(BlockState instance, Block block, Operation<Boolean> original) {
        boolean or = original.call(instance, block);
        if (!or && instance.is(ModBlocks.ROOTED_GRASS_BLOCK.get())) {
            or = true;
        }
        return or;
    }

}
