package com.ordana.immersive_weathering.reg;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModParticles {

    public static void init() {
        //BlockSetAPI.addDynamicRegistration(ModParticles::registerLeafParticles, LeavesType.class, Registries.PARTICLE_TYPE);
    }

    public static Supplier<SimpleParticleType> registerParticle(String name) {
        return RegHelper.registerParticle(ImmersiveWeathering.res(name));
    }

    public static Map<LeavesType, SimpleParticleType> FALLING_LEAVES_PARTICLES = new LinkedHashMap<>();
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
    public static final Supplier<SimpleParticleType> DARK_OAK_BARK = registerParticle("dark_oak_bark");
    public static final Supplier<SimpleParticleType> ACACIA_BARK = registerParticle("acacia_bark");
    public static final Supplier<SimpleParticleType> MANGROVE_BARK = registerParticle("mangrove_bark");
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


    private static void registerLeafParticles(Registrator<ParticleType<?>> event, Collection<LeavesType> leavesTypes) {
        for (LeavesType type : leavesTypes) {
            var p = PlatHelper.newParticle();
            //if (type.getTypeName().equals("flowering_azalea")) {
            //    p = (SimpleParticleType) Registry.PARTICLE_TYPE.get(ImmersiveWeathering.res("azalea_leaf"));
            //} else {
                event.register(ImmersiveWeathering.res(type.getVariantId("leaf", false)), p);
                FALLING_LEAVES_PARTICLES.put(type, p);
                type.addChild("immersive_weathering:leaf_particle", p);
            //}
        }
    }
}
