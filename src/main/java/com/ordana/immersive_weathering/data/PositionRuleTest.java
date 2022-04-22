package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface PositionRuleTest {

    PositionRuleTest EMPTY = new PositionRuleTest() {

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            return false;
        }

        @Override
        public BiomeRuleTestType<?> getType() {
            return null;
        }
    };

    Codec<PositionRuleTest> CODEC = BiomeRuleTestType.CODEC
            .dispatch("predicate_type", PositionRuleTest::getType, BiomeRuleTestType::codec);


    Map<String, ? extends BiomeRuleTestType<? extends PositionRuleTest>> TYPES = new HashMap<>() {{
        put(BiomeSetMatchTest.TYPE.name, BiomeSetMatchTest.TYPE);
        put(TemperatureMatchTest.TYPE.name, TemperatureMatchTest.TYPE);
        put(IsDayTest.TYPE.name, IsDayTest.TYPE);
    }};


    static Optional<? extends BiomeRuleTestType<? extends PositionRuleTest>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }

    boolean test(Holder<Biome> biome, BlockPos pos, Level level);

    BiomeRuleTestType<?> getType();

    record BiomeRuleTestType<T extends PositionRuleTest>(Codec<T> codec, String name) {
        public static Codec<BiomeRuleTestType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Biome Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

    //implementations

    record BiomeSetMatchTest(HolderSet<Biome> biomes) implements PositionRuleTest {

        public static final String NAME = "biome_match";
        public static final Codec<BiomeSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).fieldOf("biomes").forGetter(BiomeSetMatchTest::biomes)
        ).apply(instance, BiomeSetMatchTest::new));
        static final BiomeRuleTestType<BiomeSetMatchTest> TYPE = new BiomeRuleTestType<>(BiomeSetMatchTest.CODEC, BiomeSetMatchTest.NAME);

        @Override
        public BiomeRuleTestType<BiomeSetMatchTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            return biomes.contains(biome);
        }
    }


    record IsDayTest(boolean day) implements PositionRuleTest {

        public static final String NAME = "day_test";
        public static final Codec<IsDayTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("day").forGetter(IsDayTest::day)
        ).apply(instance, IsDayTest::new));
        static final BiomeRuleTestType<IsDayTest> TYPE = new BiomeRuleTestType<>(IsDayTest.CODEC, IsDayTest.NAME);

        @Override
        public BiomeRuleTestType<IsDayTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            return level.isDay() == day;
        }
    }

    record PrecipitationTest(Biome.Precipitation precipitation) implements PositionRuleTest {

        public static final String NAME = "precipitation_test";
        public static final Codec<PrecipitationTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(PrecipitationTest::precipitation)
        ).apply(instance, PrecipitationTest::new));
        static final BiomeRuleTestType<PrecipitationTest> TYPE = new BiomeRuleTestType<>(PrecipitationTest.CODEC, PrecipitationTest.NAME);

        @Override
        public BiomeRuleTestType<PrecipitationTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            //TODO: finish
            return true;//level.isRaining() == precipitation;
        }
    }

    record TemperatureMatchTest(float max, float min, Optional<Boolean> useLocalPos) implements PositionRuleTest {


        public static final String NAME = "temperature_range";
        public static final Codec<TemperatureMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("max").forGetter(TemperatureMatchTest::max),
                Codec.FLOAT.fieldOf("min").forGetter(TemperatureMatchTest::min),
                Codec.BOOL.optionalFieldOf("use_local_pos").forGetter(TemperatureMatchTest::useLocalPos)
        ).apply(instance, TemperatureMatchTest::new));
        static final BiomeRuleTestType<TemperatureMatchTest> TYPE = new BiomeRuleTestType<>(TemperatureMatchTest.CODEC, TemperatureMatchTest.NAME);

        @Override
        public BiomeRuleTestType<TemperatureMatchTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            float temp;
            if (level.dimensionType().ultraWarm()) {
                temp = 2;
            } else if (useLocalPos.isPresent() && useLocalPos.get()) {
                temp = biome.value().getTemperature(pos);
            } else {
                temp = biome.value().getBaseTemperature();
            }
            return temp >= min && temp <= max;
        }
    }

}
