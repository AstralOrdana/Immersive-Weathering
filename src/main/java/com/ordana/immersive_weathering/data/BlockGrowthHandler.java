package com.ordana.immersive_weathering.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.data.BlockGrowthConfiguration;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.biome.Biome;

import java.io.FileWriter;
import java.util.*;

import static com.ordana.immersive_weathering.data.BlockGrowthConfiguration.CODEC;

public class BlockGrowthHandler extends JsonDataLoader implements IdentifiableResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private static final Map<Block, Set<BlockGrowthConfiguration>> GROWTH_FOR_BLOCK = new HashMap<>();
    private static final Map<Identifier, JsonElement> PENDING_JSONS = new HashMap<>();

    public BlockGrowthHandler() {
        super(GSON, "block_growths");
    }

    public static Optional<Set<BlockGrowthConfiguration>> getBlockGrowthConfig(Block block) {
        return Optional.ofNullable(GROWTH_FOR_BLOCK.get(block));
    }

    public static boolean tickBlock(BlockState state, ServerWorld world, BlockPos pos) {
        boolean success = false;
        var growth = getBlockGrowthConfig(state.getBlock());
        if (growth.isPresent()) {
            RegistryEntry<Biome> biome = world.getBiome(pos);

            for (var config : growth.get()) {
                boolean s = config.tryGrowing(pos, world, biome);
                if (s) success = Boolean.TRUE;
            }
        }
        return success;
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
    protected void apply(Map<Identifier, JsonElement> jsons, ResourceManager manager, Profiler profile) {
        PENDING_JSONS.clear();
        for (var e : jsons.entrySet()) {
            PENDING_JSONS.put(e.getKey(),e.getValue().deepCopy());
        }
    }


    public static void rebuild(DynamicRegistryManager registryAccess) {
        for(var e : PENDING_JSONS.entrySet()){
            var result = CODEC.parse(RegistryOps.of(JsonOps.INSTANCE, registryAccess),
                    e.getValue());
            var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
            if(o.isPresent()) {
                BlockGrowthConfiguration config = o.get();
                RegistryEntryList<Block> owners = config.getOwners();
                owners.forEach(b -> GROWTH_FOR_BLOCK.computeIfAbsent(b.value(), k -> new HashSet<>()).add(config));
            }
        }
        PENDING_JSONS.clear();
    }



    @Override
    public Identifier getFabricId() {
        return new Identifier(ImmersiveWeathering.MOD_ID,"block_growths");
    }
}