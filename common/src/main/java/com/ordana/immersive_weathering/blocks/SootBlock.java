package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.reg.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class SootBlock extends MultifaceBlock {
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public SootBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (level.isClientSide) {
                RandomSource random = level.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    level.addParticle(ModParticles.SOOT.get(),
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
