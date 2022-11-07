package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Supplier;

record NandTest(List<IPositionRuleTest> predicates) implements IPositionRuleTest {

    public static final String NAME = "nand";
    public static final Codec<NandTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IPositionRuleTest.CODEC.listOf().fieldOf("predicates").forGetter(NandTest::predicates)
    ).apply(instance, NandTest::new));

    static final Type<NandTest> TYPE =
            new Type<>(NandTest.CODEC, NandTest.NAME);

    @Override
    public Type<NandTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        for(var p : predicates){
            if(!p.test(biome,pos, level))return true;
        }
        return false;
    }
}
