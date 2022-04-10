package com.ordana.immersive_weathering.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Author: MehVahdJukaar
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BlockGrowthConfiguration implements IForgeRegistryEntry<BlockGrowthConfiguration> {

    public static final BlockGrowthConfiguration EMPTY = new BlockGrowthConfiguration(1,
            AlwaysTrueTest.INSTANCE, AreaCondition.EMPTY, List.of(), Blocks.AIR, Optional.empty(), Optional.empty());

    public static final Codec<BlockGrowthConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("growth_chance").forGetter(BlockGrowthConfiguration::getGrowthChance),
            RuleTest.CODEC.fieldOf("replacing_target").forGetter(BlockGrowthConfiguration::getTargetPredicate),
            AreaCondition.CODEC.fieldOf("area_condition").forGetter(BlockGrowthConfiguration::getAreaCondition),
            DirectionalList.CODEC.listOf().fieldOf("growth_for_face").forGetter(BlockGrowthConfiguration::encodeRandomLists),
            Registry.BLOCK.byNameCodec().fieldOf("owner").forGetter(BlockGrowthConfiguration::getOwner),
            PositionRuleTest.CODEC.listOf().optionalFieldOf("position_predicates").forGetter(BlockGrowthConfiguration::getBiomePredicates),
            Codec.BOOL.optionalFieldOf("target_self").forGetter(b->b.targetSelf() ? Optional.of(Boolean.TRUE) : Optional.empty())
    ).apply(instance, BlockGrowthConfiguration::new));



    private final float growthChance;
    private final Block owner;
    private final RuleTest targetPredicate;
    private final SimpleWeightedRandomList<Direction> growthForDirection;
    private final Map<Direction, SimpleWeightedRandomList<BlockPair>> blockGrowths;
    private final Set<Block> possibleBlocks;
    private final List<PositionRuleTest> biomePredicates;
    private final boolean targetSelf;

    private final int maxRange;
    private final AreaCondition areaCondition;

    public BlockGrowthConfiguration(float growthChance, RuleTest targetPredicate, AreaCondition areaCheck,
                                    List<DirectionalList> growthForDirection,
                                    Block owner, Optional<List<PositionRuleTest>> biomePredicates,
                                    Optional<Boolean> targetSelf) {
        this.growthChance = growthChance;
        this.owner = owner;
        this.biomePredicates = biomePredicates.orElse(List.of());
        this.targetPredicate = targetPredicate;

        SimpleWeightedRandomList.Builder<Direction> dirBuilder = SimpleWeightedRandomList.builder();
        ImmutableMap.Builder<Direction, SimpleWeightedRandomList<BlockPair>> growthBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<Block> blockBuilder = new ImmutableSet.Builder<>();
        for (var randomBlockList : growthForDirection) {
            //if direction is null it means if is for all directions
            if (randomBlockList.direction.isEmpty()) {//hackery
                //clearing builders since only 1 exist no
                dirBuilder = SimpleWeightedRandomList.builder();
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

        this.areaCondition = areaCheck;
        this.maxRange = areaCheck.getMaxRange();
        this.targetSelf = targetSelf.orElse(false);
    }

    private void decodeRandomList(Direction direction, SimpleWeightedRandomList.Builder<Direction> dirBuilder, ImmutableMap.Builder<Direction,
            SimpleWeightedRandomList<BlockPair>> growthBuilder, ImmutableSet.Builder<Block> blockBuilder, DirectionalList v) {
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

    public List<DirectionalList> encodeRandomLists() {
        List<DirectionalList> list = new ArrayList<>();
        for (var e : growthForDirection.unwrap()) {
            Optional<Direction> dir;
            Optional<Integer> weight;
            if (growthForDirection.unwrap().size() == 1) {
                dir = Optional.empty();
                weight = Optional.empty();
            } else {
                dir = Optional.of(e.getData());
                weight = Optional.of(e.getWeight().asInt());
            }
            list.add(new DirectionalList(dir, weight, blockGrowths.get(e.getData())));
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

    public boolean targetSelf() {
        return targetSelf;
    }

    public Optional<List<PositionRuleTest>> getBiomePredicates() {
        return this.biomePredicates.isEmpty() ? Optional.empty() : Optional.of(this.biomePredicates);
    }

    public boolean isEmpty() {
        return this.possibleBlocks.isEmpty();
    }

    private boolean canGrow(BlockPos pos, Level level, Holder<Biome> biome) {
        for (PositionRuleTest biomeTest : this.biomePredicates) {
            if (!biomeTest.test(biome, pos, level)) return false;
        }
        return level.isAreaLoaded(pos, maxRange) && level.random.nextFloat() < this.growthChance;
    }

    public float getGrowthChance() {
        return growthChance;
    }

    public boolean tryGrowing(BlockPos pos, Level level, Holder<Biome> biome) {

        if (this.canGrow(pos, level, biome)) {
            Direction dir = this.growthForDirection.getRandomValue(level.random).orElse(Direction.UP);

            Random seed = new Random(Mth.getSeed(pos));
            BlockPos targetPos = targetSelf ? pos : pos.relative(dir);
            BlockState target =  level.getBlockState(targetPos);

            if (targetSelf || targetPredicate.test(target, seed)) {

                if (areaCondition.test(pos, level, this)) {

                    var l = blockGrowths.get(dir);
                    if (l != null) {
                        var toPlace = l.getRandomValue(level.random).orElse(null);
                        if (toPlace != null) {
                            if (toPlace.isDouble()) {
                                BlockPos targetPos2 = targetPos.relative(dir);
                                BlockState target2 = level.getBlockState(targetPos2);
                                if (targetPredicate.test(target2, seed)) {
                                    level.setBlock(targetPos, setWaterIfNeeded(toPlace.getFirst(), target), 2);
                                    level.setBlock(targetPos2, setWaterIfNeeded(toPlace.getSecond(), target2), 2);
                                }
                            } else {
                                level.setBlock(targetPos, setWaterIfNeeded(toPlace.getFirst(), target), 2);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //builtin waterlogging support
    private BlockState setWaterIfNeeded(BlockState toPlace, BlockState target) {
        if (toPlace.hasProperty(BlockStateProperties.WATERLOGGED) && target.getFluidState().is(FluidTags.WATER)) {
            return toPlace.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        return toPlace;
    }

    @Override
    public BlockGrowthConfiguration setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<BlockGrowthConfiguration> getRegistryType() {
        return null;
    }

    public record DirectionalList(Optional<Direction> direction, Optional<Integer> weight,
                                  SimpleWeightedRandomList<BlockPair> randomList) {

        public static final Codec<DirectionalList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Direction.CODEC.optionalFieldOf("direction").forGetter(DirectionalList::direction),
                Codec.INT.optionalFieldOf("weight").forGetter(DirectionalList::weight),
                SimpleWeightedRandomList.wrappedCodec(BlockPair.CODEC).fieldOf("growth").forGetter(DirectionalList::randomList)
        ).apply(instance, DirectionalList::new));
    }

}
