package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

record NandTest(List<PositionRuleTest> predicates) implements PositionRuleTest {

    public static final String NAME = "nand";
    public static final Codec<NandTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PositionRuleTest.CODEC.listOf().fieldOf("predicates").forGetter(NandTest::predicates)
    ).apply(instance, NandTest::new));

    static final PositionRuleTestType<NandTest> TYPE = new PositionRuleTestType<>(NandTest.CODEC, NandTest.NAME);

    @Override
    public PositionRuleTestType<NandTest> getType() {
        return TYPE;
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {
        for(var p : predicates){
            if(!p.test(biome,pos, level))return true;
        }
        return false;
    }
}
