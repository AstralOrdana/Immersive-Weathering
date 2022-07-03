package com.ordana.immersive_weathering.forge.dynamic;

import com.mojang.blaze3d.platform.NativeImage;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.forge.RegistryConfigs;
import net.mehvahdjukaar.selene.block_set.BlockType;
import net.mehvahdjukaar.selene.client.asset_generators.LangBuilder;
import net.mehvahdjukaar.selene.client.asset_generators.textures.Palette;
import net.mehvahdjukaar.selene.client.asset_generators.textures.PaletteColor;
import net.mehvahdjukaar.selene.client.asset_generators.textures.Respriter;
import net.mehvahdjukaar.selene.client.asset_generators.textures.TextureImage;
import net.mehvahdjukaar.selene.resourcepack.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ClientDynamicResourcesHandler extends RPAwareDynamicTextureProvider {

    public ClientDynamicResourcesHandler() {
        super(new DynamicTexturePack(ImmersiveWeathering.res("generated_pack")));
        this.dynamicPack.generateDebugResources = false;
    }

    @Override
    public void register(IEventBus bus) {
        super.register(bus);
    }

    @Override
    public Logger getLogger() {
        return ImmersiveWeathering.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return RegistryConfigs.RESOURCE_PACK_SUPPORT.get();
    }

    @Override
    public void generateStaticAssetsOnStartup(ResourceManager manager) {
        //generate static resources
        this.dynamicPack.generateDebugResources = false;//ClientConfigs.SAVE_GENERATED_RESOURCES.get();
        // LangBuilder langBuilder = new LangBuilder();

        //------leaf piles------
        {

            StaticResource leafParticle = StaticResource.getOrLog(manager,
                    ResType.PARTICLES.getPath(ImmersiveWeathering.res("oak_leaf")));

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

            ModDynamicRegistry.LEAF_TO_TYPE.forEach((pile, leafType) -> {
                if (leafType.isVanilla()) return;

                String path = leafType.getNamespace() + "/" + leafType.getTypeName();
                String id = path + "_leaf_pile";
                String particleId = path + "_leaf";
                // langBuilder.addEntry(pile, leafType.getVariantReadableName("leaf_pile"));

                try {
                    dynamicPack.addSimilarJsonResource(lpBlockState, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile blockstate definition for {} : {}", pile, ex);
                }

                try {
                    dynamicPack.addSimilarJsonResource(lpItemModel, "oak_leaf_pile", id);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Pile item model for {} : {}", pile, ex);
                }

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

                try {
                    dynamicPack.addSimilarJsonResource(leafParticle, "oak_leaf", particleId);
                } catch (Exception ex) {
                    getLogger().error("Failed to generate Leaf Particle for {} : {}", pile, ex);
                }

            });
        }
        //bark
        {
            StaticResource itemModel = StaticResource.getOrLog(manager,
                    ResType.ITEM_MODELS.getPath(ImmersiveWeathering.res("oak_bark")));

            ModDynamicRegistry.MODDED_BARK.forEach((woodType, bark) -> {
                if (!woodType.isVanilla()) {

                    String id = bark.getRegistryName().getPath();

                    //  langBuilder.addEntry(bark, woodType.getVariantReadableName("bark"));

                    try {
                        dynamicPack.addSimilarJsonResource(itemModel, "oak_bark", id);
                    } catch (Exception ex) {
                        getLogger().error("Failed to generate Bark item model for {} : {}", bark, ex);
                    }
                }
            });
        }

        //only needed for datagen. remove later
        /*
        {
            //slabs
            List<Block> vs = new ArrayList<>();
            StaticResource bs = getResOrLog(manager,
                    ResType.BLOCKSTATES.getPath(ImmersiveWeathering.res("cut_iron_vertical_slab")));
            StaticResource model = getResOrLog(manager,
                    ResType.BLOCK_MODELS.getPath(ImmersiveWeathering.res("cracked_end_stone_vertical_slab")));
            StaticResource im = getResOrLog(manager,
                    ResType.ITEM_MODELS.getPath(ImmersiveWeathering.res("cut_iron_vertical_slab")));

            for (Field f : ModBlocks.class.getDeclaredFields()) {
                try {
                    if (RegistryObject.class.isAssignableFrom(f.getType()) &&
                    f.getName().toLowerCase(Locale.ROOT).contains("vertical_slab")) {
                        vs.add(((RegistryObject<Block>)f.get(null)).get());
                    }
                } catch (Exception ignored) {
                }
            }

            for(var b : vs){
                String name = b.getRegistryName().getPath();
                langBuilder.addEntry(b, LangBuilder.getReadableName(name));
                dynamicPack.addSimilarJsonResource(im, "cut_iron_vertical_slab", name);

                {
                    var s = name.replace("_vertical_slab", "");

                    //bs
                    String string = new String(model.data, StandardCharsets.UTF_8);
                    String path = model.location.getPath().replace("cracked_end_stone", s);
                    s = s.replace("tile","tiles");
                    s =s.replace("brick","bricks");
                    string = string.replace("cracked_end_stone", s);

                    //adds modified under my namespace
                    ResourceLocation newRes = ImmersiveWeathering.res(path);
                    dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
                }
                {

                    //bs
                    String string = new String(bs.data, StandardCharsets.UTF_8);
                    String path = bs.location.getPath().replace("cut_iron_vertical_slab", name);
                    string = string.replace("cut_iron", name.replace("_vertical_slab", ""));

                    //adds modified under my namespace
                    ResourceLocation newRes = ImmersiveWeathering.res(path);
                    dynamicPack.addBytes(newRes, string.getBytes(), ResType.GENERIC);
                }

            }


        }
        */
        // dynamicPack.addLang(ImmersiveWeathering.res("en_us"), langBuilder.build());


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

            ModDynamicRegistry.LEAF_TO_TYPE.forEach((pile, type) -> {
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
        ModDynamicRegistry.LEAF_TO_TYPE.forEach((pile, type) -> {
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

            ModDynamicRegistry.MODDED_BARK.forEach((type, bark) -> {

                if (type.isVanilla()) return;

                ResourceLocation textureRes = ImmersiveWeathering.res(
                        "item/" + bark.getRegistryName().getPath());
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
        ModDynamicRegistry.MODDED_BARK.forEach((type, bark) -> {
            LangBuilder.addDynamicEntry(lang, "item.immersive_weathering.bark",(BlockType) type, bark);
        });
        ModDynamicRegistry.LEAF_TO_TYPE.forEach((leaf, type) -> {
            LangBuilder.addDynamicEntry(lang, "block.immersive_weathering.leaf_pile",(BlockType) type, leaf);
        });
    }

}
