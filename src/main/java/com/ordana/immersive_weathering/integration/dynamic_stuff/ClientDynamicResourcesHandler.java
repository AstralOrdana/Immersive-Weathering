package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.mojang.blaze3d.platform.NativeImage;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesTypeRegistry;
import net.mehvahdjukaar.selene.resourcepack.*;
import net.mehvahdjukaar.selene.resourcepack.asset_generators.LangBuilder;
import net.mehvahdjukaar.selene.resourcepack.asset_generators.textures.Palette;
import net.mehvahdjukaar.selene.resourcepack.asset_generators.textures.Respriter;
import net.mehvahdjukaar.selene.resourcepack.asset_generators.textures.SpriteUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClientDynamicResourcesHandler extends RPAwareDynamicTextureProvider {

    public ClientDynamicResourcesHandler() {
        super(new DynamicTexturePack(ImmersiveWeathering.res("virtual_resourcepack")));
        this.dynamicPack.generateDebugResources = true;
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
    public void generateStaticAssetsOnStartup(ResourceManager manager) {
        //generate static resources

        LangBuilder langBuilder = new LangBuilder();

        //------leaf piles------
        {

            StaticResource leafParticle = getResOrLog(manager,
                    ResType.PARTICLES.getPath(ImmersiveWeathering.res("oak_leaf")));

            StaticResource lpBlockState = getResOrLog(manager,
                    ResType.BLOCKSTATES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));
            StaticResource lpModel1 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height1")));
            StaticResource lpModel2 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height2")));
            StaticResource lpModel4 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height4")));
            StaticResource lpModel6 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height6")));
            StaticResource lpModel8 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height8")));
            StaticResource lpModel10 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height10")));
            StaticResource lpModel12 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height12")));
            StaticResource lpModel14 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height14")));
            StaticResource lpModel16 = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height16")));

            StaticResource lpItemModel = getResOrLog(manager,
                    ResType.ITEM_MODELS.getPath(ImmersiveWeathering.res("oak_leaf_pile")));

            for (var e : ModDynamicRegistry.LEAF_TO_TYPE.entrySet()) {
                LeavesType leafType = e.getValue();
                if (!leafType.isVanilla()) {
                    var v = e.getKey();

                    String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                    String id = path + "_leaf_pile";
                    String particleId = path + "_leaf";
                    langBuilder.addEntry(v, leafType.getNameForTranslation("leaf_pile"));

                    try {
                        dynamicPack.addSimilarJsonResource(lpBlockState, "oak_leaf_pile", id);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Leaf Pile blockstate definition for {} : {}", v, ex);
                    }

                    try {
                        dynamicPack.addSimilarJsonResource(lpItemModel, "oak_leaf_pile", id);
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

                    try {
                        dynamicPack.addSimilarJsonResource(leafParticle, "oak_leaf", particleId);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Leaf Particle for {} : {}", v, ex);
                    }

                }
            }
        }

        dynamicPack.addLang(ImmersiveWeathering.res("en_us"), langBuilder.build());


    }

    public void addLeafPilesModel(StaticResource resource, String id, String texturePath) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("immersive_weathering:block/light_oak_leaves", texturePath);
        string = string.replace("immersive_weathering:block/medium_oak_leaves", texturePath);
        string = string.replace("heavy_oak_leaves", id.replace("/", "/heavy_"));

        //adds modified under my namespace
        ResourceLocation newRes = ImmersiveWeathering.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }


    //-------------resource pack dependant textures-------------

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        //leaf particle textures
        try (NativeImage template = SpriteUtils.readImage(manager, ImmersiveWeathering.res(
                "textures/particle/oak_leaf_0.png"));
             NativeImage template1 = SpriteUtils.readImage(manager, ImmersiveWeathering.res(
                     "textures/particle/oak_leaf_1.png"))) {

            Respriter respriter = new Respriter(template);
            Respriter respriter1 = new Respriter(template1);

            for (LeavesType type : LeavesTypeRegistry.LEAVES_TYPES.values()) {
                if (!type.isVanilla()) {

                    String path = type.getNamespace() + "/" + type.getTypeName();


                    try (NativeImage baseTexture = RPUtils.findFirstBlockTexture(manager, type.leaves)) {

                        {
                            ResourceLocation textureRes = ImmersiveWeathering.res(
                                    String.format("particle/%s_leaf_0", path));
                            if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                                Palette targetPalette = Palette.fromImage(baseTexture);
                                NativeImage newImage = respriter.recolorImage(targetPalette);

                                dynamicPack.addTexture(textureRes, newImage);
                            }
                        }

                        {
                            ResourceLocation textureRes = ImmersiveWeathering.res(
                                    String.format("particle/%s_leaf_1", path));
                            if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                                Palette targetPalette = Palette.fromImage(baseTexture);
                                NativeImage newImage = respriter1.recolorImage(targetPalette);

                                dynamicPack.addTexture(textureRes, newImage);
                            }
                        }

                    } catch (Exception ex) {
                        //getLogger().error("Could not find sign texture for wood type {}. Using plank texture : {}", wood, ex);
                    }

                }
            }
        } catch (Exception ex) {
            getLogger().error("Could not generate any Leaf Particle texture : ", ex);
        }

        //heavy leaves textures

        //leaf particle textures

        for (LeavesType type : LeavesTypeRegistry.LEAVES_TYPES.values()) {
            if (!type.isVanilla()) {

                String path = type.getNamespace() + "/heavy_" + type.getTypeName() + "_leaf_pile";

                try (NativeImage baseTexture = RPUtils.findFirstBlockTexture(manager, type.leaves)) {

                    {
                        ResourceLocation textureRes = ImmersiveWeathering.res(
                                String.format("block/%s", path));
                        if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                            Palette targetPalette = Palette.fromImage(baseTexture);
                            if (targetPalette.getDarkest().occurrence > 5) {
                                targetPalette.increaseDown();
                            }
                            var dark = targetPalette.getDarkest();


                            for (int x = 0; x < baseTexture.getWidth(); ++x) {
                                for (int y = 0; y < baseTexture.getHeight(); ++y) {
                                    int oldValue = baseTexture.getPixelRGBA(x, y);
                                    if (NativeImage.getA(oldValue) == 0) {
                                        baseTexture.setPixelRGBA(x, y, dark.color);
                                    }
                                }
                            }

                            dynamicPack.addTexture(textureRes, baseTexture);
                        }
                    }

                } catch (Exception ex) {
                    getLogger().error("Could not generate heavy leaf pile texture for type {}", type, ex);
                }

            }
        }

    }

}
