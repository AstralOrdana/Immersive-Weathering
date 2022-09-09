package net.mehvahdjukaar.moonlight.api.platform;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class dedicated to platform independent client utility methods
 */
public class ClientPlatformHelper {

    @ExpectPlatform
    public static void registerRenderType(Block block, RenderType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemProperty(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addClientReloadListener(PreparableReloadListener listener, ResourceLocation location) {
        throw new AssertionError();
    }

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface ParticleFactory<T extends ParticleOptions> {
        @NotNull ParticleProvider<T> create(SpriteSet spriteSet);
    }

    @FunctionalInterface
    public interface ParticleEvent {
        <P extends ParticleType<T>, T extends ParticleOptions> void register(P particleType, ParticleFactory<T> factory);
    }

    @ExpectPlatform
    public static void addParticleRegistration(Consumer<ParticleEvent> eventListener) {
        throw new AssertionError();
    }


    @FunctionalInterface
    public interface EntityRendererEvent {
        <E extends Entity> void register(EntityType<? extends E> entity, EntityRendererProvider<E> renderer);
    }

    @ExpectPlatform
    public static void addEntityRenderersRegistration(Consumer<EntityRendererEvent> eventListener) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntityRendererEvent {
        <E extends BlockEntity> void register(BlockEntityType<? extends E> blockEntity, BlockEntityRendererProvider<E> renderer);
    }

    @ExpectPlatform
    public static void addBlockEntityRenderersRegistration(Consumer<BlockEntityRendererEvent> eventListener) {
        throw new AssertionError();
    }

    public interface BlockColorEvent {
        void register(BlockColor color, Block... block);

        int getColor(BlockState block, BlockAndTintGetter level, BlockPos pos, int tint);
    }

    @ExpectPlatform
    public static void addBlockColorsRegistration(Consumer<BlockColorEvent> eventListener) {
        throw new AssertionError();
    }

    public interface ItemColorEvent {
        void register(ItemColor color, ItemLike... items);

        int getColor(ItemStack stack, int tint);
    }

    @ExpectPlatform
    public static void addItemColorsRegistration(Consumer<ItemColorEvent> eventListener) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface ModelLayerEvent {
        void register(ModelLayerLocation modelLayer, Supplier<LayerDefinition> provider);
    }

    @ExpectPlatform
    public static void addModelLayerRegistration(Consumer<ModelLayerEvent> eventListener) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface SpecialModelEvent {
        void register(ResourceLocation modelLocation);
    }

    @FunctionalInterface
    public interface TooltipComponentEvent {
        <T extends TooltipComponent> void register(Class<T> type, Function<? super T, ? extends ClientTooltipComponent> factory);
    }


    @FunctionalInterface
    public interface AtlasTextureEvent {
        void addSprite(ResourceLocation spriteLocation);
    }

    @ExpectPlatform
    public static void addAtlasTextureCallback(ResourceLocation atlasLocation, Consumer<AtlasTextureEvent> eventListener) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface KeyBindEvent {
        void register(KeyMapping keyMapping);
    }

    @ExpectPlatform
    public static Path getModIcon(String modId) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void renderBlock(FallingBlockEntity entity, PoseStack matrixStack, MultiBufferSource buffer, BlockState blockstate, Level world, BlockPos blockpos, BlockRenderDispatcher modelRenderer) {
    }
}