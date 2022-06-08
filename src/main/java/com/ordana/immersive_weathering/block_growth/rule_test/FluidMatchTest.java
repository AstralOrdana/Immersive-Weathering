package com.ordana.immersive_weathering.block_growth.rule_test;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.RuleTestType;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class FluidMatchTest extends RuleTest {

    public static final Codec<FluidMatchTest> CODEC = Registry.FLUID.getCodec().fieldOf("fluid")
            .xmap(FluidMatchTest::new, (fluidMatchTest) -> fluidMatchTest.fluid).codec();

    public static final RuleTestType<FluidMatchTest> TYPE = RuleTestType.register("immersive_weathering:fluid_match",CODEC);

    private final Fluid fluid;

    public FluidMatchTest(Fluid fluid) {
        this.fluid = fluid;
    }

    public boolean test(BlockState state, net.minecraft.util.math.random.Random random) {
        return state.getFluidState().isOf(this.fluid);
    }

    protected RuleTestType<FluidMatchTest> getType() {
        return TYPE;
    }
    //just need to load and static init will register
    public static void init() {}

}