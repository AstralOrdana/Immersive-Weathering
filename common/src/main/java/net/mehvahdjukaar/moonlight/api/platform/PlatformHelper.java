package net.mehvahdjukaar.moonlight.api.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Helper class dedicated to platform independent common utility methods
 */
public class PlatformHelper {

    @ExpectPlatform
    public static boolean isDev() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isData() {
        throw new AssertionError();
    }


    public enum Platform {
        FORGE, FABRIC;

        public boolean isForge() {
            return this == FORGE;
        }

        public boolean isFabric() {
            return this == FABRIC;
        }

        public void ifForge(Runnable runnable) {
            if (isForge()) runnable.run();
        }

        public void ifFabric(Runnable runnable) {
            if (isFabric()) runnable.run();
        }
    }

    @ExpectPlatform
    public static Platform getPlatform() {
        throw new AssertionError();
    }

    public enum Env {
        CLIENT, SERVER;

        public boolean isClient() {
            return this == CLIENT;
        }

        public boolean isServer() {
            return this == SERVER;
        }

        public void ifClient(Runnable runnable) {
            if (isClient()) runnable.run();
        }

        public void ifServer(Runnable runnable) {
            if (isServer()) runnable.run();
        }
    }

    @ExpectPlatform
    public static Env getEnv() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getGamePath() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getModFilePath(String modId) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static <T> Field findField(Class<? super T> clazz, String fieldName) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static MinecraftServer getCurrentServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String name) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static boolean isMobGriefingOn(Level level, Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isAreaLoaded(LevelReader level, BlockPos pos, int maxRange) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        throw new AssertionError();
    }

    @ExpectPlatform
    @Nullable
    public static FoodProperties getFoodProperties(Item food, ItemStack stack, Player player) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static int getBurnTime(ItemStack stack) {
        throw new AssertionError();
    }


    public static CreativeModeTab createModTab(ResourceLocation name, Supplier<ItemStack> icon, boolean hasSearchBar){
        return createModTab(name ,icon, hasSearchBar, null);
    }

    @ExpectPlatform
    public static CreativeModeTab createModTab(ResourceLocation name, Supplier<ItemStack> icon, boolean hasSearchBar,
                                               @Nullable BiConsumer<List<ItemStack>, CreativeModeTab> itemSupplier){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SpawnEggItem newSpawnEgg(Supplier<? extends EntityType<? extends Mob>> entityType, int color, int outerColor, Item.Properties properties){
        throw new AssertionError();
    }
    @ExpectPlatform
    public static FlowerPotBlock newFlowerPot(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> supplier, BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> newBlockEntityType(BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntitySupplier<T extends BlockEntity> {
        @NotNull T create(BlockPos pos, BlockState state);
    }
    @ExpectPlatform
    public static <E extends Entity> EntityType<E> newEntityType(String name,
            EntityType.EntityFactory<E> factory, MobCategory category, float width, float height,
            int clientTrackingRange, boolean velocityUpdates, int updateInterval) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addServerReloadListener(PreparableReloadListener listener, ResourceLocation location) {
        throw new AssertionError();
    }


}
