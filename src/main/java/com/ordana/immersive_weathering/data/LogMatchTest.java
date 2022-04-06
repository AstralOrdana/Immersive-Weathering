package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Random;

public class LogMatchTest extends RuleTest {

    public static final LogMatchTest INSTANCE = new LogMatchTest();

    public static final Codec<LogMatchTest> CODEC = Codec.unit(() -> INSTANCE);

    public static final RuleTestType<LogMatchTest> TYPE = RuleTestType.register("immersive_weathering:tree_log", CODEC);

    public boolean test(BlockState state, Random random) {
        return WeatheringHelper.isLog(state);
    }

    protected RuleTestType<LogMatchTest> getType() {
        return TYPE;
    }

    //just need to load and static init will register
    public static void init() {
    }

}
