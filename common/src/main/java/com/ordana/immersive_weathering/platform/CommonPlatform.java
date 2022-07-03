package com.ordana.immersive_weathering.platform;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.configs.ConfigBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class CommonPlatform {


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
    public static Platform getPlatform(){
        throw new AssertionError();
    }

    public enum Env{
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
    public static Env getEnv(){
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


    @ExpectPlatform
    public static boolean isModLoaded(String name) {
        throw new AssertionError();
    }



    @ExpectPlatform
    public static boolean isMobGriefingOn(Level level, Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isAreaLoaded(Level level, BlockPos pos, int maxRange) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FlowingFluid getFlowingFluid(LiquidBlock block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Collection<LeafPileBlock> getLeafPiles() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ConfigBuilder getConfigBuilder(String name, ConfigBuilder.ConfigType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
        throw new AssertionError();
    }

}
