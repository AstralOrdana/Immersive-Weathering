package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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
        public PositionRuleTestType<?> getType() {
            return null;
        }
    };

    Codec<PositionRuleTest> CODEC = PositionRuleTestType.CODEC
            .dispatch("predicate_type", PositionRuleTest::getType, PositionRuleTestType::codec);


    Map<String, PositionRuleTestType<?>> TYPES = new HashMap<>(){{
        put(TemperatureMatchTest.TYPE.name, TemperatureMatchTest.TYPE);
        put(PrecipitationTest.TYPE.name, PrecipitationTest.TYPE);
        put(PosRandomTest.TYPE.name, PosRandomTest.TYPE);
        put(OrTest.TYPE.name, OrTest.TYPE);
        put(NandTest.TYPE.name, NandTest.TYPE);
        put(IsDayTest.TYPE.name, IsDayTest.TYPE);
        put(HeightTest.TYPE.name, HeightTest.TYPE);
        put(EntityTest.TYPE.name, EntityTest.TYPE);
        put(BiomeSetMatchTest.TYPE.name, BiomeSetMatchTest.TYPE);
    }};


    static <B extends PositionRuleTestType<?>> B register(B newType){
        TYPES.put(newType.name(), newType);
        return newType;
    }


    static Optional<? extends PositionRuleTestType<? extends PositionRuleTest>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }


    boolean test(Holder<Biome> biome, BlockPos pos, Level level);

    PositionRuleTestType<?> getType();



    record PositionRuleTestType<T extends PositionRuleTest>(Codec<T> codec, String name) {
        public static Codec<PositionRuleTestType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Position Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

}
