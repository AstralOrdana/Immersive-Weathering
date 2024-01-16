package com.ordana.immersive_weathering.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;


public class EmberParticle extends TextureSheetParticle {

    EmberParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.setSize(0.005F, 0.005F);

        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 1F;
        this.lifetime = (int)(10.0D + (Mth.randomBetween(level.random, 0.1f, 0.2f)));
        this.hasPhysics = false;
        this.friction = 0.0F;
        this.gravity = -0.005F;



    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public record EmberFactory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientlevel, double d, double e, double f, double g, double h, double i) {
            return new EmberParticle(clientlevel, this.spriteProvider, d, e, f, 0.0D, 0.0D, 0.0D);
        }
    }


}

