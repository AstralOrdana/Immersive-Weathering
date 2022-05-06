package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

record BiomeSetMatchTest(RegistryEntryList<Biome> biomes) implements PositionRuleTest {

    public static final String NAME = "biome_match";
    public static final Codec<BiomeSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.entryList(Registry.BIOME_KEY).fieldOf("biomes").forGetter(BiomeSetMatchTest::biomes)
    ).apply(instance, BiomeSetMatchTest::new));
    static final PositionRuleTestType<BiomeSetMatchTest> TYPE = new PositionRuleTestType<>(BiomeSetMatchTest.CODEC, BiomeSetMatchTest.NAME);

    @Override
    public PositionRuleTestType<BiomeSetMatchTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
        return biomes.contains(biome);
    }
}
