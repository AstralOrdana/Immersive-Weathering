package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.IcicleBlock;
import com.ordana.immersive_weathering.common.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
abstract public class IceMixin extends Block {
    @Shadow
    protected abstract void melt(BlockState p_54169_, Level p_54170_, BlockPos p_54171_);

    public IceMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.below();
        var biome = world.getBiome(pos).value();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).is(Blocks.AIR)) {

                //to form we need hot weather in a cold biome or water above & cold biome
                if (biome.coldEnoughToSnow(pos)) {
                    if (world.getFluidState(pos.above()).is(FluidTags.WATER) || (world.isDay() && !world.isRaining() && !world.isThundering())) {
                        world.setBlockAndUpdate(icePos, ModBlocks.ICICLE.get().defaultBlockState()
                                .setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN)
                                .setValue(IcicleBlock.THICKNESS, DripstoneThickness.TIP));
                    }
                }
            }
        }

        if (world.dimensionType().ultraWarm()) {
            world.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.3F, 2.9F + (random.nextFloat() - random.nextFloat()) * 0.6F);

            float i = pos.getX() + 0.5f;
            float j = pos.getY() + 0.5f;
            float k = pos.getZ() + 0.5f;
            world.sendParticles(ParticleTypes.LARGE_SMOKE, i, j, k, 12, 0.2D, 0.2D, 0.2D, 0);
            this.melt(state, world, pos);
        } else if (biome.shouldSnowGolemBurn(pos) && world.isDay()) {
            this.melt(state, world, pos);
        }
    }

    //TODO: is day is broken on client side
    private boolean canMelt(BlockState state, Level level, BlockPos pos) {
        return level.dimensionType().ultraWarm() || (level.getBiome(pos).value().shouldSnowGolemBurn(pos) && level.isDay()) ||
                (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos));

    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (random.nextInt(25) == 1) {
            if (this.canMelt(state, level, pos)) {

                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
