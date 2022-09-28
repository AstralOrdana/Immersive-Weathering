package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSoundEvents {
    public static void init() {}

    public static SoundEvent YUMMY = registerSoundEvent("yummy");
    public static SoundEvent ICICLE_CRACK = registerSoundEvent("icicle_crack");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(ImmersiveWeathering.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
/*
    public static <T extends SoundEvent> RegSupplier<T> registerSound(ResourceLocation name, Supplier<T> feature) {
        return RegHelper.registerSound(name, feature);
    }
 */

}
