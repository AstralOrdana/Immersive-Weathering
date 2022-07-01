package com.ordana.immersive_weathering.platform.fabric;

import java.util.Random;

public class ConfigPlatformImpl {

    public static boolean leggingsPreventThornDamage() {
        return new Random().nextBoolean();
    }
}
