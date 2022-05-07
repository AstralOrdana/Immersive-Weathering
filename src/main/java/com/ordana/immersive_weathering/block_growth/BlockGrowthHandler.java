package com.ordana.immersive_weathering.block_growth;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.hardcoded.HardcodedGrowths;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
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

import static com.ordana.immersive_weathering.block_growth.BlockGrowthConfiguration.CODEC;

public class BlockGrowthHandler extends JsonDataLoader implements IdentifiableResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private static ImmutableSet<Block> TICKING_BLOCKS = ImmutableSet.of();
    private static final Map<Block, Set<IBlockGrowth>> GROWTH_FOR_BLOCK = new HashMap<>();
    private static final Map<Identifier, JsonElement> PENDING_JSONS = new HashMap<>();

    public BlockGrowthHandler() {
        super(GSON, "block_growths");
    }

    public static Optional<Set<IBlockGrowth>> getBlockGrowth(Block block) {
        return Optional.ofNullable(GROWTH_FOR_BLOCK.get(block));
    }

    public static void tickBlock(BlockPos pos, ServerWorld world) {
        BlockState state = world.getBlockState(pos);
        if (!TICKING_BLOCKS.contains(state.getBlock())) return;
        var growth = getBlockGrowth(state.getBlock());
        if (growth.isPresent()) {
            //TODO: move this line to datapack self predicate
            if (state.getBlock() instanceof IConditionalGrowingBlock cb && !cb.canGrow(state)) return;
            RegistryEntry<Biome> biome = world.getBiome(pos);

            for (var config : growth.get()) {
                config.tryGrowing(pos, state, world, biome);
            }
        }
    }

    /*
    public static void tickBlock(BlockState state, ServerWorld world, BlockPos pos) {
        if (!TICKING_BLOCKS.contains(state.getBlock())) return;
        var growth = getBlockGrowth(state.getBlock());
        if (growth.isPresent()) {
            //TODO: move this line to datapack self predicate
            if (state.getBlock() instanceof IConditionalGrowingBlock cb && !cb.canGrow(state)) return;
            RegistryEntry<Biome> biome = world.getBiome(pos);

            for (var config : growth.get()) {
                config.tryGrowing(pos, state, world, biome);
            }
        }
    }*/

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
        HardcodedGrowths.getHardcoded().forEach(h->{
            h.getOwners().forEach(b -> GROWTH_FOR_BLOCK.computeIfAbsent(b, k -> new HashSet<>()).add(h));
        });
        for(var e : PENDING_JSONS.entrySet()){
            var result = CODEC.parse(RegistryOps.of(JsonOps.INSTANCE, registryAccess),
                    e.getValue());
            var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
            if(o.isPresent()) {
                BlockGrowthConfiguration config = o.get();
                config.getOwners().forEach(b -> GROWTH_FOR_BLOCK.computeIfAbsent(b, k -> new HashSet<>()).add(config));
            }
        }
        ImmutableSet.Builder<Block> b = ImmutableSet.builder();
        TICKING_BLOCKS = b.addAll(GROWTH_FOR_BLOCK.keySet()).build();
        PENDING_JSONS.clear();
    }



    @Override
    public Identifier getFabricId() {
        return new Identifier(ImmersiveWeathering.MOD_ID,"block_growths");
    }
}