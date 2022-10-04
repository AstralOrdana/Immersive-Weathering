package net.mehvahdjukaar.moonlight.api.platform.fabric;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.RandomSource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

public class ClientPlatformHelperImpl {

    public static void registerRenderType(Block block, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, type);
    }

    public static void registerItemProperty(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.register(item, name, property);
    }

    public static void addParticleRegistration(Consumer<ClientPlatformHelper.ParticleEvent> eventListener) {
        eventListener.accept(ClientPlatformHelperImpl::registerParticle);
    }

    private static <P extends ParticleType<T>, T extends ParticleOptions> void registerParticle(P type, ClientPlatformHelper.ParticleFactory<T> registration) {
        ParticleFactoryRegistry.getInstance().register(type, registration::create);
    }

    public static void addEntityRenderersRegistration(Consumer<ClientPlatformHelper.EntityRendererEvent> eventListener) {
        eventListener.accept(EntityRendererRegistry::register);
    }

    public static void addBlockEntityRenderersRegistration(Consumer<ClientPlatformHelper.BlockEntityRendererEvent> eventListener) {
        eventListener.accept(BlockEntityRendererRegistry::register);
    }

    public static void addBlockColorsRegistration(Consumer<ClientPlatformHelper.BlockColorEvent> eventListener) {
        eventListener.accept(new ClientPlatformHelper.BlockColorEvent() {
            @Override
            public void register(BlockColor color, Block... block) {
                ColorProviderRegistry.BLOCK.register(color, block);
            }

            @Override
            public int getColor(BlockState block, BlockAndTintGetter level, BlockPos pos, int tint) {
                var c = ColorProviderRegistry.BLOCK.get(block.getBlock());
                return c == null ? -1 : c.getColor(block, level, pos, tint);
            }
        });
    }

    public static void addItemColorsRegistration(Consumer<ClientPlatformHelper.ItemColorEvent> eventListener) {
        eventListener.accept(new ClientPlatformHelper.ItemColorEvent() {
            @Override
            public void register(ItemColor color, ItemLike... items) {
                ColorProviderRegistry.ITEM.register(color, items);
            }

            @Override
            public int getColor(ItemStack stack, int tint) {
                var c = ColorProviderRegistry.ITEM.get(stack.getItem());
                return c == null ? -1 : c.getColor(stack, tint);
            }
        });
    }

    public static void addAtlasTextureCallback(ResourceLocation atlasLocation, Consumer<ClientPlatformHelper.AtlasTextureEvent> eventListener) {
        ClientSpriteRegistryCallback.event(atlasLocation).register(((atlasTexture, registry) -> {
            eventListener.accept(registry::register);
        }));
    }

    public static void addClientReloadListener(PreparableReloadListener listener, ResourceLocation name) {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return name;
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return listener.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });
    }


    public static void addModelLayerRegistration(Consumer<ClientPlatformHelper.ModelLayerEvent> eventListener) {
        eventListener.accept((a, b) -> EntityModelLayerRegistry.registerModelLayer(a, b::get));
    }

    public static void addSpecialModelRegistration(Consumer<ClientPlatformHelper.SpecialModelEvent> eventListener) {
        //this is shit and fires asynchronously on a random thread, usually worker main. unfit to use for dynamic dependand models
        //ModelLoadingRegistryImpl.INSTANCE.registerModelProvider((m, loader) -> eventListener.accept(loader::accept));
        MODEL_APPENDERS.add(eventListener);
    }

    private static final List<Consumer<ClientPlatformHelper.SpecialModelEvent>> MODEL_APPENDERS = new ArrayList<>();

    @ApiStatus.Internal
    public static void addSpecialModels(ClientPlatformHelper.SpecialModelEvent event) {
        MODEL_APPENDERS.forEach(a -> a.accept(event));
    }

    public static void addTooltipComponentRegistration(Consumer<ClientPlatformHelper.TooltipComponentEvent> eventListener) {
        eventListener.accept(ClientPlatformHelperImpl::tooltipReg);
    }

    private static <T extends TooltipComponent> void tooltipReg(Class<T> tClass, Function<? super T, ? extends ClientTooltipComponent> factory) {
        TooltipComponentCallback.EVENT.register(data -> tClass.isAssignableFrom(data.getClass()) ? factory.apply((T) data) : null);
    }


    public static void addKeyBindRegistration(Consumer<ClientPlatformHelper.KeyBindEvent> eventListener) {
        eventListener.accept(KeyBindingHelper::registerKeyBinding);
    }


    public static Path getModIcon(String modId) {
        var container = FabricLoader.getInstance().getModContainer(modId).get();
        return container.getMetadata().getIconPath(512).flatMap(container::findPath).orElse(null);
    }

    public static BlockModel parseBlockModel(JsonElement json) {
        return BlockModel.fromString(json.toString()); //sub optimal... too bad
    }


    public static void renderBlock(FallingBlockEntity entity, PoseStack poseStack, MultiBufferSource buffer, BlockState state, Level level, BlockPos pos, BlockRenderDispatcher modelRenderer) {
        BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
        blockRenderDispatcher.getModelRenderer().tesselateBlock(level, blockRenderDispatcher.getBlockModel(state), state, pos, poseStack, buffer.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(state)),
                false, new Random(), state.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY);
    }

}
