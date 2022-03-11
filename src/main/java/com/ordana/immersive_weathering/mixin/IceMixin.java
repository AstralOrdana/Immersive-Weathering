package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
abstract public class IceMixin extends Block {
    @Shadow
    protected abstract void melt(BlockState p_54169_, World p_54170_, BlockPos p_54171_);

    public IceMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.down();
        var biome = world.getBiome(pos).value();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).isOf(Blocks.AIR)) {

                //to form we need hot weather in a cold biome or water above & cold biome
                if (biome.isCold(pos)) {
                    if (world.getFluidState(pos.up()).isIn(FluidTags.WATER) || (world.isDay() && !world.isRaining() && !world.isThundering())) {
                        world.setBlockState(icePos, ModBlocks.ICICLE.getDefaultState()
                                .with(Properties.VERTICAL_DIRECTION, Direction.DOWN)
                                .with(IcicleBlock.THICKNESS, Thickness.TIP));
                    }
                }
            }
        }

        if (world.getDimension().isUltrawarm()) {
            world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.3F, 2.9F + (random.nextFloat() - random.nextFloat()) * 0.6F);

            float i = pos.getX() + 0.5f;
            float j = pos.getY() + 0.5f;
            float k = pos.getZ() + 0.5f;
            world.spawnParticles(ParticleTypes.LARGE_SMOKE, i, j, k, 12, 0.2D, 0.2D, 0.2D, 0);
            this.melt(state, world, pos);
        } else if (biome.isHot(pos) && world.isDay()) {
            this.melt(state, world, pos);
        }
    }

    //TODO: is day is broken on client side
    private boolean canMelt(BlockState state, World level, BlockPos pos) {
        return level.getDimension().isUltrawarm() || (level.getBiome(pos).value().isHot(pos) && level.isDay()) ||
                (level.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(level, pos));

    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, Random random) {
        if (random.nextInt(25) == 1) {
            if (this.canMelt(state, level, pos)) {

                BlockPos blockpos = pos.down();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
