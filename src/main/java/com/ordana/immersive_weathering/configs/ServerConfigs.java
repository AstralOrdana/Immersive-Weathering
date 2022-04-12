package com.ordana.immersive_weathering.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfigs {

    public static ForgeConfigSpec SERVER_SPEC;


    public static ForgeConfigSpec.DoubleValue MOSS_INTEREST_FOR_FACE;
    public static ForgeConfigSpec.DoubleValue MOSS_DISJOINT_GROWTH;
    public static ForgeConfigSpec.DoubleValue MOSS_UN_WEATHERABLE_CHANCE;
    public static ForgeConfigSpec.BooleanValue MOSS_NEEDS_WATER;

    public static ForgeConfigSpec.DoubleValue CRACK_INTEREST_FOR_FACE;
    public static ForgeConfigSpec.DoubleValue CRACK_DISJOINT_GROWTH;
    public static ForgeConfigSpec.DoubleValue CRACK_UN_WEATHERABLE_CHANCE;
    public static ForgeConfigSpec.BooleanValue CRACK_NEEDS_WATER;

    public static ForgeConfigSpec.BooleanValue BARK_ENABLED;
    public static ForgeConfigSpec.BooleanValue LEAF_PILES_PATCHES;
    public static ForgeConfigSpec.BooleanValue ICICLES_PATCHES;
    public static ForgeConfigSpec.BooleanValue ICICLES_FALLING;
    public static ForgeConfigSpec.BooleanValue ICICLES_GENERATION;

    public static ForgeConfigSpec.DoubleValue MOSS_CLUMP_CHANCE;
    public static ForgeConfigSpec.BooleanValue ICICLE_FIRE_RESISTANCE;
    public static ForgeConfigSpec.BooleanValue ICICLE_FOOD;

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
        MOSS_NEEDS_WATER = builder.comment("If a block needs to be exposed to air to be able to weather. " +
                "Currently moss sources blocks ignore this check")
                        .define("needs_air",true);
        builder.pop();
        builder.push("cracked_blocks");
        CRACK_INTEREST_FOR_FACE = builder.comment("How likely each block face is to propagate its cracked state to its neighbor." +
                        "Set to 0 to completely disable cracking")
                .defineInRange("interest_for_face",0.3, 0,1);
        CRACK_DISJOINT_GROWTH = builder.comment("Determines how likely a crack patch is to spread in a non uniform manner allowing more distant blocks to be affected by eachother." +
                        "In more in depth terms this makes it so a block will be affected by neighbors with WEATHERING state set to true" +
                        "as opposed to only already cracked blocks or crack source blocks")
                .defineInRange("disjoint_growth_chance",0.5, 0,1);
        CRACK_UN_WEATHERABLE_CHANCE = builder.comment("Determines the percentage of blocks that will not be allowed to weather if not directly next to a moss source block." +
                        "The actual shape of a crack patch is influenced by all 3 of these values")
                .defineInRange("unWeatherable_chance",0.4,0,1);
        CRACK_NEEDS_WATER = builder.comment("If a block needs to be exposed to air to be able to weather. " +
                        "Currently crack sources blocks ignore this check")
                .define("needs_air",false);
        builder.pop();

        BARK_ENABLED =  builder.comment("Allows bark to be dropped after scraping off log blocks")
                .define("bark_enabled",true);

        builder.push("leaf_piles");
        LEAF_PILES_PATCHES = builder.comment("Enables leaf piles patches features to spawn in forests")
                        .define("leaf_piles_patches",true);
        builder.pop();

        builder.push("icicles");
        ICICLES_PATCHES = builder.comment("Enables icicle patches features to spawn in icy biomes and caves")
                .define("icicle_patches",true);
        ICICLES_FALLING = builder.comment("Allows icicles to fall when a lound sound is player nearby")
                        .define("fall_on_vibrations",true);
        ICICLES_GENERATION = builder.comment("Allows icicles to naturally generate on the underside of blocks when it snows")
                        .define("icicle_formation",true);
        builder.pop();

        builder.push("food");
        MOSS_CLUMP_CHANCE = builder.comment("Chance that a moss clump will give regeneration effect")
                        .defineInRange("moss_regeneration_chance", 0.3,0,1);
        ICICLE_FIRE_RESISTANCE = builder.comment("Eaten icicles will give a short fire resistance buff")
                        .define("icicle_fire_resistance",true);
        ICICLE_FOOD = builder.comment("Allows icicles to be eaten")
                .define("icicle_food",true);
        builder.pop();


        SERVER_SPEC = builder.build();
    }
}
