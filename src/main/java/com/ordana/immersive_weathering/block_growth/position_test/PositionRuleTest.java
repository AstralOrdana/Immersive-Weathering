package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
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
        put(NandTest.TYPE.name, NandTest.TYPE);
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
}