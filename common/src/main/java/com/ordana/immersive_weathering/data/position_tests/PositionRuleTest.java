package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface PositionRuleTest {

    //just loads the class and registers its stuff
    static void register(){}

    PositionRuleTest EMPTY = new PositionRuleTest() {

        @Override
        public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
            return false;
        }

        @Override
        public Type<?> getType() {
            return null;
        }
    };

    Codec<PositionRuleTest> CODEC = Type.CODEC
            .dispatch("type", PositionRuleTest::getType, Type::codec);


    Map<String, Type<?>> TYPES = new HashMap<>(){{
        put(TemperatureMatchTest.TYPE.name, TemperatureMatchTest.TYPE);
        put(PrecipitationTest.TYPE.name, PrecipitationTest.TYPE);
        put(PosRandomTest.TYPE.name, PosRandomTest.TYPE);
        put(OrTest.TYPE.name, OrTest.TYPE);
        put(NandTest.TYPE.name, NandTest.TYPE);
        put(AndTest.TYPE.name, AndTest.TYPE);
        put(IsDayTest.TYPE.name, IsDayTest.TYPE);
        put(HeightTest.TYPE.name, HeightTest.TYPE);
        put(EntityTest.TYPE.name, EntityTest.TYPE);
        put(LightTest.TYPE.name, LightTest.TYPE);
        put(BiomeSetMatchTest.TYPE.name, BiomeSetMatchTest.TYPE);
        put(BlockTest.TYPE.name, BlockTest.TYPE);
    }};


    static <B extends Type<?>> B register(B newType){
        TYPES.put(newType.name(), newType);
        return newType;
    }


    static Optional<? extends Type<? extends PositionRuleTest>> get(String name) {
        return Optional.ofNullable(TYPES.get(name));
    }


    boolean test(Holder<Biome> biome, BlockPos pos, Level level);

    Type<?> getType();



    record Type<T extends PositionRuleTest>(Codec<T> codec, String name) {

        private static final Codec<Type<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Position Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

}
