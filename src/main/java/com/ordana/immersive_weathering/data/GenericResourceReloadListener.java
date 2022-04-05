package com.ordana.immersive_weathering.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.util.*;
import java.util.function.Consumer;

public class GenericResourceReloadListener<T> extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(); //json object that will write stuff

    private final Codec<T> codec;
  //  private final Consumer<GenericResourceReloadListener<T>> syncOnReload;
    private final Class<T> objClass;

    protected Map<ResourceLocation, Optional<T>> OBJECTS = new HashMap<>();

    public GenericResourceReloadListener(final String folderIn,//folder to save stuff
                                     final Class<T> oClass, //type of stuff to save
                                     final Codec<T> oCodec //codec to save stuff
                                      //sync function Consumer<GenericResourceReloadListener<T>> syncOnReloadConsumer
    ) {
        super(GSON, folderIn);
        objClass = oClass;
        codec = oCodec;
       // syncOnReload = syncOnReloadConsumer;
    }

    public DataResult<Tag> writeObject(final T obj) {
        // write Object T to NBT
        return codec.encodeStart(NbtOps.INSTANCE, obj);
    }

    public DataResult<JsonElement> writeJson(final T obj) {
        // write Object T to NBT
        return codec.encodeStart(JsonOps.INSTANCE, obj);
    }

    public void writeToFile(final T obj, FileWriter writer) {
        var r = writeJson(obj);
        r.result().ifPresent(a->GSON.toJson(sortJson(a.getAsJsonObject()),writer));
        // write Object T to NBT
    }

    private JsonObject sortJson(JsonObject jsonObject){
        try{
            Map<String, JsonElement> joToMap = new TreeMap<>();
            jsonObject.entrySet().forEach(e->{
                var j = e.getValue();
                if(j instanceof JsonObject jo) j = sortJson(jo);
                joToMap.put(e.getKey(),j);
            });
            JsonObject sortedJSON = new JsonObject();
            joToMap.forEach(sortedJSON::add);
            return sortedJSON;
        } catch (Exception ignored){}
        return jsonObject;
    }

    public DataResult<T> jsonToObject(final JsonElement json) {
        // read Object T from json
        return codec.parse(JsonOps.INSTANCE, json);
    }

    public DataResult<T> readObject(final Tag nbt) {
        // read Object T from nbt
        return codec.parse(NbtOps.INSTANCE, nbt);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager manager, ProfilerFiller profile) {

        OBJECTS.clear();

        jsons.forEach((key, input) -> OBJECTS.put(key, jsonToObject(input).resultOrPartial(error -> ImmersiveWeathering.LOGGER.error("Failed to read JSON object for type" + objClass.getName() + "\n" + error))));

        boolean isServer = true;
        try {
            //LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        } catch (Exception e) {
            isServer = false;
        }
        // if we're on the server, send syncing packets
        if (isServer == true) {
            //syncOnReload.accept(this);
        }
    }

}
