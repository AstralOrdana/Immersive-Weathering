package com.ordana.immersive_weathering.data.fluid_generators;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FluidGeneratorsHandler extends SimpleJsonResourceReloadListener {

    public static final ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS = ImmutableList.of(
            Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST
    );

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    public static final FluidGeneratorsHandler RELOAD_INSTANCE = new FluidGeneratorsHandler();

    private static final Map<ResourceLocation, JsonElement> TO_PARSE = new HashMap<>();

    private static final Map<Fluid, ImmutableList<IFluidGenerator>> STILL_GENERATORS = new Object2ObjectOpenHashMap<>();
    private static final Map<Fluid, ImmutableList<IFluidGenerator>> FLOWING_GENERATORS = new Object2ObjectOpenHashMap<>();
    private static final Set<Fluid> HAS_GENERATOR = new HashSet<>();

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
    }

    //called after all tags are reloaded
    public void rebuild(RegistryAccess registryAccess) {

        if (this.needsRefresh) {
            this.needsRefresh = false;

            List<IFluidGenerator> generators = new ArrayList<>();

            for (var e : TO_PARSE.entrySet()) {
                var json = e.getValue();

                var result = IFluidGenerator.CODEC.parse(RegistryOps.create(JsonOps.INSTANCE, registryAccess), json);
                var o = result.resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read liquid generator JSON object for {} : {}", e.getKey(), error));

                o.ifPresent(generators::add);
            }
            ImmersiveWeathering.LOGGER.info("Loaded {} liquid generators configurations", TO_PARSE.size());

            STILL_GENERATORS.clear();
            FLOWING_GENERATORS.clear();
            HAS_GENERATOR.clear();

            Map<Fluid, List<IFluidGenerator>> flowingMap = new HashMap<>();
            Map<Fluid, List<IFluidGenerator>> stillMap = new HashMap<>();

            for (var g : generators) {
                HAS_GENERATOR.add(g.getFluid());

                if (g.getFluidType().isFlowing()) {
                    var list = flowingMap.computeIfAbsent(g.getFluid(), e -> new ArrayList<>());

                    list.add(g);
                    Collections.sort(list);
                }

                if (g.getFluidType().isStill()) {
                    var list = stillMap.computeIfAbsent(g.getFluid(), e -> new ArrayList<>());

                    list.add(g);
                    Collections.sort(list);
                }
            }
            flowingMap.forEach((key, value) -> FLOWING_GENERATORS.put(key, ImmutableList.copyOf(value)));
            stillMap.forEach((key, value) -> STILL_GENERATORS.put(key, ImmutableList.copyOf(value)));

            TO_PARSE.clear();
        }
    }

    public static Optional<Pair<BlockPos, @Nullable SoundEvent>> applyGenerators(FlowingFluid fluid, List<Direction> possibleFlowDir,
                                                                                 BlockPos pos, Level level) {
        var source = fluid.getSource();
        if (HAS_GENERATOR.contains(source)) {
            var list = level.getFluidState(pos).isSource() ? STILL_GENERATORS.get(source) : FLOWING_GENERATORS.get(source);
            return generate(possibleFlowDir, pos, level, list);
        }
        return Optional.empty();
    }

    private static Optional<Pair<BlockPos, @Nullable SoundEvent>> generate(List<Direction> possibleFlowDir, BlockPos pos, Level level, ImmutableList<IFluidGenerator> list) {
        if (list != null && !list.isEmpty()) {
            Map<Direction, BlockState> neighborCache = new EnumMap<>(Direction.class);
            for (var generator : list) {
                var res = generator.tryGenerating(possibleFlowDir, pos, level, neighborCache);
                if (res.isPresent()) return res.map(a -> Pair.of(a, generator.getSound()));
            }
        }
        return Optional.empty();
    }

    //debug


    public void saveGeneartor(IFluidGenerator song) {

        File folder = PlatHelper.getGamePath().resolve("test").toFile();

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

    private void writeToFile(final IFluidGenerator obj, FileWriter writer) {
        DataResult<JsonElement> r = IFluidGenerator.CODEC.encodeStart(JsonOps.INSTANCE, obj);
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
