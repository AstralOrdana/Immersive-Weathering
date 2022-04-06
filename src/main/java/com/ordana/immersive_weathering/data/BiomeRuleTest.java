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

public interface BiomeRuleTest {

    BiomeRuleTest EMPTY = new BiomeRuleTest() {

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos) {
            return false;
        }

        @Override
        public BiomeRuleTestType<?> getType() {
            return null;
        }
    };

    Codec<BiomeRuleTest> CODEC = BiomeRuleTestType.CODEC
            .dispatch("predicate_type", BiomeRuleTest::getType, BiomeRuleTestType::codec);


    Map<String, ? extends BiomeRuleTestType<? extends BiomeRuleTest>> TYPES = new HashMap<>() {{
        put(BiomeSetMatchTest.TYPE.name, BiomeSetMatchTest.TYPE);
        put(TemperatureMatchTest.TYPE.name, TemperatureMatchTest.TYPE);
    }};


    static Optional<? extends BiomeRuleTestType<? extends BiomeRuleTest>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }

    boolean test(Holder<Biome> biome, BlockPos pos);

    BiomeRuleTestType<?> getType();

    record BiomeRuleTestType<T extends BiomeRuleTest>(Codec<T> codec, String name) {
        public static Codec<BiomeRuleTestType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Biome Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

    //implementations

    record BiomeSetMatchTest(HolderSet<Biome> biomes) implements BiomeRuleTest {

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
        public boolean test(Holder<Biome> biome, BlockPos pos) {
            return biomes.contains(biome);
        }
    }

    record TemperatureMatchTest(float max, float min, Optional<Boolean> useLocalPos) implements BiomeRuleTest {


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
        public boolean test(Holder<Biome> biome, BlockPos pos) {
            float temp;
            if (useLocalPos.isPresent() && useLocalPos.get()) {
                Level l;
                temp = biome.value().getTemperature(pos);
            } else {
                temp = biome.value().getBaseTemperature();
            }
            return temp >= min && temp <= max;
        }
    }

}
