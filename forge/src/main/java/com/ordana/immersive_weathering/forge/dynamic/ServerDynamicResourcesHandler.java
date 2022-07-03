package com.ordana.immersive_weathering.forge.dynamic;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.resourcepack.DynamicDataPack;
import net.mehvahdjukaar.selene.resourcepack.RPAwareDynamicDataProvider;
import net.mehvahdjukaar.selene.resourcepack.ResType;
import net.mehvahdjukaar.selene.resourcepack.StaticResource;
import net.mehvahdjukaar.selene.resourcepack.resources.TagBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ServerDynamicResourcesHandler extends RPAwareDynamicDataProvider {

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
        return false;
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        StaticResource lootTable = StaticResource.getOrLog(manager, ResType.BLOCK_LOOT_TABLES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));
        StaticResource recipe = StaticResource.getOrLog(manager, ResType.RECIPES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));

        for (var e : ModDynamicRegistry.LEAF_TO_TYPE.entrySet()) {
            LeavesType leafType = e.getValue();
            if (!leafType.isVanilla()) {
                var v = e.getKey();

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                String leavesId = leafType.leaves.getRegistryName().toString();

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
        TagBuilder leafPiles = TagBuilder.of(ImmersiveWeathering.res("leaf_piles"))
                .addEntries(ModDynamicRegistry.LEAF_TO_TYPE.keySet());
        dynamicPack.addTag(leafPiles, Registry.BLOCK_REGISTRY);
        dynamicPack.addTag(leafPiles, Registry.ITEM_REGISTRY);

        dynamicPack.addTag(TagBuilder.of(ImmersiveWeathering.res("bark"))
                .addEntries(ModDynamicRegistry.MODDED_BARK.values()), Registry.ITEM_REGISTRY);

        //only needed for datagen. remove later
        /*
        {
            //slabs
            List<Block> vs = new ArrayList<>();
            StaticResource loot = getResOrLog(manager,
                    ResType.BLOCK_LOOT_TABLES.getPath(ImmersiveWeathering.res("cut_iron_vertical_slab")));
            StaticResource r1 = getResOrLog(manager,
                    ResType.RECIPES.getPath(ImmersiveWeathering.res("cut_iron_vertical_slab")));
            StaticResource r2 = getResOrLog(manager,
                    ResType.RECIPES.getPath(ImmersiveWeathering.res("cut_iron_vertical_slab_to_slab")));
            StaticResource r3 = getResOrLog(manager,
                    ResType.RECIPES.getPath(ImmersiveWeathering.res("cracked_end_stone_vertical_slab_stonecutting")));

            for (Field f : ModBlocks.class.getDeclaredFields()) {
                try {
                    if (RegistryObject.class.isAssignableFrom(f.getType()) &&
                            f.getName().toLowerCase(Locale.ROOT).contains("vertical_slab")) {
                        vs.add(((RegistryObject<Block>) f.get(null)).get());
                    }
                } catch (Exception ignored) {
                }
            }

            for (var b : vs) {
                String name = b.getRegistryName().getPath();
                if(name.contains("cut_iron") || name.contains("plate_iron"))continue;
                String base = name.replace("_vertical_slab","");

                dynamicPack.addSimilarJsonResource(loot, "cut_iron_vertical_slab", name);
                dynamicPack.addSimilarJsonResource(r2, "cut_iron", base);
                dynamicPack.addSimilarJsonResource(r1, "cut_iron", base);
                dynamicPack.addSimilarJsonResource(r3, "cracked_end_stone", base);
            }
            dynamicPack.addTag(ImmersiveWeathering.res("vertical_slabs"),
                    vs.stream().map(ForgeRegistryEntry::getRegistryName).collect(Collectors.toList()),
                    Registry.BLOCK_REGISTRY);
        }
        */
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
