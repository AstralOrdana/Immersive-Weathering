package com.ordana.immersive_weathering.block_growth;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.block_growth.area_condition.AreaCondition;
import com.ordana.immersive_weathering.block_growth.position_test.PositionRuleTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.collection.Weighted;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: MehVahdJukaar
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BlockGrowthConfiguration implements IBlockGrowth {

    public static final BlockGrowthConfiguration EMPTY = new BlockGrowthConfiguration(1,
            AlwaysTrueRuleTest.INSTANCE, Optional.empty(), List.of(), RegistryEntryList.of(RegistryEntry.of(Blocks.AIR)), Optional.empty(), Optional.empty(), Optional.empty());

    public static final Codec<BlockGrowthConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("growth_chance").forGetter(BlockGrowthConfiguration::getGrowthChance),
            RuleTest.TYPE_CODEC.fieldOf("replacing_target").forGetter(BlockGrowthConfiguration::getTargetPredicate),
            AreaCondition.CODEC.optionalFieldOf("area_condition").forGetter(BlockGrowthConfiguration::getAreaCondition),
            DirectionalList.CODEC.listOf().fieldOf("growth_for_face").forGetter(BlockGrowthConfiguration::encodeRandomLists),
            RegistryCodecs.entryList(Registry.BLOCK_KEY).fieldOf("owners").forGetter(b->b.owners),
            PositionRuleTest.CODEC.listOf().optionalFieldOf("position_predicates").forGetter(BlockGrowthConfiguration::getBiomePredicates),
            Codec.BOOL.optionalFieldOf("target_self").forGetter(b->b.targetSelf() ? Optional.of(Boolean.TRUE) : Optional.empty()),
            Codec.BOOL.optionalFieldOf("destroy_target").forGetter(b -> b.destroyTarget() ? Optional.of(Boolean.TRUE) : Optional.empty())
    ).apply(instance, BlockGrowthConfiguration::new));


    private final float growthChance;
    private final RegistryEntryList<Block> owners;
    private final RuleTest targetPredicate;
    private final DataPool<Direction> growthForDirection;
    private final Map<Direction, DataPool<BlockPair>> blockGrowths;
    private final Set<Block> possibleBlocks;
    private final List<PositionRuleTest> biomePredicates;
    private final boolean targetSelf;
    private final boolean destroyTarget;


    private final int maxRange;
    private final AreaCondition areaCondition;

    public BlockGrowthConfiguration(float growthChance, RuleTest targetPredicate, Optional<AreaCondition> areaCheck,
                                    List<DirectionalList> growthForDirection,
                                    RegistryEntryList<Block> owners, Optional<List<PositionRuleTest>> biomePredicates,
                                    Optional<Boolean> targetSelf, Optional<Boolean> destroyTarget) {
        this.growthChance = growthChance;
        this.owners = owners;
        this.biomePredicates = biomePredicates.orElse(List.of());
        this.targetPredicate = targetPredicate;

        DataPool.Builder<Direction> dirBuilder = DataPool.builder();
        ImmutableMap.Builder<Direction, DataPool<BlockPair>> growthBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<Block> blockBuilder = new ImmutableSet.Builder<>();
        for (var randomBlockList : growthForDirection) {
            //if direction is null it means if is for all directions
            if (randomBlockList.direction.isEmpty()){ //hackery
                //clearing builders since only 1 exist no
                dirBuilder = DataPool.builder();
                growthBuilder = new ImmutableMap.Builder<>();
                blockBuilder = new ImmutableSet.Builder<>();
                decodeRandomList(Direction.UP, dirBuilder, growthBuilder, blockBuilder, randomBlockList);
                for (Direction d : Direction.values()) {
                    if (d != Direction.UP) {
                        growthBuilder.put(d, randomBlockList.randomList);
                        dirBuilder.add(d, 1);
                    }
                }
                break;
            } else {
                decodeRandomList(randomBlockList.direction.get(), dirBuilder, growthBuilder, blockBuilder, randomBlockList);
            }

        }
        this.growthForDirection = dirBuilder.build();
        this.blockGrowths = growthBuilder.build();
        this.possibleBlocks = blockBuilder.build();

        this.areaCondition = areaCheck.orElse(AreaCondition.EMPTY);
        this.maxRange = areaCondition.getMaxRange();
        this.targetSelf = targetSelf.orElse(false);
        this.destroyTarget = destroyTarget.orElse(false);
    }

    private void decodeRandomList(Direction direction, DataPool.Builder<Direction> dirBuilder, ImmutableMap.Builder<Direction,
            DataPool<BlockPair>> growthBuilder, ImmutableSet.Builder<Block> blockBuilder, DirectionalList v) {
        int weight = v.weight.orElse(1);
        dirBuilder.add(direction, weight);
        growthBuilder.put(direction, v.randomList);
        v.randomList.getEntries().stream().map(Weighted.Present::getData).forEach(o -> {
            BlockState f = o.getFirst();
            if (f != null) blockBuilder.add(f.getBlock());
            BlockState s = o.getFirst();
            if (s != null) blockBuilder.add(s.getBlock());
        });
    }

    public List<DirectionalList> encodeRandomLists() {
        List<DirectionalList> list = new ArrayList<>();
        for (var e : growthForDirection.getEntries()){

            Optional<Direction> dir;
            Optional<Integer> weight;
            if (growthForDirection.getEntries().size() == 1 ){
                dir = Optional.empty();
                weight = Optional.empty();
            } else {
                dir = Optional.of(e.getData());
                weight = Optional.of(e.getWeight().getValue());
            }
            list.add(new DirectionalList(dir, weight, blockGrowths.get(e.getData())));

        }
        return list;
    }

    public RuleTest getTargetPredicate() {
        return targetPredicate;
    }

    public Optional<AreaCondition> getAreaCondition() {
        return areaCondition == AreaCondition.EMPTY ? Optional.empty() : Optional.of(areaCondition);
    }

    public Set<Block> getPossibleBlocks() {
        return this.possibleBlocks;
    }

    public boolean targetSelf() {
        return targetSelf;
    }

    public boolean destroyTarget() {
        return destroyTarget;
    }

    public Optional<List<PositionRuleTest>> getBiomePredicates() {
        return this.biomePredicates.isEmpty() ? Optional.empty() : Optional.of(this.biomePredicates);
    }

    public boolean isEmpty() {
        return this.possibleBlocks.isEmpty();
    }

    private boolean canGrow(BlockPos pos, World world, RegistryEntry<Biome> biome) {
        if (this.growthChance == 0) return false;
        if(world.random.nextFloat() < this.growthChance) {
            for (PositionRuleTest biomeTest : this.biomePredicates) {
                if (!biomeTest.test(biome, pos, world)) return false;
            }
            return world.isChunkLoaded(pos);
        }return false;
    }

    public float getGrowthChance() {
        return growthChance;
    }

    @Override
    public Iterable<Block> getOwners(){
        return this.owners.stream().map(RegistryEntry::value).collect(Collectors.toList());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState self, ServerWorld world, RegistryEntry<Biome> biome) {

        if (this.canGrow(pos, world, biome)) {
            Direction dir = this.growthForDirection.getDataOrEmpty(world.random).orElse(Direction.UP);

            Random seed = Random.create(MathHelper.hashCode(pos));
            BlockPos targetPos = targetSelf ? pos : pos.offset(dir);
            BlockState target =  world.getBlockState(targetPos);

            if (targetSelf || targetPredicate.test(target, seed)) {

                var l = blockGrowths.get(dir);
                if (l != null) {
                    var toPlace = l.getDataOrEmpty(world.random).orElse(null);
                    if (toPlace != null && toPlace.getFirst().canPlaceAt(world, targetPos)) {
                        BlockPos targetPos2 = null;
                        BlockState target2 = null;
                        boolean db = toPlace.isDouble();
                        if (db) {
                            targetPos2 = targetPos.offset(dir);
                            target2 = world.getBlockState(targetPos2);
                            if (!targetPredicate.test(target2, seed)) {
                                return;
                            }
                        }

                        if (areaCondition.test(pos, world, this)) {
                            if (destroyTarget) world.breakBlock(targetPos, true);
                            world.setBlockState(targetPos, setWaterIfNeeded(toPlace.getFirst(), target), 2);
                            if (db) {
                                if (destroyTarget) world.breakBlock(targetPos2, true);
                                world.setBlockState(targetPos2, setWaterIfNeeded(toPlace.getSecond(), target2), 2);
                            }
                            return;
                        }
                    }
                }
            }
        }
        return;
    }

    private BlockState setWaterIfNeeded(BlockState toPlace, BlockState target) {
        if (toPlace.contains(Properties.WATERLOGGED) && target.getFluidState().isIn(FluidTags.WATER)) {
            return toPlace.with(Properties.WATERLOGGED, true);
        }
        return toPlace;
    }

    public record DirectionalList(Optional<Direction> direction, Optional<Integer> weight,
                                  DataPool<BlockPair> randomList) {

        public static final Codec<DirectionalList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Direction.CODEC.optionalFieldOf("direction").forGetter(DirectionalList::direction),
                Codec.INT.optionalFieldOf("weight").forGetter(DirectionalList::weight),
                DataPool.createCodec(BlockPair.CODEC).fieldOf("growth").forGetter(DirectionalList::randomList)
        ).apply(instance, DirectionalList::new));
    }
}