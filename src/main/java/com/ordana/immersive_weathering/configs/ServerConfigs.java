package com.ordana.immersive_weathering.configs;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ServerConfigs {

    public static ForgeConfigSpec SERVER_SPEC;


    public static ForgeConfigSpec.DoubleValue MOSS_INTEREST_FOR_FACE;
    public static ForgeConfigSpec.DoubleValue MOSS_DISJOINT_GROWTH;
    public static ForgeConfigSpec.DoubleValue MOSS_UN_WEATHERABLE_CHANCE;
    public static ForgeConfigSpec.BooleanValue MOSS_NEEDS_AIR;

    public static ForgeConfigSpec.DoubleValue CRACK_INTEREST_FOR_FACE;
    public static ForgeConfigSpec.DoubleValue CRACK_DISJOINT_GROWTH;
    public static ForgeConfigSpec.DoubleValue CRACK_UN_WEATHERABLE_CHANCE;
    public static ForgeConfigSpec.BooleanValue CRACK_NEEDS_AIR;

    public static ForgeConfigSpec.BooleanValue BARK_ENABLED;
    public static ForgeConfigSpec.BooleanValue LEAF_PILES_PATCHES;
    public static ForgeConfigSpec.IntValue MAX_LEAF_PILE_HEIGHT;
    public static ForgeConfigSpec.IntValue LEAF_PILE_REACH;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> LEAF_PILES_BLACKLIST;

    public static ForgeConfigSpec.BooleanValue ICICLES_PATCHES;
    public static ForgeConfigSpec.BooleanValue ICICLES_FALLING;
    public static ForgeConfigSpec.IntValue ICICLES_GENERATION_RARITY;


    public static ForgeConfigSpec.DoubleValue MOSS_CLUMP_CHANCE;
    public static ForgeConfigSpec.BooleanValue ICICLE_FIRE_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue ICICLE_FOOD;

    public static ForgeConfigSpec.BooleanValue CRACK_REQUIRES_SHIFTING;

    public static ForgeConfigSpec.DoubleValue HUMUS_SPAWN_BELOW_LEAVES;
    public static ForgeConfigSpec.DoubleValue FALLING_LEAVES;

    public static ForgeConfigSpec.BooleanValue VITRIFIED_LIGHTNING;
    public static ForgeConfigSpec.BooleanValue VITRIFIED_LAVA;

    public static ForgeConfigSpec.BooleanValue HUMUS_PATCHES;



    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("mossy_blocks");
        MOSS_INTEREST_FOR_FACE = builder.comment("How likely each block face is to propagate its mossy state to its neighbor." +
                        "Set to 0 to completely disable mossification")
                .defineInRange("interest_for_face",0.3, 0,1);
        MOSS_DISJOINT_GROWTH = builder.comment("Determines how likely a moss patch is to spread in a non uniform manner allowing more distant blocks to be affected by eachother." +
                        "In more in depth terms this makes it so a block will be affected by neighbors with WEATHERING state set to true" +
                        "as opposed to only already mossy blocks or moss source blocks")
                .defineInRange("disjoint_growth_chance",0.5, 0,1);
        MOSS_UN_WEATHERABLE_CHANCE = builder.comment("Determines the percentage of blocks that will not be allowed to weather if not directly next to a moss source block." +
                "The actual shape of a moss patch is influenced by all 3 of these values")
                .defineInRange("un_weatherable_chance",0.4,0,1);
        MOSS_NEEDS_AIR = builder.comment("If a block needs to be exposed to air to be able to weather. " +
                "Currently moss sources blocks ignore this check")
                        .define("needs_air",true);
        builder.pop();
        builder.push("cracked_blocks");
        CRACK_INTEREST_FOR_FACE = builder.comment("How likely each block face is to propagate its cracked state to its neighbor." +
                        "Set to 0 to completely disable cracking")
                .defineInRange("interest_for_face",0.6, 0,1);
        CRACK_DISJOINT_GROWTH = builder.comment("Determines how likely a crack patch is to spread in a non uniform manner allowing more distant blocks to be affected by eachother." +
                        "In more in depth terms this makes it so a block will be affected by neighbors with WEATHERING state set to true" +
                        "as opposed to only already cracked blocks or crack source blocks")
                .defineInRange("disjoint_growth_chance",0.4, 0,1);
        CRACK_UN_WEATHERABLE_CHANCE = builder.comment("Determines the percentage of blocks that will not be allowed to weather if not directly next to a moss source block." +
                        "The actual shape of a crack patch is influenced by all 3 of these values")
                .defineInRange("unWeatherable_chance",0.5,0,1);
        CRACK_NEEDS_AIR = builder.comment("If a block needs to be exposed to air to be able to weather. " +
                        "Currently crack sources blocks ignore this check")
                .define("needs_air",false);
        CRACK_REQUIRES_SHIFTING = builder.comment("If crating a cracked block by clicking on it with a pickaxe requires shifting or not")
                        .define("pickaxe_cracking_requires_shifting", false);
        builder.pop();

        BARK_ENABLED =  builder.comment("Allows bark to be dropped after scraping off log blocks")
                .define("bark_enabled",true);


        builder.push("icicles");
        ICICLES_PATCHES = builder.comment("Enables icicle patches features to spawn in icy biomes and caves")
                .define("icicle_patches",true);
        ICICLES_FALLING = builder.comment("Allows icicles to fall when a loud sound is player nearby")
                        .define("fall_on_vibrations",true);
        ICICLES_GENERATION_RARITY = builder.comment("Allows icicles to naturally generate on the underside of blocks when it snows." +
                        "Determines the how many blocks on average an icicle can generate in. The higher the rarer. Set to 1001 to disable entirely")
                        .defineInRange("icicle_formation",12,1,1001);
        builder.pop();

        builder.push("food").comment("some of these arent working yet");
        MOSS_CLUMP_CHANCE = builder.comment("Chance that a moss clump will give regeneration effect")
                        .defineInRange("moss_regeneration_chance", 0.3,0,1);
        ICICLE_FIRE_RESISTANCE = builder.comment("Eaten icicles will give a short fire resistance buff")
                        .define("icicle_fire_resistance",true);
        ICICLE_FOOD = builder.comment("Allows icicles to be eaten")
                .define("icicle_food",true);
        ICICLE_FOOD = builder.comment("Allows moss clump to be eaten")
                .define("moss_food",true);
        builder.pop();

        builder.push("leaf_piles");
        LEAF_PILES_PATCHES = builder.comment("Enables leaf piles patches features to spawn in forests")
                .define("leaf_piles_patches",true);
        HUMUS_SPAWN_BELOW_LEAVES = builder.comment("Allows natural humus or podzol generation below leaf piles with more than 1 layer")
                        .defineInRange("humus_and_podzol_spawn_chance",0.02,0, 1);
        FALLING_LEAVES = builder.comment("Chance for leaf piles to spawn below leaves")
                .defineInRange("fallen_leaves_chance",0.03,0, 1);
        MAX_LEAF_PILE_HEIGHT = builder.comment("Maximum height that leaf piles can naturally pile up to." +
                "Refers to the previously defined falling leaves feature")
                        .defineInRange("fallen_leaves_height",3,1,8);
        LEAF_PILE_REACH = builder.comment("Maximum height at which a leaf block can generate a leaf pile below")
                .defineInRange("fallen_leaves_reach",12,1,256);

        LEAF_PILES_BLACKLIST = builder.comment("Leaves Block ids that should not spawn from leaves (i.e: minecraftoak_leaves)")
                .defineList("mod_blacklist",
                        List.of("ars_nouveau:purple_archwood_leaves",
                                "ars_nouveau:blue_archwood_leaves",
                                "ars_nouveau:green_archwood_leaves",
                                "ars_nouveau:red_archwood_leaves"), o -> o instanceof String);
        builder.pop();

        builder.push("vitrified_sand");
        VITRIFIED_LIGHTNING = builder.comment("Allows lightning to create vitrified sand")
                .define("from_lightning", true);
        VITRIFIED_LAVA = builder.comment("Allows lava to create vitrified sand")
                .define("from_lava", true);
        builder.pop();

        builder.push("soil_blocks");
        HUMUS_PATCHES = builder.comment("Enables leaf piles patches features to spawn in dark oak forests")
                .define("humus_patches",true);
        builder.pop();

        SERVER_SPEC = builder.build();
    }
}
