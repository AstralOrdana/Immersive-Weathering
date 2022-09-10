package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public record HeightTest( HeightProvider height, int tolerance) implements PositionRuleTest {

    public static final String NAME = "height_test";
    public static final Codec<HeightTest> CODEC = RecordCodecBuilder.create((i) -> i.group(
            HeightProvider.CODEC.fieldOf("distribution").forGetter(HeightTest::height),
            Codec.INT.fieldOf("tolerance").forGetter(HeightTest::tolerance)
    ).apply(i, HeightTest::new));

    static final Type<HeightTest> TYPE =
            new Type<>(HeightTest.CODEC, HeightTest.NAME);

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        int sampledY = this.height.sample(level.random, new DummyWorldGenerationContext(level));

        return (Math.abs(sampledY - pos.getY()) <= tolerance);
    }

    @Override
    public Type<?> getType() {
        return TYPE;
    }
}
