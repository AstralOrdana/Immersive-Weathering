package com.ordana.immersive_weathering.data.fluid_generators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IFluidGenerator extends Comparable<IFluidGenerator> {

    static void register() {
    }

    Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache);

    Fluid getFluid();

    Type<?> getType();

    int getPriority();

    //NYI
    default SoundEvent getSound() {
        return null;
    }

    ;

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
}
