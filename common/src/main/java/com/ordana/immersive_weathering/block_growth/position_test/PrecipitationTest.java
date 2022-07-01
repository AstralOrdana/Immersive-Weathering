package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;

record PrecipitationTest(Biome.Precipitation precipitation) implements PositionRuleTest {

    public static final String NAME = "precipitation_test";
    public static final Codec<PrecipitationTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(PrecipitationTest::precipitation)
    ).apply(instance, PrecipitationTest::new));

    static final PositionRuleTestType<PrecipitationTest> TYPE =
            new PositionRuleTestType<>(PrecipitationTest.CODEC, PrecipitationTest.NAME);

    @Override
    public PositionRuleTestType<PrecipitationTest> getType() {
        return TYPE;
    }

    //tests if the condition is true in any of the neighboring blocks
    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        return switch (precipitation) {
            case NONE -> Arrays.stream(Direction.values()).anyMatch(d ->
                    !level.isRainingAt(pos.relative(d)));
            case SNOW -> Arrays.stream(Direction.values()).anyMatch(d ->
                    level.isRainingAt(pos.relative(d)) && biome.value().coldEnoughToSnow(pos.relative(d)));
            case RAIN -> Arrays.stream(Direction.values()).anyMatch(d ->
                    level.isRainingAt(pos.relative(d)) && biome.value().warmEnoughToRain(pos.relative(d)));
        };
    }
}
