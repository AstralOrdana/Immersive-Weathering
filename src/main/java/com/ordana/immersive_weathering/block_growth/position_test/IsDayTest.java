package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

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
