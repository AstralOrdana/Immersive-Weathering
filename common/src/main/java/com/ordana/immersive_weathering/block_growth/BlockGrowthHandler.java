package com.ordana.immersive_weathering.block_growth;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.builtin.HardcodedGrowths;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

import java.io.FileWriter;
import java.util.*;
import java.util.function.Supplier;

import static com.ordana.immersive_weathering.block_growth.BlockGrowthConfiguration.CODEC;

public class BlockGrowthHandler extends SimpleJsonResourceReloadListener {

    public static BlockGrowthHandler getInstance(){
        return getInstance(BlockGrowthHandler::new);
    }
    public static <T extends BlockGrowthHandler> T getInstance(Supplier<T> factory){
        if(INSTANCE == null)INSTANCE = factory.get();
        return (T) INSTANCE;
    }

    private static BlockGrowthHandler INSTANCE;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private static final Map<ResourceLocation, JsonElement> GROWTH_TO_PARSE = new HashMap<>();

    //block specific growth. fast access with map
    private static final Map<TickSource, ImmutableSet<Block>> TICKING_BLOCKS = new HashMap<>();
    private static final Map<TickSource, Map<Block, Set<IBlockGrowth>>> GROWTH_FOR_BLOCK = new HashMap<>();
    //set or universal ones
    private static final Map<TickSource, Set<IBlockGrowth>> UNIVERSAL_GROWTHS = new HashMap<>();

    private boolean needsRefresh;

    public BlockGrowthHandler() {
        super(GSON, "block_growths");
    }

    public static Optional<Set<IBlockGrowth>> getBlockGrowth(TickSource source, Block block) {
        return Optional.ofNullable(GROWTH_FOR_BLOCK.get(source)).map(m -> m.get(block));
    }

    public static void tickBlock(TickSource source, BlockState state, ServerLevel level, BlockPos pos) {
        if (!CommonConfigs.BLOCK_GROWTHS.get()) return;

        //TODO: move this line to datapack self predicate
        if (state.getBlock() instanceof IConditionalGrowingBlock cb && !cb.canGrow(state)) return;

        Holder<Biome> biome = null;

        var universalGroup = UNIVERSAL_GROWTHS.get(source);

        if (universalGroup != null) {
            biome = level.getBiome(pos);
            for (var config : universalGroup) {
                config.tryGrowing(pos, state, level, biome);
            }
        }

        //hopefully faster than calling get directly
        var group = TICKING_BLOCKS.get(source);
        if (group == null || !group.contains(state.getBlock())) return;
        var growth = getBlockGrowth(source, state.getBlock());
        if (growth.isPresent()) {
            if (biome == null) biome = level.getBiome(pos);

            for (var config : growth.get()) {
                config.tryGrowing(pos, state, level, biome);
            }
        }
    }

    //called by mixin
    public static void performSkyAccessTick(ServerLevel level, LevelChunk levelChunk, int randomTickSpeed) {
        ChunkPos chunkpos = levelChunk.getPos();

        float chance = (float) randomTickSpeed / (3f * 16f);
        int minX = chunkpos.getMinBlockX();
        int minZ = chunkpos.getMinBlockZ();
        boolean isRaining = level.isRaining();
        do {
            if (chance > level.random.nextFloat()) {

                BlockPos firstAirPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, level.getBlockRandomPos(minX, 0, minZ, 15));
                BlockPos targetPos = firstAirPos.below();
                BlockState state = level.getBlockState(targetPos);

                TickSource source = TickSource.CLEAR_SKY;
                if (isRaining) {
                    Biome biome = level.getBiome(targetPos).value();
                    Biome.Precipitation precipitation = biome.getPrecipitation();
                    if (precipitation == Biome.Precipitation.RAIN) {
                        if (biome.coldEnoughToSnow(targetPos)) source = TickSource.SNOW;
                        else source = TickSource.RAIN;
                    }
                }
                tickBlock(source, state, level, targetPos);
            }
            chance--;
        } while (chance > 0);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profile) {
        this.needsRefresh = true;
        GROWTH_TO_PARSE.clear();
        for (var e : jsons.entrySet()) {
            GROWTH_TO_PARSE.put(e.getKey(), e.getValue().deepCopy());
        }
    }

    //called after all tags are reloaded
    public void rebuild(RegistryAccess registryAccess) {

        if (this.needsRefresh) {
            this.needsRefresh = false;

            List<IBlockGrowth> growths = new ArrayList<>(HardcodedGrowths.getHardcoded());

            for (var e : GROWTH_TO_PARSE.entrySet()) {
                //var result = CODEC.parse(JsonOps.INSTANCE,e.getValue());
                var result = CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registryAccess),
                        e.getValue());
                var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
                o.ifPresent(growths::add);
            }
            ImmersiveWeathering.LOGGER.info("Loaded {} block growths configurations", GROWTH_TO_PARSE.size());

            GROWTH_FOR_BLOCK.clear();
            UNIVERSAL_GROWTHS.clear();

            for (var config : growths) {

                var sources = config.getTickSources();
                for (var s : sources) {
                    var owners = config.getOwners();

                    if (owners == null) { //null owners mean it applies to everything
                        var group = UNIVERSAL_GROWTHS.computeIfAbsent(s, e -> new HashSet<>());
                        group.add(config);
                    } else {
                        var group = GROWTH_FOR_BLOCK.computeIfAbsent(s, e -> new HashMap<>());
                        config.getOwners().forEach(b -> {

                            group.computeIfAbsent(b, k -> new HashSet<>()).add(config);
                        });
                    }

                }
            }
            TICKING_BLOCKS.clear();
            for (var g : GROWTH_FOR_BLOCK.entrySet()) {
                ImmutableSet.Builder<Block> b = ImmutableSet.builder();
                b.addAll(g.getValue().keySet());
                TICKING_BLOCKS.put(g.getKey(), b.build());
            }
            GROWTH_TO_PARSE.clear();
        }
    }

    //debug

    private void writeToFile(final BlockGrowthConfiguration obj, FileWriter writer) {
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
}
