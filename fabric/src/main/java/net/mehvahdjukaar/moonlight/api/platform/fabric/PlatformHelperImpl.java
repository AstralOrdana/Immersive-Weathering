package net.mehvahdjukaar.moonlight.api.platform.fabric;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.fabric.ImmersiveWeatheringFabric;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PlatformHelperImpl {

    public static PlatformHelper.Platform getPlatform() {
        return PlatformHelper.Platform.FABRIC;
    }

    public static boolean isData() {
        return false;
    }

    public static boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    @Nullable
    public static <T> Field findField(Class<? super T> clazz, String fieldName) {
        return null;
    }

    @Nullable
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return null;
    }


    public static boolean isMobGriefingOn(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    public static boolean isAreaLoaded(LevelReader level, BlockPos pos, int maxRange) {
        return level.hasChunksAt(pos.offset(-maxRange, -maxRange, -maxRange), pos.offset(maxRange, maxRange, maxRange));
    }

    public static int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        return FlammableBlockRegistry.getDefaultInstance().get(state.getBlock()).getBurnChance();
    }

    public static PlatformHelper.Env getEnv() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? PlatformHelper.Env.CLIENT : PlatformHelper.Env.SERVER;
    }

    @Nullable
    public static FoodProperties getFoodProperties(Item food, ItemStack stack, Player player) {
        return food.getFoodProperties();
    }

    public static boolean isDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }


    public static int getBurnTime(ItemStack stack) {
        var v = FuelRegistry.INSTANCE.get(stack.getItem());
        if (v == null) return 0;
        return v;
    }

    @Nullable
    public static MinecraftServer getCurrentServer() {
        return ImmersiveWeatheringFabric.currentServer;
    }

    public static Path getGamePath() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static CreativeModeTab createModTab(ResourceLocation name, Supplier<ItemStack> icon, boolean hasSearchBar,
                                               @Nullable BiConsumer<List<ItemStack>, CreativeModeTab> fillItemList) {
        var t = FabricItemGroupBuilder.create(name);
        t.appendItems(fillItemList).icon(icon);
        return t.build();
    }

    public static SpawnEggItem newSpawnEgg(Supplier<? extends EntityType<? extends Mob>> entityType, int color, int outerColor, Item.Properties properties) {
        return new SpawnEggItem(entityType.get(), color, outerColor, properties);
    }

    public static Path getModFilePath(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).get().getRootPaths().get(0);
    }

    public static FlowerPotBlock newFlowerPot(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> supplier, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(supplier.get(), properties);
    }

    public static <T extends BlockEntity> BlockEntityType<T> newBlockEntityType(PlatformHelper.BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        return FabricBlockEntityTypeBuilder.create(blockEntitySupplier::create, validBlocks).build();
    }

    public static <E extends Entity> EntityType<E> newEntityType(String name,
                                                                 EntityType.EntityFactory<E> factory, MobCategory category, float width, float height,
                                                                 int clientTrackingRange, boolean velocityUpdates, int updateInterval) {
        return FabricEntityTypeBuilder.create(category, factory)
                .dimensions(EntityDimensions.scalable(width, height))
                .trackedUpdateRate(updateInterval)
                .trackRangeChunks(clientTrackingRange)
                .forceTrackedVelocityUpdates(velocityUpdates).build();
    }

    public static void addServerReloadListener(PreparableReloadListener listener, ResourceLocation name) {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
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


}
