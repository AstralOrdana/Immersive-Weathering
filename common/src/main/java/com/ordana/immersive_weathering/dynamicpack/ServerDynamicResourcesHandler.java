package com.ordana.immersive_weathering.dynamicpack;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagBuilder;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ServerDynamicResourcesHandler extends DynServerResourcesProvider {

    public static final ServerDynamicResourcesHandler INSTANCE = new ServerDynamicResourcesHandler();

    public ServerDynamicResourcesHandler() {
        super(new DynamicDataPack(ImmersiveWeathering.res("generated_pack")));
        this.dynamicPack.generateDebugResources = false;
    }

    @Override
    public Logger getLogger() {
        return ImmersiveWeathering.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;//RegistryConfigs.RESOURCE_PACK_SUPPORT.get();
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        StaticResource lootTable = StaticResource.getOrLog(manager, ResType.BLOCK_LOOT_TABLES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));
        StaticResource recipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));

        for (var e : ModBlocks.LEAF_PILES.entrySet()) {
            LeavesType leafType = e.getKey();
            if (!leafType.isVanilla()) {
                var v = e.getKey();

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                String leavesId = Utils.getID(leafType.leaves).toString();

                //TODO: use new system
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
        //tag
        SimpleTagBuilder tag = SimpleTagBuilder.of(ImmersiveWeathering.res("leaf_piles"));
        tag.addEntries(ModBlocks.LEAF_PILES.values());
        dynamicPack.addTag(tag, Registry.BLOCK_REGISTRY);
        dynamicPack.addTag(tag, Registry.ITEM_REGISTRY);

        dynamicPack.addTag(SimpleTagBuilder.of(ImmersiveWeathering.res("bark"))
                .addEntries(ModItems.BARK.values()), Registry.ITEM_REGISTRY);
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
