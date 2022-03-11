package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.SpreadingPatchBlock;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
    default boolean shouldWeather(BlockState state, BlockPos pos, World level) {
        return Mossable.super.shouldWeather(state, pos, level) ||
                Crackable.super.shouldWeather(state, pos, level);
    }

}
