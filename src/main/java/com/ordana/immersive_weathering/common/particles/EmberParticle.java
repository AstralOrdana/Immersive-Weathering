package com.ordana.immersive_weathering.common.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;


public class EmberParticle extends TextureSheetParticle {

    EmberParticle(ClientLevel world, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y - 0.125D, z, velocityX, velocityY, velocityZ);
        this.setSize(0.001F, 0.001F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.friction = 1.0F;
        this.gravity = 0.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public record EmberFactory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            double j = (double) clientWorld.random.nextFloat() * -1.9D * (double) clientWorld.random.nextFloat() * 0.1D;

            return new EmberParticle(clientWorld, this.spriteProvider, d, e, f, 0.0D, j, 0.0D);
        }
    }
}

