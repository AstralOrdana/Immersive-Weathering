package com.ordana.immersive_weathering.items;

import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.reg.ModParticles;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
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
import java.util.function.Supplier;

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
                        serverLevel.sendParticles(            getParticle(stack),
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
            if (m != null) return m.textureLocation;
        }
        return null;
    }

    public static SimpleParticleType getParticle(ItemStack stack) {
        var type = getSpecialType(stack);
        if(type != null)return type.particle.get();
        return ModParticles.AZALEA_FLOWER.get();
    }

    public static float getItemTextureIndex(ItemStack stack) {
        var type = getSpecialType(stack);
        if(type != null)return type.itemModelIndex;
        return 0f;
    }


    @Nullable
    public static SpecialType getSpecialType(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            var name = stack.getHoverName().getContents();
            return SUPPORTERS_LIST.get(name.toLowerCase(Locale.ROOT));
        }
        return null;
    }

    //pair of item name, entity model texture location and item model index
    //KEEP THESE LOWERCASE
    private static final Map<String, SpecialType> SUPPORTERS_LIST = new HashMap<>() {{
        //dev and gift crowns
        put("ordana", new SpecialType("textures/models/armor/bee.png",
                0.10f, ModParticles.AZALEA_FLOWER));
        put("mehvahdjukaar", Pair.of("textures/models/armor/jar.png", 0.11f));

        //pride crowns
        put("asexual", Pair.of("textures/models/armor/ace.png", 0.20f));
        put("ace", Pair.of("textures/models/armor/ace.png", 0.20f));
        put("aromantic", Pair.of("textures/models/armor/aro.png", 0.21f));
        put("aro", Pair.of("textures/models/armor/aro.png", 0.21f));
        put("bisexual", Pair.of("textures/models/armor/bi.png", 0.22f));
        put("bi", Pair.of("textures/models/armor/bi.png", 0.22f));
        put("non-binary", Pair.of("textures/models/armor/enby.png", 0.23f));
        put("nb", Pair.of("textures/models/armor/enby.png", 0.23f));
        put("enby", Pair.of("textures/models/armor/enby.png", 0.23f));
        put("gay", Pair.of("textures/models/armor/gay.png", 0.24f));
        put("mlm", Pair.of("textures/models/armor/gay.png", 0.24f));
        put("lesbian", Pair.of("textures/models/armor/lesbian.png", 0.25f));
        put("wlw", Pair.of("textures/models/armor/lesbian.png", 0.25f));
        put("rainbow", Pair.of("textures/models/armor/rainbow.png", 0.26f));
        put("pride", Pair.of("textures/models/armor/rainbow.png", 0.26f));
        put("trans", Pair.of("textures/models/armor/trans.png", 0.27f));
        put("transgender", Pair.of("textures/models/armor/trans.png", 0.27f));

        //supporter crowns
        put("flax", Pair.of("textures/models/armor/flax.png", 0.30f));
    }};

    public record SpecialType(String textureLocation, float itemModelIndex,
                              Supplier<SimpleParticleType> particle){};


}