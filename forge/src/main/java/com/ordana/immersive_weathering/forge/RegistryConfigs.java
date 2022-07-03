package com.ordana.immersive_weathering.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class RegistryConfigs {
    public static final String FILE_NAME = ImmersiveWeathering.MOD_ID + "-registry.toml";
    public static ForgeConfigSpec REGISTRY_CONFIG;

    public static ForgeConfigSpec.BooleanValue RESOURCE_PACK_SUPPORT;

    public static void init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        RESOURCE_PACK_SUPPORT = builder.comment("Enable generated resources to depend on loaded resource packs")
                .define("resource_pack_support", true);
        REGISTRY_CONFIG = builder.build();

        addAndLoadConfigFile(REGISTRY_CONFIG, FILE_NAME, true);

    }


    public static ModConfig addAndLoadConfigFile(ForgeConfigSpec targetSpec, String fileName, boolean addToMod) {
        loadConfigFile(fileName, targetSpec);
        ModContainer modContainer = ModLoadingContext.get().getActiveContainer();
        ModConfig config = new ModConfig(ModConfig.Type.COMMON, targetSpec, modContainer, fileName);
        if (addToMod) {
            modContainer.addConfig(config);
        }

        return config;
    }

    public static void loadConfigFile(String fileName, ForgeConfigSpec targetSpec) {
        CommentedFileConfig replacementConfig = (CommentedFileConfig) CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(fileName)).sync().preserveInsertionOrder().writingMode(WritingMode.REPLACE).build();
        replacementConfig.load();
        replacementConfig.save();
        targetSpec.setConfig(replacementConfig);
    }


}
