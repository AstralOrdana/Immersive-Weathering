package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.reg.ModParticles;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    @PlatformOnly(PlatformOnly.FORGE)
    //@Override
    public void onArmorTick(ItemStack stack, Level level, Player player){
        if (level.isClientSide) {
            spawnParticles(stack, level, player, level);
        }
    }

    @PlatformOnly(PlatformOnly.FABRIC)
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.HEAD) == stack) {
                spawnParticles(stack, level, entity, level);
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    private static void spawnParticles(ItemStack stack, Level level, Entity entity, Level serverLevel) {
        if (level.random.nextFloat() < 0.05) {
            Vec3 v = entity.getViewVector(1).scale(entity.isSwimming() ? 1.8 : -0.15f);

            serverLevel.addParticle(getParticle(stack),
                    v.x + entity.getRandomX(0.675D),
                    v.y + entity.getY() + entity.getEyeHeight() + 0.15D,
                    v.z + entity.getRandomZ(0.675D),0,0,0);
        }
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
        if (type != null) return type.particle.get();
        return ModParticles.AZALEA_FLOWER.get();
    }

    public static float getItemTextureIndex(ItemStack stack) {
        var type = getSpecialType(stack);
        if (type != null) return type.itemModelIndex;
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
                0.10f, ModParticles.FLOWER_BEE));
        put("mehvahdjukaar", new SpecialType("textures/models/armor/jar.png",
                0.11f, ModParticles.FLOWER_JAR));
        put("jar", new SpecialType("textures/models/armor/jar.png",
                0.11f, ModParticles.FLOWER_JAR));

        //pride crowns
        put("asexual", new SpecialType("textures/models/armor/ace.png",
                0.20f, ModParticles.FLOWER_ACE));
        put("ace", new SpecialType("textures/models/armor/ace.png",
                0.20f, ModParticles.FLOWER_ACE));
        put("aromantic", new SpecialType("textures/models/armor/aro.png",
                0.21f, ModParticles.FLOWER_ARO));
        put("aro", new SpecialType("textures/models/armor/aro.png",
                0.21f, ModParticles.FLOWER_ARO));
        put("bisexual", new SpecialType("textures/models/armor/bi.png",
                0.22f, ModParticles.FLOWER_BI));
        put("bi", new SpecialType("textures/models/armor/bi.png",
                0.22f, ModParticles.FLOWER_BI));
        put("non-binary", new SpecialType("textures/models/armor/enby.png",
                0.23f, ModParticles.FLOWER_ENBY));
        put("nb", new SpecialType("textures/models/armor/enby.png",
                0.23f, ModParticles.FLOWER_ENBY));
        put("enby", new SpecialType("textures/models/armor/enby.png",
                0.23f, ModParticles.FLOWER_ENBY));
        put("gay", new SpecialType("textures/models/armor/gay.png",
                0.24f, ModParticles.FLOWER_GAY));
        put("mlm", new SpecialType("textures/models/armor/gay.png",
                0.24f, ModParticles.FLOWER_GAY));
        put("lesbian", new SpecialType("textures/models/armor/lesbian.png",
                0.25f, ModParticles.FLOWER_LESBIAN));
        put("wlw", new SpecialType("textures/models/armor/lesbian.png",
                0.25f, ModParticles.FLOWER_LESBIAN));
        put("rainbow", new SpecialType("textures/models/armor/rainbow.png",
                0.26f, ModParticles.FLOWER_RAINBOW));
        put("pride", new SpecialType("textures/models/armor/rainbow.png",
                0.26f, ModParticles.FLOWER_RAINBOW));
        put("trans", new SpecialType("textures/models/armor/trans.png",
                0.27f, ModParticles.FLOWER_TRANS));
        put("transgender", new SpecialType("textures/models/armor/trans.png",
                0.27f, ModParticles.FLOWER_TRANS));

        //supporter crowns
        put("flax", new SpecialType("textures/models/armor/flax.png",
                0.30f, ModParticles.FLOWER_FLAX));
    }};

    public record SpecialType(String textureLocation, float itemModelIndex,
                              Supplier<SimpleParticleType> particle) {
    }

    ;


}