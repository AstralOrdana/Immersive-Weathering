package com.ordana.immersive_weathering.items.materials;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public final class FlowerCrownMaterial {

    public static final Holder<ArmorMaterial> INSTANCE = Holder.direct(new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
                defense.put(ArmorItem.Type.BOOTS, 0);
                defense.put(ArmorItem.Type.LEGGINGS, 0);
                defense.put(ArmorItem.Type.CHESTPLATE, 0);
                defense.put(ArmorItem.Type.HELMET, 0);
                defense.put(ArmorItem.Type.BODY, 0);
            }),
            64,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(ModItems.AZALEA_FLOWERS.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace("flower"))),
            0.0F,
            0.0F
    ));

    private FlowerCrownMaterial() {
    }
}
