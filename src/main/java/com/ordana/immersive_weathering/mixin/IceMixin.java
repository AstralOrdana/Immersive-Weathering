package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(IceBlock.class)
abstract public class IceMixin extends Block {

    public IceMixin(Settings settings) {
        super(settings);
    }

    //TODO: is day is broken on client side
    private boolean canMelt(BlockState state, World level, BlockPos pos) {
        if(ImmersiveWeathering.getConfig().fireAndIceConfig.naturalIceMelt) {
            return level.getDimension().isUltrawarm() || (level.getBiome(pos).value().isHot(pos) && level.isDay()) ||
                    (level.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(level, pos));

        }
        return false;
    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, Random random) {
        if (random.nextInt(25) == 1) {
            if (this.canMelt(state, level, pos) || level.isDay()) {

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
