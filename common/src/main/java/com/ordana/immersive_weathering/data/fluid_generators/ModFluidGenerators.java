package com.ordana.immersive_weathering.data.fluid_generators;

import com.ordana.immersive_weathering.data.fluid_generators.builtin.BurnMossGenerator;
import com.ordana.immersive_weathering.data.position_tests.IPositionRuleTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModFluidGenerators {

    public static void register(){}

    private static final Map<String, IFluidGenerator.Type<?>> TYPES;

    static {
        TYPES = new HashMap<>();
        TYPES.put(SelfFluidGenerator.TYPE.name(), SelfFluidGenerator.TYPE);
        TYPES.put(OtherFluidGenerator.TYPE.name(), OtherFluidGenerator.TYPE);
        TYPES.put(BurnMossGenerator.TYPE.name(), BurnMossGenerator.TYPE);
    }


    public static Optional<? extends IFluidGenerator.Type<? extends IFluidGenerator>> get(String name) {
        return Optional.ofNullable(TYPES.get(name));
    }

    public static <B extends IFluidGenerator.Type<?>> B register(B newType){
        TYPES.put(newType.name(), newType);
        return newType;
    }


}