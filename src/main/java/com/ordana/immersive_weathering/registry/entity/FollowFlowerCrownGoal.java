package com.ordana.immersive_weathering.registry.entity;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;

public class FollowFlowerCrownGoal extends TemptGoal {

    private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(10.0D).ignoreVisibility();
    private final TargetPredicate predicate;
    private int cooldown;
    private final double speed;

    public FollowFlowerCrownGoal(PathAwareEntity entity, double speed, Ingredient food, boolean canBeScared) {
        super(entity, speed, food, canBeScared);
        this.predicate = TEMPTING_ENTITY_PREDICATE.copy().setPredicate(e -> e.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.FLOWER_CROWN));
        this.speed = speed;
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else {
            this.closestPlayer = this.mob.world.getClosestPlayer(this.predicate, this.mob);
            return this.closestPlayer != null;
        }
    }

    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, (float)(this.mob.getMaxHeadRotation() + 20), (float)this.mob.getMaxLookPitchChange());
        if (this.mob.squaredDistanceTo(this.closestPlayer) < 6.25D) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);
        }
    }
}