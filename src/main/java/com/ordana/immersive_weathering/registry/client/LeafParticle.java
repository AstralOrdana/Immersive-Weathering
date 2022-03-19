package com.ordana.immersive_weathering.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.particle.*;
import com.ordana.immersive_weathering.registry.ModParticles;
import net.minecraft.client.texture.NativeImage;
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
        this.velocityMultiplier = 0.1F;
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
            this.velocityY -= 0.09D;
            this.velocityY = Math.max(this.velocityY, -0.14000000059604645D);
        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public record LeafFactory(
            SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        @Override
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y, double z, double g, double color, double i) {
            //TODO: idk why the given speeds arent used
            var p = new LeafParticle(clientWorld, this.spriteProvider, x, y, z, 0.0D, 0.1D, 0.0D);


            if (particleType == ModParticles.BIRCH_LEAF) {
                color = FoliageColors.getBirchColor();
            } else if (particleType == ModParticles.SPRUCE_LEAF) {
                color = FoliageColors.getSpruceColor();
            }else if(particleType == ModParticles.AZALEA_FLOWER || particleType == ModParticles.AZALEA_LEAF || particleType == ModParticles.MULCH || particleType == ModParticles.NULCH || particleType == ModParticles.SOOT){
                color = -1;
            }

            p.setColor(NativeImage.getBlue((int) color)/255f,
                    NativeImage.getGreen((int) color)/255f,
                    NativeImage.getRed((int) color)/255f);
            return p;
        }
    }
}

