package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

record BiomeSetMatchTest(HolderSet<Biome> biomes) implements IPositionRuleTest {

    public static final String NAME = "biome_match";
    public static final Codec<BiomeSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(BiomeSetMatchTest::biomes)
    ).apply(instance, BiomeSetMatchTest::new));

    static final Type<BiomeSetMatchTest> TYPE =
            new Type<>(BiomeSetMatchTest.CODEC, BiomeSetMatchTest.NAME);

    @Override
    public Type<BiomeSetMatchTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Supplier<Holder<Biome>> biome, BlockPos pos, Level level) {
        return biomes.contains(biome.get());
    }
}
