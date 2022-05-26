package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
    public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World level) {
        return switch (precipitation) {
            case NONE -> Arrays.stream(Direction.values()).anyMatch(d ->
                    !level.hasRain(pos.offset(d)));
            case SNOW -> Arrays.stream(Direction.values()).anyMatch(d ->
                    level.hasRain(pos.offset(d)) && biome.value().isCold(pos.offset(d)));
            case RAIN -> Arrays.stream(Direction.values()).anyMatch(d ->
                    level.hasRain(pos.offset(d)) && biome.value().doesNotSnow(pos.offset(d)));
        };
    }
}