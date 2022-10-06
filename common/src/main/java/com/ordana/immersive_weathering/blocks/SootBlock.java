package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.reg.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.util.RandomSource;

public class SootBlock extends MultifaceBlock {

    public SootBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (world.isClientSide) {
                RandomSource random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ModParticles.SOOT.get(),
                            entity.getX() + Mth.randomBetween(random, -0.2f, 0.2f),
                            entity.getY() + 0.125,
                            entity.getZ() + Mth.randomBetween(random, -0.2f, 0.2f),
                            Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f,
                            0.05D,
                            Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f);

                }
            }
        }
    }
}
