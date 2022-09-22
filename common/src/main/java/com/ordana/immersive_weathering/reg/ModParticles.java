package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticles {

    public static void init() {
    }

    public static Supplier<SimpleParticleType> registerParticle(String name) {
        return RegHelper.registerParticle(ImmersiveWeathering.res(name));
    }

    public static final Supplier<SimpleParticleType> OAK_LEAF = registerParticle("oak_leaf");
    public static final Supplier<SimpleParticleType> BIRCH_LEAF = registerParticle("birch_leaf");
    public static final Supplier<SimpleParticleType> SPRUCE_LEAF = registerParticle("spruce_leaf");
    public static final Supplier<SimpleParticleType> JUNGLE_LEAF = registerParticle("jungle_leaf");
    public static final Supplier<SimpleParticleType> ACACIA_LEAF = registerParticle("acacia_leaf");
    public static final Supplier<SimpleParticleType> DARK_OAK_LEAF = registerParticle("dark_oak_leaf");
    public static final Supplier<SimpleParticleType> AZALEA_LEAF = registerParticle("azalea_leaf");
    public static final Supplier<SimpleParticleType> AZALEA_FLOWER = registerParticle("azalea_flower");

    public static final Supplier<SimpleParticleType> MULCH = registerParticle("mulch");
    public static final Supplier<SimpleParticleType> NULCH = registerParticle("nulch");

    public static final Supplier<SimpleParticleType> SCRAPE_RUST = registerParticle("scrape_rust");

    //ash

    public static final Supplier<SimpleParticleType> EMBER = registerParticle("ember");
    public static final Supplier<SimpleParticleType> SOOT = registerParticle("soot");
    public static final Supplier<SimpleParticleType> EMBERSPARK = registerParticle("emberspark");

    //bark

    public static final Supplier<SimpleParticleType> OAK_BARK = registerParticle("oak_bark");
    public static final Supplier<SimpleParticleType> BIRCH_BARK = registerParticle("birch_bark");
    public static final Supplier<SimpleParticleType> SPRUCE_BARK = registerParticle("spruce_bark");
    public static final Supplier<SimpleParticleType> JUNGLE_BARK = registerParticle("jungle_bark");
    public static final Supplier<SimpleParticleType> ACACIA_BARK = registerParticle("acacia_bark");
    public static final Supplier<SimpleParticleType> DARK_OAK_BARK = registerParticle("dark_oak_bark");
    public static final Supplier<SimpleParticleType> NETHER_SCALE = registerParticle("nether_scale");

    public static final Supplier<SimpleParticleType> MOSS = registerParticle("moss");

    //custom flower crowns

    //pride particles
    public static final Supplier<SimpleParticleType> FLOWER_ACE = registerParticle("flower_ace");
    public static final Supplier<SimpleParticleType> FLOWER_ARO = registerParticle("flower_aro");
    public static final Supplier<SimpleParticleType> FLOWER_BI = registerParticle("flower_bi");
    public static final Supplier<SimpleParticleType> FLOWER_ENBY = registerParticle("flower_enby");
    public static final Supplier<SimpleParticleType> FLOWER_GAY = registerParticle("flower_gay");
    public static final Supplier<SimpleParticleType> FLOWER_LESBIAN = registerParticle("flower_lesbian");
    public static final Supplier<SimpleParticleType> FLOWER_RAINBOW = registerParticle("flower_rainbow");
    public static final Supplier<SimpleParticleType> FLOWER_TRANS = registerParticle("flower_trans");

    //supporter particles
    public static final Supplier<SimpleParticleType> FLOWER_FLAX = registerParticle("flower_flax");

    //dev and gift particles
    public static final Supplier<SimpleParticleType> FLOWER_BEE = registerParticle("flower_bee");
    public static final Supplier<SimpleParticleType> FLOWER_JAR = registerParticle("flower_jar");


}
