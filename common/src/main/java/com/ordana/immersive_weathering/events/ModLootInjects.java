package com.ordana.immersive_weathering.events;

import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.ForgeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.function.Consumer;

public class ModLootInjects {

    public static void onLootInject(LootTables lootManager, ResourceLocation name, Consumer<LootPool.Builder> builderConsumer) {

        if (name.equals(EntityType.BLAZE.getDefaultLootTable())) {
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "blaze_ash";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
            {
                LootPool.Builder pool = LootPool.lootPool();
                String id = "delete";
                pool.add(LootTableReference.lootTableReference(ImmersiveWeathering.res("injects/" + id)));
                ForgeHelper.setPoolName(pool, "IW_" + id);
                builderConsumer.accept(pool);
            }
        }

    }
}
