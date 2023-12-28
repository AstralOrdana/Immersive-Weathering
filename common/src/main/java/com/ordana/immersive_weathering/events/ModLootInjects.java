package com.ordana.immersive_weathering.events;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class ModLootInjects {

    public static void onLootInject(RegHelper.LootInjectEvent event) {

        ResourceLocation name = event.getTable();

        if (name.equals(EntityType.BLAZE.getDefaultLootTable())) {
            event.addTableReference(ImmersiveWeathering.res("injects/blaze_ash"));
        }
        //same here
        if (name.equals(EntityType.PIG.getDefaultLootTable())) {
            event.addTableReference(ImmersiveWeathering.res("injects/pig_tallow"));
        }
        if (name.equals(EntityType.HOGLIN.getDefaultLootTable())) {
            event.addTableReference(ImmersiveWeathering.res("injects/hoglin_tallow"));
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/buried_treasure"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/buried_treasure"));
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/shipwreck_supply"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/shipwreck_supply"));
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/shipwreck_treasure"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/shipwreck_treasure"));
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/farmer_gift"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/farmer_gift"));
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/mason_gift"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/mason_gift"));
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/toolsmith_gift"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/toolsmith_gift"));
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/weaponsmith_gift"))) {
            event.addTableReference(ImmersiveWeathering.res("injects/weaponsmith_gift"));
        }


    }
}
