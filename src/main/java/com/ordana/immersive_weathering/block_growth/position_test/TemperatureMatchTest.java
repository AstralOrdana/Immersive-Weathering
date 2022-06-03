package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

record TemperatureMatchTest(float max, float min, Optional<Boolean> useLocalPos) implements PositionRuleTest {


    public static final String NAME = "temperature_range";
    public static final Codec<TemperatureMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("max").forGetter(TemperatureMatchTest::max),
            Codec.FLOAT.fieldOf("min").forGetter(TemperatureMatchTest::min),
            Codec.BOOL.optionalFieldOf("use_local_pos").forGetter(TemperatureMatchTest::useLocalPos)
    ).apply(instance, TemperatureMatchTest::new));

    static final PositionRuleTestType<TemperatureMatchTest> TYPE =
            new PositionRuleTestType<>(TemperatureMatchTest.CODEC, TemperatureMatchTest.NAME);

    @Override
    public PositionRuleTestType<TemperatureMatchTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        float temp;
        if (level.dimensionType().ultraWarm()) {
            temp = 3;
        } else if (useLocalPos.isPresent() && useLocalPos.get()) {
            temp = biome.value().getTemperature(pos);
        } else {
            temp = biome.value().getBaseTemperature();
        }
        return temp >= min && temp <= max;
    }
}