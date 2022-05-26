package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.block_growth.TemperatureAccessWidener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

record TemperatureMatchTest(float max, float min, Optional<Boolean> useLocalPos) implements PositionRuleTest {


    public static final String NAME = "temperature_range";
    public static final Codec<TemperatureMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("max").forGetter(TemperatureMatchTest::max),
            Codec.FLOAT.fieldOf("min").forGetter(TemperatureMatchTest::min),
            Codec.BOOL.optionalFieldOf("use_local_pos").forGetter(TemperatureMatchTest::useLocalPos)
    ).apply(instance, TemperatureMatchTest::new));
    static final PositionRuleTestType<TemperatureMatchTest> TYPE = new PositionRuleTestType<>(TemperatureMatchTest.CODEC, TemperatureMatchTest.NAME);

    @Override
    public PositionRuleTestType<TemperatureMatchTest> getType() {
        return TYPE;
    }


    @Override
    public boolean test(RegistryEntry<Biome> biome, BlockPos pos, World world) {
        float temp = 0;
        if (world.getDimension().isUltrawarm()) {
            temp = 2;
        } else if (useLocalPos.isPresent() && useLocalPos.get() && biome instanceof TemperatureAccessWidener aw) {
            temp = (aw).getTempForPredicate(pos);
        } else {
            temp = biome.value().getTemperature();
        }
        return temp >= min && temp <= max;
    }
}
