package com.ordana.immersive_weathering.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class LeafParticle extends TextureSheetParticle {
    private final float rotationSpeed;

    LeafParticle(ClientLevel level, SpriteSet spriteProvider, double x, double y, double z,
                 double velocityX, double velocityY, double velocityZ, int color) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.setSize(1.0F, 1.0F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.4F;
        this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = true;
        this.friction = 1.0F;
        this.gravity = 1.0F;
        this.rotationSpeed = ((float) Math.random() - 0.5F) * 0.1F;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.oRoll = this.roll;
            this.roll += 3.1415927F * this.rotationSpeed * 2.0F;
            if (this.onGround) {
                this.oRoll = this.roll = 0.0F;
            }

            this.move(this.xd, this.yd, this.zd);
            this.yd -= 0.003000000026077032D;
            this.yd = Math.max(this.yd, -0.14000000059604645D);
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record ColoredLeafParticle(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel clientlevel, double x, double y, double z, double g, double color, double i) {

            return new LeafParticle(clientlevel, this.spriteProvider, x, y, z, 0.0D, -1D, 0.0D,
                    (int) color);
        }
    }

    public record SimpleLeafParticle(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel clientlevel, double x, double y, double z, double vel, double color, double i) {
            if (vel == 0) vel = -3;
            return new LeafParticle(clientlevel, this.spriteProvider, x, y, z, 0.0D, vel, 0.0D,
                    -1);
        }
    }
}

