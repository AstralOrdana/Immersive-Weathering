package com.ordana.immersive_weathering.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushMixin {

    @Redirect(method = "entityInside",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean removeBlock(Entity entity, DamageSource source, float amount) {
        if (entity instanceof Player player && !player.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
            return false;
        } else {
            return entity.hurt(source, amount);
        }
    }

}
