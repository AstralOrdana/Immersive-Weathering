package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public class FlowerCrownItem extends ArmorItem {

    public FlowerCrownItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world instanceof ServerWorld serverWorld) {
            if (world.random.nextFloat() < 0.2) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.getEquippedStack(EquipmentSlot.HEAD).getItem() == this) {
                        Vec3d v = entity.getRotationVector().multiply(entity.isSwimming() ? 1.5 : -0.125f);
                        serverWorld.spawnParticles(ModParticles.AZALEA_FLOWER,
                                v.x + entity.getParticleX(0.675D),
                                v.y + entity.getY() + entity.getStandingEyeHeight() + 0.15D,
                                v.z + entity.getParticleZ(0.675D),
                                1,
                                0, 0, 0,0);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}