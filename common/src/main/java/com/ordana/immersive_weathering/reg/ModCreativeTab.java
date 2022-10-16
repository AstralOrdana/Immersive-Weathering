package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeTab {
    public static final CreativeModeTab TAB = !CommonConfigs.CREATIVE_TAB.get() ? null :
            PlatformHelper.createModTab(ImmersiveWeathering.res(ImmersiveWeathering.MOD_ID),
                    () -> ModBlocks.IVY.get().asItem().getDefaultInstance(), false);

    public static CreativeModeTab getTab(CreativeModeTab tab) {
        return TAB == null ? tab : TAB;
    }
}
