package com.ordana.immersive_weathering.data.block_growths.area_condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.growths.ConfigurableBlockGrowth;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

record NeighborCheck(RuleTest mustHavePredicate, RuleTest mustNotHavePredicate,
                     Integer requiredAmount, List<Direction> directions) implements AreaCondition {

    public static final String NAME = "neighbor_based_generation";
    public static final Codec<NeighborCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RuleTest.CODEC.fieldOf("must_have").forGetter(NeighborCheck::mustHavePredicate),
            RuleTest.CODEC.optionalFieldOf("must_not_have", AlwaysTrueTest.INSTANCE).forGetter(NeighborCheck::mustNotHavePredicate),
            Codec.INT.optionalFieldOf("required_amount", 1).forGetter(NeighborCheck::requiredAmount),
            Direction.CODEC.listOf().optionalFieldOf("directions",List.of(Direction.values())).forGetter(NeighborCheck::directions)

    ).apply(instance, NeighborCheck::new));
    static final AreaConditionType<NeighborCheck> TYPE = new AreaConditionType<>(NeighborCheck.CODEC, NeighborCheck.NAME);

    @Override
    public AreaConditionType<NeighborCheck> getType() {
        return TYPE;
    }

    @Override
    public boolean test(BlockPos pos, Level level, ConfigurableBlockGrowth config) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        RandomSource random = RandomSource.create(Mth.getSeed(pos));
        List<Direction> list = Util.toShuffledList(directions.stream(), random);

        for (Direction dir : list) {
            BlockPos p = pos.relative(dir);
            BlockState state = level.getBlockState(p);
            if (mustHavePredicate.test(state, random)) count += 1;
            else if (mustNotHavePredicate != AlwaysTrueTest.INSTANCE && mustNotHavePredicate.test(state, random))
                return false;
            if (count >= this.requiredAmount) return true;
        }
        return false;
    }

    @Override
    public int getMaxRange() {
        return 1;
    }
}
