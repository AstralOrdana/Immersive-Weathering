package com.ordana.immersive_weathering.configs.fabric;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConfigSpec {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static ConfigSpec CLIENT_INSTANCE;
    public static ConfigSpec COMMON_INSTANCE;

    private final List<ConfigCategory> categories;

    private final String name;
    private final File file;

    public ConfigSpec(String name, ImmutableList<ConfigCategory> categories, String filePath) {
        this.name = name;
        this.categories = categories;
        this.file = new File(FabricLoader.getInstance().getConfigDir().toFile(), filePath);
    }


    public List<ConfigCategory> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }


    public void loadConfig() {
        JsonElement config = null;


        if (file.exists() && file.isFile()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                config = GSON.fromJson(bufferedReader, JsonElement.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        }

        if (config instanceof JsonObject jo) {
            categories.forEach(e -> e.loadFromJson(jo));
        }
    }

    public void saveConfig() {
        try (FileOutputStream stream = new FileOutputStream(this.file);
             Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {

            JsonObject jo = new JsonObject();
            categories.forEach(e -> e.saveToJson(jo));

            GSON.toJson(jo, writer);
        } catch (IOException ignored) {
        }
    }
}
