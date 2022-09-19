package com.ordana.immersive_weathering.items;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.reg.ModParticles;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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


    @Nullable
    @PlatformOnly(PlatformOnly.FORGE)
    //@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return getModelTexture(stack);
    }

    @Nullable
    public static String getModelTexture(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            var name = stack.getHoverName().getContents();
            var m = SUPPORTERS_LIST.get(name.toLowerCase(Locale.ROOT));
            if (m != null) return m.getFirst();
        }
        return null;
    }


    //pair of item name, entity model texture location and item model index
    //KEEP THESE LOWERCASE
    private static final Map<String, Pair<String, Integer>> SUPPORTERS_LIST = new HashMap<>() {{
        put("ordana", Pair.of("textures/models/armor/bee.png", 1));
        put("mehvahdjukaar", Pair.of("gib me a texture pls", 2));
        put("asexual", Pair.of("textures/models/armor/ace.png", 3));
        put("aromantic", Pair.of("textures/models/armor/aro.png", 4));
        put("bisexual", Pair.of("textures/models/armor/bi.png", 5));
        put("non-binary", Pair.of("textures/models/armor/enby.png", 6));
        put("gay", Pair.of("textures/models/armor/gay.png", 7));
        put("lesbian", Pair.of("textures/models/armor/lesbian.png", 8));
        put("rainbow", Pair.of("textures/models/armor/rainbow.png", 9));
        put("pride", Pair.of("textures/models/armor/rainbow.png", 9));
        put("trans", Pair.of("textures/models/armor/trans.png", 10));
        put("flax", Pair.of("textures/models/armor/flax.png", 11));
    }};

    public static int getItemTextureIndex(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            var name = stack.getHoverName().getContents();
            var m = SUPPORTERS_LIST.get(name);
            if (m != null) return m.getSecond();
        }
        return 0;
    }
}