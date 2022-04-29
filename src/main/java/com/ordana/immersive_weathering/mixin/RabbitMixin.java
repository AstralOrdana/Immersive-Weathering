package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal {

    protected RabbitMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Redirect(method = "registerGoals", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Ingredient;of([Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/crafting/Ingredient;"))
    private Ingredient isTempting(ItemLike[] itemLikes) {
        var list = new ArrayList<>(List.of(itemLikes));
        list.add(ModBlocks.WEEDS.get());
        return Ingredient.of(list.toArray(new ItemLike[itemLikes.length]));
    }
}