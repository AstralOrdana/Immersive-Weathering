package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Optional;
import java.util.Random;

public class BlockSetMatchTest extends RuleTest {

    public static final Codec<BlockSetMatchTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("blocks").forGetter(b -> b.blocks),
            Codec.FLOAT.optionalFieldOf("probability").forGetter(b -> Optional.of(b.probability))
    ).apply(instance, BlockSetMatchTest::new));

    public static final RuleTestType<BlockSetMatchTest> TYPE = RuleTestType.register("immersive_weathering:block_set_match", CODEC);

    private final HolderSet<Block> blocks;
    private final float probability;

    public BlockSetMatchTest(HolderSet<Block> blocks, Optional<Float> chance) {
        this.blocks = blocks;
        this.probability = chance.orElse(1f);
    }

    public boolean test(BlockState state, Random random) {
        return state.is(blocks) && random.nextFloat() < this.probability;
    }

    protected RuleTestType<BlockSetMatchTest> getType() {
        return TYPE;
    }

    //just need to load and static init will register
    public static void init() {
    }

}
