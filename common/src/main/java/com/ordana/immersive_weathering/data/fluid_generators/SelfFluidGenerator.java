package com.ordana.immersive_weathering.data.fluid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.position_tests.PositionRuleTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class SelfFluidGenerator implements IFluidGenerator {

    public static final Codec<SelfFluidGenerator> CODEC = RecordCodecBuilder.<SelfFluidGenerator>create(
            instance -> instance.group(
                    Registry.FLUID.byNameCodec().fieldOf("fluid").forGetter(SelfFluidGenerator::getFluid),
                    BlockState.CODEC.fieldOf("generate").forGetter(SelfFluidGenerator::getGrowth),
                    Codec.simpleMap(Side.CODEC, RuleTest.CODEC, StringRepresentable.keys(Side.values()))
                            .fieldOf("neighbors").forGetter(SelfFluidGenerator::getNeighborBlocks),
                    PositionRuleTest.CODEC.listOf().optionalFieldOf("additional_checks", List.of()).forGetter(SelfFluidGenerator::getPositionTests),
                    Codec.INT.optionalFieldOf("priority", 0).forGetter(SelfFluidGenerator::getPriority)
            ).apply(instance, SelfFluidGenerator::new)).comapFlatMap(arg -> {
        if (arg.neighborBlocks.isEmpty()) {
            return DataResult.error("Neighbor predicate map must not be empty");
        }
        return DataResult.success(arg);
    }, Function.identity());

    public static final IFluidGenerator.Type<SelfFluidGenerator> TYPE = new Type<>(CODEC, "target_self");

    private final Fluid fluid;
    private final BlockState growth;
    private final Map<Side, RuleTest> neighborBlocks;
    private final List<PositionRuleTest> positionTests;
    private final int priority;

    public SelfFluidGenerator(Fluid fluid, BlockState growth, Map<Side, RuleTest> neighborBlocks,
                             List<PositionRuleTest> positionRuleTests, int priority) {
        this.fluid = fluid;
        this.growth = growth;
        this.neighborBlocks = neighborBlocks;
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

    public BlockState getGrowth() {
        return growth;
    }

    public Map<Side, RuleTest> getNeighborBlocks() {
        return neighborBlocks;
    }

    public List<PositionRuleTest> getPositionTests() {
        return positionTests;
    }

    public int getPriority() {
        return priority;
    }

    public Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {
        if (this.neighborBlocks.isEmpty()) return Optional.empty();
        BlockPos targetPos = pos;
        for (var e : this.neighborBlocks.entrySet()) {
            Side s = e.getKey();
            switch (s) {
                case SIDES -> {
                    for (var d : possibleFlowDir) {
                        targetPos = null;
                        if (d.getAxis().isHorizontal()) {
                            BlockPos side = pos.relative(d);
                            BlockState state = neighborCache.computeIfAbsent(d, p -> level.getBlockState(side));
                            if (e.getValue().test(state, level.random)) {
                                targetPos = pos;
                                break;
                            }
                        }
                    }
                }
                case UP -> {
                    Direction d = Direction.UP;
                    BlockState state = neighborCache.computeIfAbsent(d, p -> level.getBlockState(pos.relative(d)));
                    if (!e.getValue().test(state, level.random)) return Optional.empty();
                }
                case DOWN -> {
                    Direction d = Direction.DOWN;
                    BlockState state = neighborCache.computeIfAbsent(d, p -> level.getBlockState(pos.relative(d)));
                    if (!e.getValue().test(state, level.random)) return Optional.empty();
                }
            }

        }
        if (!this.positionTests.isEmpty()) {
            var biome = level.getBiome(pos);
            for (var a : this.positionTests) {
                if (!a.test(biome, pos, level)) return Optional.empty();
            }
        }

        if (targetPos != null) {
            level.setBlockAndUpdate(targetPos, this.growth);
            return Optional.of(targetPos);
        }
        return Optional.empty();
    }


    public enum Side implements StringRepresentable {
        SIDES("sides"), UP("up"), DOWN("down");

        private final String name;

        Side(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Nullable
        public static Side byName(String string) {
            return switch (string.toLowerCase(Locale.ROOT)) {
                default -> null;
                case "sides" -> SIDES;
                case "up" -> UP;
                case "down" -> DOWN;
            };
        }

        private static final Codec<Side> CODEC = StringRepresentable.fromEnum(Side::values, Side::byName);
    }
}
