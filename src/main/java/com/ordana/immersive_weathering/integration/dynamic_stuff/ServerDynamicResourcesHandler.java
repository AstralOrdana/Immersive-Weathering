package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.mehvahdjukaar.selene.resourcepack.DynamicDataPack;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ServerDynamicResourcesHandler {

    public static final DynamicDataPack DYNAMIC_DATA_PACK =
            new DynamicDataPack(ImmersiveWeathering.res("virtual_resourcepack"));
/*
    //fired on mod setup
    public static void registerBus(IEventBus forgeBus) {
        DYNAMIC_DATA_PACK.registerPack(forgeBus);
        FMLJavaModLoadingContext.get().getModEventBus()
                .addListener(ServerDynamicResourcesHandler::generateAssets);
        //TODO: fix tags not working
        DYNAMIC_DATA_PACK.generateDebugResources = !FMLLoader.isProduction();
    }

    public static void generateAssets(final FMLCommonSetupEvent event) {

        Stopwatch watch = Stopwatch.createStarted();

        //hanging signs
        {
            List<ResourceLocation> signs = new ArrayList<>();

            //loot table
            for (var r : DynamicRegistrationHandler.LEAF_PILES.keySet()) {
                DYNAMIC_DATA_PACK.addSimpleBlockLootTable(r);
                signs.add(r.getRegistryName());

                makeHangingSignRecipe(r.woodType, DYNAMIC_DATA_PACK::addRecipe);
            }
            //tag
            DYNAMIC_DATA_PACK.addTag(ImmersiveWeathering.res("hanging_signs"), signs, Registry.BLOCK_REGISTRY);
            DYNAMIC_DATA_PACK.addTag(ImmersiveWeathering.res("hanging_signs"), signs, Registry.ITEM_REGISTRY);
        }

        ImmersiveWeathering.LOGGER.info("Generated runtime data resources in: {} seconds", watch.elapsed().toSeconds());
    }


    public static void makeConditionalWoodRec(FinishedRecipe r, WoodType wood, Consumer<FinishedRecipe> consumer, String name) {

        ConditionalRecipe.builder().addCondition(new OptionalRecipeCondition(name))
                .addCondition(new ModLoadedCondition(wood.getNamespace()))
                .addRecipe(r)
                .generateAdvancement()
                .build(consumer, "supplementaries", name + "_" + wood.getAppendableId());
    }

    private static ResourceLocation getPlankRegName(WoodType wood) {
        return new ResourceLocation(wood.getNamespace(), wood.getWoodName() + "_planks");
    }

    private static ResourceLocation getSignRegName(WoodType wood) {
        return new ResourceLocation(wood.getNamespace(), wood.getWoodName() + "_sign");
    }

    private static void makeSignPostRecipe(WoodType wood, Consumer<FinishedRecipe> consumer) {
        try {
            Item plank = wood.plankBlock.asItem();
            Preconditions.checkArgument(plank != Items.AIR);

            Item sign = ForgeRegistries.ITEMS.getValue(getSignRegName(wood));

            if (sign != null && sign != Items.AIR) {
                ShapelessRecipeBuilder.shapeless(ModRegistry.SIGN_POST_ITEMS.get(wood), 2)
                        .requires(sign)
                        .group(RegistryConstants.SIGN_POST_NAME)
                        .unlockedBy("has_plank", InventoryChangeTrigger.TriggerInstance.hasItems(plank))
                        //.build(consumer);
                        .save((s) -> makeConditionalWoodRec(s, wood, consumer, RegistryConstants.SIGN_POST_NAME)); //
            } else {
                ShapedRecipeBuilder.shaped(ModRegistry.SIGN_POST_ITEMS.get(wood), 3)
                        .pattern("   ")
                        .pattern("222")
                        .pattern(" 1 ")
                        .define('1', Items.STICK)
                        .define('2', plank)
                        .group(RegistryConstants.SIGN_POST_NAME)
                        .unlockedBy("has_plank", InventoryChangeTrigger.TriggerInstance.hasItems(plank))
                        //.build(consumer);
                        .save((s) -> makeConditionalWoodRec(s, wood, consumer, RegistryConstants.SIGN_POST_NAME)); //
            }
        } catch (Exception ignored) {
            Supplementaries.LOGGER.error("Failed to generate sign post recipe for wood type {}", wood);
        }
    }

    private static void makeHangingSignRecipe(WoodType wood, Consumer<FinishedRecipe> consumer) {
        try {
            Item plank = wood.plankBlock.asItem();
            Preconditions.checkArgument(plank != Items.AIR);
            ShapedRecipeBuilder.shaped(ModRegistry.HANGING_SIGNS.get(wood), 2)
                    .pattern("010")
                    .pattern("222")
                    .pattern("222")
                    .define('0', Items.IRON_NUGGET)
                    .define('1', Items.STICK)
                    .define('2', plank)
                    .group(RegistryConstants.HANGING_SIGN_NAME)
                    .unlockedBy("has_plank", InventoryChangeTrigger.TriggerInstance.hasItems(plank))
                    //.build(consumer);
                    .save((s) -> makeConditionalWoodRec(s, wood, consumer, RegistryConstants.HANGING_SIGN_NAME)); //

        } catch (Exception ignored) {
            Supplementaries.LOGGER.error("Failed to generate hanging sign recipe for wood type {}", wood);
        }
    }
*/

}
