package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.reg.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FlowerCrownItem extends ArmorItem {

    public FlowerCrownItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level instanceof ServerLevel serverLevel) {
            if (level.random.nextFloat() < 0.05) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == this) {


                        Vec3 v = entity.getViewVector(1).scale(entity.isSwimming() ? 1.8 : -0.15f);

                        //TODO: fix particles
                        serverLevel.sendParticles(ModParticles.AZALEA_FLOWER.get(),
                                v.x + entity.getRandomX(0.675D),
                                v.y + entity.getY() + entity.getEyeHeight() + 0.15D,
                                v.z + entity.getRandomZ(0.675D),
                                1,
                                0, 0, 0, 0);
                    }
                }
            }
        }/* else if (level.random.nextFloat() < 0.001) {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() == this) {
                    if (!livingEntity.hasEffect(MobEffects.REGENERATION)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false, false));
                    }
                }
            }
        }*/
        super.inventoryTick(stack, level, entity, slot, selected);
    }
}