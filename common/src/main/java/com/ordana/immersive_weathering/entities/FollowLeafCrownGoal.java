package com.ordana.immersive_weathering.entities;

import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.crafting.Ingredient;

public class FollowLeafCrownGoal extends TemptGoal {

    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    private int calmDown;
    private final double speedModifier;

    public FollowLeafCrownGoal(PathfinderMob mob, double speed, boolean canScare) {
        super(mob, speed, Ingredient.of(ModItems.FLOWER_CROWN.get()), canScare);
        this.targetingConditions = TEMP_TARGETING.copy()
                .selector(e -> e.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.FLOWER_CROWN.get()));
        this.speedModifier = speed;
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
            return this.player != null;
        }
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
        if (this.mob.distanceToSqr(this.player) < 6.25D) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.player.getX(), this.player.getY() + 2, this.player.getZ(), this.speedModifier);
        }

    }
}