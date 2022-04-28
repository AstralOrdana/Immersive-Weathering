package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.entities.WeedEatGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntity.class)
public abstract class RabbitMixin extends AnimalEntity {

    protected RabbitMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    protected void initGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new TemptGoal(this, 1.0D, Ingredient.ofItems(ModBlocks.WEEDS), false));
        this.rabbitGoalHelper(this);
    }

    @Inject(method = "isTempting", at = @At("HEAD"), cancellable = true)
    private static void isTempting(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(Items.CARROT) || stack.isOf(Items.GOLDEN_CARROT) || stack.isOf(Blocks.DANDELION.asItem()) || stack.isOf(ModBlocks.WEEDS.asItem())) {
            cir.setReturnValue(true);
        }
    }

    private void rabbitGoalHelper(AnimalEntity animal){
        this.goalSelector.add(5, new WeedEatGoal(animal));
    }

    @Nullable
    @Shadow
    public abstract PassiveEntity createChild(ServerWorld world, PassiveEntity entity);
}
