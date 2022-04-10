package com.ordana.immersive_weathering.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.io.FileWriter;
import java.util.*;

import static com.ordana.immersive_weathering.data.BlockGrowthConfiguration.CODEC;

public class BlockGrowthHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private static final Map<Block, Set<BlockGrowthConfiguration>> GROWTH_FOR_BLOCK = new HashMap<>();

    public RegistryAccess registryAccess;

    public BlockGrowthHandler() {
        super(GSON, "block_growths");
    }

    public static Optional<Set<BlockGrowthConfiguration>> getBlockGrowthConfig(Block block) {
        return Optional.ofNullable(GROWTH_FOR_BLOCK.get(block));
    }

    public static void tickBlock(BlockState state, ServerLevel level, BlockPos pos) {
        getBlockGrowthConfig(state.getBlock()).ifPresent(l -> {
            Holder<Biome> biome = level.getBiome(pos);
            for (var config : l) {
                config.tryGrowing(pos, level, biome);
            }
        });
    }

    public void writeToFile(final BlockGrowthConfiguration obj, FileWriter writer) {
        var r = CODEC.encodeStart(JsonOps.INSTANCE, obj);
        r.result().ifPresent(a -> GSON.toJson(sortJson(a.getAsJsonObject()), writer));
    }

    private JsonObject sortJson(JsonObject jsonObject) {
        try {
            Map<String, JsonElement> joToMap = new TreeMap<>();
            jsonObject.entrySet().forEach(e -> {
                var j = e.getValue();
                if (j instanceof JsonObject jo) j = sortJson(jo);
                joToMap.put(e.getKey(), j);
            });
            JsonObject sortedJSON = new JsonObject();
            joToMap.forEach(sortedJSON::add);
            return sortedJSON;
        } catch (Exception ignored) {
        }
        return jsonObject;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profile) {
        if (registryAccess == null) return;
        GROWTH_FOR_BLOCK.clear();
        for (var e : jsons.entrySet()) {
            //var result = CODEC.parse(JsonOps.INSTANCE,e.getValue());
            var result = CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registryAccess),
                    e.getValue());
            var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
            o.ifPresent(config -> GROWTH_FOR_BLOCK.computeIfAbsent(config.getOwner(), k -> new HashSet<>()).add(config));
        }
    }

}
