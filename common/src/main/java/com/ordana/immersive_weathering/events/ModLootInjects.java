package com.ordana.immersive_weathering.events;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.ForgeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;

import java.util.Locale;
import java.util.function.Consumer;

public class ModLootInjects {

    public static void onLootInject(LootTables lootManager, ResourceLocation name, Consumer<LootPool.Builder> builderConsumer) {

        if(name.equals(new ResourceLocation("something"))){

            //this is how you add an hardcoded one
              LootPool.Builder poolBuilder = LootPool.lootPool();
                 poolBuilder.setRolls(ConstantValue.exactly(1))

                    .add(LootItem.lootTableItem(ModBlocks.SOOT.get().asItem())
                            .when(LootItemRandomChanceCondition.randomChance(0.75f)))
                    .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)).build())
                    .withFunction(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1f, 0f)).build());

            table.withPool(poolBuilder.build());

            builderConsumer.accept(poolBuilder);


            //this is a json one

            String id = type.toString().toLowerCase(Locale.ROOT) + "_" + name;
            LootPool.Builder pool = LootPool.lootPool();

                  pool  .add(LootTableReference.lootTableReference(ImmersiveWeathering.res("inject/" + id)));
            //ForgeHelper.setPoolName(pool, "supp_" + name); TODO: add
            consumer.accept(pool);
        }
    }
}
