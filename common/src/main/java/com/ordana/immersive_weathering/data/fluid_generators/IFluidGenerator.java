package com.ordana.immersive_weathering.data.fluid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public interface IFluidGenerator extends Comparable<IFluidGenerator> {

    static void register() {
    }

    Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache);

    Fluid getFluid();

    FluidType getFluidType();

    Type<?> getType();

    int getPriority();

    //NYI
    default SoundEvent getSound() {
        return null;
    }


    default int compareTo(@NotNull IFluidGenerator o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }


    Codec<IFluidGenerator> CODEC = Type.CODEC.dispatch("type", IFluidGenerator::getType, Type::codec);

    Map<String, IFluidGenerator.Type<?>> TYPES = new HashMap<>() {{
        put(SelfFluidGenerator.TYPE.name, SelfFluidGenerator.TYPE);
        put(OtherFluidGenerator.TYPE.name, OtherFluidGenerator.TYPE);
    }};

    static Optional<? extends IFluidGenerator.Type<? extends IFluidGenerator>> get(String name) {
        return Optional.ofNullable(TYPES.get(name));
    }

    record Type<T extends IFluidGenerator>(Codec<T> codec, String name) {
        private static final Codec<Type<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Fluid Generator type: " + name)),
                (t) -> DataResult.success(t.name()));

    }

    enum FluidType implements StringRepresentable {
        BOTH("both"),
        FLOWING("flowing"),
        STILL("still");

        public static final Codec<FluidType> CODEC = StringRepresentable.fromEnum(FluidType::values, FluidType::byName);
        private static final Map<String, FluidType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(FluidType::getName, (source) -> source));

        private final String name;

        FluidType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static FluidType byName(String s) {
            return BY_NAME.get(s);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public boolean isStill() {
        return this != FLOWING;
        }
        public boolean isFlowing() {
            return this != STILL;
        }
    }
}
