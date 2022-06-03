package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class HeightTest implements PositionRuleTest {

    public static final String NAME = "height_test";
    public static final Codec<HeightTest> CODEC = RecordCodecBuilder.create((i) -> i.group(
            HeightProvider.CODEC.fieldOf("distribution").forGetter(HeightTest::getHeight),
            Codec.INT.fieldOf("tolerance").forGetter(HeightTest::getTolerance)
    ).apply(i, HeightTest::new));

    static final PositionRuleTestType<HeightTest> TYPE =
            new PositionRuleTestType<>(HeightTest.CODEC, HeightTest.NAME);


    private final HeightProvider height;
    private final int tolerance;

    private HeightTest(HeightProvider provider, int deltaY) {
        this.height = provider;
        this.tolerance = deltaY;
    }

    public int getTolerance() {
        return tolerance;
    }

    public HeightProvider getHeight() {
        return height;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        int sampledY = this.height.sample(level.random, new DummyWorldGenerationContext(level));

        return (Math.abs(sampledY - pos.getY()) <= tolerance);
    }

    @Override
    public PositionRuleTestType<?> getType() {
        return TYPE;
    }
}
