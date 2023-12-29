package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ModCreativeTab {

    public static void init(){
        RegHelper.addItemsToTabsRegistration(ModCreativeTab::addItems);
    }

    public static void addItems(RegHelper.ItemToTabEvent event) {

        if(MOD_TAB != null) {
            Map<ResourceLocation, Item> items = new TreeMap<>();
            for (var i : BuiltInRegistries.ITEM.entrySet()) {
                //TODO: change
                ResourceLocation location = i.getKey().location();
                if (location.getNamespace().equals(ImmersiveWeathering.MOD_ID)) {
                    items.put(location, i.getValue());
                }
            }
            event.add(MOD_TAB.getKey(), items.values().toArray(ItemLike[]::new));
        }
    }

    //my dude, you are doing conditional registration here. aparently it works somehow without desync but just for tabs
    public static final RegSupplier<CreativeModeTab> MOD_TAB = (!CommonConfigs.CREATIVE_TAB.get() && false) ? null :
            RegHelper.registerCreativeModeTab(ImmersiveWeathering.res("immersive_weathering"),
                    (c) -> c.title(Component.translatable("itemGroup.immersive_weathering.immersive_weathering"))
                            .icon(() -> ModBlocks.IVY.get().asItem().getDefaultInstance()));

}
