package com.ordana.immersive_weathering.block_growth.rute_test;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Random;

public class LogMatchTest extends RuleTest {

    public static final LogMatchTest INSTANCE = new LogMatchTest();

    public static final Codec<LogMatchTest> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    public boolean test(BlockState state, Random random) {
        return WeatheringHelper.isLog(state);
    }

    @Override
    protected RuleTestType<LogMatchTest> getType() {
        return ModRuleTests.LOG_TEST.get();
    }

}
