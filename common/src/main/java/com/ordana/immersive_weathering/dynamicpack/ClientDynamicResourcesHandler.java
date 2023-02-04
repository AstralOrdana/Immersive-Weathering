package com.ordana.immersive_weathering.dynamicpack;

import com.mojang.blaze3d.platform.NativeImage;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.assets.LangBuilder;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesProvider;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.resources.textures.Palette;
import net.mehvahdjukaar.moonlight.api.resources.textures.PaletteColor;
import net.mehvahdjukaar.moonlight.api.resources.textures.Respriter;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClientDynamicResourcesHandler extends DynClientResourcesProvider {

    public static final ClientDynamicResourcesHandler INSTANCE = new ClientDynamicResourcesHandler();

    public ClientDynamicResourcesHandler() {
        super(new DynamicTexturePack(ImmersiveWeathering.res("generated_pack")));
        this.dynamicPack.generateDebugResources = PlatformHelper.isDev() || CommonConfigs.DEBUG_RESOURCES.get();
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

        //particles
        {
            StaticResource leafParticle = StaticResource.getOrLog(manager,
                    ResType.PARTICLES.getPath(ImmersiveWeathering.res("oak_leaf")));

            ModParticles.FALLING_LEAVES_PARTICLES.forEach((leafType,particle)->{

                String particleId = Registry.PARTICLE_TYPE.getKey(particle).getPath();
                try {
                    addSimilarJsonResource(manager, leafParticle, "oak_leaf", particleId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Particle for {} : {}", particle, ex);
                }
            });
        }

        //------leaf piles------
        {

            StaticResource lpBlockState = StaticResource.getOrLog(manager,
                    ResType.BLOCKSTATES.getPath(ImmersiveWeathering.res("oak_leaf_pile")));
            StaticResource lpModel1 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height1")));
            StaticResource lpModel2 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height2")));
            StaticResource lpModel4 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height4")));
            StaticResource lpModel6 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height6")));
            StaticResource lpModel8 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height8")));
            StaticResource lpModel10 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height10")));
            StaticResource lpModel12 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height12")));
            StaticResource lpModel14 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height14")));
            StaticResource lpModel16 = StaticResource.getOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("leaf_piles/oak_leaf_pile_height16")));

            StaticResource lpItemModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(ImmersiveWeathering.res("oak_leaf_pile")));

            ModBlocks.LEAF_PILES.forEach((leafType, pile) -> {
                if (leafType.isVanilla()) return;

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";

                try {
                    addSimilarJsonResource(manager,lpBlockState, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile blockstate definition for {} : {}", pile, ex);
                }

                try {
                    addSimilarJsonResource(manager,lpItemModel, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile item model for {} : {}", pile, ex);
                }

                //models
                try {
                    ResourceLocation leavesTexture;
                    try {
                        leavesTexture = RPUtils.findFirstBlockTextureLocation(manager, leafType.leaves, (s) -> true);
                    } catch (Exception exception) {
                        getLogger().warn("Failed to find texture for Leaf Pile {}, using oak one instead", pile);
                        leavesTexture = RPUtils.findFirstBlockTextureLocation(manager, Blocks.OAK_LEAVES, (s) -> true);
                    }
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
                    getLogger().error("Failed to generate Leaf Pile model for {} : {}", pile, ex);
                }
            });
        }

        //bark
        {
            StaticResource itemModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(ImmersiveWeathering.res("oak_bark")));

            ModItems.BARK.forEach((woodType, bark) -> {
                if (!woodType.isVanilla()) {

                    String id = Utils.getID(bark).getPath();

                    try {
                        addSimilarJsonResource(manager,itemModel, "oak_bark", id);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Bark item model for {} : {}", bark, ex);
                    }
                }
            });
        }
    }

    public void addLeafPilesModel(StaticResource resource, String id, ResourceLocation texturePath) {
        String string = new String(resource.data, StandardCharsets.UTF_8);

        String path = resource.location.getPath().replace("oak_leaf_pile", id);

        string = string.replace("immersive_weathering:block/light_oak_leaves", texturePath.toString());
        string = string.replace("immersive_weathering:block/medium_oak_leaves", texturePath.toString());
        string = string.replace("heavy_oak_leaves", id.replace("/", "/heavy_"));

        //adds modified under my namespace
        ResourceLocation newRes = ImmersiveWeathering.res(path);
        dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
    }


    //-------------resource pack dependant textures-------------

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {

        //leaf particle textures
        try (TextureImage template = TextureImage.open(manager, ImmersiveWeathering.res("particle/oak_leaf_0"));
             TextureImage template1 = TextureImage.open(manager, ImmersiveWeathering.res("particle/oak_leaf_1"))) {

            Respriter respriter = Respriter.of(template);
            Respriter respriter1 = Respriter.of(template1);

            ModParticles.FALLING_LEAVES_PARTICLES.forEach((type, particle) -> {
                if (type.isVanilla()) return;

                String path = type.getNamespace() + "/" + type.getTypeName();

                try (TextureImage baseTexture = TextureImage.open(manager,
                        RPUtils.findFirstBlockTextureLocation(manager, type.leaves))) {

                    {
                        ResourceLocation textureRes = ImmersiveWeathering.res(
                                String.format("particle/%s_leaf_0", path));
                        if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                            Palette targetPalette = Palette.fromImage(baseTexture);
                            TextureImage newImage = respriter.recolor(targetPalette);

                            dynamicPack.addAndCloseTexture(textureRes, newImage);
                        }
                    }

                    {
                        ResourceLocation textureRes = ImmersiveWeathering.res(
                                String.format("particle/%s_leaf_1", path));
                        if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                            Palette targetPalette = Palette.fromImage(baseTexture);
                            TextureImage newImage = respriter1.recolor(targetPalette);

                            dynamicPack.addAndCloseTexture(textureRes, newImage);
                        }
                    }

                } catch (Exception ex) {
                    getLogger().error("Fail to generate Leaf Particle texture for type {} : {}", type, ex);
                }
            });
        } catch (Exception ex) {
            getLogger().error("Could not generate any Leaf Particle texture : ", ex);
        }

        //heavy leaves textures
        ModBlocks.LEAF_PILES.forEach((type, pile) -> {
            if (type.isVanilla()) return;

            String path = type.getNamespace() + "/heavy_" + type.getTypeName() + "_leaf_pile";

            try (TextureImage baseTexture = TextureImage.open(manager, RPUtils.findFirstBlockTextureLocation(manager, type.leaves))) {

                ResourceLocation textureRes = ImmersiveWeathering.res(
                        String.format("block/%s", path));
                if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                    Palette targetPalette = Palette.fromImage(baseTexture);
                    if (targetPalette.getDarkest().occurrence > 5) {
                        targetPalette.increaseDown();
                    }
                    var dark = targetPalette.getDarkest();

                    baseTexture.removeAlpha(dark.value());

                    dynamicPack.addAndCloseTexture(textureRes, baseTexture);
                }
            } catch (Exception ex) {
                getLogger().error("Could not generate heavy leaf pile texture for type {}", type, ex);
            }
        });

        //bark textures
        try (TextureImage template = TextureImage.open(manager, ImmersiveWeathering.res("item/bark_template"))) {

            ModItems.BARK.forEach((type, bark) -> {

                if (type.isVanilla()) return;

                ResourceLocation textureRes = ImmersiveWeathering.res(
                        "item/" + Utils.getID(bark).getPath());
                if (!alreadyHasTextureAtLocation(manager, textureRes)) {

                    try (TextureImage logTexture = TextureImage.open(manager,
                            RPUtils.findFirstBlockTextureLocation(manager, type.log, s -> !s.contains("top")))) {
                        Palette palette = Palette.fromImage(logTexture);
                        //PaletteColor average = palette.calculateAverage();
                        palette.increaseDown();
                        PaletteColor dark = palette.getDarkest();
                        assert template.imageWidth() <= logTexture.imageWidth() && template.imageHeight() <= logTexture.imageHeight();
                        TextureImage newImage = template.makeCopy();
                        var logImage = logTexture.getImage();
                        newImage.forEachFrame((i, x, y) -> {
                            var image = newImage.getImage();
                            int darkBorder = image.getPixelRGBA(x, y);
                            if (darkBorder == -1) {
                                image.setPixelRGBA(x, y, NativeImage.combine(0, 0, 0, 0));
                            } else if (NativeImage.getA(darkBorder) == 0) {
                                image.setPixelRGBA(x, y, logImage.getPixelRGBA(x, y));
                            } else {
                                //HCLColor bc = new RGBColor(darkBorder).asHCL();
                                //image.setPixelRGBA(x, y, BaseColor.mixColors(dark.hcl(), average.hcl(), bc.asHCL()).asRGB().toInt());
                                image.setPixelRGBA(x, y, dark.value());
                            }
                        });

                        dynamicPack.addAndCloseTexture(textureRes, newImage);
                    } catch (Exception ex) {
                        getLogger().error("Failed to find log texture for bark {}", type, ex);
                    }
                }
            });
        } catch (Exception e) {
            getLogger().error("Could not generate any Bark texture : ", e);
        }
    }

    @Override
    public void addDynamicTranslations(AfterLanguageLoadEvent lang) {
        ModItems.BARK.forEach((type, bark) -> {
            LangBuilder.addDynamicEntry(lang, "item.immersive_weathering.bark", type, bark);
        });
        ModBlocks.LEAF_PILES.forEach((type, leaf) -> {
            LangBuilder.addDynamicEntry(lang, "block.immersive_weathering.leaf_pile", type, leaf);
        });
    }

}