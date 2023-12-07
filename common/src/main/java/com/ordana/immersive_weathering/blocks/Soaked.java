package com.ordana.immersive_weathering.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface Soaked {

    BooleanProperty SOAKED = ModBlockProperties.SOAKED;

    default InteractionResult soakOrDrain(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == Items.WATER_BUCKET && !state.getValue(ModBlockProperties.SOAKED)) {
            level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
            if (player instanceof ServerPlayer) {
                ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
                player.setItemInHand(hand, itemStack2);
                level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SOAKED, true));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (stack.getItem() == Items.BUCKET && state.getValue(ModBlockProperties.SOAKED)) {
            level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
            if (player instanceof ServerPlayer) {
                ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.WATER_BUCKET.getDefaultInstance());
                player.setItemInHand(hand, itemStack2);
                level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SOAKED, false));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
