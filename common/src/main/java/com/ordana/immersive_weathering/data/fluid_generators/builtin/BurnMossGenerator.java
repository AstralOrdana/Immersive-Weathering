package com.ordana.immersive_weathering.data.fluid_generators.builtin;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.blocks.Weatherable;
import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.data.fluid_generators.IFluidGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BurnMossGenerator implements IFluidGenerator {


    public static final BurnMossGenerator INSTANCE = new BurnMossGenerator();

    public static final Codec<BurnMossGenerator> CODEC = Codec.unit(() -> INSTANCE);

    public static final IFluidGenerator.Type<BurnMossGenerator> TYPE = new IFluidGenerator.Type<>(CODEC, "burn_moss");


    @Override
    public Optional<BlockPos> tryGenerating(List<Direction> possibleFlowDir, BlockPos pos, Level level, Map<Direction, BlockState> neighborCache) {

        for (Direction d : possibleFlowDir) {
            BlockPos p = pos.relative(d);
            BlockState state = neighborCache.computeIfAbsent(d, c -> level.getBlockState(p));
            var s = Mossable.getUnaffectedMossBlock(state);
            if (s != state) {
                level.setBlockAndUpdate(p, s.setValue(Mossable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    @Override
    public Fluid getFluid() {
        return Fluids.LAVA;
    }

    @Override
    public FluidType getFluidType() {
        return FluidType.BOTH;
    }

    @Override
    public Type<?> getType() {
        return TYPE;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
