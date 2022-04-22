package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.blocks.LeafPilesRegistry;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.mehvahdjukaar.selene.resourcepack.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
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
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ServerDynamicResourcesHandler extends RPAwareDynamicDataProvider {

    public ServerDynamicResourcesHandler() {
        super(new DynamicDataPack(ImmersiveWeathering.res("virtual_resourcepack")));
        this.dynamicPack.generateDebugResources = false;
    }

    @Override
    public Logger getLogger() {
        return ImmersiveWeathering.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        StaticResource lootTable = getResOrLog(manager, ResType.BLOCK_LOOT_TABLES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));
        StaticResource recipe = getResOrLog(manager, ResType.RECIPES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));


        for (var e : ModDynamicRegistry.LEAF_TO_TYPE.entrySet()) {
            LeavesType leafType = e.getValue();
            if (!leafType.isVanilla()) {
                var v = e.getKey();

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                String leavesId =leafType.leaves.getRegistryName().toString();

                try {
                    addLeafPileJson(Objects.requireNonNull(lootTable), id, leavesId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile loot table for {} : {}", v, ex);
                }

                try {
                    addLeafPileJson(Objects.requireNonNull(recipe), id, leavesId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile recipe for {} : {}", v, ex);
                }

            }
        }
    }

    @Override
    public void generateStaticAssetsOnStartup(ResourceManager manager) {

        List<ResourceLocation> leafPiles = new ArrayList<>();

        //loot table
        for (var r : ModDynamicRegistry.LEAF_TO_TYPE.entrySet()) {
            leafPiles.add(r.getKey().getRegistryName());
        }
        //tag
        dynamicPack.addTag(ImmersiveWeathering.res("leaf_piles"), leafPiles, Registry.BLOCK_REGISTRY);
        dynamicPack.addTag(ImmersiveWeathering.res("leaf_piles"), leafPiles, Registry.ITEM_REGISTRY);
    }

    public void addLeafPileJson(StaticResource resource, String id, String leafBlockId) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("oak_leaf_pile", id);
        string = string.replace("minecraft:oak_leaves", leafBlockId);

        //adds modified under my namespace
        ResourceLocation newRes = ImmersiveWeathering.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }

}
