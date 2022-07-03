package com.ordana.immersive_weathering.platform.forge;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.configs.ConfigBuilder;
import com.ordana.immersive_weathering.forge.ForgeConfigBuilder;
import com.ordana.immersive_weathering.forge.dynamic.ModDynamicRegistry;
import com.ordana.immersive_weathering.platform.CommonPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommonPlatformImpl {

    public static CommonPlatform.Platform getPlatform() {
        return CommonPlatform.Platform.FORGE;
    }

    public static boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    @Nullable
    public static <T> Field findField(Class<? super T> clazz, String fieldName) {
        try {
            return ObfuscationReflectionHelper.findField(clazz, fieldName);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return ObfuscationReflectionHelper.findMethod(clazz, methodName, parameterTypes);
        } catch (Exception e) {
            return null;
        }
    }

    public static void addExtraFloweryBlocks(ImmutableBiMap.Builder<Block, Block> builder) {

        Block a = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_hedge"));
        Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_hedge"));
        if (a != null && b != null) {
            builder.put(a, b);
        }
        Block c = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:flowering_azalea_leaf_carpet"));
        Block d = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("quark:azalea_leaf_carpet"));
        if (c != null && d != null) {
            builder.put(c, d);
        }


    }

    public static void addExtraMossyBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        //if (IntegrationHandler.quark) {
        //    QuarkPlugin.addAllVerticalSlabs(builder);
        // }
    }

    public static void addExtraCrackedBlocks(ImmutableBiMap.Builder<Block, Block> builder) {
        // if (IntegrationHandler.quark) {
        //    QuarkPlugin.addAllVerticalSlabs(builder);
        // }
    }

    public static boolean isMobGriefingOn(Level level, Entity entity) {
        return ForgeEventFactory.getMobGriefingEvent(level, entity);
    }

    public static boolean isAreaLoaded(Level level, BlockPos pos, int maxRange) {
        return level.isAreaLoaded(pos, maxRange);
    }

    public static int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        return state.getFlammability(level, pos, face);
    }

    public static FlowingFluid getFlowingFluid(LiquidBlock block) {
        return block.getFluid();
    }

    public static ConfigBuilder getConfigBuilder(String name, ConfigBuilder.ConfigType type) {
        return new ForgeConfigBuilder(name, type);
    }

    public static Collection<LeafPileBlock> getLeafPiles() {
        return List.of();
    }

    public static void addFeatureToBiome(GenerationStep.Decoration step, TagKey<Biome> tagKey, ResourceKey<PlacedFeature> feature) {

        Consumer<BiomeLoadingEvent> c = e -> {

            var biome = ForgeRegistries.BIOMES.getHolder(e.getName());
            if (biome.isPresent() && biome.get().is(tagKey)) {
                Holder<PlacedFeature> featureHolder = BuiltinRegistries.PLACED_FEATURE.getHolderOrThrow(feature);
                e.getGeneration().addFeature(step, featureHolder);
            }
        };
        MinecraftForge.EVENT_BUS.addListener(c);
    }

    public static CommonPlatform.Env getEnv() {
        return FMLEnvironment.dist == Dist.CLIENT ? CommonPlatform.Env.CLIENT : CommonPlatform.Env.SERVER;
    }

    @Nullable
    public static Map<Block, LeafPileBlock> getDynamicLeafPiles() {
        return ModDynamicRegistry.getLeafToLeafPileMap();
    }

    @Nullable
    public static Map<Block, SimpleParticleType> getDynamicLeafParticles() {
        return ModDynamicRegistry.getLeavesToParticleMap();
    }

    public static void addExtraBark(ImmutableMap.Builder<Block, Pair<Item, Block>> builder) {
        builder.putAll(ModDynamicRegistry.getBarkMap());
    }


}
