package com.ordana.immersive_weathering.client;

import com.ordana.immersive_weathering.reg.LeafPilesRegistry;
import com.ordana.immersive_weathering.utils.CommonUtils;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ParticleHelper {

    public static void spawnParticlesOnBlockFaces(Level p_144963_, BlockPos p_144964_, ParticleOptions p_144965_, UniformInt p_144966_) {
        for(Direction direction : Direction.values()) {
            int i = p_144966_.sample(p_144963_.random);

            for(int j = 0; j < i; ++j) {
                spawnParticleOnFace(p_144963_, p_144964_, direction, p_144965_);
            }
        }

    }

    //TODO: replace this with the function I made for sup
    public static void spawnParticleOnFace(Level p_144958_, BlockPos p_144959_, Direction p_144960_, ParticleOptions p_144961_) {
        Vec3 vec3 = Vec3.atCenterOf(p_144959_);
        int i = p_144960_.getStepX();
        int j = p_144960_.getStepY();
        int k = p_144960_.getStepZ();
        double d0 = vec3.x + (i == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)i * 0.55D);
        double d1 = vec3.y + (j == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)j * 0.55D);
        double d2 = vec3.z + (k == 0 ? Mth.nextDouble(p_144958_.random, -0.5D, 0.5D) : (double)k * 0.55D);
        float v = CommonUtils.getID(p_144961_.getType()).getNamespace().equals("immersive_weathering") ? -2:0;
        //TODO: generalize this and redo leaf particle physics
        p_144958_.addParticle(p_144961_, d0, d1, d2, v, 0, 0);
    }


    public static void spawnLeavesParticles(BlockState state, Level world, BlockPos pos, Random random) {
        var leafParticle = LeafPilesRegistry.getFallenLeafParticle(state).orElse(null);
        if (leafParticle == null) return;
        //TODO: readd
        /*
        if(ImmersiveWeathering1.getConfig().leavesConfig.fallingLeafParticles && state.is(ModTags.VANILLA_LEAVES)) {
            if (random.nextInt(32) == 0 && !world.getBlockState(pos.below()).isRedstoneConductor(world, pos)) {
                if (!(world.getBlockState(pos.below()).getBlock() instanceof LeavesBlock) && state.is(ModTags.VANILLA_LEAVES)) {
                    ParticleUtils.spawnParticlesOnBlockFaces(world, pos, leafParticle, UniformInt.of(0, 1));
                }
            }
        }*/
    }
}
