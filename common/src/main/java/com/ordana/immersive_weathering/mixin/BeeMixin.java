package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.entities.FollowLeafCrownGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal {

    protected BeeMixin(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void initGoals(CallbackInfo ci) {
        this.beeGoalHelper(this);
    }

    private void beeGoalHelper(Animal animal) {
        this.goalSelector.addGoal(3, new FollowLeafCrownGoal(animal, 1D, false));
    }
}
