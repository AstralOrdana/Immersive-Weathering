package com.ordana.immersive_weathering.registry.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class EmberParticle extends SpriteBillboardParticle {

    EmberParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 1F;
        this.maxAge = (int)(10.0D + (MathHelper.nextBetween(world.random, 0.1f, 0.2f)));
        this.collidesWithWorld = false;
        this.velocityMultiplier = 0.0F;
        this.gravityStrength = 0.0F;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public record EmberFactory(
            SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            EmberParticle emberParticle = new EmberParticle(clientWorld, this.spriteProvider, d, e, f, 0.0D, 0.0D, 0.0D);
            emberParticle.setBoundingBoxSpacing(0.2F, 0.001F);
            return emberParticle;
        }
    }
}

