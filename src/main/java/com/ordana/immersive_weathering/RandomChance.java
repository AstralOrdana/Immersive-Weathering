package com.ordana.immersive_weathering;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Random;

public class RandomChance {

    private float failChange = 1;

    public void addRoll(float successChance) {
        failChange *= (1 - successChance);
    }

    public boolean success(Random random) {
        return random.nextFloat() < (1 - failChange);
    }

    //we could have this as an integer to have more control of how far we are from the weathering source


    /*
        "CampfireMixin",
    "FireMixin",
    "MagmaBlockMixin",
    "SpreadableBlockMixin",
    "NyliumBlockMixin",
    "SnowyBlockMixin",
    "FernBlockMixin",
    "RootedDirtBlockMixin",
    "BlocksMixin",
    "WaterMixin",
    "SaplingMixin",
    "FarmlandMixin",
    "CropBlockMixin",
    "RootsBlockMixin",
    "FungusBlockMixin",
    "SproutsBlockMixin",
    "CoralBlockMixin",
    "LightningEntityMixin",
    "LeavesMixin",
    "IceMixin",
    "ComposterMixin"
     */
}
