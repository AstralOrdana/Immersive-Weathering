package net.mehvahdjukaar.moonlight.api.platform.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.function.Consumer;

public class ClientPlatformHelperImpl {

    public static void registerRenderType(Block block, RenderType type) {
        //from 0.64 we should register render types in out model json
        //TODO: remove
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }

    public static void registerItemProperty(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.register(item, name, property);
    }

    public static void addParticleRegistration(Consumer<ClientPlatformHelper.ParticleEvent> eventListener) {
        Consumer<ParticleFactoryRegisterEvent> eventConsumer = event -> {
            W w = new W(event);
            eventListener.accept(w::register);
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    private record W(ParticleFactoryRegisterEvent event) {
        public <T extends ParticleOptions> void register(ParticleType<T> type, ClientPlatformHelper.ParticleFactory<T> provider) {
            Minecraft.getInstance().particleEngine.register(type, provider::create);
        }
    }

    public static void addEntityRenderersRegistration(Consumer<ClientPlatformHelper.EntityRendererEvent> eventListener) {
        Consumer<EntityRenderersEvent.RegisterRenderers> eventConsumer = event ->
                eventListener.accept(event::registerEntityRenderer);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addBlockEntityRenderersRegistration(Consumer<ClientPlatformHelper.BlockEntityRendererEvent> eventListener) {
        Consumer<EntityRenderersEvent.RegisterRenderers> eventConsumer = event ->
                eventListener.accept(event::registerBlockEntityRenderer);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addBlockColorsRegistration(Consumer<ClientPlatformHelper.BlockColorEvent> eventListener) {
        Consumer<ColorHandlerEvent.Block> eventConsumer = event -> {
            eventListener.accept(new ClientPlatformHelper.BlockColorEvent() {
                @Override
                public void register(BlockColor color, Block... block) {
                    event.getBlockColors().register(color, block);
                }

                @Override
                public int getColor(BlockState block, BlockAndTintGetter level, BlockPos pos, int tint) {
                    return event.getBlockColors().getColor(block, level, pos, tint);
                }
            });
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addItemColorsRegistration(Consumer<ClientPlatformHelper.ItemColorEvent> eventListener) {
        Consumer<ColorHandlerEvent.Item> eventConsumer = event -> {
            eventListener.accept(new ClientPlatformHelper.ItemColorEvent() {
                @Override
                public void register(ItemColor color, ItemLike... items) {
                    event.getItemColors().register(color, items);
                }

                @Override
                public int getColor(ItemStack stack, int tint) {
                    return event.getItemColors().getColor(stack, tint);
                }
            });
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addAtlasTextureCallback(ResourceLocation atlasLocation, Consumer<ClientPlatformHelper.AtlasTextureEvent> eventListener) {
        Consumer<TextureStitchEvent.Pre> eventConsumer = event -> {
            if (event.getAtlas().location().equals(atlasLocation)) {
                eventListener.accept(event::addSprite);
            }
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static void addClientReloadListener(PreparableReloadListener listener, ResourceLocation location) {
        ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager())
                .registerReloadListener(listener);
    }

    public static void addModelLayerRegistration(Consumer<ClientPlatformHelper.ModelLayerEvent> eventListener) {
        Consumer<EntityRenderersEvent.RegisterLayerDefinitions> eventConsumer = event -> {
            eventListener.accept(event::registerLayerDefinition);
        };
        FMLJavaModLoadingContext.get().getModEventBus().addListener(eventConsumer);
    }

    public static int getPixelRGBA(TextureAtlasSprite sprite, int frameIndex, int x, int y) {
        return sprite.getPixelRGBA(frameIndex, x, y);
    }

    public static BakedModel getModel(ModelManager modelManager, ResourceLocation modelLocation) {
        return modelManager.getModel(modelLocation);
    }

    @Nullable
    public static Path getModIcon(String modId) {
        var m = ModList.get().getModContainerById(modId);
        if (m.isPresent()) {
            IModInfo mod = m.get().getModInfo();
            IModFile file = mod.getOwningFile().getFile();

            var logo = mod.getLogoFile().orElse(null);
            if (logo != null && file != null) {
                Path logoPath = file.findResource(logo);
                if (Files.exists(logoPath)) {
                    return logoPath;
                }
            }
        }
        return null;
    }


    public static void renderBlock(FallingBlockEntity entity, PoseStack matrixStack, MultiBufferSource buffer, BlockState blockstate, Level world, BlockPos blockpos, BlockRenderDispatcher modelRenderer) {
        for (RenderType type : RenderType.chunkBufferLayers()) {
            //TODO. move to lib
            if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
                ForgeHooksClient.setRenderType(type);
                modelRenderer.getModelRenderer().tesselateBlock(world, modelRenderer.getBlockModel(blockstate), blockstate, blockpos, matrixStack,
                        buffer.getBuffer(type), false, new Random(), blockstate.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY);
            }
        }
        ForgeHooksClient.setRenderType(null);
    }


}
