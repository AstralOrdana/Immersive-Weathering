package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public interface IPositionRuleTest {

    Codec<IPositionRuleTest> CODEC = IPositionRuleTest.Type.CODEC
            .dispatch("type", IPositionRuleTest::getType, IPositionRuleTest.Type::codec);


    //just loads the class and registers its stuff
    static void register(){}

    IPositionRuleTest EMPTY = new IPositionRuleTest() {

        @Override
        public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
            return false;
        }

        @Override
        public Type<?> getType() {
            return null;
        }
    };

    boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level);

    Type<?> getType();

    record Type<T extends IPositionRuleTest>(Codec<T> codec, String name) {

        public static final Codec<Type<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> ModPositionRuleTests.get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Position Predicate: " + name)),
                (t) -> DataResult.success(t.name()));

    }

}
