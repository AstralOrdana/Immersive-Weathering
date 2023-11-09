package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.reg.ModParticles;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.Util;
import net.minecraft.core.particles.SimpleParticleType;
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

    public FlowerCrownItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    //@Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
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
                    v.z + entity.getRandomZ(0.675D), 0, 0, 0);
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
            var name = stack.getHoverName().getString();
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
            var name = stack.getHoverName().getString();
            return SUPPORTERS_LIST.get(name.toLowerCase(Locale.ROOT));
        }
        return null;
    }

    //pair of item name, entity model texture location and item model index
    //KEEP THESE LOWERCASE
    private static final Map<String, SpecialType> SUPPORTERS_LIST = Util.make(() -> {
        Map<String, SpecialType> map = new HashMap<>();
        //dev and gift crowns
        map.put("ordana", new SpecialType("textures/models/armor/bee.png",
                0.10f, ModParticles.FLOWER_BEE));
        map.put("bee", new SpecialType("textures/models/armor/bee.png",
                0.10f, ModParticles.FLOWER_BEE));
        map.put("mehvahdjukaar", new SpecialType("textures/models/armor/jar.png",
                0.11f, ModParticles.FLOWER_JAR));
        map.put("jar", new SpecialType("textures/models/armor/jar.png",
                0.11f, ModParticles.FLOWER_JAR));
        map.put("bob", new SpecialType("textures/models/armor/bob.png",
                0.12f, ModParticles.FLOWER_BOB));
        map.put("bobisnotaperson", new SpecialType("textures/models/armor/bob.png",
                0.12f, ModParticles.FLOWER_BOB));
        /*
        put("key", new SpecialType("textures/models/armor/key.png",
                0.12f, ModParticles.FLOWER_KEY));
        put("keybounce", new SpecialType("textures/models/armor/key.png",
                0.12f, ModParticles.FLOWER_KEY));
        put("keyb0unce", new SpecialType("textures/models/armor/key.png",
                0.12f, ModParticles.FLOWER_KEY));
         */

        map.put("asexual", new SpecialType("textures/models/armor/ace.png",
                0.200f, ModParticles.FLOWER_ACE));
        map.put("ace", new SpecialType("textures/models/armor/ace.png",
                0.200f, ModParticles.FLOWER_ACE));
        map.put("aromantic", new SpecialType("textures/models/armor/aro.png",
                0.201f, ModParticles.FLOWER_ARO));
        map.put("aro", new SpecialType("textures/models/armor/aro.png",
                0.201f, ModParticles.FLOWER_ARO));
        map.put("bisexual", new SpecialType("textures/models/armor/bi.png",
                0.202f, ModParticles.FLOWER_BI));
        map.put("bi", new SpecialType("textures/models/armor/bi.png",
                0.202f, ModParticles.FLOWER_BI));
        map.put("non-binary", new SpecialType("textures/models/armor/enby.png",
                0.203f, ModParticles.FLOWER_ENBY));
        map.put("nb", new SpecialType("textures/models/armor/enby.png",
                0.203f, ModParticles.FLOWER_ENBY));
        map.put("enby", new SpecialType("textures/models/armor/enby.png",
                0.203f, ModParticles.FLOWER_ENBY));
        map.put("gay", new SpecialType("textures/models/armor/gay.png",
                0.204f, ModParticles.FLOWER_GAY));
        map.put("mlm", new SpecialType("textures/models/armor/gay.png",
                0.204f, ModParticles.FLOWER_GAY));
        map.put("lesbian", new SpecialType("textures/models/armor/lesbian.png",
                0.205f, ModParticles.FLOWER_LESBIAN));
        map.put("wlw", new SpecialType("textures/models/armor/lesbian.png",
                0.205f, ModParticles.FLOWER_LESBIAN));
        map.put("rainbow", new SpecialType("textures/models/armor/rainbow.png",
                0.206f, ModParticles.FLOWER_RAINBOW));
        map.put("pride", new SpecialType("textures/models/armor/rainbow.png",
                0.206f, ModParticles.FLOWER_RAINBOW));
        map.put("trans", new SpecialType("textures/models/armor/trans.png",
                0.207f, ModParticles.FLOWER_TRANS));
        map.put("transgender", new SpecialType("textures/models/armor/trans.png",
                0.207f, ModParticles.FLOWER_TRANS));
        map.put("pan", new SpecialType("textures/models/armor/pan.png",
                0.208f, ModParticles.FLOWER_PAN));
        map.put("pansexual", new SpecialType("textures/models/armor/pan.png",
                0.208f, ModParticles.FLOWER_PAN));
        map.put("intersex", new SpecialType("textures/models/armor/intersex.png",
                0.209f, ModParticles.FLOWER_INTERSEX));
        map.put("genderqueer", new SpecialType("textures/models/armor/genderqueer.png",
                0.210f, ModParticles.FLOWER_GENDERQUEER));
        map.put("genderfluid", new SpecialType("textures/models/armor/fluid.png",
                0.211f, ModParticles.FLOWER_FLUID));
        map.put("fluid", new SpecialType("textures/models/armor/fluid.png",
                0.211f, ModParticles.FLOWER_FLUID));

        //supporter crowns
        map.put("flax", new SpecialType("textures/models/armor/flax.png",
                0.30f, ModParticles.FLOWER_FLAX));
        map.put("neko", new SpecialType("textures/models/armor/nekomaster.png",
                0.31f, ModParticles.FLOWER_NEKOMASTER));
        map.put("nekomaster", new SpecialType("textures/models/armor/nekomaster.png",
                0.31f, ModParticles.FLOWER_NEKOMASTER));
        map.put("akashii", new SpecialType("textures/models/armor/akashii.png",
                0.32f, ModParticles.FLOWER_AKASHII));

        return map;
    });

    public record SpecialType(String textureLocation, float itemModelIndex,
                              Supplier<SimpleParticleType> particle) {
    }
}