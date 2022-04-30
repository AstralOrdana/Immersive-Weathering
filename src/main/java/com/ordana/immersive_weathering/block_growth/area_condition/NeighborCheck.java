package com.ordana.immersive_weathering.block_growth.area_condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.block_growth.BlockGrowthConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.*;

record NeighborCheck(RuleTest mustHavePredicate, Optional<RuleTest> mustNotHavePredicate,
                     Optional<Integer> requiredAmount, Optional<List<Direction>> directions) implements AreaCondition {

    public static final String NAME = "neighbor_based_generation";
    public static final Codec<NeighborCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RuleTest.CODEC.fieldOf("must_have").forGetter(NeighborCheck::mustHavePredicate),
            RuleTest.CODEC.optionalFieldOf("must_not_have").forGetter(NeighborCheck::mustNotHavePredicate),
            Codec.INT.optionalFieldOf("required_amount").forGetter(NeighborCheck::requiredAmount),
            Direction.CODEC.listOf().optionalFieldOf("directions").forGetter(NeighborCheck::directions)

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
        List<Direction> list = directions.orElse(new ArrayList<>(List.of(Direction.values())));
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
