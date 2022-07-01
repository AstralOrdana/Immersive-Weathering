package com.ordana.immersive_weathering.block_growth.rute_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraft.world.level.material.Fluid;

import java.util.Random;

public class FluidMatchTest extends RuleTest {

    public static final Codec<FluidMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registry.FLUID_REGISTRY).fieldOf("fluids").forGetter(b -> b.fluids),
            Codec.FLOAT.optionalFieldOf("probability",1f).forGetter(b->b.probability)
    ).apply(instance, FluidMatchTest::new));


    public static final RuleTestType<FluidMatchTest> TYPE = RuleTestType.register("immersive_weathering:fluid_match",CODEC);

    private final HolderSet<Fluid> fluids;
    private final float probability;


    public FluidMatchTest(HolderSet<Fluid> fluids, Float chance) {
        this.fluids = fluids;
        this.probability = chance;
    }

    public boolean test(BlockState state, Random random) {
        return state.getFluidState().is(fluids) && random.nextFloat() < this.probability;
    }

    protected RuleTestType<FluidMatchTest> getType() {
        return TYPE;
    }
    //just need to load and static register will register
    public static void register() {}

}
