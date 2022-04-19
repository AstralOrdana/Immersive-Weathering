package com.ordana.immersive_weathering.common;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

    public static final RegistryObject<SimpleParticleType> SCRAPE_RUST = regParticle("scrape_rust");


    public static void spawnParticlesOnBlockFaces(Level p_144963_, BlockPos p_144964_, ParticleOptions p_144965_, UniformInt p_144966_) {
        for(Direction direction : Direction.values()) {
            int i = p_144966_.sample(p_144963_.random);

            for(int j = 0; j < i; ++j) {
                spawnParticleOnFace(p_144963_, p_144964_, direction, p_144965_);
            }
        }

    }

    //TODO: replace this with the function I made for sup
    public static void spawnParticleOnFace(Level p_144958_, BlockPos p_144959_, Direction p_144960_, ParticleOptions p_144961_) {
        Vec3 vec3 = Vec3.atCenterOf(p_144959_);
        int i = p_144960_.getStepX();
        int j = p_144960_.getStepY();
        int k = p_144960_.getStepZ();
        double d0 = vec3.x + (i == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)i * 0.55D);
        double d1 = vec3.y + (j == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)j * 0.55D);
        double d2 = vec3.z + (k == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)k * 0.55D);
        float v = p_144961_.getType().getRegistryName().getNamespace().equals("immersive_weathering") ? -2:0;
        //TODO: generalize this and redo leaf particle physics
        p_144958_.addParticle(p_144961_, d0, d1, d2, v, 0, 0);
    }
}