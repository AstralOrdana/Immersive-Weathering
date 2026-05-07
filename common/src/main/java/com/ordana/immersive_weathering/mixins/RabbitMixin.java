package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal {

    protected RabbitMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "lambda$registerGoals$0", at = @At("HEAD"), cancellable = true)
    private static void isTempting(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(ModBlocks.WEEDS.get().asItem())) {
            cir.setReturnValue(true);
        }
    }
}
