package com.ordana.immersive_weathering.registry.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class LeafParticle extends SpriteBillboardParticle {
    private final float rotationSpeed;

        LeafParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.setSprite(spriteProvider);
        this.scale *= this.random.nextFloat() * 0.6F + 0.4F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D - 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 1.0F;
        this.rotationSpeed = ((float)Math.random() - 0.5F) * 0.1F;
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.prevAngle = this.angle;
            this.angle += 3.1415927F * this.rotationSpeed * 2.0F;
            if (this.onGround) {
                this.prevAngle = this.angle = 0.0F;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.velocityY -= 0.003000000026077032D;
            this.velocityY = Math.max(this.velocityY, -0.14000000059604645D);
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public record LeafFactory(
            SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            LeafParticle leafParticle = new LeafParticle(clientWorld, this.spriteProvider, d, e, f, 0.0D, -1.0D, 0.0D);
            leafParticle.setBoundingBoxSpacing(0.001F, 0.001F);
            return leafParticle;
        }
    }
}

