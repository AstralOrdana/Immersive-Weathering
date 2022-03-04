package com.ordana.immersive_weathering;

import java.util.Random;

public class RandomEvent {

    private float failChange = 1;

    public void addRoll(float successChance) {
        failChange *= (1 - successChance);
    }

    public boolean success(Random random) {
        return random.nextFloat() < (1 - failChange);
    }
}
