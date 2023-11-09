package com.ordana.immersive_weathering.events;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.ForgeHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

public class ModLootInjects {

    public static void onLootInject(RegHelper.LootInjectEvent event) {

        ResourceLocation name = event.getTable();

        if (name.equals(EntityType.BLAZE.getDefaultLootTable())) {
            event.addTableReference(ImmersiveWeathering.res("injects/blaze_ash"));
        }
        //same here
        if (name.equals(EntityType.PIG.getDefaultLootTable())) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "pig_tallow";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(EntityType.HOGLIN.getDefaultLootTable())) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "hoglin_tallow";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/buried_treasure"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "buried_treasure";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/shipwreck_supply"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "shipwreck_supply";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "chests/shipwreck_treasure"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "shipwreck_treasure";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/farmer_gift"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "farmer_gift";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/mason_gift"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "mason_gift";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/toolsmith_gift"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "toolsmith_gift";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }
        if (name.equals(new ResourceLocation("minecraft", "gameplay/hero_of_the_village/weaponsmith_gift"))) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "weaponsmith_gift";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }


    }
