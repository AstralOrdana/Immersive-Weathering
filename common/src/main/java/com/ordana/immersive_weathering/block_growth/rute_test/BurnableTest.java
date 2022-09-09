package com.ordana.immersive_weathering.block_growth.rute_test;

import com.mojang.serialization.Codec;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Random;

public class BurnableTest extends RuleTest {

    private static final BurnableTest INSTANCE = new BurnableTest();

    public static final Codec<BurnableTest> CODEC = Codec.unit(() -> INSTANCE);

    public static final RuleTestType<BurnableTest> TYPE = RuleTestType.register("immersive_weathering:burnable_test", CODEC);

    public boolean test(BlockState state, Random random) {
        //hack since we don't have world and pos. hopefully mods aren't using those lol
        try {
            return PlatformHelper.getFlammability(state, null, null, Direction.UP) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    protected RuleTestType<BurnableTest> getType() {
        return TYPE;
    }

    //just need to load and static register will register
    public static void register() {
    }

}
