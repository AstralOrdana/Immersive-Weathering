package com.ordana.immersive_weathering.block_growth.rule_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.RuleTestType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;

import java.util.Optional;
import java.util.Random;

public class BlockSetMatchTest extends RuleTest {

    public static final Codec<BlockSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("blocks").forGetter(b -> b.blocks),
            Codec.FLOAT.optionalFieldOf("probability").forGetter(b -> Optional.of(b.probability))
    ).apply(instance, BlockSetMatchTest::new));

    public static final RuleTestType<BlockSetMatchTest> TYPE = RuleTestType.register("immersive_weathering:block_set_match", CODEC);

    private final RegistryEntryList<Block> blocks;
    private final float probability;

    public BlockSetMatchTest(RegistryEntryList<Block> blocks, Optional<Float> chance) {
        this.blocks = blocks;
        this.probability = chance.orElse(1f);
    }

    public boolean test(BlockState state, Random random) {
        return state.isIn(blocks) && random.nextFloat() < this.probability;
    }

    protected RuleTestType<BlockSetMatchTest> getType() {
        return TYPE;
    }

    //just need to load and static init will register
    public static void init() {
    }

}