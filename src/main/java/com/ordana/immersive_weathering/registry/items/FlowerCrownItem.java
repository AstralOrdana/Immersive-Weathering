package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.registry.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class FlowerCrownItem extends ArmorItem {

    public FlowerCrownItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            if (world.random.nextFloat() < 0.2) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() == this) {


                        Vec3d v = entity.getRotationVector().multiply(-0.125f);
                        world.addParticle(ModParticles.AZALEA_FLOWER,
                                v.x + entity.getParticleX(0.675D),
                                v.y + entity.getY() + entity.getEyeHeight(EntityPose.STANDING) + 0.15D,
                                v.z + entity.getParticleZ(0.675D),
                                -3, -1, 0);
                    }
                }
            }
        } else if (world.random.nextFloat() < 0.1) {
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() == this) {
                    if (!livingEntity.hasStatusEffect(StatusEffects.HEALTH_BOOST)) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 250, 0, false, false, true));
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}