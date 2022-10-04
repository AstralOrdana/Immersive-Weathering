package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import net.minecraft.util.RandomSource;

record HasAroundTest(PositionRuleTest predicate) implements PositionRuleTest {

    public static final String NAME = "has_around";

    public static final Codec<HasAroundTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PositionRuleTest.CODEC.fieldOf("predicate").forGetter(HasAroundTest::predicate)
    ).apply(instance, HasAroundTest::new));

    static final Type<HasAroundTest> TYPE =
            new Type<>(HasAroundTest.CODEC, HasAroundTest.NAME);

    @Override
    public Type<HasAroundTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        for(var d : Direction.values()){
            if(!predicate.test(biome, pos.relative(d),  level))return false;
        }
        return true;
    }

}
