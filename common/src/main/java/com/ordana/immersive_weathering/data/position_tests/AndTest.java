package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

record AndTest(List<IPositionRuleTest> predicates) implements IPositionRuleTest {

    public static final String NAME = "and";
    public static final Codec<AndTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IPositionRuleTest.CODEC.listOf().fieldOf("predicates").forGetter(AndTest::predicates)
    ).apply(instance, AndTest::new));

    static final Type<AndTest> TYPE =
            new Type<>(AndTest.CODEC, AndTest.NAME);

    @Override
    public Type<AndTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        for(var p : predicates){
            if(!p.test(biome,pos, level))return false;
        }
        return true;
    }
}
