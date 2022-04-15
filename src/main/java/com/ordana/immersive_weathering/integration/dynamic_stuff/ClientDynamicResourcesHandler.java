package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.resourcepack.AssetGenerators;
import net.mehvahdjukaar.selene.resourcepack.DynamicTexturePack;
import net.mehvahdjukaar.selene.resourcepack.RPUtils;
import net.mehvahdjukaar.selene.resourcepack.RPUtils.ResType;
import net.mehvahdjukaar.selene.resourcepack.RPUtils.StaticResource;
import net.mehvahdjukaar.selene.resourcepack.ResourcePackAwareDynamicTextureProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClientDynamicResourcesHandler extends ResourcePackAwareDynamicTextureProvider {

    public static final ClientDynamicResourcesHandler INSTANCE = new ClientDynamicResourcesHandler();
    public static final DynamicTexturePack DYNAMIC_TEXTURE_PACK =
            new DynamicTexturePack(ImmersiveWeathering.res("virtual_resourcepack"));


    public static void registerBus(IEventBus bus) {
        DYNAMIC_TEXTURE_PACK.registerPack(bus);
        DYNAMIC_TEXTURE_PACK.generateDebugResources = true;
    }

    @Override
    public DynamicTexturePack getDynamicPack() {
        return DYNAMIC_TEXTURE_PACK;
    }

    @Override
    public Logger getLogger() {
        return ImmersiveWeathering.LOGGER;
    }

    @Override
    public boolean hasTexturePackSupport() {
        return false;
    }


    @Override
    public void generateStaticAssetsOnStartup(ResourceManager manager) {
        //generate static resources

        AssetGenerators.LangBuilder langBuilder = new AssetGenerators.LangBuilder();

        //------leaf piles------
        {

            StaticResource lpBlockState = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("oak_leaf_pile"), ResType.BLOCKSTATES));
            StaticResource lpModel1 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height1"), ResType.BLOCK_MODELS));
            StaticResource lpModel2 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height2"), ResType.BLOCK_MODELS));
            StaticResource lpModel4 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height4"), ResType.BLOCK_MODELS));
            StaticResource lpModel6 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height6"), ResType.BLOCK_MODELS));
            StaticResource lpModel8 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height8"), ResType.BLOCK_MODELS));
            StaticResource lpModel10 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height10"), ResType.BLOCK_MODELS));
            StaticResource lpModel12 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height12"), ResType.BLOCK_MODELS));
            StaticResource lpModel14 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height14"), ResType.BLOCK_MODELS));
            StaticResource lpModel16 = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height16"), ResType.BLOCK_MODELS));

            StaticResource lpItemModel = getResOrLog(manager,
                    RPUtils.resPath(ImmersiveWeathering.res("oak_leaf_pile"), ResType.ITEM_MODELS));

            for (var e : ModDynamicRegistry.LEAF_TO_TYPE.entrySet()) {
                LeavesType leafType = e.getValue();
                if (!leafType.isVanilla()) {
                    var v = e.getKey();

                    String id = leafType.getNamespace() + "/" + leafType.getTypeName() + "_leaf_pile";
                    langBuilder.addEntry(v, leafType.getNameForTranslation("leaf_pile"));

                    try {
                        DYNAMIC_TEXTURE_PACK.addSimilarJsonResource(lpBlockState, "oak_leaf_pile", id);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Leaf Pile blockstate definition for {} : {}", v, ex);
                    }

                    try {
                        DYNAMIC_TEXTURE_PACK.addSimilarJsonResource(lpItemModel, "oak_leaf_pile", id);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Leaf Pile item model for {} : {}", v, ex);
                    }

                    try {
                        String leavesTexture;
                        leavesTexture = RPUtils.findFirstBlockTextureLocation(manager, leafType.leaves, s -> !s.contains("top"));
                        addLeafPilesModel(Objects.requireNonNull(lpModel1), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel2), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel4), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel6), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel8), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel10), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel12), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel14), id, leavesTexture);
                        addLeafPilesModel(Objects.requireNonNull(lpModel16), id, leavesTexture);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Leaf Pile model for {} : {}", v, ex);
                    }
                }
            }
        }

        DYNAMIC_TEXTURE_PACK.addLang(ImmersiveWeathering.res("en_us"), langBuilder.build());
    }

    public void addLeafPilesModel(RPUtils.StaticResource resource, String id, String texturePath) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile",id);

        string = string.replace("immersive_weathering:block/light_oak_leaves", texturePath);
        string = string.replace("immersive_weathering:block/medium_oak_leaves", texturePath);

        //adds modified under my namespace
        ResourceLocation newRes = ImmersiveWeathering.res(path);
        DYNAMIC_TEXTURE_PACK.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }


    //-------------resource pack dependant textures-------------

    @Override
    public void regenerateTextures(ResourceManager manager) {
    }

}
