package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public interface CrackableMossable extends Mossable, Crackable {

    @Override
    default <T extends Enum<?>> Optional<SpreadingPatchBlock<T>> getPatchSpreader(Class<T> weatheringClass) {
        if (weatheringClass == MossLevel.class) {
            return Optional.of((SpreadingPatchBlock<T>) getMossSpreader());
        } else if (weatheringClass == CrackLevel.class) {
            return Optional.of((SpreadingPatchBlock<T>) getCrackSpreader());
        }
        return Optional.empty();
    }

    @Override
    default boolean shouldWeather(BlockState state, BlockPos pos, Level level) {
        return Mossable.super.shouldWeather(state, pos, level) ||
                Crackable.super.shouldWeather(state, pos, level);
    }

}
