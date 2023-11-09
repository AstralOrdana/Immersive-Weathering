package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.util.TemperatureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IceBlock.class)
public abstract class IceMixin extends Block {

    protected IceMixin(Properties settings) {
        super(settings);
    }

    //TODO: is day is broken on client side
    private boolean canMelt(BlockState state, Level level, BlockPos pos) {
        return level.dimensionType().ultraWarm() || (!level.getBiome(pos).value().coldEnoughToSnow(pos) && level.isDay()) ||
                (level.getBrightness(LightLayer.BLOCK, pos) > 11 - state.getLightBlock(level, pos));

    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(25) == 1) {
            if (this.canMelt(state, level, pos)) {

                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = pos.getX() + random.nextDouble();
                    double d1 = pos.getY() - 0.05D;
                    double d2 = pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}