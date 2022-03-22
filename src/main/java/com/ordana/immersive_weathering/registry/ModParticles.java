package com.ordana.immersive_weathering.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.logging.Level;

public class ModParticles {

    public static final DefaultParticleType EMBER = FabricParticleTypes.simple();
    public static final DefaultParticleType SOOT = FabricParticleTypes.simple();

    public static final DefaultParticleType OAK_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType BIRCH_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType SPRUCE_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType JUNGLE_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType ACACIA_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType DARK_OAK_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType AZALEA_LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType AZALEA_FLOWER = FabricParticleTypes.simple();

    public static final DefaultParticleType MULCH = FabricParticleTypes.simple();
    public static final DefaultParticleType NULCH = FabricParticleTypes.simple();

    public static final DefaultParticleType SCRAPE_RUST = FabricParticleTypes.simple();

    public static void spawnParticlesOnBlockFaces(World world, BlockPos pos, ParticleEffect particleEffect, UniformIntProvider uniformInt) {
        for(Direction direction : Direction.values()) {
            int i = uniformInt.get(world.random);

            for(int j = 0; j < i; ++j) {
                spawnParticleOnFace(world, pos, direction, particleEffect);
            }
        }

    }

    public static void spawnParticleOnFace(World world, BlockPos blockPos, Direction direction, ParticleEffect particleEffect) {
        Vec3d vec3 = Vec3d.ofCenter(blockPos);
        int i = direction.getOffsetX();
        int j = direction.getOffsetY();
        int k = direction.getOffsetZ();
        double d0 = vec3.x + (i == 0 ? MathHelper.nextDouble(world.random, -0.5D, 0.5D) : (double)i * 0.55D);
        double d1 = vec3.y + (j == 0 ? MathHelper.nextDouble(world.random, -0.5D, 0.5D) : (double)j * 0.55D);
        double d2 = vec3.z + (k == 0 ? MathHelper.nextDouble(world.random, -0.5D, 0.5D) : (double)k * 0.55D);
        world.addParticle(particleEffect, d0, d1, d2, 0, 0, 0);
    }

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "ember"), EMBER);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "soot"), SOOT);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "oak_leaf"), OAK_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "birch_leaf"), BIRCH_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "spruce_leaf"), SPRUCE_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "jungle_leaf"), JUNGLE_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "acacia_leaf"), ACACIA_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "dark_oak_leaf"), DARK_OAK_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "azalea_leaf"), AZALEA_LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "azalea_flower"), AZALEA_FLOWER);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "mulch"), MULCH);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "nulch"), NULCH);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier("immersive_weathering", "scrape_rust"), SCRAPE_RUST);
    }
}