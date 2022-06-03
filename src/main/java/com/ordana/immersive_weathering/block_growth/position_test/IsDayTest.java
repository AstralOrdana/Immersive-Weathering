package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

record IsDayTest(boolean day) implements PositionRuleTest {

    public static final String NAME = "day_test";
    public static final Codec<IsDayTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("day").forGetter(IsDayTest::day)
    ).apply(instance, IsDayTest::new));
    static final PositionRuleTestType<IsDayTest> TYPE =
            new PositionRuleTestType<>(IsDayTest.CODEC, IsDayTest.NAME);

    @Override
    public PositionRuleTestType<IsDayTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        return level.isDay() == day;
    }
}