package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.Operator;
import com.ordana.immersive_weathering.util.StrOpt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

record LightTest(int targetLight, Operator operator, Optional<Vec3i> offset) implements IPositionRuleTest {

    public static final String NAME = "light_test";
    public static final Codec<LightTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0,15).fieldOf("light").forGetter(LightTest::targetLight),
            Operator.CODEC.fieldOf("operator").forGetter(LightTest::operator),
            StrOpt.of(Vec3i.offsetCodec(16),"offset").forGetter(LightTest::offset)
    ).apply(instance, LightTest::new));

    static final Type<LightTest> TYPE =
            new Type<>(LightTest.CODEC, LightTest.NAME);

    @Override
    public Type<LightTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        //if offset only look there if not look everywhere to see if at least one matches
        return offset.map(off -> operator.apply(level.getLightEmission(pos.offset(off)), targetLight)).orElseGet(
                () -> Arrays.stream(Direction.values()).anyMatch(d ->
                        operator.apply(level.getLightEmission(pos), targetLight)));
    }
}