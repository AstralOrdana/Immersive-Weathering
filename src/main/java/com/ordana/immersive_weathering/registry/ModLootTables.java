package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
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

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) ->
                {
                    //mobs
                    if (BLAZE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.SOOT)
                                        .conditionally(RandomChanceLootCondition.builder(0.75f)))
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build())
                                .withFunction(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(1f, 0f)).build());

                        table.withPool(poolBuilder.build());
                    }

                    //loot chests
                    if (SHIPWRECK_TREASURE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                        .conditionally(RandomChanceLootCondition.builder(0.5f)))

                                .with(ItemEntry.builder(ModItems.GOLDEN_MOSS_CLUMP)
                                        .conditionally(RandomChanceLootCondition.builder(0.75f)))
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (SHIPWRECK_SUPPLY_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.MOSS_CLUMP)
                                        .conditionally(RandomChanceLootCondition.builder(0.75f)))
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 5.0f)).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (BURIED_TREASURE_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP)
                                        .conditionally(RandomChanceLootCondition.builder(0.75f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (NETHER_FORTRESS_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(Items.NETHER_BRICK)
                                        .conditionally(RandomChanceLootCondition.builder(0.25f)))
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(5.0f, 11.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (FARMER_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.MULCH_BLOCK)
                                        .conditionally(RandomChanceLootCondition.builder(0.25f)))
                                .with(ItemEntry.builder(ModItems.HUMUS)
                                        .conditionally(RandomChanceLootCondition.builder(0.25f)))
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                    if (ARMORER_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.STEEL_WOOL)
                                        .conditionally(RandomChanceLootCondition.builder(0.25f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (TOOLSMITH_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(Items.FLINT_AND_STEEL))
                                .with(ItemEntry.builder(ModItems.STEEL_WOOL))
                                .with(ItemEntry.builder(Items.HONEYCOMB))
                                .with(ItemEntry.builder(Items.SHEARS));

                        table.withPool(poolBuilder.build());
                    }
                    if (WEAPONSMITH_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))

                                .with(ItemEntry.builder(ModItems.ICE_SICKLE)
                                        .conditionally(RandomChanceLootCondition.builder(0.25f)));

                        table.withPool(poolBuilder.build());
                    }
                    if (MASON_GIFT_ID.equals(id)) {
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
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
                                .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f), false).build());

                        table.withPool(poolBuilder.build());
                    }
                }
        );
    }
}
