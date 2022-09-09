package com.ordana.immersive_weathering.block_growth.liquid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.block_growth.position_test.PositionRuleTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class LiquidGenerator implements Comparable<LiquidGenerator> {

    public static final Codec<LiquidGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.FLUID.byNameCodec().fieldOf("owner").forGetter(LiquidGenerator::getLiquid),
            Codec.simpleMap(Direction.CODEC, RuleTest.CODEC, StringRepresentable.keys(Direction.values()))
                    .fieldOf("neighbors").forGetter(LiquidGenerator::getNeighborBlocks),
            PositionRuleTest.CODEC.listOf().optionalFieldOf("additional_checks", List.of()).forGetter(LiquidGenerator::getPositionTests),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(LiquidGenerator::getPriority)
    ).apply(instance, LiquidGenerator::new));

    private final Fluid owners;
    private final Map<Direction, RuleTest> neighborBlocks;
    private final List<PositionRuleTest> positionTests;
    private final int priority;

    public LiquidGenerator(Fluid owner, Map<Direction, RuleTest> neighborBlocks, List<PositionRuleTest> positionRuleTests, int priority) {
        this.owners = owner;
        this.neighborBlocks = neighborBlocks;

        this.positionTests = positionRuleTests;
        this.priority = priority;
    }

    public Fluid getLiquid() {
        return owners;
    }

    public Map<Direction, RuleTest> getNeighborBlocks() {
        return neighborBlocks;
    }

    public List<PositionRuleTest> getPositionTests() {
        return positionTests;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(@NotNull LiquidGenerator o) {
        return Integer.compare(this.priority, o.priority);
    }

    public boolean tryGenerating(FluidState fluidState, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {

        return false;
    }
}
