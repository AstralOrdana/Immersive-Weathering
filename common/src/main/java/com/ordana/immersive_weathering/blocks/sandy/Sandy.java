package com.ordana.immersive_weathering.blocks.sandy;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public interface Sandy {

    default void animateTick (BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double d0 = (double) pos.getX() + random.nextDouble();
                double d1 = (double) pos.getY() - 0.05D;
                double d2 = (double) pos.getZ() + random.nextDouble();
                if (state.getValue(ModBlockProperties.SANDINESS) == 0 && random.nextInt(10) == 1) {
                    level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
                else level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
