package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImmersiveWeathering.MOD_ID);

    public static RegistryObject<SimpleParticleType> regParticle(String name) {
        return PARTICLES.register(name, () -> new SimpleParticleType(true));
    }

    public static final RegistryObject<SimpleParticleType> EMBER = regParticle("ember");
    public static final RegistryObject<SimpleParticleType> SOOT = regParticle("soot");

    public static final RegistryObject<SimpleParticleType> OAK_LEAF = regParticle("oak_leaf");
    public static final RegistryObject<SimpleParticleType> BIRCH_LEAF = regParticle("birch_leaf");
    public static final RegistryObject<SimpleParticleType> SPRUCE_LEAF = regParticle("spruce_leaf");
    public static final RegistryObject<SimpleParticleType> JUNGLE_LEAF = regParticle("jungle_leaf");
    public static final RegistryObject<SimpleParticleType> ACACIA_LEAF = regParticle("acacia_leaf");
    public static final RegistryObject<SimpleParticleType> DARK_OAK_LEAF = regParticle("dark_oak_leaf");
    public static final RegistryObject<SimpleParticleType> AZALEA_LEAF = regParticle("azalea_leaf");
    public static final RegistryObject<SimpleParticleType> AZALEA_FLOWER = regParticle("azalea_flower");

    public static final RegistryObject<SimpleParticleType> MULCH = regParticle("mulch");
    public static final RegistryObject<SimpleParticleType> NULCH = regParticle("nulch");

}