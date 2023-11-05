package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.function.Supplier;

record BlockTest(Vec3i offset, RuleTest predicate) implements IPositionRuleTest {

    public static final String NAME = "block_test";

    public static final Codec<BlockTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3i.offsetCodec(16).optionalFieldOf("offset", Vec3i.ZERO).forGetter(BlockTest::offset),
            RuleTest.CODEC.fieldOf("predicate").forGetter(BlockTest::predicate)
    ).apply(instance, BlockTest::new));

    static final Type<BlockTest> TYPE =
            new Type<>(BlockTest.CODEC, BlockTest.NAME);

    @Override
    public Type<BlockTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        return predicate.test(level.getBlockState(pos.offset(offset)), RandomSource.create(Mth.getSeed(pos)));
    }
}
