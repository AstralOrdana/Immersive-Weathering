package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

record PrecipitationTest(Biome.Precipitation precipitation) implements IPositionRuleTest {

    public static final String NAME = "precipitation_test";
    public static final Codec<PrecipitationTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Biome.Precipitation.CODEC.fieldOf("precipitation").forGetter(PrecipitationTest::precipitation)
    ).apply(instance, PrecipitationTest::new));

    static final Type<PrecipitationTest> TYPE =
            new Type<>(PrecipitationTest.CODEC, PrecipitationTest.NAME);

    @Override
    public Type<PrecipitationTest> getType() {
        return TYPE;
    }

    //tests if the condition is true in any of the neighboring blocks
    //for none it checks if none of the neighbor have rain
    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        for (var d : Direction.values()) {
            if (d != Direction.DOWN) {
                switch (precipitation) {
                    case NONE -> {
                        if (level.isRainingAt(pos.relative(d))){
                            return false;
                        }
                    }
                    case SNOW -> {
                        if (level.isRainingAt(pos.relative(d)) && biome.get().value().coldEnoughToSnow(pos.relative(d))) {
                            return true;
                        }
                    }
                    case RAIN -> {
                        if (level.isRainingAt(pos.relative(d)) && biome.get().value().warmEnoughToRain(pos.relative(d))) {
                            return true;
                        }
                    }
                }
            }
        }
        return precipitation == Biome.Precipitation.NONE;
    }
}
