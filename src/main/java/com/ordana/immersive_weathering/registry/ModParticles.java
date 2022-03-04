package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImmersiveWeathering.MOD_ID);

    public static final RegistryObject<SimpleParticleType> EMBER = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> SOOT = FabricParticleTypes.simple();

    public static final RegistryObject<SimpleParticleType> OAK_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> BIRCH_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> SPRUCE_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> JUNGLE_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> ACACIA_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> DARK_OAK_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> AZALEA_LEAF = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> AZALEA_FLOWER = FabricParticleTypes.simple();

    public static final RegistryObject<SimpleParticleType> MULCH = FabricParticleTypes.simple();
    public static final RegistryObject<SimpleParticleType> NULCH = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "ember"), EMBER);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "soot"), SOOT);

        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "oak_leaf"), OAK_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "birch_leaf"), BIRCH_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "spruce_leaf"), SPRUCE_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "jungle_leaf"), JUNGLE_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "acacia_leaf"), ACACIA_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "dark_oak_leaf"), DARK_OAK_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "azalea_leaf"), AZALEA_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "azalea_flower"), AZALEA_FLOWER);

        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "mulch"), MULCH);
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation("immersive_weathering", "nulch"), NULCH);
    }
}