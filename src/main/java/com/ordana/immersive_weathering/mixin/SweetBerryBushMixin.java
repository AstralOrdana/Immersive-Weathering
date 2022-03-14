package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushMixin {

    @Redirect(method = "onEntityCollision",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean onEntityCollision(Entity entity, DamageSource source, float amount) {
        if(entity instanceof PlayerEntity player && !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()){
            return false;
        }
        else{
            return entity.damage(source, amount);
        }
    }
}