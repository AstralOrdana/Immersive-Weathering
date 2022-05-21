package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

record PrecipitationTest(Biome.Precipitation precipitation) implements PositionRuleTest {

    public static final String NAME = "precipitation_test";
    public static final Codec<PrecipitationTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(PrecipitationTest::precipitation)
    ).apply(instance, PrecipitationTest::new));
    static final PositionRuleTestType<PrecipitationTest> TYPE = new PositionRuleTestType<>(PrecipitationTest.CODEC, PrecipitationTest.NAME);

    @Override
    public PositionRuleTestType<PrecipitationTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        return biome.value().getPrecipitation() == precipitation &&
                ((precipitation != Biome.Precipitation.NONE) == level.isRainingAt(pos));
    }
}
