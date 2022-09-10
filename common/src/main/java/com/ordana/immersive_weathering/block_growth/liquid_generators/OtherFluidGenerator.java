package com.ordana.immersive_weathering.block_growth.liquid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class OtherFluidGenerator implements IFluidGenerator {

    public static final String NAME = "target_other";
    public static final Codec<OtherFluidGenerator> CODEC = RecordCodecBuilder.<OtherFluidGenerator>create(
            instance -> instance.group(
                    Registry.FLUID.byNameCodec().fieldOf("fluid").forGetter(OtherFluidGenerator::getFluid),
                    BlockState.CODEC.fieldOf("generate").forGetter(OtherFluidGenerator::getGrowth),
                    RuleTest.CODEC.fieldOf("target").forGetter(OtherFluidGenerator::getTarget),
                    PositionRuleTest.CODEC.listOf().optionalFieldOf("additional_checks", List.of()).forGetter(OtherFluidGenerator::getPositionTests),
                    Codec.INT.optionalFieldOf("priority", 0).forGetter(OtherFluidGenerator::getPriority)
            ).apply(instance, OtherFluidGenerator::new));

    public static final IFluidGenerator.Type<OtherFluidGenerator> TYPE = new IFluidGenerator.Type<>(CODEC, NAME);

    private final Fluid fluid;
    private final BlockState growth;
    private final RuleTest target;
    private final List<PositionRuleTest> positionTests;
    private final int priority;

    public OtherFluidGenerator(Fluid fluid, BlockState growth,
                               RuleTest target, List<PositionRuleTest> positionRuleTests, int priority) {
        this.fluid = fluid;
        this.growth = growth;
        this.target = target;
        this.positionTests = positionRuleTests;
        this.priority = priority;
    }

    @Override
    public Type<?> getType() {
        return TYPE;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public RuleTest getTarget() {
        return target;
    }

    public BlockState getGrowth() {
        return growth;
    }

    public List<PositionRuleTest> getPositionTests() {
        return positionTests;
    }

    public int getPriority() {
        return priority;
    }

    public Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {
        if (!this.positionTests.isEmpty()) {
            var biome = level.getBiome(pos);
            for (var a : this.positionTests) {
                if (!a.test(biome, pos, level)) return Optional.empty();
            }
        }
        for (Direction d : possibleFlowDir) {
            BlockPos p = pos.relative(d);
            BlockState state = neighborCache.computeIfAbsent(d, c -> level.getBlockState(p));
            if (target.test(state, level.random)) {
                level.setBlockAndUpdate(p, this.growth);
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}
