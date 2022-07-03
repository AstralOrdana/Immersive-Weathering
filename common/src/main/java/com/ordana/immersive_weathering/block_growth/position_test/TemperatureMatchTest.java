package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.mixins.accessors.BiomeAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;
import java.util.function.Function;

record TemperatureMatchTest(float max, float min, Optional<Boolean> useLocalPos) implements PositionRuleTest {

    public static final String NAME = "temperature_range";

    private static final Codec<TemperatureMatchTest> C = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("min").forGetter(biasedToBottomInt -> biasedToBottomInt.min),
                    Codec.FLOAT.fieldOf("max").forGetter(biasedToBottomInt -> biasedToBottomInt.max),
                    Codec.BOOL.optionalFieldOf("use_local_pos").forGetter(TemperatureMatchTest::useLocalPos))
            .apply( instance, TemperatureMatchTest::new));

    public static final Codec<TemperatureMatchTest> CODEC = C.comapFlatMap(t -> {
        if (t.max < t.min) {
            return DataResult.error("Max must be at least min, min_inclusive: " + t.min + ", max_inclusive: " + t.max);
        }
        return DataResult.success(t);
    }, Function.identity());


    static final PositionRuleTestType<TemperatureMatchTest> TYPE =
            new PositionRuleTestType<>(TemperatureMatchTest.CODEC, TemperatureMatchTest.NAME);

    @Override
    public PositionRuleTestType<TemperatureMatchTest> getType() {

        return TYPE;
    }

    //snow is at >0.15F
    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        float temp;
        if (level.dimensionType().ultraWarm()) {
            temp = 3;
        } else if (useLocalPos.isPresent() && useLocalPos.get()) {
            temp = ((BiomeAccessor) (Object) biome.value()).invokeGetTemperature(pos);
        } else {
            temp = biome.value().getBaseTemperature();
        }
        return temp >= min && temp <= max;
    }
}