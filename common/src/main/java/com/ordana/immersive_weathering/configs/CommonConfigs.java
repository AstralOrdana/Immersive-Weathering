package com.ordana.immersive_weathering.configs;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class CommonConfigs {


    public static ConfigSpec SERVER_SPEC;

    public static Supplier<Boolean> BLOCK_GROWTHS;

    public static Supplier<Double> MOSS_INTERESTS_FOR_FACE;
    public static Supplier<Double> MOSS_PATCHINESS;
    public static Supplier<Double> MOSS_IMMUNE_CHANCE;
    public static Supplier<Boolean> MOSS_NEEDS_AIR;

    public static Supplier<Double> CRACK_INTERESTS_FOR_FACE;
    public static Supplier<Double> CRACK_PATCHINESS;
    public static Supplier<Double> CRACK_IMMUNE_CHANCE;
    public static Supplier<Boolean> CRACK_NEEDS_AIR;

    public static Supplier<Boolean> FALLING_ICICLES;
    public static Supplier<Integer> ICICLE_RARITY;

    public static Supplier<Integer> FREEZING_WATER_SEVERITY;
    public static Supplier<Integer> FREEZING_ICICLE_SEVERITY;
    public static Supplier<Integer> FREEZING_PERMAFROST_SEVERITY;

    public static Supplier<Double> FIRE_CHARS_WOOD_CHANCE;
    public static Supplier<Double> ASH_SPAWNS_CHANCE;
    public static Supplier<Boolean> SOOT_SPAWN;
    public static Supplier<Boolean> FLAMMABLE_COBWEBS;


    public static Supplier<Boolean> CAULDRON_WASHING;
    public static Supplier<Boolean> PICKAXE_CRACKING;
    public static Supplier<Boolean> PICKAXE_CRACKING_SHIFT;
    public static Supplier<Boolean> BRICK_REPAIRING;
    public static Supplier<Boolean> PISTON_SLIMING;
    public static Supplier<Boolean> SOIL_SHEARING;
    public static Supplier<Boolean> AZALEA_SHEARING;
    public static Supplier<Boolean> MOSS_SHEARING;
    public static Supplier<Boolean> MOSS_BURNING;
    public static Supplier<Boolean> CHARRED_BLOCK_IGNITING;
    public static Supplier<Boolean> SHOVEL_EXTINGUISH;
    public static Supplier<Boolean> SPONGE_RUSTING;
    public static Supplier<Boolean> AXE_STRIPPING;
    public static Supplier<Boolean> AXE_SCRAPING;


    public static Supplier<Boolean> CRYOSOL_FEATURE;
    public static Supplier<Boolean> HUMUS_FEATURE;
    public static Supplier<Boolean> FLUVISOL_FEATURE;
    public static Supplier<Boolean> SILT_FEATURE;
    public static Supplier<Boolean> VERITOSOL_FEATURE;
    public static Supplier<Boolean> LAKEBED_FEATURE;
    public static Supplier<Boolean> ICICLE_FEATURE;
    public static Supplier<Boolean> ROOTS_FEATURE;


    public static Supplier<Boolean> ICICLE_FOOD;
    public static Supplier<Boolean> ICICLE_FIRE_RESISTANCE;
    public static Supplier<Boolean> MUDDY_WATER_ENABLED;

    public static Supplier<Boolean> COMPOSTER_DIRT;
    public static Supplier<Boolean> FEATHER_FALLING_FARMERS;
    public static Supplier<Boolean> LEGGINGS_PREVENTS_THORN_DAMAGE;
    public static Supplier<String> GENERIC_BARK;

    public static Supplier<Double> LEAF_PILES_FROM_DECAY_CHANCE;
    public static Supplier<Boolean> LEAF_DECAY_SOUND;
    public static Supplier<Double> LEAF_PILES_CHANCE;
    public static Supplier<Integer> LEAF_PILE_MAX_HEIGHT;
    public static Supplier<Integer> LEAF_PILES_REACH;

    public static Supplier<Boolean> THIN_ICE_MELTING;

    public static Supplier<Boolean> VITRIFIED_LIGHTNING;

    public static Supplier<Boolean> RUSTING;

    public static Supplier<Boolean> MULCH_GROWS_CROPS;

    public static void init() {
        ConfigBuilder builder = ConfigBuilder.create(ImmersiveWeathering.res("common"), ConfigType.COMMON);

        builder.setSynced();

        builder.push("general");
        BLOCK_GROWTHS = builder.define("block_growths", true);
        builder.pop();

        builder.push("mossy_blocks");
        MOSS_INTERESTS_FOR_FACE = builder.define("interest_for_face", 0.3, 0, 1d);
        MOSS_PATCHINESS = builder.define("patchiness", 0.5, 0, 1);
        MOSS_IMMUNE_CHANCE = builder.define("immune_chance", 0.4, 0, 1);
        MOSS_NEEDS_AIR = builder.define("needs_air", true);
        builder.pop();

        builder.push("cracked_blocks");
        CRACK_INTERESTS_FOR_FACE = builder.define("interest_for_face", 0.6, 0, 1d);
        CRACK_PATCHINESS = builder.define("patchiness", 0.4, 0, 1);
        CRACK_IMMUNE_CHANCE = builder.define("immune_chance", 0.4, 0, 1);
        CRACK_NEEDS_AIR = builder.define("needs_air", false);
        builder.pop();

        builder.push("icicle");
        FALLING_ICICLES = builder.define("react_to_vibrations", true);
        ICICLE_RARITY = builder.define("spawn_rarity", 12, 1, 1001); //1001 to disable
        builder.pop();

        builder.push("freezing");
        //all these are disabled when at 0 of course
        FREEZING_WATER_SEVERITY = builder.define("water_severity", 200, 0, 1000);
        FREEZING_ICICLE_SEVERITY = builder.define("icicle", 300, 0, 1000);
        FREEZING_PERMAFROST_SEVERITY = builder.define("permafrost", 300, 0, 1000);
        builder.pop();

        builder.push("charring");
        FIRE_CHARS_WOOD_CHANCE = builder.define("fire_chars_wood", 0.3, 0, 1);
        ASH_SPAWNS_CHANCE = builder.define("ash_spawn", 0.3, 0, 1); //gets aplied after fire charring
        SOOT_SPAWN = builder.define("soot_spawn", true);
        FLAMMABLE_COBWEBS = builder.define("flammable_cobweb", true);
        builder.pop();

        builder.push("generation");
        CRYOSOL_FEATURE = builder.define("cryosol_feature", true);
        HUMUS_FEATURE = builder.define("humus_feature", true);
        FLUVISOL_FEATURE = builder.define("fluvisol_feature", true);
        SILT_FEATURE = builder.define("silt_feature", true);
        VERITOSOL_FEATURE = builder.define("vertisol_feature", true);
        LAKEBED_FEATURE = builder.define("lakebed_feature", true);
        ICICLE_FEATURE = builder.define("icicle_feature", true);
        ROOTS_FEATURE = builder.define("roots_feature", true);
        builder.pop();


        builder.push("item_interaction");
        //TODO: use cauldron registry
        CAULDRON_WASHING = builder.define("cauldron_washing", false);
        PICKAXE_CRACKING = builder.define("pickaxe_cracking", true);
        PICKAXE_CRACKING_SHIFT = builder.define("pickaxe_cracking_shift", false);
        BRICK_REPAIRING = builder.define("brick_breaking", true);
        PISTON_SLIMING = builder.define("piston_sliming", true);
        SOIL_SHEARING = builder.define("soil_shearing", true);
        AZALEA_SHEARING = builder.define("azalea_shearing", true);
        MOSS_SHEARING = builder.define("moss_shearing", true);
        MOSS_BURNING = builder.define("moss_burning", true);
        CHARRED_BLOCK_IGNITING = builder.define("charred_block_igniting", true);
        SHOVEL_EXTINGUISH = builder.define("shovel_extinguish", true);
        SPONGE_RUSTING = builder.define("sponge_rusting", true);
        AXE_STRIPPING = builder.define("axe_stripping", true);
        AXE_SCRAPING = builder.define("axe_rusting", true);
        builder.pop();

        builder.push("food");
        ICICLE_FOOD = builder.define("icicle_food", true);
        ICICLE_FIRE_RESISTANCE = builder.define("icicle_fire_resistance", false);
        MUDDY_WATER_ENABLED = builder.define("muddy_water_enabled", true);
        builder.pop();

        builder.push("misc");
        MULCH_GROWS_CROPS = builder.define("mulch_grows_crops", true);
        COMPOSTER_DIRT = builder.define("composter_dirt", true);
        GENERIC_BARK = builder.define("generic_bark", "");
        FEATHER_FALLING_FARMERS = builder.define("feather_falling_farmers", true);
        LEGGINGS_PREVENTS_THORN_DAMAGE = builder.define("leggings_prevents_thorn_damage", true);
        builder.pop();

        builder.push("leaf_piles");
        LEAF_PILES_FROM_DECAY_CHANCE = builder.define("spawn_entity_from_decay", 0.3, 0, 1);
        LEAF_DECAY_SOUND = builder.define("decay_sound", true);

        LEAF_PILES_CHANCE = builder.define("leaf_piles_spawn_chance", 0.005, 0, 1);
        LEAF_PILES_REACH = builder.define("reach", 12, 1, 256);
        LEAF_PILE_MAX_HEIGHT = builder.define("max_pile_height", 3, 1, 8);
        builder.pop();

        builder.push("thin_ice");
        THIN_ICE_MELTING = builder.define("natural_melting", true);
        builder.pop();

        builder.push("lightning_growths"); //TODO:move to data
        VITRIFIED_LIGHTNING = builder.define("vitrified_lightning", true);
        builder.pop();

        builder.push("rusting");
        RUSTING = builder.define("rusting", true);
        builder.pop();

        //fabric specific
        PlatformHelper.getPlatform().ifFabric(() -> {


        });


       SERVER_SPEC =  builder.buildAndRegister();
    }


    //stuff belows represents the configs that need to be added


    //moss


    // todo: LEAF_PILES_BLACKLIST tag


    //leaf piles


    public static LeafPileMode fallenLeafPiles() {
        throw new AssertionError();
    }

    public enum LeafPileMode {
        LEAF_LAYER, SIMPLE, OFF
    }


    //TODO: fix campfire soot onto non burnable
    //fire stuff


    public static boolean vitrifiedSand() {
        throw new AssertionError();
    }


    public static boolean fulgurite() {
        throw new AssertionError();
    }

    //frost


    public static boolean naturalIceMelt() { //?
        throw new AssertionError();
    }

    public static boolean iciclePlacement() { //?
        throw new AssertionError();
    }

    //food

    //these will be data driven
    public boolean lightningCreateMagma = true; //bg
    public boolean lightningCreateLava = true; //bg
    public boolean lightningCreateVitrifiedSand = true;


    public static boolean VITRIFIED_LAVA;

    //here are forge old config values. only here for their old descriptionKey


}
