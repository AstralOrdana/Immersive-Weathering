package com.ordana.immersive_weathering.registry.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

@Environment(EnvType.CLIENT)
public class LeafParticle extends TextureSheetParticle {
    private final float rotationSpeed;

        LeafParticle(ClientLevel world, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(spriteProvider);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.4F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D - 0.2D));
        this.hasPhysics = true;
        this.friction = 1.0F;
        this.gravity = 1.0F;
        this.rotationSpeed = ((float)Math.random() - 0.5F) * 0.1F;
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

