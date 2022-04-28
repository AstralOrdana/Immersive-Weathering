package com.ordana.immersive_weathering.block_growth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.hardcoded.HardcodedGrowths;
import com.ordana.immersive_weathering.configs.ServerConfigs;
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

import static com.ordana.immersive_weathering.block_growth.BlockGrowthConfiguration.CODEC;

public class BlockGrowthHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private static final Set<Block> BLOCKS = new HashSet<>();
    private static final Map<Block, Set<IBlockGrowth>> GROWTH_FOR_BLOCK = new HashMap<>();
    private static final List<IBlockGrowth> GROWTHS = new ArrayList<>();

    public RegistryAccess registryAccess;
    private boolean needsRefresh;

    public BlockGrowthHandler() {
        super(GSON, "block_growths");
    }

    public static Optional<Set<IBlockGrowth>> getBlockGrowth(Block block) {
        return Optional.ofNullable(GROWTH_FOR_BLOCK.get(block));
    }

    public static void tickBlock(BlockState state, ServerLevel level, BlockPos pos) {
        var growth = getBlockGrowth(state.getBlock());
        if (growth.isPresent()) {
            Holder<Biome> biome = level.getBiome(pos);

            for (var config : growth.get()) {
                config.tryGrowing(pos, state, level, biome);
            }
        }
    }

    public static boolean canRandomTick(BlockState state) {
        if (!ServerConfigs.BLOCK_GROWTH.get()) return false;
        return BLOCKS.contains(state.getBlock());
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
        this.needsRefresh = true;
        GROWTHS.clear();
        for (var e : jsons.entrySet()) {
            //var result = CODEC.parse(JsonOps.INSTANCE,e.getValue());
            var result = CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registryAccess),
                    e.getValue());
            var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
            o.ifPresent(GROWTHS::add);
        }
        ImmersiveWeathering.LOGGER.info("Loaded {} block growths configurations", GROWTHS.size());
    }

    //called after all tags are reloaded
    public void rebuild() {
        if (this.needsRefresh) {
            GROWTH_FOR_BLOCK.clear();
            GROWTHS.addAll(HardcodedGrowths.getHardcoded());
            for (var config : GROWTHS) {
                config.getOwners().forEach(b -> GROWTH_FOR_BLOCK.computeIfAbsent(b, k -> new HashSet<>()).add(config));
            }
            BLOCKS.addAll(GROWTH_FOR_BLOCK.keySet());
            GROWTHS.clear();
            this.needsRefresh = false;
        }
    }
}
