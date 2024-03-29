package com.ordana.immersive_weathering.data.rute_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import com.ordana.immersive_weathering.util.StrOpt;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class BlockSetMatchTest extends RuleTest {

    public static final Codec<BlockSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(b -> b.blocks),
            StrOpt.of(Codec.FLOAT, "probability", 1f).forGetter(b -> b.probability)
    ).apply(instance, BlockSetMatchTest::new));

    private final HolderSet<Block> blocks;
    private final float probability;

    public BlockSetMatchTest(HolderSet<Block> blocks, Float chance) {
        this.blocks = blocks;
        this.probability = chance;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return state.is(blocks) && random.nextFloat() < this.probability;
    }

    @Override
    protected RuleTestType<BlockSetMatchTest> getType() {
        return ModRuleTests.BLOCK_SET_MATCH_TEST.get();
    }

}
