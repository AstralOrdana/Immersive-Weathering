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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class LiquidGenerator implements Comparable<LiquidGenerator> {

    public static final Codec<LiquidGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.FLUID.byNameCodec().fieldOf("fluid").forGetter(LiquidGenerator::getLiquid),
            BlockState.CODEC.fieldOf("generate").forGetter(LiquidGenerator::getGrowth),
            Codec.simpleMap(Side.CODEC, RuleTest.CODEC, StringRepresentable.keys(Side.values()))
                    .fieldOf("neighbors").forGetter(LiquidGenerator::getNeighborBlocks),
            RuleTest.CODEC.optionalFieldOf("target_other").forGetter(LiquidGenerator::targetsOther),
            PositionRuleTest.CODEC.listOf().optionalFieldOf("additional_checks", List.of()).forGetter(LiquidGenerator::getPositionTests),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(LiquidGenerator::getPriority)
    ).apply(instance, LiquidGenerator::new));

    private final Fluid owners;
    private final BlockState growth;
    private final Map<Side, RuleTest> neighborBlocks;
    private final Optional<RuleTest> targetOther;
    private final List<PositionRuleTest> positionTests;
    private final int priority;

    public LiquidGenerator(Fluid owner, BlockState growth, Map<Side, RuleTest> neighborBlocks,
                           Optional<RuleTest> targetOther, List<PositionRuleTest> positionRuleTests, int priority) {
        this.owners = owner;
        this.growth = growth;
        this.neighborBlocks = neighborBlocks;
        this.targetOther = targetOther;
        this.positionTests = positionRuleTests;
        this.priority = priority;
    }

    public Optional<RuleTest> targetsOther() {
        return targetOther;
    }

    public Fluid getLiquid() {
        return owners;
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

    @Override
    public int compareTo(@NotNull LiquidGenerator o) {
        return Integer.compare(this.priority, o.priority);
    }

    public Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {
        if(this.neighborBlocks.isEmpty())return Optional.empty();
        for (var e : this.neighborBlocks.entrySet()) {
            Side s = e.getKey();
            switch (s) {
                case SIDES -> {
                    for (var d : possibleFlowDir) {
                        BlockState state = neighborCache.computeIfAbsent(d, p -> level.getBlockState(pos.relative(d)));
                        if (!e.getValue().test(state, level.random)) return Optional.empty();
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

        if (targetOther.isPresent()) {
            for (Direction d : Direction.values()) {
                BlockPos p = pos.relative(d);
                BlockState state = neighborCache.computeIfAbsent(d, c -> level.getBlockState(p));
                if (targetOther.get().test(state, level.random)) {
                    level.setBlockAndUpdate(p, this.growth);
                    return Optional.of(p);
                }
            }
        } else {
            level.setBlockAndUpdate(pos, this.growth);
            return Optional.of(pos);
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
