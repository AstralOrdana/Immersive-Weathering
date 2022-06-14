package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTables {
    private static final Identifier BLAZE_ID = EntityType.BLAZE.getLootTableId();
    private static final Identifier PIG_ID = EntityType.PIG.getLootTableId();
    private static final Identifier HOGLIN_ID = EntityType.HOGLIN.getLootTableId();

    private static final Identifier SHIPWRECK_SUPPLY_ID = new Identifier("minecraft", "chests/shipwreck_supply");
    private static final Identifier SHIPWRECK_TREASURE_ID = new Identifier("minecraft", "chests/shipwreck_treasure");
    private static final Identifier BURIED_TREASURE_ID = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier NETHER_FORTRESS_ID = new Identifier("minecraft", "chests/nether_bridge");

    private static final Identifier FARMER_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/farmer_gift");
    private static final Identifier TOOLSMITH_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/toolsmith_gift");
    private static final Identifier WEAPONSMITH_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/weaponsmith_gift");
    private static final Identifier ARMORER_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/armorer_gift");
    private static final Identifier MASON_GIFT_ID = new Identifier("minecraft", "gameplay/hero_of_the_village/mason_gift");

    public static void registerLootTables() {

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            //mobs
            if (BLAZE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.SOOT)
                                .conditionally(RandomChanceLootCondition.builder(0.75f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build())
                        .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(1f, 0f)).build());

                tableBuilder.pool(poolBuilder);
            }

            //loot chests
            if (SHIPWRECK_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                .conditionally(RandomChanceLootCondition.builder(0.5f)))

                        .with(ItemEntry.builder(ModItems.GOLDEN_MOSS_CLUMP)
                                .conditionally(RandomChanceLootCondition.builder(0.75f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f), false).build());

                tableBuilder.pool(poolBuilder);
            }
            if (SHIPWRECK_SUPPLY_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.MOSS_CLUMP)
                                .conditionally(RandomChanceLootCondition.builder(0.75f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 5.0f)).build());

                tableBuilder.pool(poolBuilder);
            }
            if (BURIED_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                .conditionally(RandomChanceLootCondition.builder(0.75f)));

                tableBuilder.pool(poolBuilder);
            }
            if (NETHER_FORTRESS_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(Items.NETHER_BRICK)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(5.0f, 11.0f), false).build());

                tableBuilder.pool(poolBuilder);
            }
            if (FARMER_GIFT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.MULCH_BLOCK)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(ModItems.HUMUS)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f), false).build());

                tableBuilder.pool(poolBuilder);
            }
            if (ARMORER_GIFT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.STEEL_WOOL)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)));

                tableBuilder.pool(poolBuilder);
            }
            if (TOOLSMITH_GIFT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(Items.FLINT_AND_STEEL))
                        .with(ItemEntry.builder(ModItems.STEEL_WOOL))
                        .with(ItemEntry.builder(Items.HONEYCOMB))
                        .with(ItemEntry.builder(Items.SHEARS));

                tableBuilder.pool(poolBuilder);
            }
            if (WEAPONSMITH_GIFT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.ICE_SICKLE)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)));

                tableBuilder.pool(poolBuilder);
            }
            if (MASON_GIFT_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))

                        .with(ItemEntry.builder(ModItems.STONE_BRICK)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(ModItems.DEEPSLATE_BRICK)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(ModItems.PLATE_IRON)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(ModItems.CUT_IRON)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(ModItems.VITRIFIED_SAND)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .with(ItemEntry.builder(Items.HONEYCOMB)
                                .conditionally(RandomChanceLootCondition.builder(0.25f)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f), false).build());

                tableBuilder.pool(poolBuilder);
            }
        });
    }
}
