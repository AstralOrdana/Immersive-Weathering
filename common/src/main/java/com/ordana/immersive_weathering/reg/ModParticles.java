package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.platform.RegistryPlatform;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticles {

    public static void init(){}

    public static final Supplier<SimpleParticleType> OAK_LEAF = RegistryPlatform.registerParticle("oak_leaf");
    public static final Supplier<SimpleParticleType> BIRCH_LEAF = RegistryPlatform.registerParticle("birch_leaf");
    public static final Supplier<SimpleParticleType> SPRUCE_LEAF = RegistryPlatform.registerParticle("spruce_leaf");
    public static final Supplier<SimpleParticleType> JUNGLE_LEAF = RegistryPlatform.registerParticle("jungle_leaf");
    public static final Supplier<SimpleParticleType> ACACIA_LEAF = RegistryPlatform.registerParticle("acacia_leaf");
    public static final Supplier<SimpleParticleType> DARK_OAK_LEAF = RegistryPlatform.registerParticle("dark_oak_leaf");
    public static final Supplier<SimpleParticleType> AZALEA_LEAF = RegistryPlatform.registerParticle("azalea_leaf");
    public static final Supplier<SimpleParticleType> AZALEA_FLOWER = RegistryPlatform.registerParticle("azalea_flower");

    public static final Supplier<SimpleParticleType> MULCH = RegistryPlatform.registerParticle("mulch");
    public static final Supplier<SimpleParticleType> NULCH = RegistryPlatform.registerParticle("nulch");

    public static final Supplier<SimpleParticleType> SCRAPE_RUST = RegistryPlatform.registerParticle("scrape_rust");

    //ash

    public static final Supplier<SimpleParticleType> EMBER = RegistryPlatform.registerParticle("ember");
    public static final Supplier<SimpleParticleType> SOOT = RegistryPlatform.registerParticle("soot");
    public static final Supplier<SimpleParticleType> EMBERSPARK = RegistryPlatform.registerParticle("emberspark");

    //bark

    public static final Supplier<SimpleParticleType> OAK_BARK = RegistryPlatform.registerParticle("oak_bark");
    public static final Supplier<SimpleParticleType> BIRCH_BARK = RegistryPlatform.registerParticle("birch_bark");
    public static final Supplier<SimpleParticleType> SPRUCE_BARK = RegistryPlatform.registerParticle("spruce_bark");
    public static final Supplier<SimpleParticleType> JUNGLE_BARK = RegistryPlatform.registerParticle("jungle_bark");
    public static final Supplier<SimpleParticleType> ACACIA_BARK = RegistryPlatform.registerParticle("acacia_bark");
    public static final Supplier<SimpleParticleType> DARK_OAK_BARK = RegistryPlatform.registerParticle("dark_oak_bark");
    public static final Supplier<SimpleParticleType> NETHER_SCALE = RegistryPlatform.registerParticle("nether_scale");

    public static final Supplier<SimpleParticleType> MOSS = RegistryPlatform.registerParticle("moss");

    //custom flower crowns

 //   public static final Supplier<SimpleParticleType> CROWN_BEE = RegistryPlatform.registerParticle("crown_bee");


}
