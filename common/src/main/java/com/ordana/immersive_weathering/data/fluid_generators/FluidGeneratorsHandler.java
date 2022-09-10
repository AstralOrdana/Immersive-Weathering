package com.ordana.immersive_weathering.data.fluid_generators;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FluidGeneratorsHandler extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    public static final FluidGeneratorsHandler RELOAD_INSTANCE = new FluidGeneratorsHandler();

    private static final Map<ResourceLocation, JsonElement> TO_PARSE = new HashMap<>();

    private static final Map<Fluid, ImmutableList<SelfFluidGenerator>> GENERATORS = new HashMap<>();
    private boolean needsRefresh;

    public FluidGeneratorsHandler() {
        super(GSON, "fluid_generators");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profile) {
        this.needsRefresh = true;
        TO_PARSE.clear();
        for (var e : jsons.entrySet()) {
            TO_PARSE.put(e.getKey(), e.getValue().deepCopy());
        }

        var b = new SelfFluidGenerator(Fluids.LAVA, Blocks.ACACIA_LOG.defaultBlockState(),
                Map.of(SelfFluidGenerator.Side.SIDES, new BlockStateMatchTest(Blocks.CAKE.defaultBlockState())
                ), List.of(), 0);

        saveGeneartor(b);
    }

    //called after all tags are reloaded
    public void rebuild(RegistryAccess registryAccess) {

        if (this.needsRefresh) {
            this.needsRefresh = false;

            List<SelfFluidGenerator> generators = new ArrayList<>();

            for (var e : TO_PARSE.entrySet()) {
                var json = e.getValue();

                var result = SelfFluidGenerator.CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registryAccess), json);

                var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read block growth JSON object for {} : {}", e.getKey(), error));
                if (o.isPresent()) {
                    SelfFluidGenerator g = o.get();
                    generators.add(g);
                }
                o.ifPresent(generators::add);
            }
            ImmersiveWeathering.LOGGER.info("Loaded {} liquid generators configurations", TO_PARSE.size());

            GENERATORS.clear();

            Map<Fluid, List<SelfFluidGenerator>> tempMap = new HashMap<>();

            for (var g : generators) {

                var list = tempMap.computeIfAbsent(g.getFluid(), e -> new ArrayList<>());

                list.add(g);
                Collections.sort(list);
            }
            tempMap.forEach((key, value) -> GENERATORS.put(key, ImmutableList.copyOf(value)));

            TO_PARSE.clear();
        }
    }

    public static Optional<BlockPos> applyGenerators(FlowingFluid fluidState, List<Direction> possibleFlowDir, BlockPos pos, Level level) {
        var list = GENERATORS.get(fluidState.getSource());
        if (list != null && !list.isEmpty()) {
            Map<Direction, BlockState> neighborCache = new EnumMap<>(Direction.class);
            for (var generator : list) {
                var res = generator.tryGenerating(possibleFlowDir, pos, level, neighborCache);
                if (res.isPresent()) return res;
            }
        }
        return Optional.empty();
    }

    //debug


    public void saveGeneartor(SelfFluidGenerator song) {

        File folder = PlatformHelper.getGamePath().resolve("test").toFile();

        if (!folder.exists()) {
            folder.mkdir();
        }

        File exportPath = new File(folder, "template" + ".json");

        try {
            try (FileWriter writer = new FileWriter(exportPath)) {
                writeToFile(song, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(final SelfFluidGenerator obj, FileWriter writer) {
        DataResult<JsonElement> r = SelfFluidGenerator.CODEC.encodeStart(JsonOps.INSTANCE, obj);
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
