package com.ordana.immersive_weathering.reg;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.rute_test.*;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.function.Supplier;

public class ModRuleTests {


    public static void init() {
    }

    public static final Supplier<RuleTestType<BlockPropertyTest>> BLOCK_PROPERTY_TEST = register(
            "block_property_test", BlockPropertyTest.CODEC);
    public static final Supplier<RuleTestType<BlockSetMatchTest>> BLOCK_SET_MATCH_TEST = register(
            "block_set_match", BlockSetMatchTest.CODEC);
    public static final Supplier<RuleTestType<BurnableTest>> BURNABLE_TEST = register(
            "burnable_test", BurnableTest.CODEC);
    public static final Supplier<RuleTestType<FluidMatchTest>> FLUID_MATCH_TEST = register(
            "fluid_match", FluidMatchTest.CODEC);
    public static final Supplier<RuleTestType<LogMatchTest>> LOG_TEST = register(
            "tree_log", LogMatchTest.CODEC);


    private static <P extends RuleTest> Supplier<RuleTestType<P>> register(String name, Codec<P> codec) {
        return RegHelper.register(ImmersiveWeathering.res(name), () -> () -> codec, Registry.RULE_TEST);
    }
}
