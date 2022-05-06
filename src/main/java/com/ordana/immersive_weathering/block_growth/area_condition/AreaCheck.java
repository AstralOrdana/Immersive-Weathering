package com.ordana.immersive_weathering.block_growth.area_condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.block_growth.BlockGrowthConfiguration;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryCodecs;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

record AreaCheck(int rX, int rY, int rZ, int requiredAmount, Optional<Integer> yOffset,
                 Optional<RuleTest> mustHavePredicate,
                 Optional<RuleTest> mustNotHavePredicate,
                 Optional<RegistryEntryList<Block>> extraIncluded) implements AreaCondition {

    public static final String NAME = "generate_if_not_too_many";
    public static final Codec<AreaCheck> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("radiusX").forGetter(AreaCheck::rX),
            Codec.INT.fieldOf("radiusY").forGetter(AreaCheck::rY),
            Codec.INT.fieldOf("radiusZ").forGetter(AreaCheck::rZ),
            Codec.INT.fieldOf("requiredAmount").forGetter(AreaCheck::requiredAmount),
            Codec.INT.optionalFieldOf("yOffset").forGetter(AreaCheck::yOffset),
            RuleTest.TYPE_CODEC.optionalFieldOf("must_have").forGetter(AreaCheck::mustHavePredicate),
            RuleTest.TYPE_CODEC.optionalFieldOf("must_not_have").forGetter(AreaCheck::mustNotHavePredicate),
            RegistryCodecs.entryList(Registry.BLOCK_KEY).optionalFieldOf("includes").forGetter(AreaCheck::extraIncluded)
    ).apply(instance, AreaCheck::new));
    static final AreaConditionType<AreaCheck> TYPE = new AreaConditionType<>(AreaCheck.CODEC, AreaCheck.NAME);

    @Override
    public AreaConditionType<AreaCheck> getType() {
        return TYPE;
    }

    @Override
    public boolean test(BlockPos pos, World level, BlockGrowthConfiguration config) {
        if (yOffset.isPresent()) pos = pos.up(yOffset.get());
        int count = 0;
        Random random = new Random(MathHelper.hashCode(pos));
        boolean hasRequirement = this.mustHavePredicate.isEmpty();
        //shuffling. provides way better result that iterating through it conventionally
        //if(hasRequirement && requiredAmount == -1)return true;
        var list = WeatheringHelper.grabBlocksAroundRandomly(pos, rX, rY, rZ);
        for (BlockPos p : list) {
            BlockState state = level.getBlockState(p);
            if (config.getPossibleBlocks().contains(state.getBlock()) ||
                    (extraIncluded.isPresent() && state.isIn(extraIncluded.get()))) count += 1;
            if (!hasRequirement &&
                    mustHavePredicate.get().test(state, random)) {
                hasRequirement = true;
                // if -1 means it can accept any number so we exit early
                if (requiredAmount == -1) {
                    break;
                }
            } else if (mustNotHavePredicate.isPresent() && mustNotHavePredicate.get().test(state, random)) {
                return false;
            }
            if (count >= requiredAmount) return false;
        }
        return hasRequirement;
    }

    @Override
    public int getMaxRange() {
        return Math.max(rX, Math.max(rY, rZ));
    }
}
