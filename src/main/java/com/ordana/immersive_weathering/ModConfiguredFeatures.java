package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {

    public static ConfiguredFeature<?, ?> BONEMEAL_MOSSY_BLOCK;

    public static final RuleTest STONE = new BlockMatchRuleTest(Blocks.STONE);
    public static final RuleTest COBBLESTONE = new BlockMatchRuleTest(Blocks.COBBLESTONE);
    public static final RuleTest STONE_BRICKS = new BlockMatchRuleTest(Blocks.STONE_BRICKS);
    public static final RuleTest BRICKS = new BlockMatchRuleTest(Blocks.BRICKS);
    public static void register() {
        BONEMEAL_MOSSY_BLOCK = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(ImmersiveWeathering.MOD_ID, "bonemeal_mossy_block"),
                Feature.ORE.configure(new OreFeatureConfig(
                        List.of(OreFeatureConfig.createTarget(STONE, ModBlocks.MOSSY_STONE.getDefaultState()),
                                OreFeatureConfig.createTarget(COBBLESTONE, Blocks.MOSSY_COBBLESTONE.getDefaultState()),
                                OreFeatureConfig.createTarget(STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS.getDefaultState()),
                                OreFeatureConfig.createTarget(BRICKS, ModBlocks.MOSSY_BRICKS.getDefaultState())),
                        16, // The size of the vein. Do not do less than 3 or else it places nothing.
                        0f // % of exposed ore block will not generate if touching air.
                )));

    }
}
