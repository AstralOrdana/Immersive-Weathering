package com.ordana.immersive_weathering.data.position_tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModPositionRuleTests {

    public static void register(){}

    private static final Map<String, IPositionRuleTest.Type<?>> TYPES;

    static {
        var b = new HashMap<String , IPositionRuleTest.Type<?>>();

        b.put(TemperatureMatchTest.TYPE.name(), TemperatureMatchTest.TYPE);
        b.put(PrecipitationTest.TYPE.name(), PrecipitationTest.TYPE);
        b.put(PosRandomTest.TYPE.name(), PosRandomTest.TYPE);
        b.put(OrTest.TYPE.name(), OrTest.TYPE);
        b.put(NandTest.TYPE.name(), NandTest.TYPE);
        b.put(AndTest.TYPE.name(), AndTest.TYPE);
        b.put(IsDayTest.TYPE.name(), IsDayTest.TYPE);
        b.put(HeightTest.TYPE.name(), HeightTest.TYPE);
        b.put(EntityTest.TYPE.name(), EntityTest.TYPE);
        b.put(LightTest.TYPE.name(), LightTest.TYPE);
        b.put(BiomeSetMatchTest.TYPE.name(), BiomeSetMatchTest.TYPE);
        b.put(BlockTest.TYPE.name(), BlockTest.TYPE);

        TYPES = b;
    }

    public static <B extends IPositionRuleTest.Type<?>> B register(B newType){
        TYPES.put(newType.name(), newType);
        return newType;
    }


    public static Optional<? extends IPositionRuleTest.Type<? extends IPositionRuleTest>> get(String name) {
        return Optional.ofNullable(TYPES.get(name));
    }

}
