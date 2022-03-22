package com.ordana.immersive_weathering.integration;

import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.client.module.GreenerGrassModule;

import java.lang.reflect.Method;
import java.util.List;

public class QuarkPlugin {

    public static void onFirstClientTick() {

        try {
            if (ModuleLoader.INSTANCE.isModuleEnabled(GreenerGrassModule.class)) {
                Method method = ObfuscationReflectionHelper.findMethod(GreenerGrassModule.class, "registerGreenerColor", Iterable.class, boolean.class);
                List<String> leaves = List.of("immersive_weathering:oak_leaf_pile", "immersive_weathering:dark_oak_leaf_pile",
                        "immersive_weathering:acacia_leaf_pile", "immersive_weathering:jungle_leaf_pile",
                        "immersive_weathering:spruce_leaf_pile", "immersive_weathering:birch_leaf_pile");
                method.setAccessible(true);
                method.invoke(ModuleLoader.INSTANCE.getModuleInstance(GreenerGrassModule.class), leaves, true);
            }
        } catch (Exception ignored) {
        }
    }

}
