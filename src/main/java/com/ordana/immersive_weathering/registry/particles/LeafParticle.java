package com.ordana.immersive_weathering.registry.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.*;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class LeafParticle extends SpriteBillboardParticle {
    private final float rotationSpeed;

    LeafParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ,
                 int color) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.setSprite(spriteProvider);
        this.getSize(0.0F);
        this.scale *= this.random.nextFloat() * 0.2F + 1F;
        this.maxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
        this.collidesWithWorld = true;
        this.velocityMultiplier = 1.0F;
        this.gravityStrength = 1.0F;
        this.rotationSpeed = ((float)Math.random() - 0.5F) * 0.1F;
        this.setColor(NativeImage.getBlue((int) color) / 255f,
                NativeImage.getGreen((int) color) / 255f,
                NativeImage.getRed((int) color) / 255f);
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

    public record ColoredLeafParticle(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y, double z, double dx, double dy, double dz) {
            return new LeafParticle(clientWorld, this.spriteProvider, x, y, z, 0.0D, -1D, 0.0D,
                    BiomeColors.getFoliageColor(clientWorld, new BlockPos(x,y,z)));
        }
    }

    public record SpruceLeafParticle(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y, double z, double g, double color, double i) {
            return new LeafParticle(clientWorld, this.spriteProvider, x, y, z, 0.0D, -1D, 0.0D,
                    FoliageColors.getSpruceColor());
        }
    }

    public record BirchLeafParticle(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y, double z, double g, double color, double i) {
            return new LeafParticle(clientWorld, this.spriteProvider, x, y, z, 0.0D, -1D, 0.0D,
                    FoliageColors.getBirchColor());
        }
    }

    public record SimpleLeafParticle(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y, double z, double g, double color, double i) {

            return new LeafParticle(clientWorld, this.spriteProvider, x, y, z, 0.0D, -1D, 0.0D,
                    -1);
        }
    }
}
