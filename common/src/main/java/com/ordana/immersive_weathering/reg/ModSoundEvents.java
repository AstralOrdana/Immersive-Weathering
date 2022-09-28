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

    public static Supplier<SoundEvent> YUMMY = registerSoundEvent("yummy");
    public static Supplier<SoundEvent> ICICLE_CRACK = registerSoundEvent("icicle_crack");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ImmersiveWeathering.res(name);
        return RegHelper.registerSound(id,()-> new SoundEvent(id));
    }

}
