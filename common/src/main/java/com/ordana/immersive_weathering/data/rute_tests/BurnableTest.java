package com.ordana.immersive_weathering.data.rute_tests;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class BurnableTest extends RuleTest {

    private static final BurnableTest INSTANCE = new BurnableTest();

    public static final Codec<BurnableTest> CODEC = Codec.unit(() -> INSTANCE);

    public boolean test(BlockState state, RandomSource random) {
        //hack since we don't have world and pos. hopefully mods aren't using those lol
        try {
            return PlatformHelper.getFlammability(state, null, null, Direction.UP) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    protected RuleTestType<BurnableTest> getType() {
        return ModRuleTests.BURNABLE_TEST.get();
    }

}
