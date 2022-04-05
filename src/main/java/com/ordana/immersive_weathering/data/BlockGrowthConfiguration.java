package com.ordana.immersive_weathering.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.*;

/**
 * Author: MehVahdJukaar
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BlockGrowthConfiguration {

    public static final Codec<BlockGrowthConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RuleTest.CODEC.fieldOf("replacing_target").forGetter(BlockGrowthConfiguration::getTargetPredicate),
            AreaCondition.CODEC.fieldOf("area_condition").forGetter(BlockGrowthConfiguration::getAreaCondition),
            RandomBlockList.CODEC.listOf().fieldOf("growth_for_face").forGetter(BlockGrowthConfiguration::getRandomBlockList),
            Registry.BLOCK.byNameCodec().fieldOf("owner").forGetter(BlockGrowthConfiguration::owner),
            RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).optionalFieldOf("biomes").forGetter(BlockGrowthConfiguration::biomes)
    ).apply(instance, BlockGrowthConfiguration::new));


    private final Block owner;
    private final RuleTest targetPredicate;
    private final SimpleWeightedRandomList<Direction> growthForDirection;
    private final Map<Direction, SimpleWeightedRandomList<BlockPair>> blockGrowths;
    private final Set<Block> possibleBlocks;
    private final Optional<HolderSet<Biome>> biomes;

    private final List<RandomBlockList> randomBlockList; //used for codec

    private final int maxRange;
    private final AreaCondition areaCondition;

    public BlockGrowthConfiguration(RuleTest targetPredicate, AreaCondition areaCheck, List<RandomBlockList> growthForDirection,
                                    Block owner, Optional<HolderSet<Biome>> biomes) {
        this.owner = owner;
        this.biomes = biomes;
        this.targetPredicate = targetPredicate;
        this.randomBlockList = growthForDirection;
        SimpleWeightedRandomList.Builder<Direction> dirBuilder = SimpleWeightedRandomList.builder();
        ImmutableMap.Builder<Direction, SimpleWeightedRandomList<BlockPair>> growthBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<Block> blockBuilder = new ImmutableSet.Builder<>();
        for (var v : growthForDirection) {
            Direction direction = v.direction.orElse(Direction.UP);
            int weight = v.weight.orElse(1);
            dirBuilder.add(direction, weight);
            growthBuilder.put(direction, v.randomList);
            v.randomList.unwrap().stream().map(WeightedEntry.Wrapper::getData).forEach(o -> {
                BlockState f = o.getFirst();
                if (f != null) blockBuilder.add(f.getBlock());
                BlockState s = o.getFirst();
                if (s != null) blockBuilder.add(s.getBlock());
            });
        }
        this.growthForDirection = dirBuilder.build();
        this.blockGrowths = growthBuilder.build();
        this.possibleBlocks = blockBuilder.build();

        this.areaCondition = areaCheck;
        this.maxRange = areaCheck.getMaxRange();
    }

    public RuleTest getTargetPredicate() {
        return targetPredicate;
    }

    public AreaCondition getAreaCondition() {
        return areaCondition;
    }

    public List<RandomBlockList> getRandomBlockList() {
        return randomBlockList;
    }

    public Set<Block> getPossibleBlocks() {
        return this.possibleBlocks;
    }

    private Block owner() {
        return this.owner;
    }

    public Optional<HolderSet<Biome>> biomes() {
        return this.biomes;
    }

    public void grow(BlockPos pos, Level level) {

        if (level.isAreaLoaded(pos, maxRange)) {
            Direction dir = this.growthForDirection.getRandomValue(level.random).orElse(Direction.UP);

            BlockPos targetPos = pos.relative(dir);
            BlockState target = level.getBlockState(targetPos);
            Random seed = new Random(Mth.getSeed(pos));
            if (targetPredicate.test(target, seed)) {

                if (areaCondition.isValid(pos, level, this)) {

                    var l = blockGrowths.get(dir);
                    if (l != null) {
                        l.getRandomValue(level.random).ifPresent(p -> {

                            if (p.isDouble()) {
                                BlockPos targetPos2 = targetPos.relative(dir);
                                BlockState target2 = level.getBlockState(targetPos2);
                                if (targetPredicate.test(target2, seed)) {
                                    level.setBlockAndUpdate(targetPos, p.getFirst());
                                    level.setBlockAndUpdate(targetPos2, p.getSecond());
                                }
                            } else {
                                level.setBlockAndUpdate(targetPos, p.getFirst());
                            }
                        });
                    }
                }
            }
        }
    }


    public record RandomBlockList(Optional<Direction> direction, Optional<Integer> weight,
                                  SimpleWeightedRandomList<BlockPair> randomList) {

        public static final Codec<RandomBlockList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Direction.CODEC.optionalFieldOf("direction").forGetter(RandomBlockList::direction),
                Codec.INT.optionalFieldOf("weight").forGetter(RandomBlockList::weight),
                SimpleWeightedRandomList.wrappedCodec(BlockPair.CODEC).fieldOf("growth").forGetter(RandomBlockList::randomList)
        ).apply(instance, RandomBlockList::new));
    }

}
