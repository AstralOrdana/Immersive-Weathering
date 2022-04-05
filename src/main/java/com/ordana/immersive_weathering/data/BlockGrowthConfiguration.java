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
            JsonReadyRandomBlockList.CODEC.listOf().fieldOf("growth_for_face").forGetter(BlockGrowthConfiguration::encodeRandomLists),
            Registry.BLOCK.byNameCodec().fieldOf("owner").forGetter(BlockGrowthConfiguration::getOwner),
            RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).optionalFieldOf("biomes").forGetter(BlockGrowthConfiguration::biomes)
    ).apply(instance, BlockGrowthConfiguration::new));


    private final Block owner;
    private final RuleTest targetPredicate;
    private final SimpleWeightedRandomList<Direction> growthForDirection;
    private final Map<Direction, SimpleWeightedRandomList<BlockPair>> blockGrowths;
    private final Set<Block> possibleBlocks;
    private final Optional<HolderSet<Biome>> biomes;

    private final int maxRange;
    private final AreaCondition areaCondition;

    public BlockGrowthConfiguration(RuleTest targetPredicate, AreaCondition areaCheck,
                                    List<JsonReadyRandomBlockList> growthForDirection,
                                    Block owner, Optional<HolderSet<Biome>> biomes) {
        this.owner = owner;
        this.biomes = biomes;
        this.targetPredicate = targetPredicate;

        SimpleWeightedRandomList.Builder<Direction> dirBuilder = SimpleWeightedRandomList.builder();
        ImmutableMap.Builder<Direction, SimpleWeightedRandomList<BlockPair>> growthBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<Block> blockBuilder = new ImmutableSet.Builder<>();
        for (var randomBlockList : growthForDirection) {
            //if direction is null it means if is for all directions
            if(randomBlockList.direction.isEmpty()){//hackery
                //clearing builders since only 1 exist no
                dirBuilder = SimpleWeightedRandomList.builder();
                growthBuilder = new ImmutableMap.Builder<>();
                blockBuilder = new ImmutableSet.Builder<>();
                decodeRandomList(Direction.UP, dirBuilder, growthBuilder, blockBuilder, randomBlockList);
                for(Direction d: Direction.values()){
                    if(d != Direction.UP) {
                        growthBuilder.put(d, randomBlockList.randomList);
                        dirBuilder.add(d, 1);
                    }
                }

                break;
            }else{
                decodeRandomList(randomBlockList.direction.get(), dirBuilder, growthBuilder, blockBuilder, randomBlockList);
            }
        }
        this.growthForDirection = dirBuilder.build();
        this.blockGrowths = growthBuilder.build();
        this.possibleBlocks = blockBuilder.build();

        this.areaCondition = areaCheck;
        this.maxRange = areaCheck.getMaxRange();
    }

    private void decodeRandomList(Direction direction, SimpleWeightedRandomList.Builder<Direction> dirBuilder, ImmutableMap.Builder<Direction,
            SimpleWeightedRandomList<BlockPair>> growthBuilder, ImmutableSet.Builder<Block> blockBuilder, JsonReadyRandomBlockList v) {
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

    public List<JsonReadyRandomBlockList> encodeRandomLists() {
        List<JsonReadyRandomBlockList> list = new ArrayList<>();
        for(var e: growthForDirection.unwrap() ){
            Optional<Direction> dir;
            Optional<Integer> weight;
            if(growthForDirection.unwrap().size()==1){
                dir = Optional.empty();
                weight = Optional.empty();
            }else{
                dir = Optional.of(e.getData());
                weight = Optional.of(e.getWeight().asInt());
            }
            list.add(new JsonReadyRandomBlockList(dir, weight,blockGrowths.get(e.getData())));
        }
        return list;
    }

    public RuleTest getTargetPredicate() {
        return targetPredicate;
    }

    public AreaCondition getAreaCondition() {
        return areaCondition;
    }

    public Set<Block> getPossibleBlocks() {
        return this.possibleBlocks;
    }

    public Block getOwner() {
        return this.owner;
    }

    public Optional<HolderSet<Biome>> biomes() {
        return this.biomes;
    }

    public boolean grow(BlockPos pos, Level level) {

        if (level.isAreaLoaded(pos, maxRange)) {
            Direction dir = this.growthForDirection.getRandomValue(level.random).orElse(Direction.UP);

            Random seed = new Random(Mth.getSeed(pos));
            BlockPos targetPos = pos.relative(dir);
            BlockState target = level.getBlockState(targetPos);

            if (targetPredicate.test(target, seed)) {

                if (areaCondition.isValid(pos, level, this)) {

                    var l = blockGrowths.get(dir);
                    if (l != null) {
                        var toPlace = l.getRandomValue(level.random).orElse(null);
                        if(toPlace != null){
                            if (toPlace.isDouble()) {
                                BlockPos targetPos2 = targetPos.relative(dir);
                                BlockState target2 = level.getBlockState(targetPos2);
                                if (targetPredicate.test(target2, seed)) {
                                    level.setBlock(targetPos, toPlace.getFirst(),2);
                                    level.setBlock(targetPos2, toPlace.getSecond(),2);
                                }
                            } else {
                                level.setBlock(targetPos, toPlace.getFirst(),2);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public record JsonReadyRandomBlockList(Optional<Direction> direction, Optional<Integer> weight,
                                           SimpleWeightedRandomList<BlockPair> randomList) {

        public static final Codec<JsonReadyRandomBlockList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Direction.CODEC.optionalFieldOf("direction").forGetter(JsonReadyRandomBlockList::direction),
                Codec.INT.optionalFieldOf("weight").forGetter(JsonReadyRandomBlockList::weight),
                SimpleWeightedRandomList.wrappedCodec(BlockPair.CODEC).fieldOf("growth").forGetter(JsonReadyRandomBlockList::randomList)
        ).apply(instance, JsonReadyRandomBlockList::new));
    }

}
