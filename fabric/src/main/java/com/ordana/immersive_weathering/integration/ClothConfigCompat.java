package com.ordana.immersive_weathering.integration;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.fabric.*;
import com.ordana.immersive_weathering.configs.fabric.ConfigSpec;
import com.ordana.immersive_weathering.configs.fabric.FabricConfigBuilder;
import com.ordana.immersive_weathering.configs.fabric.values.BoolConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.DoubleConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.EnumConfigValue;
import com.ordana.immersive_weathering.configs.fabric.values.IntConfigValue;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ClothConfigCompat {

    public static Screen makeScreen(Screen parent, ConfigSpec spec) {
        spec.loadConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableComponent(spec.getName()));

        builder.setDefaultBackgroundTexture(ImmersiveWeathering.res("textures/block/cracked_bricks.png"));

        builder.setSavingRunnable(spec::saveConfig);

        for (var c : spec.getCategories()) {
            ConfigCategory category = builder.getOrCreateCategory(new TranslatableComponent(c.getName()));
            for (var entry : c.getValues()) {
                String name = entry.getName();
                if (entry instanceof IntConfigValue ic) {
                    category.addEntry(builder.entryBuilder()
                            .startIntField(FabricConfigBuilder.descriptionKey(name), ic.get())
                            .setMax(ic.getMax())
                            .setMin(ic.getMin())
                            .setDefaultValue(ic.getDefaultValue()) // Recommended: Used when user click "Reset"
                            .setTooltip(FabricConfigBuilder.tooltipKey(name)) // Optional: Shown when the user hover over this option
                            .setSaveConsumer(ic::set) // Recommended: Called when user save the config
                            .build()); // Builds the option entry for cloth config
                } else if (entry instanceof DoubleConfigValue dc) {
                    category.addEntry(builder.entryBuilder()
                            .startDoubleField(FabricConfigBuilder.descriptionKey(name), dc.get())
                            .setMax(dc.getMax())
                            .setMin(dc.getMin())
                            .setDefaultValue(dc.getDefaultValue()) // Recommended: Used when user click "Reset"
                            .setTooltip(FabricConfigBuilder.tooltipKey(name)) // Optional: Shown when the user hover over this option
                            .setSaveConsumer(dc::set) // Recommended: Called when user save the config
                            .build()); // Builds the option entry for cloth config
                } else if (entry instanceof StringConfigValue sc) {
                    category.addEntry(builder.entryBuilder()
                            .startStrField(FabricConfigBuilder.descriptionKey(name), sc.get())
                            .setDefaultValue(sc.getDefaultValue()) // Recommended: Used when user click "Reset"
                            .setTooltip(FabricConfigBuilder.tooltipKey(name)) // Optional: Shown when the user hover over this option
                            .setSaveConsumer(sc::set) // Recommended: Called when user save the config
                            .build()); // Builds the option entry for cloth config
                } else if (entry instanceof BoolConfigValue bc) {
                    category.addEntry(builder.entryBuilder()
                            .startBooleanToggle(FabricConfigBuilder.descriptionKey(name), bc.get())
                            .setDefaultValue(bc.getDefaultValue()) // Recommended: Used when user click "Reset"
                            .setTooltip(FabricConfigBuilder.tooltipKey(name)) // Optional: Shown when the user hover over this option
                            .setSaveConsumer(bc::set) // Recommended: Called when user save the config
                            .build()); // Builds the option entry for cloth config else if (entry instanceof EnumConfigValue<?> ec) {
                } else if (entry instanceof EnumConfigValue<?> ec) {
                    addEnum(builder, category, name, ec);
                }
            }
        }
        return builder.build();
    }

    private static <T extends Enum<T>> void addEnum(ConfigBuilder builder, ConfigCategory category, String name, EnumConfigValue<T> ec) {
        category.addEntry(builder.entryBuilder()
                .startEnumSelector(FabricConfigBuilder.descriptionKey(name), ec.getEnum(), ec.get())
                .setDefaultValue(ec.getDefaultValue()) // Recommended: Used when user click "Reset"
                .setTooltip(FabricConfigBuilder.tooltipKey(name)) // Optional: Shown when the user hover over this option
                .setSaveConsumer(ec::set) // Recommended: Called when user save the config
                .build()); // Builds the option entry for cloth config
    }
}
