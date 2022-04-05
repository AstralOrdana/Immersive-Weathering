package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.material.Fluid;

import java.util.Random;

public class FluidMatchTest extends RuleTest {

    public static final Codec<FluidMatchTest> CODEC = Registry.FLUID.byNameCodec().fieldOf("fluid")
            .xmap(FluidMatchTest::new, (fluidMatchTest) -> fluidMatchTest.fluid).codec();

    public static final RuleTestType<FluidMatchTest> TYPE = RuleTestType.register("immersive_weathering:fluid_match",CODEC);

    private final Fluid fluid;

    public FluidMatchTest(Fluid fluid) {
        this.fluid = fluid;
    }

    public boolean test(BlockState state, Random random) {
        return state.getFluidState().is(this.fluid);
    }

    protected RuleTestType<FluidMatchTest> getType() {
        return TYPE;
    }
    //just need to load and static init will register
    public static void init() {}

}
