package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;


public class ModLootTables {
    private static final ResourceLocation BLAZE_ID = EntityType.BLAZE.getDefaultLootTable();
    private static final ResourceLocation PIG_ID = EntityType.PIG.getDefaultLootTable();
    private static final ResourceLocation HOGLIN_ID = EntityType.HOGLIN.getDefaultLootTable();

    private static final ResourceLocation SHIPWRECK_SUPPLY_ID = new ResourceLocation("minecraft", "chests/shipwreck_supply");
    private static final ResourceLocation SHIPWRECK_TREASURE_ID = new ResourceLocation("minecraft", "chests/shipwreck_treasure");
    private static final ResourceLocation BURIED_TREASURE_ID = new ResourceLocation("minecraft", "chests/buried_treasure");
    private static final ResourceLocation NETHER_FORTRESS_ID = new ResourceLocation("minecraft", "chests/nether_bridge");

    private static final ResourceLocation FARMER_GIFT_ID = new ResourceLocation("minecraft", "gameplay/hero_of_the_village/farmer_gift");
    private static final ResourceLocation TOOLSMITH_GIFT_ID = new ResourceLocation("minecraft", "gameplay/hero_of_the_village/toolsmith_gift");
    private static final ResourceLocation WEAPONSMITH_GIFT_ID = new ResourceLocation("minecraft", "gameplay/hero_of_the_village/weaponsmith_gift");
    private static final ResourceLocation ARMORER_GIFT_ID = new ResourceLocation("minecraft", "gameplay/hero_of_the_village/armorer_gift");
    private static final ResourceLocation MASON_GIFT_ID = new ResourceLocation("minecraft", "gameplay/hero_of_the_village/mason_gift");

    public static void registerLootTables() {

        //TODO: Use this
        /*
        LootTableEvents.MODIFY
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) ->
                {
                    //mobs
                    if (BLAZE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModBlocks.SOOT.get().asItem())
                                        .when(LootItemRandomChanceCondition.randomChance(0.75f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)).build())
                                .withFunction(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1f, 0f)).build());

                        table.withPool(poolBuilder.build());
                    }

                    //loot chests
                    if (SHIPWRECK_TREASURE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                        .when(LootItemRandomChanceCondition.randomChance(0.5f)))

                                .add(LootItem.lootTableItem(ModItems.GOLDEN_MOSS_CLUMP)
                                        .when(LootItemRandomChanceCondition.randomChance(0.75f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (SHIPWRECK_SUPPLY_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.MOSS_CLUMP)
                                        .when(LootItemRandomChanceCondition.randomChance(0.75f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(3.0f, 5.0f)).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (BURIED_TREASURE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                        .when(LootItemRandomChanceCondition.randomChance(0.75f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (NETHER_FORTRESS_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(Items.NETHER_BRICK)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(5.0f, 11.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (FARMER_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.MULCH_BLOCK)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(ModItems.HUMUS)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 5.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (ARMORER_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.STEEL_WOOL)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (TOOLSMITH_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL))
                                .add(LootItem.lootTableItem(ModItems.STEEL_WOOL))
                                .add(LootItem.lootTableItem(Items.HONEYCOMB))
                                .add(LootItem.lootTableItem(Items.SHEARS));

                        table.withPool(poolBuilder.build());
                    }
                    if (WEAPONSMITH_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.ICE_SICKLE)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (MASON_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .setRolls(ConstantValue.exactly(1))

                                .add(LootItem.lootTableItem(ModItems.STONE_BRICK)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(ModItems.DEEPSLATE_BRICK)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(ModItems.PLATE_IRON)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(ModItems.CUT_IRON)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(ModItems.VITRIFIED_SAND)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .add(LootItem.lootTableItem(Items.HONEYCOMB)
                                        .when(LootItemRandomChanceCondition.randomChance(0.25f)))
                                .withFunction(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 7.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                }
        );

         */
    }
}