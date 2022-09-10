package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

record BiomeSetMatchTest(HolderSet<Biome> biomes) implements PositionRuleTest {

    public static final String NAME = "biome_match";
    public static final Codec<BiomeSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).fieldOf("biomes").forGetter(BiomeSetMatchTest::biomes)
    ).apply(instance, BiomeSetMatchTest::new));

    static final Type<BiomeSetMatchTest> TYPE =
            new Type<>(BiomeSetMatchTest.CODEC, BiomeSetMatchTest.NAME);

    @Override
    public Type<BiomeSetMatchTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        return biomes.contains(biome);
    }
}
