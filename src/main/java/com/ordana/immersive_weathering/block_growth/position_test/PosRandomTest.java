package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Random;

public record PosRandomTest(int rarity) implements PositionRuleTest {

    public static final String NAME = "pos_random";
    public static final Codec<PosRandomTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 10000).fieldOf("rarity").forGetter(PosRandomTest::rarity)
    ).apply(instance, PosRandomTest::new));

    static final PositionRuleTestType<PosRandomTest> TYPE =
            new PositionRuleTestType<>(PosRandomTest.CODEC, PosRandomTest.NAME);

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(rarity) == 0;
    }

    @Override
    public PositionRuleTestType<?> getType() {
        return TYPE;
    }
}
