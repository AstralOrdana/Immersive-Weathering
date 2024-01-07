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

    public static final Supplier<SimpleParticleType> GRAVITY_AZALEA_FLOWER = registerParticle("gravity_azalea_flower");
    public static final Supplier<SimpleParticleType> SCRAPE_RUST = registerParticle("scrape_rust");
    public static final Supplier<SimpleParticleType> EMBERSPARK = registerParticle("emberspark");
    public static final Supplier<SimpleParticleType> MOSS = registerParticle("moss");

    //custom flower crowns

    //pride particles
    public static final Supplier<SimpleParticleType> AZALEA_FLOWER = registerParticle("azalea_flower");
    public static final Supplier<SimpleParticleType> FLOWER_ACE = registerParticle("flower_ace");
    public static final Supplier<SimpleParticleType> FLOWER_ARO = registerParticle("flower_aro");
    public static final Supplier<SimpleParticleType> FLOWER_BI = registerParticle("flower_bi");
    public static final Supplier<SimpleParticleType> FLOWER_ENBY = registerParticle("flower_enby");
    public static final Supplier<SimpleParticleType> FLOWER_GAY = registerParticle("flower_gay");
    public static final Supplier<SimpleParticleType> FLOWER_LESBIAN = registerParticle("flower_lesbian");
    public static final Supplier<SimpleParticleType> FLOWER_RAINBOW = registerParticle("flower_rainbow");
    public static final Supplier<SimpleParticleType> FLOWER_TRANS = registerParticle("flower_trans");
    public static final Supplier<SimpleParticleType> FLOWER_FLUID = registerParticle("flower_fluid");
    public static final Supplier<SimpleParticleType> FLOWER_GENDERQUEER = registerParticle("flower_genderqueer");
    public static final Supplier<SimpleParticleType> FLOWER_INTERSEX = registerParticle("flower_intersex");
    public static final Supplier<SimpleParticleType> FLOWER_PAN = registerParticle("flower_pan");

    //supporter particles
    public static final Supplier<SimpleParticleType> FLOWER_FLAX = registerParticle("flower_flax");
    public static final Supplier<SimpleParticleType> FLOWER_NEKOMASTER = registerParticle("flower_nekomaster");
    public static final Supplier<SimpleParticleType> FLOWER_AKASHII = registerParticle("flower_akashii");

    //dev and gift particles
    public static final Supplier<SimpleParticleType> FLOWER_BEE = registerParticle("flower_bee");
    public static final Supplier<SimpleParticleType> FLOWER_JAR = registerParticle("flower_jar");
    public static final Supplier<SimpleParticleType> FLOWER_BOB = registerParticle("flower_bob");
}
