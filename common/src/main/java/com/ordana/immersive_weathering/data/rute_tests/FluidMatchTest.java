package com.ordana.immersive_weathering.data.rute_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import com.ordana.immersive_weathering.util.StrOpt;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.material.Fluid;

public class FluidMatchTest extends RuleTest {

    public static final Codec<FluidMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.FLUID).fieldOf("fluids").forGetter(b -> b.fluids),
            StrOpt.of(Codec.FLOAT,"probability",1f).forGetter(b->b.probability)
    ).apply(instance, FluidMatchTest::new));

    private final HolderSet<Fluid> fluids;
    private final float probability;


    public FluidMatchTest(HolderSet<Fluid> fluids, Float chance) {
        this.fluids = fluids;
        this.probability = chance;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return state.getFluidState().is(fluids) && random.nextFloat() < this.probability;
    }

    @Override
    protected RuleTestType<FluidMatchTest> getType() {
        return ModRuleTests.FLUID_MATCH_TEST.get();
    }

}
