package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PositionRuleTest {

    PositionRuleTest EMPTY = new PositionRuleTest() {

        @Override
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
            return false;
        }

        @Override
        public PositionRuleTestType<?> getType() {
            return null;
        }
    };

    Codec<PositionRuleTest> CODEC = PositionRuleTestType.CODEC
            .dispatch("predicate_type", PositionRuleTest::getType, PositionRuleTestType::codec);


    Map<String, ? extends PositionRuleTestType<? extends PositionRuleTest>> TYPES = new HashMap<>() {{
        put(BiomeSetMatchTest.TYPE.name, BiomeSetMatchTest.TYPE);
        put(TemperatureMatchTest.TYPE.name, TemperatureMatchTest.TYPE);
        put(IsDayTest.TYPE.name, IsDayTest.TYPE);
    }};


    static Optional<? extends PositionRuleTestType<? extends PositionRuleTest>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }

    boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world);

    PositionRuleTestType<?> getType();

    record PositionRuleTestType<T extends PositionRuleTest>(Codec<T> codec, String name) {
        public static Codec<PositionRuleTestType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Biome Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

    //implementations

    record BiomeSetMatchTest(RegistryEntryList<Biome> biomes) implements PositionRuleTest {

        public static final String NAME = "biome_match";
        public static final Codec<BiomeSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryCodecs.entryList(Registry.BIOME_KEY).fieldOf("biomes").forGetter(BiomeSetMatchTest::biomes)
        ).apply(instance, BiomeSetMatchTest::new));
        static final PositionRuleTestType<BiomeSetMatchTest> TYPE = new PositionRuleTestType<>(BiomeSetMatchTest.CODEC, BiomeSetMatchTest.NAME);

        @Override
        public PositionRuleTestType<BiomeSetMatchTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
            return biomes.contains(biome);
        }
    }

    record IsDayTest(boolean day) implements PositionRuleTest {

        public static final String NAME = "day_test";
        public static final Codec<IsDayTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("day").forGetter(IsDayTest::day)
        ).apply(instance, IsDayTest::new));
        static final PositionRuleTestType<IsDayTest> TYPE = new PositionRuleTestType<>(IsDayTest.CODEC, IsDayTest.NAME);

        @Override
        public PositionRuleTestType<IsDayTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
            return world.isDay() == day;
        }
    }

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
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
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
        static final PositionRuleTestType<TemperatureMatchTest> TYPE = new PositionRuleTestType<>(TemperatureMatchTest.CODEC, TemperatureMatchTest.NAME);

        @Override
        public PositionRuleTestType<TemperatureMatchTest> getType() {
            return TYPE;
        }


        @Override
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
            float temp = 0;
            if (world.getDimension().isUltrawarm()) {
                temp = 2;
            } else if (useLocalPos.isPresent() && useLocalPos.get() && biome instanceof TemperatureAccessWidener aw) {
                (aw).getTempForPredicate(pos);
            } else {
                temp = biome.value().getTemperature();
            }
            return temp >= min && temp <= max;
        }
    }

    record NandTest(List<PositionRuleTest> predicates) implements PositionRuleTest {

        public static final String NAME = "nand";
        public static final Codec<NandTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                PositionRuleTest.CODEC.listOf().fieldOf("predicates").forGetter(NandTest::predicates)
        ).apply(instance, NandTest::new));

        static final PositionRuleTestType<NandTest> TYPE = new PositionRuleTestType<>(NandTest.CODEC, NandTest.NAME);

        @Override
        public PositionRuleTestType<NandTest> getType() {
            return TYPE;
        }

        @Override
        public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
            for(var p : predicates){
                if(p.test(biome,pos, world))return false;
            }
            return true;
        }
    }
}