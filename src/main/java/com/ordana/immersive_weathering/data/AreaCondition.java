package com.ordana.immersive_weathering.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.*;

public interface AreaCondition {

    AreaCondition EMPTY = new AreaCondition() {
        @Override
        public boolean test(BlockPos pos, Level level, BlockGrowthConfiguration config) {
            return false;
        }

        @Override
        public int getMaxRange() {
            return 0;
        }

        @Override
        public AreaConditionType<?> getType() {
            return null;
        }
    };

    Codec<AreaCondition> CODEC = AreaConditionType.CODEC
            .dispatch("type", AreaCondition::getType, AreaConditionType::codec);


    Map<String, ? extends AreaConditionType<? extends AreaCondition>> TYPES = new HashMap<>() {{
        put(AreaCheck.TYPE.name, AreaCheck.TYPE);
        put(NeighborCheck.TYPE.name, NeighborCheck.TYPE);
    }};


    static Optional<? extends AreaConditionType<? extends AreaCondition>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }

    boolean test(BlockPos pos, Level level, BlockGrowthConfiguration config);

    int getMaxRange();

    AreaConditionType<?> getType();

    record AreaConditionType<T extends AreaCondition>(Codec<T> codec, String name) {
        public static Codec<AreaConditionType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Area Condition: " + name)),
                (t) -> DataResult.success(t.name()));
    }

    //implementations

    record AreaCheck(int rX, int rY, int rZ, int requiredAmount, Optional<Integer> yOffset,
                            Optional<RuleTest> mustHavePredicate,
                            Optional<RuleTest> mustNotHavePredicate) implements AreaCondition {

        public static final String NAME = "generate_if_not_too_many";
        public static final Codec<AreaCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("radiusX").forGetter(AreaCheck::rX),
                Codec.INT.fieldOf("radiusY").forGetter(AreaCheck::rY),
                Codec.INT.fieldOf("radiusZ").forGetter(AreaCheck::rZ),
                Codec.INT.fieldOf("requiredAmount").forGetter(AreaCheck::requiredAmount),
                Codec.INT.optionalFieldOf("yOffset").forGetter(AreaCheck::yOffset),
                RuleTest.CODEC.optionalFieldOf("must_have").forGetter(AreaCheck::mustHavePredicate),
                RuleTest.CODEC.optionalFieldOf("must_not_have").forGetter(AreaCheck::mustNotHavePredicate)
        ).apply(instance, AreaCheck::new));
        static final AreaConditionType<AreaCheck> TYPE = new AreaConditionType<>(AreaCheck.CODEC, AreaCheck.NAME);

        @Override
        public AreaConditionType<AreaCheck> getType() {
            return TYPE;
        }

        @Override
        public boolean test(BlockPos pos, Level level, BlockGrowthConfiguration config) {
            if (yOffset.isPresent()) pos = pos.above(yOffset.get());
            int count = 0;
            Random random = new Random(Mth.getSeed(pos));
            boolean hasRequirement = this.mustHavePredicate.isEmpty();
            //shuffling. provides way better result that iterating through it conventionally
            var list = WeatheringHelper.grabBlocksAroundRandomly(pos, rX, rY, rZ);
            for (BlockPos p : list) {
                BlockState state = level.getBlockState(p);
                if (config.getPossibleBlocks().contains(state.getBlock())) count += 1;
                else {
                    if (!hasRequirement && mustHavePredicate.get().test(state, random)){
                        hasRequirement = true;
                        // if -1 means it can accept any number so we exit early
                        if(requiredAmount==-1)break;
                    }
                    else if (mustNotHavePredicate.isPresent() && mustNotHavePredicate.get().test(state, random))
                        return false;
                }
                if (count >= requiredAmount) return false;
            }
            return hasRequirement;
        }

        public int getMaxRange() {
            return Math.max(rX, Math.max(rY, rZ));
        }

    }

    record NeighborCheck(RuleTest mustHavePredicate, Optional<RuleTest> mustNotHavePredicate,
                                Optional<Integer> requiredAmount) implements AreaCondition {


        public static final String NAME = "neighbor_based_generation";
        public static final Codec<NeighborCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.fieldOf("must_have").forGetter(NeighborCheck::mustHavePredicate),
                RuleTest.CODEC.optionalFieldOf("must_not_have").forGetter(NeighborCheck::mustNotHavePredicate),
                Codec.INT.optionalFieldOf("required_amount").forGetter(NeighborCheck::requiredAmount)
        ).apply(instance, NeighborCheck::new));
        static final AreaConditionType<NeighborCheck> TYPE = new AreaConditionType<>(NeighborCheck.CODEC, NeighborCheck.NAME);

        @Override
        public AreaConditionType<NeighborCheck> getType() {
            return TYPE;
        }

        @Override
        public boolean test(BlockPos pos, Level level, BlockGrowthConfiguration config) {
            int count = 0;
            //shuffling. provides way better result that iterating through it conventionally
            List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
            Random random = new Random(Mth.getSeed(pos));
            Collections.shuffle(list, random);
            int required = this.requiredAmount.orElse(1);
            for (Direction dir : list) {
                BlockPos p = pos.relative(dir);
                BlockState state = level.getBlockState(p);
                if (mustHavePredicate.test(state, random)) count += 1;
                else if (mustNotHavePredicate.isPresent() && mustNotHavePredicate.get().test(state, random))
                    return false;
                if (count >= required) return true;
            }
            return false;
        }

        @Override
        public int getMaxRange() {
            return 1;
        }
    }

}
