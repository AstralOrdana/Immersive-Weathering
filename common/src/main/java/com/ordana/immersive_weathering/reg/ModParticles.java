package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticles {

    public static void init() {
        //BlockSetAPI.addDynamicRegistration(ModParticles::registerLeafParticles, LeavesType.class, Registries.PARTICLE_TYPE);
    }

    public static Supplier<SimpleParticleType> registerParticle(String name) {
        return RegHelper.registerParticle(ImmersiveWeathering.res(name));
    }

    public static final Supplier<SimpleParticleType> EMBERSPARK = registerParticle("emberspark");
}
