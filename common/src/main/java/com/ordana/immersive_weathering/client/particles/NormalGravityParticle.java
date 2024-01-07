package com.ordana.immersive_weathering.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class NormalGravityParticle extends TextureSheetParticle {

    protected NormalGravityParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.setSize(1.0F, 1.0F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.4F;
        this.gravity = 1.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public record Particle(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        @Override
        public NormalGravityParticle createParticle(SimpleParticleType particleType, ClientLevel clientlevel, double x, double y, double z, double g, double color, double i) {
            return new NormalGravityParticle(clientlevel, this.spriteProvider, x, y, z);
        }
    }
}

