package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.mixins.accessors.BiomeAccessor;
import com.ordana.immersive_weathering.util.StrOpt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

record TemperatureMatchTest(float min, float max, boolean useLocalPos) implements IPositionRuleTest {

    public static final String NAME = "temperature_range";

    private static final MapCodec<TemperatureMatchTest> C = RecordCodecBuilder.<TemperatureMatchTest>mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("min").forGetter(TemperatureMatchTest::min),
                    Codec.FLOAT.fieldOf("max").forGetter(TemperatureMatchTest::max),
                    StrOpt.of(Codec.BOOL, "use_local_pos", true).forGetter(TemperatureMatchTest::useLocalPos))
                    .apply(instance, TemperatureMatchTest::new));


    static final Type<TemperatureMatchTest> TYPE =
            new Type<>(TemperatureMatchTest.C, TemperatureMatchTest.NAME);

    @Override
    public Type<TemperatureMatchTest> getType() {

        return TYPE;
    }

    //snow is at >0.15F
    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        float temp;
        if (level.dimensionType().ultraWarm()) {
            temp = 3;
        } else if (useLocalPos) {
            temp = ((BiomeAccessor) (Object) biome.get().value()).invokeGetTemperature(pos);
        } else {
            temp = biome.get().value().getBaseTemperature();
        }
        return temp >= min && temp <= max;
    }
}