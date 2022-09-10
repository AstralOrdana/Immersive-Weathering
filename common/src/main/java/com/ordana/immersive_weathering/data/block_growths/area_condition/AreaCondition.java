package com.ordana.immersive_weathering.data.block_growths.area_condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.ordana.immersive_weathering.data.block_growths.growths.ConfigurableBlockGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public interface AreaCondition {

    AreaCondition EMPTY = new AreaCondition() {
        @Override
        public boolean test(BlockPos pos, Level level, ConfigurableBlockGrowth config) {
            return true;
        }

        @Override
        public int getMaxRange() {
            return 0;
        }

        @Override
        public AreaConditionType<?> getType() {
            return null;
        }
    };

    Codec<AreaCondition> CODEC = AreaConditionType.CODEC
            .dispatch("type", AreaCondition::getType, AreaConditionType::codec);


    Map<String, ? extends AreaConditionType<? extends AreaCondition>> TYPES = new HashMap<>() {{
        put(AreaCheck.TYPE.name, AreaCheck.TYPE);
        put(NeighborCheck.TYPE.name, NeighborCheck.TYPE);
    }};


    static Optional<? extends AreaConditionType<? extends AreaCondition>> get(String name) {
        var r = TYPES.get(name);
        return r == null ? Optional.empty() : Optional.of(r);
    }


    boolean test(BlockPos pos, Level level, ConfigurableBlockGrowth config);

    int getMaxRange();

    AreaConditionType<?> getType();


    record AreaConditionType<T extends AreaCondition>(Codec<T> codec, String name) {
        public static Codec<AreaConditionType<?>> CODEC = Codec.STRING.flatXmap(
                (name) -> get(name).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown Area Condition: " + name)),
                (t) -> DataResult.success(t.name()));
    }

}
